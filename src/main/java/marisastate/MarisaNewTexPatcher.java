package marisastate;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;
import org.clapper.util.classutil.*;
import savestate.SaveStateMod;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;

@SpirePatch(
        clz = CardCrawlGame.class,
        method = SpirePatch.CONSTRUCTOR
)
public class MarisaNewTexPatcher {
    public static void Raw(CtBehavior ctBehavior) throws NotFoundException, CannotCompileException {
        System.out.println("Starting dynamic patch:");

        ClassFinder finder = new ClassFinder();

        finder.add(new File(Loader.STS_JAR));

        for (ModInfo modInfo : Loader.MODINFOS) {
            if (modInfo.ID.equals("TS05_Marisa") && modInfo.jarURL != null) {
                try {
                    finder.add(new File(modInfo.jarURL.toURI()));
                } catch (URISyntaxException e) {
                    // do nothing
                }
            }
        }

        ClassFilter filter = new AndClassFilter(
                new NotClassFilter(new InterfaceOnlyClassFilter()),
                new ClassModifiersClassFilter(Modifier.PUBLIC)
        );

        ArrayList<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        System.out.println("\t- Done Finding Classes.\n\t- Patching:");

        for (ClassInfo classInfo : foundClasses) {
            CtClass ctClass = ctBehavior.getDeclaringClass().getClassPool().get(classInfo.getClassName());

            System.out.println("\t\t- Patching Class: " + ctClass.getSimpleName());
            try {
                CtConstructor[] methods = ctClass.getConstructors();
                for (CtConstructor m : methods) {
                    m.instrument(new ExprEditor() {
                        @Override
                        public void edit(NewExpr f) throws CannotCompileException {
                            if (f.getClassName().equals(Texture.class.getName())) {
                                f.replace(String.format("if(%s.isSimulation()) {" +
                                        "$_ = %s.tex; } else {" +
                                        "$_ = $proceed($$);" +
                                        "}", MarisaNewTexPatcher.class.getName(), MarisaNewTexPatcher.class.getName()));
                            }
                        }
                    });
                }

                System.out.println("\t\t\tSuccess.\n");
            } catch (CannotCompileException e) {
                System.out.println("\t\t\tFailure.\n");
                e.printStackTrace();
            }
        }
        System.out.println("\t- Done Patching.");
    }

    //TODO: Make blank texture
    public static final Texture tex = ImageMaster.BLOCK_BAR_B;

    public static boolean isSimulation() {
        return SaveStateMod.shouldGoFast;
    }
}