package marisastate.powers;

import ThMod.powers.Marisa.SingularityPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class SingularityPowerState extends PowerState {
    public SingularityPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new SingularityPower(targetAndSource, amount);
    }
}
