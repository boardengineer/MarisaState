package marisastate.actions;

import ThMod.action.DrawDrawPileAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import savestate.actions.ActionState;

public class DrawDrawPileActionState implements ActionState {
    @Override
    public AbstractGameAction loadAction() {
        return new DrawDrawPileAction();
    }
}
