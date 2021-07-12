package marisastate.actions;

import ThMod.action.ShootingEchoAction;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import savestate.actions.CurrentActionState;
import savestate.fastobjects.actions.UpdateOnlyUseCardAction;

public class ShootingEchoActionState implements CurrentActionState {
    public ShootingEchoActionState(AbstractGameAction action) {
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        AbstractCard card = null;

        for (AbstractGameAction managerAction : AbstractDungeon.actionManager.actions) {
            if (managerAction instanceof UseCardAction) {
                card = ReflectionHacks
                        .getPrivate(managerAction, UseCardAction.class, "targetCard");
            } else if (managerAction instanceof UpdateOnlyUseCardAction) {
                card = ReflectionHacks
                        .getPrivate(managerAction, UpdateOnlyUseCardAction.class, "targetCard");
            }
        }

        ShootingEchoAction result = new ShootingEchoAction(card);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = ShootingEchoAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class NoDoubleExhaustActionPatch {
        public static void Postfix(ShootingEchoAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved && AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
