package marisastate.powers;

import ThMod.powers.Marisa.OneTimeOffPlusPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class OneTimeOffPlusPowerState extends PowerState {
    public OneTimeOffPlusPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new OneTimeOffPlusPower(targetAndSource);
    }
}
