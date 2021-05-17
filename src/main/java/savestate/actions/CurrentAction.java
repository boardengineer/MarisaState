package savestate.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.actions.unique.DualWieldAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;

import java.util.function.Function;

public enum CurrentAction {
    ARMAMENTS_ACTION(ArmamentsAction.class, action -> new ArmamentsActionState(action)),
    DISCARD_ACTION(DiscardAction.class, action -> new DiscardActionState((DiscardAction) action)),
    DISCARD_PILE_TO_TOP_OF_DECK_ACTION(DiscardPileToTopOfDeckAction.class, action -> new DiscardPileToTopOfDeckActionState(action)),
    DUAL_WIELD_ACTION(DualWieldAction.class, action -> new DualWieldActionState(action)),
    EXHAUST_ACTION(ExhaustAction.class, action -> new ExhaustActionState(action)),
    RETAIN_CARDS_ACTION(RetainCardsAction.class, action -> new RetainCardsActionState(action));

    public Function<AbstractGameAction, CurrentActionState> factory;
    public Class<? extends AbstractGameAction> actionClass;

    CurrentAction(Class<? extends AbstractGameAction> actionClass, Function<AbstractGameAction, CurrentActionState> factory) {
        this.factory = factory;
        this.actionClass = actionClass;
    }

}