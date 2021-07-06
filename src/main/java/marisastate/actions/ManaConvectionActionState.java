package marisastate.actions;

import ThMod.action.ManaConvectionAction;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import savestate.actions.CurrentActionState;

public class ManaConvectionActionState implements CurrentActionState {
    private final int num;

    public ManaConvectionActionState(AbstractGameAction action) {
        this.num = ReflectionHacks.getPrivate(action, ManaConvectionAction.class, "num");
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        ManaConvectionAction result = new ManaConvectionAction(num);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = ManaConvectionAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(ManaConvectionAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved && AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
