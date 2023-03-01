package marisastate.patches;

import ThMod.ThMod;
import ThMod.action.BlazeAwayAction;
import ThMod.cards.Marisa.BlazeAway;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlazeAwayPatches {
    @SpirePatch(clz = BlazeAway.class, method = "use")
    public static class BetterBlazePatch {
        @SpirePrefixPatch
        public static SpireReturn betterUse(BlazeAway blazeAway, AbstractPlayer p, AbstractMonster monster) {
            AbstractCard lastAttack = ReflectionHacks
                    .getPrivateStatic(BlazeAway.class, "lastAttack");

            if (lastAttack != null) {
                ThMod.logger.info("BlazeAway : last attack :" + lastAttack.cardID);


                for (int i = 0; i < blazeAway.magicNumber; ++i) {
                    AbstractCard card = lastAttack.makeStatEquivalentCopy();
                    if (card.costForTurn >= 0) {
                        card.setCostForTurn(0);
                    }


                    AbstractDungeon.actionManager.addToBottom(new BlazeAwayAction(card));
                }
            }

            return SpireReturn.Return(null);
        }
    }
}
