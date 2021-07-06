package marisastate.powers;

import ThMod.powers.Marisa.MPPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class MPPowerState extends PowerState {
    public MPPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new MPPower(targetAndSource, amount);
    }
}
