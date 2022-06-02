package marisastate.actions;

import ThMod.action.DiscToHandRandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import savestate.actions.ActionState;

public class DiscToHandRandActionState implements ActionState {
    @Override
    public AbstractGameAction loadAction() {
        return new DiscToHandRandAction();
    }
}
