package marisastate.patches;

import ThMod.action.TreasureHunterDamageAction;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class TreasureHunterDamagePatch {
    @SpirePatch(
            clz = TreasureHunterDamageAction.class,
            method = "update"
    )
    public static class NoMetricsReadingPatch {
        @SpirePrefixPatch
        public static SpireReturn update(TreasureHunterDamageAction action) {

            float duration = ReflectionHacks
                    .getPrivate(action, AbstractGameAction.class, "duration");
            DamageInfo info = ReflectionHacks
                    .getPrivate(action, TreasureHunterDamageAction.class, "info");
            boolean reward;
            AbstractRelic.RelicTier tier = ReflectionHacks
                    .getPrivate(action, TreasureHunterDamageAction.class, "tier");

            if (duration == 0.1F && action.target != null) {
                AbstractDungeon.effectList
                        .add(new FlashAtkImgEffect(action.target.hb.cX, action.target.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                action.target.damage(info);
                AbstractMonster mon = (AbstractMonster) action.target;
                AbstractRoom curRoom = AbstractDungeon.getCurrRoom();

                reward = curRoom.eliteTrigger || curRoom instanceof MonsterRoomBoss;

                /*
                This code used last combat metrics.

                reward = curRoom.eliteTrigger || curRoom instanceof MonsterRoomBoss || AbstractDungeon.lastCombatMetricKey
                        .equals("Mind Bloom Boss Battle") || AbstractDungeon.lastCombatMetricKey
                        .equals("3 Sentries") || AbstractDungeon.lastCombatMetricKey
                        .equals("Gremlin Nob") || AbstractDungeon.lastCombatMetricKey
                        .equals("Lagavulin Event");
                 */

                if (!(curRoom instanceof MonsterRoomBoss)) {
                    while (tier == AbstractRelic.RelicTier.RARE) {
                        tier = AbstractDungeon.returnRandomRelicTier();
                    }
                }

                if (reward) {
                    if ((action.target.isDying || action.target.currentHealth <= 0) && !action.target.halfDead && !action.target
                            .hasPower("Minion")) {
                        AbstractDungeon.getCurrRoom().addRelicToRewards(tier);
                    }
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
            }

            ReflectionHacks.privateMethod(AbstractGameAction.class, "tickDuration").invoke(action);

            return SpireReturn.Return(null);
        }
    }
}
