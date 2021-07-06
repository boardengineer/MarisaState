package marisastate.powers;

import ThMod.powers.Marisa.PolarisUniquePower;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class PolarisUniquePowerState extends PowerState {
    private final boolean Gain;

    public PolarisUniquePowerState(AbstractPower power) {
        super(power);

        this.Gain = ((PolarisUniquePower) power).Gain;
    }

    public PolarisUniquePowerState(String jsonString) {
        super(jsonString);

        JsonObject parsed = new JsonParser().parse(jsonString).getAsJsonObject();

        this.Gain = parsed.get("gain").getAsBoolean();
    }

    @Override
    public String encode() {
        JsonObject parsed = new JsonParser().parse(super.encode()).getAsJsonObject();

        parsed.addProperty("gain", Gain);

        return parsed.toString();
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        PolarisUniquePower result = new PolarisUniquePower(targetAndSource);

        result.Gain = Gain;

        return result;
    }
}
