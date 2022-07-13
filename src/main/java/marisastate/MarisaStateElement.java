package marisastate;

import ThMod.cards.Marisa.TreasureHunter;
import ThMod.powers.Marisa.PropBagPower;
import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import marisastate.patches.TreasureHunterDamagePatch;
import savestate.CardState;
import savestate.SaveState;
import savestate.StateElement;
import savestate.StateFactories;

public class MarisaStateElement implements StateElement {
    public static String ELEMENT_KEY = "MARISA_MOD_STATE";

    public final int treasureHunterHitCount;
    public final int propBagPowerStateIdOffset;

    public MarisaStateElement() {
        treasureHunterHitCount = TreasureHunterDamagePatch.UpdatePatch.hitCount;

        propBagPowerStateIdOffset = ReflectionHacks
                .getPrivateStatic(PropBagPower.class, "IdOffset");
    }

    public MarisaStateElement(String jsonState) {
        JsonObject parsed = new JsonParser().parse(jsonState).getAsJsonObject();

        treasureHunterHitCount = parsed.get("treasure_hunter_hit_count").getAsInt();
        propBagPowerStateIdOffset = parsed.get("prop_bag_power_id_offset").getAsInt();
    }

    @Override
    public String encode() {
        JsonObject statJson = new JsonObject();

        statJson.addProperty("treasure_hunter_hit_count", treasureHunterHitCount);
        statJson.addProperty("prop_bag_power_id_offset", propBagPowerStateIdOffset);

        return statJson.toString();
    }

    @Override
    public void restore() {
        TreasureHunterDamagePatch.UpdatePatch.hitCount = treasureHunterHitCount;

        ReflectionHacks.setPrivateStatic(PropBagPower.class, "IdOffset", propBagPowerStateIdOffset);
    }

    public static int getElementScore(SaveState saveState) {
        MarisaStateElement marisaStateElement = (MarisaStateElement) saveState.additionalElements
                .get(ELEMENT_KEY);

        int treasureHunterCardCount = 0;
        for (CardState card : saveState.playerState.hand) {
            switch (StateFactories.cardIds[card.cardIdIndex]) {
                case TreasureHunter.ID:
                    treasureHunterCardCount++;
                    break;
                default:
                    break;
            }
        }

        for (CardState card : saveState.playerState.drawPile) {
            switch (StateFactories.cardIds[card.cardIdIndex]) {
                case TreasureHunter.ID:
                    treasureHunterCardCount++;
                    break;
                default:
                    break;
            }
        }

        for (CardState card : saveState.playerState.discardPile) {
            switch (StateFactories.cardIds[card.cardIdIndex]) {
                case TreasureHunter.ID:
                    treasureHunterCardCount++;
                    break;
                default:
                    break;
            }
        }

        int teasureHunterScore = treasureHunterCardCount * 40 + marisaStateElement.treasureHunterHitCount * 200;

        return teasureHunterScore;
    }
}
