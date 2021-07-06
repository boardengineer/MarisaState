package marisastate.powers;

import ThMod.powers.Marisa.ChargeUpPower;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.SaveStateMod;
import savestate.powers.PowerState;

public class ChargeUpPowerState extends PowerState {
    private final int cnt;
    private final int stc;

    public ChargeUpPowerState(AbstractPower power) {
        super(power);

        this.cnt = ReflectionHacks.getPrivate(power, ChargeUpPower.class, "cnt");
        this.stc = ReflectionHacks.getPrivate(power, ChargeUpPower.class, "stc");
    }

    public ChargeUpPowerState(String jsonString) {
        super(jsonString);

        JsonObject parsed = new JsonParser().parse(jsonString).getAsJsonObject();

        this.cnt = parsed.get("cnt").getAsInt();
        this.stc = parsed.get("stc").getAsInt();
    }

    @Override
    public String encode() {
        JsonObject parsed = new JsonParser().parse(super.encode()).getAsJsonObject();

        parsed.addProperty("cnt", cnt);
        parsed.addProperty("stc", stc);

        return parsed.toString();
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        ChargeUpPower result = new ChargeUpPower(targetAndSource, amount);

        ReflectionHacks.setPrivate(result, ChargeUpPower.class, "cnt", cnt);
        ReflectionHacks.setPrivate(result, ChargeUpPower.class, "stc", stc);

        return result;
    }

    @SpirePatch(
            clz = ChargeUpPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class NoAnimationsPatch {
        @SpireInsertPatch(loc = 42)
        public static SpireReturn Sentry(ChargeUpPower _instance, AbstractCreature creature, int amount) {
            if (SaveStateMod.shouldGoFast) {
                _instance.img = null;

                int stc;
                if (AbstractDungeon.player.hasRelic("SimpleLauncher")) {
                    stc = 6;
                } else {
                    stc = 8;
                }
                ReflectionHacks.setPrivate(_instance, ChargeUpPower.class, "stc", stc);

                int cnt = amount / stc;

                ReflectionHacks.setPrivate(_instance, ChargeUpPower.class, "cnt", cnt);

                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
