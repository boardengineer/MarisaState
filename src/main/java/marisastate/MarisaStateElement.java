package marisastate;

import ThMod.cards.Marisa.TreasureHunter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import marisastate.patches.TreasureHunterDamagePatch;
import savestate.CardState;
import savestate.SaveState;
import savestate.StateElement;

public class MarisaStateElement implements StateElement {
    public static String ELEMENT_KEY = "MARISA_MOD_STATE";

    public final int treasureHunterHitCount;

    public MarisaStateElement() {
        treasureHunterHitCount = TreasureHunterDamagePatch.UpdatePatch.hitCount;
    }

    public MarisaStateElement(String jsonState) {
        JsonObject parsed = new JsonParser().parse(jsonState).getAsJsonObject();

        treasureHunterHitCount = parsed.get("treasure_hunter_hit_count").getAsInt();
    }

    @Override
    public String encode() {
        JsonObject statJson = new JsonObject();

        statJson.addProperty("treasure_hunter_hit_count", treasureHunterHitCount);

        return statJson.toString();
    }

    @Override
    public void restore() {
        TreasureHunterDamagePatch.UpdatePatch.hitCount = treasureHunterHitCount;
    }

    public static int getElementScore(SaveState saveState) {
        MarisaStateElement marisaStateElement = (MarisaStateElement) saveState.additionalElements
                .get(ELEMENT_KEY);

        int treasureHunterCardCount = 0;
        for (CardState card : saveState.playerState.hand) {
            switch (card.cardId) {
                case TreasureHunter.ID:
                    treasureHunterCardCount++;
                    break;
                default:
                    break;
            }
        }

        for (CardState card : saveState.playerState.drawPile) {
            switch (card.cardId) {
                case TreasureHunter.ID:
                    treasureHunterCardCount++;
                    break;
                default:
                    break;
            }
        }

        for (CardState card : saveState.playerState.discardPile) {
            switch (card.cardId) {
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
