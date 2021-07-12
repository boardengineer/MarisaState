package marisastate.actions;

import ThMod.action.MagicChantAction;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import savestate.actions.CurrentActionState;

public class MagicChantActionState implements CurrentActionState {
    @Override
    public AbstractGameAction loadCurrentAction() {
        MagicChantAction result = new MagicChantAction();

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = MagicChantAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(MagicChantAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.gridSelectScreen.selectedCards
                    .isEmpty() && AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
