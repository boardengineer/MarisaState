package marisastate.powers;

import ThMod.powers.Marisa.TempStrengthLoss;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class TempStrengthLossState extends PowerState {
    public TempStrengthLossState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new TempStrengthLoss(targetAndSource, amount);
    }
}
