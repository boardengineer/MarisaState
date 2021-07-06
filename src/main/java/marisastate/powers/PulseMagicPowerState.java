package marisastate.powers;

import ThMod.powers.Marisa.PulseMagicPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class PulseMagicPowerState extends PowerState {
    public PulseMagicPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new PulseMagicPower(targetAndSource);
    }
}
