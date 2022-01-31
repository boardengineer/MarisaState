package marisastate.actions;

import ThMod.action.RefractionSparkAction;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import savestate.DamageInfoState;
import savestate.actions.ActionState;

public class RefractionSparkActionState implements ActionState {
    DamageInfoState info;
    int targetIndex;

    public RefractionSparkActionState(AbstractGameAction action) {
        targetIndex = ActionState.indexForCreature(action.target);

        DamageInfo actionDamage = ReflectionHacks
                .getPrivate(action, RefractionSparkAction.class, "info");
        info = new DamageInfoState(actionDamage);
    }

    @Override
    public AbstractGameAction loadAction() {
        return new RefractionSparkAction(ActionState.creatureForIndex(targetIndex), info
                .loadDamageInfo());
    }
}
