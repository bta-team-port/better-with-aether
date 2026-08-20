package teamport.aether.mixins.mixin.accessors;

import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemToolSword.class)
public interface ItemToolSwordAccessor {
    @Accessor
    ToolMaterial getMaterial();
}
