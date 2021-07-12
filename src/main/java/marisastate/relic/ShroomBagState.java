package marisastate.relic;

import ThMod.relics.ShroomBag;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import savestate.relics.RelicState;

public class ShroomBagState extends RelicState {
    public ShroomBagState(AbstractRelic relic) {
        super(relic);
    }

    public ShroomBagState(String jsonString) {
        super(jsonString);
    }

    @Override
    public AbstractRelic loadRelic() {
        return new ShroomBag();
    }
}
