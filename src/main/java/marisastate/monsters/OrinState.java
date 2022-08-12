package marisastate.monsters;

import ThMod.monsters.Orin;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import savestate.monsters.MonsterState;

import static savestate.SaveStateMod.shouldGoFast;

public class OrinState extends MonsterState {
    private final int doubleTap;
    private final int catTap;
    private final int hellFireDmg;
    private final int strength;
    private final int weak;
    private final int wraith;
    private final int exc;
    private final int executeDmg;
    private final int quad;
    private final int blc;
    private final int turnCount;

    public OrinState(AbstractMonster monster) {
        super(monster);

        this.doubleTap = ReflectionHacks.getPrivate(monster, Orin.class, "doubleTap");
        this.catTap = ReflectionHacks.getPrivate(monster, Orin.class, "catTap");
        this.hellFireDmg = ReflectionHacks.getPrivate(monster, Orin.class, "hellFireDmg");
        this.strength = ReflectionHacks.getPrivate(monster, Orin.class, "strength");
        this.weak = ReflectionHacks.getPrivate(monster, Orin.class, "weak");
        this.wraith = ReflectionHacks.getPrivate(monster, Orin.class, "wraith");
        this.exc = ReflectionHacks.getPrivate(monster, Orin.class, "exc");
        this.executeDmg = ReflectionHacks.getPrivate(monster, Orin.class, "executeDmg");
        this.quad = ReflectionHacks.getPrivate(monster, Orin.class, "quad");
        this.blc = ReflectionHacks.getPrivate(monster, Orin.class, "blc");
        this.turnCount = ReflectionHacks.getPrivate(monster, Orin.class, "turnCount");
    }

    public OrinState(String jsonString) {
        super(jsonString);

        JsonObject parsed = new JsonParser().parse(jsonString).getAsJsonObject();

        this.doubleTap = parsed.get("double_tap").getAsInt();
        this.catTap = parsed.get("cat_tap").getAsInt();
        this.hellFireDmg = parsed.get("hell_fire_dmg").getAsInt();
        this.strength = parsed.get("strength").getAsInt();
        this.weak = parsed.get("weak").getAsInt();
        this.wraith = parsed.get("wraith").getAsInt();
        this.exc = parsed.get("exc").getAsInt();
        this.executeDmg = parsed.get("execut_dmg").getAsInt();
        this.quad = parsed.get("quad").getAsInt();
        this.blc = parsed.get("blc").getAsInt();
        this.turnCount = parsed.get("turn_count").getAsInt();
    }

    public OrinState(JsonObject monsterJson) {
        super(monsterJson);

        this.doubleTap = monsterJson.get("double_tap").getAsInt();
        this.catTap = monsterJson.get("cat_tap").getAsInt();
        this.hellFireDmg = monsterJson.get("hell_fire_dmg").getAsInt();
        this.strength = monsterJson.get("strength").getAsInt();
        this.weak = monsterJson.get("weak").getAsInt();
        this.wraith = monsterJson.get("wraith").getAsInt();
        this.exc = monsterJson.get("exc").getAsInt();
        this.executeDmg = monsterJson.get("execut_dmg").getAsInt();
        this.quad = monsterJson.get("quad").getAsInt();
        this.blc = monsterJson.get("blc").getAsInt();
        this.turnCount = monsterJson.get("turn_count").getAsInt();
    }

    @Override
    public AbstractMonster loadMonster() {
        Orin result = new Orin();

        ReflectionHacks.setPrivate(result, Orin.class, "doubleTap", doubleTap);
        ReflectionHacks.setPrivate(result, Orin.class, "catTap", catTap);
        ReflectionHacks.setPrivate(result, Orin.class, "hellFireDmg", hellFireDmg);
        ReflectionHacks.setPrivate(result, Orin.class, "strength", strength);
        ReflectionHacks.setPrivate(result, Orin.class, "weak", weak);
        ReflectionHacks.setPrivate(result, Orin.class, "wraith", wraith);
        ReflectionHacks.setPrivate(result, Orin.class, "exc", exc);
        ReflectionHacks.setPrivate(result, Orin.class, "executeDmg", executeDmg);
        ReflectionHacks.setPrivate(result, Orin.class, "quad", quad);
        ReflectionHacks.setPrivate(result, Orin.class, "blc", blc);
        ReflectionHacks.setPrivate(result, Orin.class, "turnCount", turnCount);

        return result;
    }

    @Override
    public String encode() {
        JsonObject monsterStateJson = new JsonParser().parse(super.encode()).getAsJsonObject();

        monsterStateJson.addProperty("double_tap", doubleTap);
        monsterStateJson.addProperty("cat_tap", catTap);
        monsterStateJson.addProperty("hell_fire_dmg", hellFireDmg);
        monsterStateJson.addProperty("strength", strength);
        monsterStateJson.addProperty("weak", weak);
        monsterStateJson.addProperty("wraith", wraith);
        monsterStateJson.addProperty("exc", exc);
        monsterStateJson.addProperty("execut_dmg", executeDmg);
        monsterStateJson.addProperty("quad", quad);
        monsterStateJson.addProperty("blc", blc);
        monsterStateJson.addProperty("turn_count", turnCount);

        return monsterStateJson.toString();
    }

    @Override
    public JsonObject jsonEncode() {
        JsonObject result = super.jsonEncode();

        result.addProperty("double_tap", doubleTap);
        result.addProperty("cat_tap", catTap);
        result.addProperty("hell_fire_dmg", hellFireDmg);
        result.addProperty("strength", strength);
        result.addProperty("weak", weak);
        result.addProperty("wraith", wraith);
        result.addProperty("exc", exc);
        result.addProperty("execut_dmg", executeDmg);
        result.addProperty("quad", quad);
        result.addProperty("blc", blc);
        result.addProperty("turn_count", turnCount);

        return result;
    }

    @SpirePatch(
            clz = Orin.class,
            paramtypez = {},
            method = SpirePatch.CONSTRUCTOR
    )
    public static class NoAnimationsPatch {
        @SpireInsertPatch(loc = 143)
        public static SpireReturn disableAnimations(Orin orin) {
            if (shouldGoFast) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
