package marisastate.powers;

import ThMod.powers.monsters.WraithPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class WraithPowerState extends PowerState {
    public WraithPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new WraithPower(targetAndSource, amount);
    }
}
