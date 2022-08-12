package marisastate.cards;

import ThMod.abstracts.AmplifiedAttack;
import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.cards.AbstractCard;
import savestate.CardState;

public class AmplifiedAttackCardState extends CardState {
    public static final String TYPE_KEY = "AmplifiedAttack";

    private final int ampNumber;
    private final boolean isException;

    public AmplifiedAttackCardState(AbstractCard card) {
        super(card);

        this.ampNumber = ReflectionHacks.getPrivate(card, AmplifiedAttack.class, "ampNumber");
        this.isException = ReflectionHacks.getPrivate(card, AmplifiedAttack.class, "isException");
    }

    public AmplifiedAttackCardState(String json) {
        super(json);

        JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();

        this.ampNumber = parsed.get("amp_number").getAsInt();
        this.isException = parsed.get("is_exception").getAsBoolean();
    }

    public AmplifiedAttackCardState(JsonObject cardJson) {
        super(cardJson);

        this.ampNumber = cardJson.get("amp_number").getAsInt();
        this.isException = cardJson.get("is_exception").getAsBoolean();
    }

    @Override
    public AbstractCard loadCard() {
        AbstractCard result = super.loadCard();

        ReflectionHacks.setPrivate(result, AmplifiedAttack.class, "ampNumber", ampNumber);
        ReflectionHacks.setPrivate(result, AmplifiedAttack.class, "isException", isException);

        return result;
    }

    @Override
    public String encode() {
        String result = super.encode();

        JsonObject parsed = new JsonParser().parse(result).getAsJsonObject();

        parsed.addProperty("amp_number", ampNumber);
        parsed.addProperty("is_exception", isException);

        parsed.addProperty("type", TYPE_KEY);

        return parsed.toString();
    }

    @Override
    public JsonObject jsonEncode() {
        JsonObject result = super.jsonEncode();

        result.addProperty("amp_number", ampNumber);
        result.addProperty("is_exception", isException);

        result.addProperty("type", TYPE_KEY);

        return result;
    }
}
