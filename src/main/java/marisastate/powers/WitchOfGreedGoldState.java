package marisastate.powers;

import ThMod.powers.Marisa.WitchOfGreedGold;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class WitchOfGreedGoldState extends PowerState {
    public WitchOfGreedGoldState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new WitchOfGreedGold(targetAndSource, amount);
    }
}
