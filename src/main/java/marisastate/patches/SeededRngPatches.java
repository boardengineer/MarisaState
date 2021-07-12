package marisastate.patches;

import ThMod.action.BlazeAwayAction;
import ThMod.action.DamageRandomEnemyAction;
import ThMod.action.PlayManaRampageCardAction;
import ThMod.action.UnstableBombAction;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// The Marisa mod sets random monsters to AbstractDungeon.getMonsters().getRandomMonster(true)
// which uses unseeded RNG. AbstractDungeon.getRandomMonster does
public class SeededRngPatches {
    @SpirePatch(
            clz = DamageRandomEnemyAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DamageRandomEnemyActionSeedRngPatch {
        @SpirePostfixPatch
        public static void setSeededRng(DamageRandomEnemyAction action, DamageInfo info, AbstractGameAction.AttackEffect effect) {
            action.target = AbstractDungeon.getRandomMonster();
        }
    }

    @SpirePatch(
            clz = BlazeAwayAction.class,
            method = "update"
    )
    public static class BlazeAwayActionSeedRngPatch {
        @SpirePrefixPatch
        public static SpireReturn setSeededRng(BlazeAwayAction action) {
            // TODO This doesn't work
            AbstractMonster target = AbstractDungeon.getRandomMonster();

            AbstractCard card = ReflectionHacks.getPrivate(action, BlazeAwayAction.class, "card");
            AbstractDungeon.player.limbo.group.add(card);
            card.current_x = (float) Settings.WIDTH / 2.0F;
            card.current_y = (float)Settings.HEIGHT / 2.0F;
            card.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            card.target_y = (float)Settings.HEIGHT / 2.0F;
            card.freeToPlayOnce = true;
            card.purgeOnUse = true;
            card.targetAngle = 0.0F;
            card.drawScale = 0.12F;
            card.applyPowers();
            AbstractDungeon.actionManager.currentAction = null;
            AbstractDungeon.actionManager.addToTop(action);
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, target));
            if (!Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }

            action.isDone = true;

            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz = PlayManaRampageCardAction.class,
            method = "update"
    )
    public static class PlayManaRampageCardActionSeedRngPatch {
        @SpireInsertPatch(loc = 25)
        public static void setSeededRng(PlayManaRampageCardAction action) {
            action.target = AbstractDungeon.getRandomMonster();
        }
    }

    @SpirePatch(
            clz = UnstableBombAction.class,
            method = "update"
    )
    public static class UnstableBombActionSeedRngPatch {
        @SpirePrefixPatch
        public static void setSeededRng(UnstableBombAction action) {
            action.target = AbstractDungeon.getRandomMonster();
        }
    }
}
