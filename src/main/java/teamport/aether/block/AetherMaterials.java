package teamport.aether.block;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.block.material.MaterialGas;
import org.jspecify.annotations.NonNull;

public final class AetherMaterials {
    public static final @NonNull Material AERCLOUD;
    public static final @NonNull Material HOLYSTONE;

    private AetherMaterials() {
    }

    static {
        AERCLOUD = new MaterialGas(MaterialColor.none);

        HOLYSTONE = (new Material(MaterialColor.paintedSilver)).setConductivity(-5).setAsStone().notAlwaysDestroyable();
    }
}
