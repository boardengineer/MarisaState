package marisastate.actions;

import ThMod.action.WasteBombAction;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import savestate.actions.ActionState;

public class WasteBombActionState implements ActionState {
    private final int damage;
    private final int num;
    private final int stacks;
    private final int targetIndex;

    public WasteBombActionState(AbstractGameAction action) {
        this.damage = ReflectionHacks.getPrivate(action, WasteBombAction.class, "damage");
        this.num = ReflectionHacks.getPrivate(action, WasteBombAction.class, "num");
        this.stacks = ReflectionHacks.getPrivate(action, WasteBombAction.class, "stacks");

        AbstractCreature target = ReflectionHacks
                .getPrivate(action, WasteBombAction.class, "target");
        this.targetIndex = ActionState.indexForCreature(target);
    }

    @Override
    public AbstractGameAction loadAction() {
        return new WasteBombAction(ActionState.creatureForIndex(targetIndex), damage, num, stacks);
    }
}
