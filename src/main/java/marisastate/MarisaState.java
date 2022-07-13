package marisastate;

import ThMod.ThMod;
import ThMod.abstracts.AmplifiedAttack;
import ThMod.action.*;
import ThMod.cards.Marisa.AbsoluteMagnitude;
import ThMod.cards.Marisa.Orbital;
import ThMod.cards.derivations.WhiteDwarf;
import ThMod.characters.Marisa;
import ThMod.monsters.Orin;
import ThMod.potions.BottledSpark;
import ThMod.potions.ShroomBrew;
import ThMod.potions.StarNLove;
import ThMod.powers.Marisa.*;
import ThMod.powers.monsters.InfernoClaw;
import ThMod.powers.monsters.WraithPower;
import ThMod.relics.ExperimentalFamiliar;
import ThMod.relics.ShroomBag;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import battleaimod.BattleAiMod;
import battleaimod.SilentLogger;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import marisastate.actions.*;
import marisastate.cards.AbsoluteMagnitudeState;
import marisastate.cards.AmplifiedAttackCardState;
import marisastate.cards.WhiteDwaftState;
import marisastate.monsters.OrinState;
import marisastate.powers.*;
import marisastate.relic.ShroomBagState;
import savestate.CardState;
import savestate.SaveStateMod;
import savestate.StateElement;
import savestate.StateFactories;
import savestate.actions.ActionState;
import savestate.actions.CurrentActionState;
import savestate.fastobjects.AnimationStateFast;
import savestate.monsters.MonsterState;
import savestate.powers.PowerState;
import savestate.relics.RelicState;

import java.util.HashSet;
import java.util.Iterator;

import static ThMod.patches.AbstractCardEnum.MARISA_COLOR;

@SpireInitializer
public class MarisaState implements PostInitializeSubscriber, EditRelicsSubscriber {
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
        populateMonsterFactories();

        BattleAiMod.cardPlayHeuristics.add(MarisaPlayOrder.COMPARATOR);

        StateFactories.powerPrefixes.add(PropBagPower.POWER_ID);

        CardLibrary.cards.remove(Orbital.ID);

        StateElement.ElementFactories stateFactories = new StateElement.ElementFactories(() -> new MarisaStateElement(), json -> new MarisaStateElement(json));
        StateFactories.elementFactories.put(MarisaStateElement.ELEMENT_KEY, stateFactories);

        BattleAiMod.additionalValueFunctions
                .add(saveState -> MarisaStateElement.getElementScore(saveState));

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
                .put(InfernoClaw.POWER_ID, new PowerState.PowerFactories(power -> new InfernoClawState(power)));
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
                .put(WraithPower.POWER_ID, new PowerState.PowerFactories(power -> new WraithPowerState(power)));
        StateFactories.powerByIdMap
                .put(WitchOfGreedGold.POWER_ID, new PowerState.PowerFactories(power -> new WitchOfGreedGoldState(power)));
        StateFactories.powerByIdMap
                .put(WitchOfGreedPotion.POWER_ID, new PowerState.PowerFactories(power -> new WitchOfGreedPotionState(power)));
        StateFactories.powerByIdMap
                .put(PropBagPower.POWER_ID, new PowerState.PowerFactories(power -> new PropBagPowerState(power), json -> new PropBagPowerState(json)));
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
        StateFactories.currentActionByClassMap
                .put(BinaryStarsAction.class, new CurrentActionState.CurrentActionFactories(action -> new BinaryStarActionState(action)));
        StateFactories.currentActionByClassMap
                .put(BlackFlareStarAction.class, new CurrentActionState.CurrentActionFactories(action -> new BlackFlareStarActionState(action)));
        StateFactories.currentActionByClassMap
                .put(DiscardPileToHandAction.class, new CurrentActionState.CurrentActionFactories(action -> new DiscardPileToHandActionState(action)));
        StateFactories.currentActionByClassMap
                .put(OrbitalAction.class, new CurrentActionState.CurrentActionFactories(action -> new OrbitalActionState()));
        StateFactories.currentActionByClassMap
                .put(DiscToHandATKOnly.class, new CurrentActionState.CurrentActionFactories(action -> new DiscToHandATKOnlyState(action)));
    }

    private void populateActionsFactory() {
        StateFactories.actionByClassMap
                .put(RefreshHandAction.class, new ActionState.ActionFactories(action -> new RefreshHandActionState()));
        StateFactories.actionByClassMap
                .put(WasteBombAction.class, new ActionState.ActionFactories(action -> new WasteBombActionState(action)));
        StateFactories.actionByClassMap
                .put(DiscToHandRandAction.class, new ActionState.ActionFactories(action -> new DiscToHandRandActionState()));
        StateFactories.actionByClassMap
                .put(DrawDrawPileAction.class, new ActionState.ActionFactories(action -> new DrawDrawPileActionState()));
        StateFactories.actionByClassMap
                .put(RefractionSparkAction.class, new ActionState.ActionFactories(action -> new RefractionSparkActionState(action)));
        StateFactories.actionByClassMap
                .put(FairyDestrucCullingAction.class, new ActionState.ActionFactories(action -> new FairyDestrucCullingActionState(action)));
        StateFactories.actionByClassMap
                .put(RobberyDamageAction.class, new ActionState.ActionFactories(action -> new RobberyDamageActionState(action)));
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


    @SpirePatch(clz = PotionHelper.class, method = "initialize")
    public static class NoCustomPotionsPatch {
        @SpirePostfixPatch
        public static void remove(AbstractPlayer.PlayerClass playerClass) {
            UNPLAYABLE_POTIONS.stream().forEach(potion -> PotionHelper.potions.remove(potion));
        }
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.removeRelicFromCustomPool(BaseMod
                .getCustomRelic(ExperimentalFamiliar.ID), MARISA_COLOR);
    }

    private void populateCardFactories() {
        StateFactories.cardFactoriesByType
                .put(WhiteDwarf.class, new CardState.CardFactories(card -> new WhiteDwaftState(card), json -> new WhiteDwaftState(json)));
        StateFactories.cardFactoriesByCardId
                .put(WhiteDwarf.ID, new CardState.CardFactories(card -> new WhiteDwaftState(card), json -> new WhiteDwaftState(json)));

        StateFactories.cardFactoriesByType
                .put(AbsoluteMagnitude.class, new CardState.CardFactories(card -> new AbsoluteMagnitudeState(card), json -> new AbsoluteMagnitudeState(json)));
        StateFactories.cardFactoriesByCardId
                .put(AbsoluteMagnitude.ID, new CardState.CardFactories(card -> new AbsoluteMagnitudeState(card), json -> new AbsoluteMagnitudeState(json)));

        StateFactories.cardFactoriesByType
                .put(AmplifiedAttack.class, new CardState.CardFactories(card -> new AmplifiedAttackCardState(card), json -> new AmplifiedAttackCardState(json)));
        StateFactories.cardFactoriesByTypeName
                .put(AmplifiedAttackCardState.TYPE_KEY, new CardState.CardFactories(card -> new AmplifiedAttackCardState(card), json -> new AmplifiedAttackCardState(json)));
    }

    private void populateMonsterFactories() {
        StateFactories.monsterByIdMap
                .put(Orin.ID, new MonsterState.MonsterFactories(monster -> new OrinState(monster), json -> new OrinState(json)));
    }
}