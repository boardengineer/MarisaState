package marisastate.powers;

import ThMod.powers.Marisa.SuperNovaPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class SuperNovaPowerState extends PowerState {
    public SuperNovaPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new SuperNovaPower(targetAndSource, amount);
    }
}
