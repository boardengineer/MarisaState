package marisastate.powers;

import ThMod.powers.Marisa.CasketOfStarPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class CasketOfStarPowerState extends PowerState {
    public CasketOfStarPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new CasketOfStarPower(targetAndSource, amount);
    }
}
