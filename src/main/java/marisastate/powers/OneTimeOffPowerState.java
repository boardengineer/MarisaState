package marisastate.powers;

import ThMod.powers.Marisa.OneTimeOffPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class OneTimeOffPowerState extends PowerState {
    public OneTimeOffPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new OneTimeOffPower(targetAndSource);
    }
}
