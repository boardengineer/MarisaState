package marisastate.powers;

import ThMod.powers.Marisa.SatelIllusPower;
import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class SatelIllusPowerState extends PowerState {
    private final int counter;

    public SatelIllusPowerState(AbstractPower power) {
        super(power);

        this.counter = ReflectionHacks.getPrivate(power, SatelIllusPower.class, "counter");
    }

    public SatelIllusPowerState(String jsonString) {
        super(jsonString);

        JsonObject parsed = new JsonParser().parse(jsonString).getAsJsonObject();

        this.counter = parsed.get("counter").getAsInt();
    }

    @Override
    public String encode() {
        JsonObject parsed = new JsonParser().parse(super.encode()).getAsJsonObject();

        parsed.addProperty("counter", counter);

        return parsed.toString();
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        SatelIllusPower result = new SatelIllusPower(targetAndSource, amount);

        ReflectionHacks.setPrivate(result, SatelIllusPower.class, "counter", counter);

        return result;
    }
}
