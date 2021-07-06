package marisastate.powers;

import ThMod.powers.Marisa.BlazeAwayPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class BlazeAwayPowerState extends PowerState {
    public BlazeAwayPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new BlazeAwayPower(targetAndSource, amount);
    }
}
