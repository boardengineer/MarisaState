package marisastate.powers;

import ThMod.powers.Marisa.EventHorizonPower;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class EventHorizonPowerState extends PowerState {
    private final int cnt;

    public EventHorizonPowerState(AbstractPower power) {
        super(power);

        cnt = ReflectionHacks.getPrivate(power, EventHorizonPower.class, "cnt");
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        EventHorizonPower result = new EventHorizonPower(targetAndSource, amount);

        ReflectionHacks.setPrivate(result, EventHorizonPower.class, "cnt", cnt);

        return result;
    }
}
