package marisastate.actions;

import ThMod.action.DiscardPileToHandAction;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import savestate.actions.ActionState;
import savestate.actions.CurrentActionState;

public class DiscardPileToHandActionState implements CurrentActionState, ActionState {
    private final int amount;

    public DiscardPileToHandActionState(AbstractGameAction action) {
        amount = action.amount;
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        DiscardPileToHandAction result = new DiscardPileToHandAction(amount);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = DiscardPileToHandAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(DiscardPileToHandAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID && AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }

    @Override
    public AbstractGameAction loadAction() {
        return new DiscardPileToHandAction(amount);
    }
}
