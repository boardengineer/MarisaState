package marisastate.actions;

import ThMod.action.RefreshHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import savestate.actions.ActionState;

public class RefreshHandActionState implements ActionState {
    @Override
    public AbstractGameAction loadAction() {
        return new RefreshHandAction();
    }
}
