package marisastate.powers;

import ThMod.powers.Marisa.PropBagPower;
import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import savestate.powers.PowerState;

import java.util.ArrayList;

public class PropBagPowerState extends PowerState {
    private final int relicIndex;

    public PropBagPowerState(AbstractPower power) {
        super(power);

        AbstractRelic powerRelic = ReflectionHacks.getPrivate(power, PropBagPower.class, "r");

        int foundIndex = 0;
        ArrayList<AbstractRelic> relics = AbstractDungeon.player.relics;
        for (int i = 0; i < relics.size(); i++) {
            if (relics.get(i) == powerRelic) {
                foundIndex = i;
                break;
            }
        }
        relicIndex = foundIndex;
    }

    public PropBagPowerState(String jsonString) {
        super(jsonString);

        JsonObject parsed = new JsonParser().parse(jsonString).getAsJsonObject();

        this.relicIndex = parsed.get("relic_index").getAsInt();
    }

    @Override
    public String encode() {
        JsonObject parsed = new JsonParser().parse(super.encode()).getAsJsonObject();

        parsed.addProperty("relic_index", relicIndex);

        return parsed.toString();
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        PropBagPower result = new PropBagPower(targetAndSource, AbstractDungeon.player.relics
                .get(relicIndex));

        result.ID = powerId;

        return result;

    }
}
