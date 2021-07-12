package marisastate.cards;

import ThMod.abstracts.AmplifiedAttack;
import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.cards.AbstractCard;
import savestate.CardState;

public class AmplifiedAttackCardState extends CardState {
    private final int ampNumber;

    public AmplifiedAttackCardState(AbstractCard card) {
        super(card);

        this.ampNumber = ReflectionHacks.getPrivate(card, AmplifiedAttack.class, "ampNumber");
    }

    public AmplifiedAttackCardState(String json) {
        super(json);

        JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();

        this.ampNumber = parsed.get("amp_number").getAsInt();
    }

    @Override
    public AbstractCard loadCard() {
        AbstractCard result = super.loadCard();

        ReflectionHacks.setPrivate(result, AmplifiedAttack.class, "ampNumber", ampNumber);



        return result;
    }

    @Override
    public String encode() {
        String result = super.encode();

        JsonObject parsed = new JsonParser().parse(result).getAsJsonObject();

        parsed.addProperty("amp_number", ampNumber);
        parsed.addProperty("type", "AmplifiedAttack");

        return parsed.toString();
    }
}
