package marisastate.actions;

import ThMod.action.FairyDestrucCullingAction;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import savestate.actions.ActionState;

public class FairyDestrucCullingActionState implements ActionState {
    private final int threshold;

    public FairyDestrucCullingActionState(AbstractGameAction action) {
        threshold = ReflectionHacks
                .getPrivate(action, FairyDestrucCullingAction.class, "threshold");
    }

    @Override
    public AbstractGameAction loadAction() {
        return new FairyDestrucCullingAction(threshold);
    }
}
