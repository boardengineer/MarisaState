package marisastate.patches;

import ThMod.ThMod;
import ThMod.action.PropBagAction;
import ThMod.powers.Marisa.PropBagPower;
import ThMod.relics.AmplifyWand;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.*;

import java.util.ArrayList;

// Prop bag does too much stuff in the power constructor making it very difficult to maneuver.
// Move more logic into the action to make it work better
public class PropBagPatches {
    @SpirePatch(clz = PropBagPower.class, method = SpirePatch.CONSTRUCTOR)
    public static class PropBagPowerPatch {
        @SpirePrefixPatch
        public static SpireReturn doLessInConstructor(PropBagPower propBagPower, AbstractCreature owner, AbstractRelic r) {
            propBagPower.name = PropBagPower.NAME;

            int IdOffset = ReflectionHacks.getPrivateStatic(PropBagPower.class, "IdOffset");
            propBagPower.ID = "PropBagPower" + IdOffset;

            propBagPower.owner = owner;
            ReflectionHacks.setPrivateStatic(PropBagPower.class, "IdOffset", IdOffset + 1);
            propBagPower.amount = -1;
            propBagPower.type = AbstractPower.PowerType.BUFF;
            propBagPower.img = new Texture("img/powers/diminish.png");

            ReflectionHacks.setPrivate(propBagPower, PropBagPower.class, "r", r);
            ReflectionHacks
                    .setPrivate(propBagPower, PropBagPower.class, "p", AbstractDungeon.player);
            ReflectionHacks
                    .setPrivate(propBagPower, PropBagPower.class, "rName", r.name);
            propBagPower.updateDescription();

            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(clz = PropBagAction.class, method = "update")
    public static class PropBagActionPatch {
        @SpirePrefixPatch
        public static SpireReturn doMoreInAction(PropBagAction action) {
            AbstractPlayer player = AbstractDungeon.player;
            ArrayList<AbstractRelic> relicPool = new ArrayList();
            if (!player.hasRelic("Meat on the Bone")) {
                AbstractRelic r = new MeatOnTheBone();
                relicPool.add(r);
            }

            if (!player.hasRelic("Mummified Hand")) {
                AbstractRelic r = new MummifiedHand();
                relicPool.add(r);
            }

            if (!player.hasRelic("Letter Opener")) {
                AbstractRelic r = new LetterOpener();
                relicPool.add(r);
            }

            if (!player.hasRelic("Shuriken")) {
                AbstractRelic r = new Shuriken();
                relicPool.add(r);
            }

            if (!player.hasRelic("Gremlin Horn")) {
                AbstractRelic r = new GremlinHorn();
                relicPool.add(r);
            }

            if (!player.hasRelic("Sundial")) {
                AbstractRelic r = new Sundial();
                relicPool.add(r);
            }

            if (!player.hasRelic("Mercury Hourglass")) {
                AbstractRelic r = new MercuryHourglass();
                relicPool.add(r);
            }

            if (!player.hasRelic("Ornamental Fan")) {
                AbstractRelic r = new OrnamentalFan();
                relicPool.add(r);
            }

            if (!player.hasRelic("Kunai")) {
                AbstractRelic r = new Kunai();
                relicPool.add(r);
            }

            if (!player.hasRelic("Blue Candle")) {
                AbstractRelic r = new BlueCandle();
                relicPool.add(r);
            }

            if (!player.hasRelic("AmplifyWand")) {
                AbstractRelic r = new AmplifyWand();
                relicPool.add(r);
            }

            if (relicPool.size() <= 0) {
                ThMod.logger.info("PropBagAction : No relic to give,returning");
                action.isDone = true;
            } else {
                AbstractRelic r;
                if (relicPool.size() == 1) {
                    obtainProp(player, relicPool.get(0));
                    action.isDone = true;
                } else {
                    relicPool.size();
                    int index = AbstractDungeon.miscRng.random(0, relicPool.size() - 1);
                    AbstractRelic relic = relicPool.get(index);
                    obtainProp(player, relic);
                    action.isDone = true;
                }
            }

            return SpireReturn.Return(null);
        }
    }

    static void obtainProp(AbstractPlayer player, AbstractRelic relic) {
        AbstractDungeon.getCurrRoom()
                       .spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, relic);
        relic.atBattleStart();

        AbstractDungeon.actionManager
                .addToBottom(new ApplyPowerAction(player, player, new PropBagPower(player, relic)));

    }
}
