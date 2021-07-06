package marisastate.actions;

import ThMod.action.MeteoricShowerAction;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import savestate.actions.CurrentActionState;

public class MeteoricShowerActionState implements CurrentActionState {
    private final int num;
    private final int dmg;
    private final boolean f2p;

    public MeteoricShowerActionState(AbstractGameAction action) {
        this.num = ReflectionHacks.getPrivate(action, MeteoricShowerAction.class, "num");
        this.dmg = ReflectionHacks.getPrivate(action, MeteoricShowerAction.class, "dmg");
        this.f2p = ReflectionHacks.getPrivate(action, MeteoricShowerAction.class, "f2p");
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        MeteoricShowerAction result = new MeteoricShowerAction(num, dmg, f2p);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = MeteoricShowerAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(MeteoricShowerAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved && AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
