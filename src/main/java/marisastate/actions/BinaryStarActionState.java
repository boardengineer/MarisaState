package marisastate.actions;

import ThMod.action.BinaryStarsAction;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import savestate.actions.CurrentActionState;

public class BinaryStarActionState implements CurrentActionState {
    private final boolean upgraded;

    public BinaryStarActionState(AbstractGameAction action) {
        upgraded = ReflectionHacks.getPrivate(action, BinaryStarsAction.class, "upg");
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        BinaryStarsAction result = new BinaryStarsAction(upgraded);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = BinaryStarsAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(BinaryStarsAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}