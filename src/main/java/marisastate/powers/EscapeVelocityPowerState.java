package marisastate.powers;

import ThMod.powers.Marisa.EscapeVelocityPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class EscapeVelocityPowerState extends PowerState {
    public EscapeVelocityPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new EscapeVelocityPower(targetAndSource, amount);
    }
}
