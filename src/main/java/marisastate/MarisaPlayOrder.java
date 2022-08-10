package marisastate;

import ThMod.cards.Marisa.*;
import ThMod.cards.derivations.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Wound;

import java.util.Comparator;
import java.util.HashMap;

public class MarisaPlayOrder {
    public static final HashMap<String, Integer> CARD_RANKS = new HashMap<String, Integer>() {{
        int size = 0;

        put(Pain.ID, size++);
        put(BlackFlareStar.ID, size++);

        put(PowerUp.ID, size++);

        // powers
        put(WitchOfGreed.ID, size++);
        put(SuperNova.ID, size++);
        put(MillisecondPulsars.ID, size++);
        put(EscapeVelocity.ID, size++);
        put(CasketOfStar.ID, size++);
        put(Singularity.ID, size++);
        put(EventHorizon.ID, size++);
        put(EnergyFlow.ID, size++);
        put(OrrerysSun.ID, size++);
        put(OortCloud.ID, size++);
        put(SatelliteIllusion.ID, size++);
        put(PropBag.ID, size++);

        put(Wound.ID, size++);
        put(Burn.ID, size++);
        put(Dazed.ID, size++);
        put(Pain.ID, size++);
        put(Doubt.ID, size++);
        put(Normality.ID, size++);
        put(Shame.ID, size++);
        put(AscendersBane.ID, size++);
        put(CurseOfTheBell.ID, size++);

        // Big Booms
        put(MaximisePower.ID, size++);
        put(BigCrunch.ID, size++);

        // Amplify Effects and X cards
        put(LuminesStrike.ID, size++);
        put(Robbery.ID, size++);
        put(BinaryStars.ID, size++);
        put(SporeBomb.ID, size++);
        put(ShootTheMoon.ID, size++);
        put(DeepEcologicalBomb.ID, size++);
        put(ManaRampage.ID, size++);
        put(MeteoricShower.ID, size++);

        // Draw Effects and other acceleration effects
        put(StarDustReverie.ID, size++);
        put(Acceleration.ID, size++);
        put(UltraShortWave.ID, size++);
        put(StarlightTyphoon.ID, size++);
        put(OpenUniverse.ID, size++);
        put(PulseMagic.ID, size++);
        put(IllusionStar.ID, size++);
        put(MagicChant.ID, size++);
        put(ChargingUp.ID, size++);
        put(EarthLightRay.ID, size++);
        put(MilkyWay.ID, size++);
        put(JA.ID, size++);

        // Attacks
        put(AbsoluteMagnitude.ID, size++);
        put(_6A.ID, size++);
        put(WitchLeyline.ID, size++);
        put(TreasureHunter.ID, size++);
        put(MysteriousBeam.ID, size++);
        put(UpSweep.ID, size++);
        put(StarBarrage.ID, size++);
        put(CollectingQuirk.ID, size++);
        put(BlazingStar.ID, size++);
        put(MasterSpark.ID, size++);
        put(AlicesGift.ID, size++);
        put(DarkSpark.ID, size++);
        put(DoubleSpark.ID, size++);
        put(RefractionSpark.ID, size++);
        put(ShootingEcho.ID, size++);
        put(DC.ID, size++);
        put(GravityBeat.ID, size++);
        put(ChargeUpSpray.ID, size++);
        put(FairyDestructionRay.ID, size++);
        put(WhiteDwarf.ID, size++);
        put(MachineGunSpark.ID, size++);
        put(NonDirectionalLaser.ID, size++);
        put(UnstableBomb.ID, size++);
        put(Spark.ID, size++);
        put(FinalSpark.ID, size++);
        put(BlazeAway.ID, size++);
        put(GrandCross.ID, size++);

        // Blocks
        put(Occultation.ID, size++);
        put(DarkMatter.ID, size++);
        put(SprinkleStarSeal.ID, size++);
        put(AsteroidBelt.ID, size++);
        put(DragonMeteor.ID, size++);
        put(GasGiant.ID, size++);
        put(MagicAbsorber.ID, size++);
        put(EnergyRecoil.ID, size++);
        put(GalacticHalo.ID, size++);
        put(OneTimeOff.ID, size++);

        put(ManaConvection.ID, size++);

        put(Wraith.ID, size++);
        put(Exhaustion_MRS.ID, size++);

        // Unplayables
        put(GuidingStar.ID, size++);
        put(Orbital.ID, size++);
        put(SuperPerseids.ID, size++);

        put(Strike_MRS.ID, size++);
        put(Defend_MRS.ID, size++);
    }};

    public static final Comparator<AbstractCard> COMPARATOR = (card1, card2) -> {
        if (CARD_RANKS.containsKey(card1.cardID) && CARD_RANKS
                .containsKey(card2.cardID)) {
            return CARD_RANKS.get(card1.cardID) - CARD_RANKS
                    .get(card2.cardID);
        }
        return 0;
    };
}
