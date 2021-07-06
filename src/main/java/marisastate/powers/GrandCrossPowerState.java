package marisastate.powers;

import ThMod.powers.Marisa.GrandCrossPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class GrandCrossPowerState extends PowerState {
    public GrandCrossPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new GrandCrossPower(targetAndSource);
    }
}
