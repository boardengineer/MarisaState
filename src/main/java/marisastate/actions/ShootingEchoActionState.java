package marisastate.actions;

import ThMod.action.ShootingEchoAction;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import savestate.CardState;
import savestate.actions.CurrentActionState;

public class ShootingEchoActionState implements CurrentActionState {
    private final CardState card;

    public ShootingEchoActionState(AbstractGameAction action) {
        AbstractCard actionCard = ReflectionHacks.getPrivate(action, ShootingEchoAction.class, "card");
        this.card = new CardState(actionCard);
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        ShootingEchoAction result = new ShootingEchoAction(card.loadCard());

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
