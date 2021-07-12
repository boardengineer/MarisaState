package marisastate;

import ThMod.ThMod;
import ThMod.abstracts.AmplifiedAttack;
import ThMod.action.*;
import ThMod.cards.Marisa.*;
import ThMod.characters.Marisa;
import ThMod.potions.BottledSpark;
import ThMod.potions.ShroomBrew;
import ThMod.potions.StarNLove;
import ThMod.powers.Marisa.*;
import ThMod.relics.ShroomBag;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.PostInitializeSubscriber;
import battleaimod.SilentLogger;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import marisastate.actions.*;
import marisastate.cards.AmplifiedAttackCardState;
import marisastate.powers.*;
import marisastate.relic.ShroomBagState;
import savestate.CardState;
import savestate.SaveStateMod;
import savestate.StateFactories;
import savestate.actions.ActionState;
import savestate.actions.CurrentActionState;
import savestate.fastobjects.AnimationStateFast;
import savestate.powers.PowerState;
import savestate.relics.RelicState;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

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
        populateRelicFactory();
        populateCardFactories();

        // stupid ID offset thing
        CardLibrary.cards.remove(PropBag.ID);

        // Hangs on something, needs debugging
        CardLibrary.cards.remove(BlazeAway.ID);
        CardLibrary.cards.remove(MagicChant.ID);
        CardLibrary.cards.remove(BinaryStars.ID);
        CardLibrary.cards.remove(DeepEcologicalBomb.ID);
        CardLibrary.cards.remove(TreasureHunter.ID);
        CardLibrary.cards.remove(EarthLightRay.ID);
        CardLibrary.cards.remove(Orbital.ID);
        CardLibrary.cards.remove(EventHorizon.ID);
        CardLibrary.cards.remove(BlazingStar.ID);

        Iterator<String> actualPotions = PotionHelper.potions.iterator();
        while (actualPotions.hasNext()) {
            String potionId = actualPotions.next();
            for (String toRemove : UNPLAYABLE_POTIONS) {
                if (potionId.equals(toRemove)) {
                    actualPotions.remove();
                    continue;
                }
            }
        }

        ThMod.isDeadBranchEnabled = true;
    }

    private void populateCardFactories() {
        CardState.CardFactories ampCardFactores = new CardState.CardFactories(card -> {
            if (card instanceof AmplifiedAttack) {
                return Optional.of(new AmplifiedAttackCardState(card));
            }
            return Optional.empty();
        }, json -> {
            JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();
            String type = "";
            if (parsed.has("type")) {
                type = parsed.get("type").getAsString();
            }
            if (type.equals("AmplifiedAttack")) {
                return Optional.of(new AmplifiedAttackCardState(json));
            }
            return Optional.empty();
        });

        StateFactories.cardFactories.add(ampCardFactores);
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
        StateFactories.currentActionByClassMap
                .put(MagicChantAction.class, new CurrentActionState.CurrentActionFactories(action -> new MagicChantActionState()));
    }

    private void populateActionsFactory() {
        StateFactories.actionByClassMap
                .put(RefreshHandAction.class, new ActionState.ActionFactories(action -> new RefreshHandActionState()));

    }

    private void populateRelicFactory() {
        StateFactories.relicByIdMap
                .put(ShroomBag.ID, new RelicState.RelicFactories(relic -> new ShroomBagState(relic), json -> new ShroomBagState(json)));
    }

    public static HashSet<String> UNPLAYABLE_POTIONS = new HashSet<String>() {
        {
            this.add(BottledSpark.POTION_ID);
            this.add(ShroomBrew.POTION_ID);
            this.add(StarNLove.POTION_ID);
        }
    };
}