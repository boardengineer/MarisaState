package marisastate.relic;

import ThMod.relics.ShroomBag;
import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import savestate.relics.RelicState;

public class ShroomBagState extends RelicState {
    public ShroomBagState(AbstractRelic relic) {
        super(relic);
    }

    public ShroomBagState(String jsonString) {
        super(jsonString);
    }

    public ShroomBagState(JsonObject relicJson) {
        super(relicJson);
    }

    @Override
    public AbstractRelic loadRelic() {
        return new ShroomBag();
    }
}
