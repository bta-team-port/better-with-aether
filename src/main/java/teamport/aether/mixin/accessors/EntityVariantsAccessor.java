package teamport.aether.mixin.accessors;

import net.minecraft.client.entity.ClientSkinVariantList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ClientSkinVariantList.EntityVariants.class)
public interface EntityVariantsAccessor {

    @Accessor("indexedSkins")
    String[] getIndexedSkins();

    @Accessor("variantEntries")
    ClientSkinVariantList.EntityVariants.VariantEntry[] getVariantEntries();
}
