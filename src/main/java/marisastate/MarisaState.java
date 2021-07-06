package marisastate;

import ThMod.ThMod;
import ThMod.action.ManaConvectionAction;
import ThMod.action.MeteoricShowerAction;
import ThMod.action.RefreshHandAction;
import ThMod.action.ShootingEchoAction;
import ThMod.cards.Marisa.PropBag;
import ThMod.characters.Marisa;
import ThMod.powers.Marisa.*;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.PostInitializeSubscriber;
import battleaimod.SilentLogger;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import marisastate.actions.ManaConvectionActionState;
import marisastate.actions.MeteoricShowerActionState;
import marisastate.actions.RefreshHandActionState;
import marisastate.actions.ShootingEchoActionState;
import marisastate.powers.*;
import savestate.SaveStateMod;
import savestate.StateFactories;
import savestate.actions.ActionState;
import savestate.actions.CurrentActionState;
import savestate.fastobjects.AnimationStateFast;
import savestate.powers.PowerState;

@SpireInitializer
public class MarisaState implements PostInitializeSubscriber {
    public static void initialize() {
        BaseMod.subscribe(new MarisaState());
    }

    @Override
    public void receivePostInitialize() {
        ReflectionHacks.setPrivateStaticFinal(ThMod.class, "logger", new SilentLogger());

        populatePowerFactory();
        populateActionsFactory();
        populateCurrentActionsFactory();

        // stupid ID offset thing
        CardLibrary.cards.remove(PropBag.ID);
    }

    @SpirePatch(
            clz = Marisa.class,
            paramtypez = {String.class},
            method = SpirePatch.CONSTRUCTOR
    )
    public static class NoAnimationsPatch {
        @SpireInsertPatch(loc = 79)
        public static SpireReturn MarisaCtr(Marisa _instance, String playerName) {
            if (SaveStateMod.shouldGoFast) {
                _instance.state = new AnimationStateFast();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    private void populatePowerFactory() {
        StateFactories.powerByIdMap
                .put(BlazeAwayPower.POWER_ID, new PowerState.PowerFactories(power -> new BlazeAwayPowerState(power)));
        StateFactories.powerByIdMap
                .put(CasketOfStarPlusPower.POWER_ID, new PowerState.PowerFactories(power -> new CasketOfStarPlusPowerState(power)));
        StateFactories.powerByIdMap
                .put(CasketOfStarPower.POWER_ID, new PowerState.PowerFactories(power -> new CasketOfStarPowerState(power)));
        StateFactories.powerByIdMap
                .put(ChargeUpPower.POWER_ID, new PowerState.PowerFactories(power -> new ChargeUpPowerState(power), json -> new ChargeUpPowerState(json)));
        StateFactories.powerByIdMap
                .put(DarkMatterPower.POWER_ID, new PowerState.PowerFactories(power -> new DarkMatterPowerState(power)));
        StateFactories.powerByIdMap
                .put(EnergyFlowPower.POWER_ID, new PowerState.PowerFactories(power -> new EnergyFlowPowerState(power)));
        StateFactories.powerByIdMap
                .put(EscapeVelocityPower.POWER_ID, new PowerState.PowerFactories(power -> new EscapeVelocityPowerState(power)));
        StateFactories.powerByIdMap
                .put(EventHorizonPower.POWER_ID, new PowerState.PowerFactories(power -> new EventHorizonPowerState(power)));
        StateFactories.powerByIdMap
                .put(GrandCrossPower.POWER_ID, new PowerState.PowerFactories(power -> new GrandCrossPowerState(power)));
        StateFactories.powerByIdMap
                .put(ManaRampagePower.POWER_ID, new PowerState.PowerFactories(power -> new ManaRampagePowerState(power)));
        StateFactories.powerByIdMap
                .put(MillisecondPulsarsPower.POWER_ID, new PowerState.PowerFactories(power -> new MillisecondPulsarsPowerState(power)));
        StateFactories.powerByIdMap
                .put(MPPower.POWER_ID, new PowerState.PowerFactories(power -> new MPPowerState(power)));
        StateFactories.powerByIdMap
                .put(OneTimeOffPlusPower.POWER_ID, new PowerState.PowerFactories(power -> new OneTimeOffPlusPowerState(power)));
        StateFactories.powerByIdMap
                .put(OneTimeOffPower.POWER_ID, new PowerState.PowerFactories(power -> new OneTimeOffPowerState(power)));
        StateFactories.powerByIdMap
                .put(OrrerysSunPower.POWER_ID, new PowerState.PowerFactories(power -> new OrrerysSunPowerState(power)));
        StateFactories.powerByIdMap
                .put(PulseMagicPower.POWER_ID, new PowerState.PowerFactories(power -> new PulseMagicPowerState(power)));
        StateFactories.powerByIdMap
                .put(SatelIllusPower.POWER_ID, new PowerState.PowerFactories(power -> new SatelIllusPowerState(power), json -> new SatelIllusPowerState(json)));
        StateFactories.powerByIdMap
                .put(SingularityPower.POWER_ID, new PowerState.PowerFactories(power -> new SingularityPowerState(power)));
        StateFactories.powerByIdMap
                .put(SuperNovaPower.POWER_ID, new PowerState.PowerFactories(power -> new SuperNovaPowerState(power)));
        StateFactories.powerByIdMap
                .put(TempStrengthLoss.POWER_ID, new PowerState.PowerFactories(power -> new TempStrengthLossState(power)));
        StateFactories.powerByIdMap
                .put(WitchOfGreedGold.POWER_ID, new PowerState.PowerFactories(power -> new WitchOfGreedGoldState(power)));
        StateFactories.powerByIdMap
                .put(WitchOfGreedPotion.POWER_ID, new PowerState.PowerFactories(power -> new WitchOfGreedPotionState(power)));
    }

    private void populateCurrentActionsFactory() {
        StateFactories.currentActionByClassMap
                .put(ShootingEchoAction.class, new CurrentActionState.CurrentActionFactories(action -> new ShootingEchoActionState(action)));
        StateFactories.currentActionByClassMap
                .put(ManaConvectionAction.class, new CurrentActionState.CurrentActionFactories(action -> new ManaConvectionActionState(action)));
        StateFactories.currentActionByClassMap
                .put(MeteoricShowerAction.class, new CurrentActionState.CurrentActionFactories(action -> new MeteoricShowerActionState(action)));

    }

    private void populateActionsFactory() {
        StateFactories.actionByClassMap
                .put(RefreshHandAction.class, new ActionState.ActionFactories(action -> new RefreshHandActionState()));

    }
}