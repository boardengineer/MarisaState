package marisastate.actions;

import ThMod.action.BlackFlareStarAction;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import savestate.actions.CurrentActionState;

public class BlackFlareStarActionState implements CurrentActionState {
    private final int blc;

    public BlackFlareStarActionState(AbstractGameAction action) {
        blc = ReflectionHacks.getPrivate(action, BlackFlareStarAction.class, "blc");
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        BlackFlareStarAction result = new BlackFlareStarAction(blc);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = BlackFlareStarAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(BlackFlareStarAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
