package marisastate.powers;

import ThMod.powers.Marisa.MillisecondPulsarsPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class MillisecondPulsarsPowerState extends PowerState {
    public MillisecondPulsarsPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new MillisecondPulsarsPower(targetAndSource);
    }
}
