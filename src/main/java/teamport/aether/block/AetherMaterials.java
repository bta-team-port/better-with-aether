package teamport.aether.block;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.block.material.MaterialGas;
import org.jspecify.annotations.NonNull;

public final class AetherMaterials {
    public static final @NonNull Material AERCLOUD;
    public static final @NonNull Material HOLYSTONE;

    public static final @NonNull Material SENTRY;
    public static final @NonNull Material ANGELIC;
    public static final @NonNull Material HELLFIRE;

    public static final @NonNull Material ZANITE;
    public static final @NonNull Material GRAVITITE;

    private AetherMaterials() {
    }

    static {
        AERCLOUD = new MaterialGas(MaterialColor.none);


        HOLYSTONE = (new Material(MaterialColor.paintedSilver))
            .setConductivity(-5)
            .setAsStone()
            .notAlwaysDestroyable();


        SENTRY = (new Material(MaterialColor.stone))
            .setConductivity(-5)
            .setAsStone()
            .notAlwaysDestroyable();

        ANGELIC = (new Material(MaterialColor.grassScorched))
            .setConductivity(-5)
            .setAsStone()
            .notAlwaysDestroyable();

        HELLFIRE = (new Material(MaterialColor.brick))
            .setConductivity(-5)
            .setAsStone()
            .notAlwaysDestroyable();


        GRAVITITE = (new Material(MaterialColor.paintedPink))
            .setConductivity(20)
            .setAsMetal()
            .notAlwaysDestroyable();

        ZANITE = (new Material(MaterialColor.paintedPurple))
            .setConductivity(-5)
            .setAsStone()
            .notAlwaysDestroyable();
    }
}
