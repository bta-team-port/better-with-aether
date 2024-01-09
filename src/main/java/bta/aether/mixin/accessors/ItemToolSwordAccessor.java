package bta.aether.mixin.accessors;

import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ItemToolSword.class, remap = false)
public interface ItemToolSwordAccessor {
    @Accessor
    ToolMaterial getMaterial();
}
