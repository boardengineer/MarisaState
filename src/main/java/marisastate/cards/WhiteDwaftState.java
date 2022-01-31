package marisastate.cards;

import ThMod.cards.derivations.WhiteDwarf;
import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class WhiteDwaftState extends AmplifiedAttackCardState {
    private final float magn;

    public WhiteDwaftState(AbstractCard card) {
        super(card);
        magn = ReflectionHacks.getPrivate(card, WhiteDwarf.class, "magn");
    }

    public WhiteDwaftState(String json) {
        super(json);

        JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();

        magn = parsed.get("magn").getAsFloat();
    }

    @Override
    public AbstractCard loadCard() {
        AbstractCard result = super.loadCard();

        ReflectionHacks.setPrivate(result, WhiteDwarf.class, "magn", magn);

        return result;
    }

    @Override
    public String encode() {
        String result = super.encode();

        JsonObject parsed = new JsonParser().parse(result).getAsJsonObject();

        parsed.addProperty("magn", magn);

        return parsed.toString();
    }
}
