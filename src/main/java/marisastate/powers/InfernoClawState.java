package marisastate.powers;

import ThMod.powers.monsters.InfernoClaw;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;

public class InfernoClawState extends PowerState {
    public InfernoClawState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new InfernoClaw(targetAndSource);
    }
}
