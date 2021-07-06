package marisastate.powers;

import ThMod.powers.Marisa.ManaRampagePower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class ManaRampagePowerState extends PowerState {
    public ManaRampagePowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new ManaRampagePower(targetAndSource, amount);
    }
}
