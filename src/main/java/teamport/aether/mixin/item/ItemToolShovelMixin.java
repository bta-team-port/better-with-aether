package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.tool.ItemToolShovel;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlocks;

@Mixin(value = ItemToolShovel.class, remap = false)
public abstract class ItemToolShovelMixin {
    @WrapOperation(
        method = "shovelBlock(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Side;)Z",
        at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/Blocks;GRASS:Lnet/minecraft/core/block/Block;", opcode = Opcodes.GETSTATIC)
    )
    private Block<?> addNewPathBlockOne(Operation<Block<?>> original, @Local(name = "block") Block<?> block) {
        if (block == AetherBlocks.GRASS_AETHER || block == AetherBlocks.DIRT_AETHER) return block;
        return original.call();
    }

    @WrapOperation(
        method = "shovelBlock(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Side;)Z",
        at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/Blocks;PATH_DIRT:Lnet/minecraft/core/block/Block;", opcode = Opcodes.GETSTATIC)
    )
    private Block<?> addNewPathBlockTwo(Operation<Block<?>> original, @Local(name = "block") Block<?> block) {
        if (block == AetherBlocks.GRASS_AETHER || block == AetherBlocks.DIRT_AETHER) return AetherBlocks.PATH_DIRT_AETHER;
        return original.call();
    }
}
