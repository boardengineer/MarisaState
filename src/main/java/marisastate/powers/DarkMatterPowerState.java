package marisastate.powers;

import ThMod.powers.Marisa.DarkMatterPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class DarkMatterPowerState extends PowerState {
    public DarkMatterPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new DarkMatterPower(targetAndSource);
    }
}
