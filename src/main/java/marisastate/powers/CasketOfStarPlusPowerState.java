package marisastate.powers;

import ThMod.powers.Marisa.CasketOfStarPlusPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class CasketOfStarPlusPowerState extends PowerState {
    public CasketOfStarPlusPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new CasketOfStarPlusPower(targetAndSource, amount);
    }
}
