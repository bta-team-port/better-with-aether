package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;

@Mixin(ItemDye.class)
public abstract class ItemDyeMixin extends Item {
    protected ItemDyeMixin(@NonNull NamespaceID namespaceId, @NonNull String translationKey, int id) {
        super(namespaceId, translationKey, id);
    }

    @WrapOperation(method = "onUseOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;hasTag(Lnet/minecraft/core/data/tag/Tag;)Z"))
    private boolean allowAetherFlowerTag(Block<?> instance, Tag<?> tag, @NonNull Operation<Boolean> original) {
        if (tag == BlockTags.GROWS_FLOWERS) {
            return original.call(instance, tag) || instance.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS);
        }

        return original.call(instance, tag);
    }

    @WrapOperation(method = "onUseOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockTypeNotify(Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/block/Block;)Z"))
    private boolean replaceWithAetherFlora(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Block<?> block, Operation<Boolean> original) {
        Block<?> soil = world.getBlockType(new TilePos(tilePos.x(), tilePos.y() - 1, tilePos.z()));

        if (soil.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS)) {
            if (block == Blocks.TALLGRASS || block == Blocks.SPINIFEX) {
                block = AetherBlocks.TALLGRASS_AETHER;
            } else {
                block = itemRand.nextBoolean() ? AetherBlocks.FLOWER_PURPLE : AetherBlocks.FLOWER_WHITE;
            }
        }

        return original.call(world, tilePos, block);
    }
}
