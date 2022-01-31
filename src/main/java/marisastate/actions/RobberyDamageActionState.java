package marisastate.actions;

import ThMod.action.RobberyDamageAction;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import savestate.DamageInfoState;
import savestate.actions.ActionState;

public class RobberyDamageActionState implements ActionState {
    private final boolean amp;
    private final DamageInfoState info;
    private final int targetIndex;

    public RobberyDamageActionState(AbstractGameAction action) {
        amp = ReflectionHacks.getPrivate(action, RobberyDamageAction.class, "amp");
        DamageInfo actionInfo = ReflectionHacks
                .getPrivate(action, RobberyDamageAction.class, "info");
        info = new DamageInfoState(actionInfo);
        targetIndex = ActionState.indexForCreature(action.target);
    }

    @Override
    public AbstractGameAction loadAction() {
        return new RobberyDamageAction(ActionState.creatureForIndex(targetIndex), info
                .loadDamageInfo(), amp);
    }
}
