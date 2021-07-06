package marisastate.powers;

import ThMod.powers.Marisa.EventHorizonPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class EventHorizonPowerState extends PowerState {
    public EventHorizonPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new EventHorizonPower(targetAndSource, amount);
    }
}
