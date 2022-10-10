package marisastate.cards;

import ThMod.cards.Marisa.AbsoluteMagnitude;
import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AbsoluteMagnitudeState extends AmplifiedAttackCardState {
    private final float multiplier;

    public AbsoluteMagnitudeState(AbstractCard card) {
        super(card);

        multiplier = ReflectionHacks.getPrivate(card, AbsoluteMagnitude.class, "multiplier");
    }

    public AbsoluteMagnitudeState(String json) {
        super(json);

        JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();

        multiplier = parsed.get("multiplier").getAsFloat();
    }

    public AbsoluteMagnitudeState(JsonObject cardJson) {
        super(cardJson);

        multiplier = cardJson.get("multiplier").getAsFloat();
    }

    @Override
    public AbstractCard loadCard() {
        AbstractCard result = super.loadCard();

        ReflectionHacks.setPrivate(result, AbsoluteMagnitude.class, "multiplier", multiplier);
        return result;
    }

    @Override
    public String encode() {
        String result = super.encode();

        JsonObject parsed = new JsonParser().parse(result).getAsJsonObject();

        parsed.addProperty("multiplier", multiplier);

        return parsed.toString();
    }

    @Override
    public JsonObject jsonEncode() {
        JsonObject result = super.jsonEncode();

        result.addProperty("multiplier", multiplier);

        return result;
    }
}
