package marisastate.powers;

import ThMod.powers.Marisa.OrrerysSunPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class OrrerysSunPowerState extends PowerState {
    public OrrerysSunPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new OrrerysSunPower(targetAndSource, amount);
    }
}
