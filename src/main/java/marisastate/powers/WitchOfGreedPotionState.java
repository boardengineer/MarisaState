package marisastate.powers;

import ThMod.powers.Marisa.WitchOfGreedPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class WitchOfGreedPotionState extends PowerState {
    public WitchOfGreedPotionState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new WitchOfGreedPotion(targetAndSource, amount);
    }
}
