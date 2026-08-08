package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IAccumulatable;
import net.minecraft.core.item.IPlaceable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlockSlab;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.MixinHelper;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;

@Mixin(IAccumulatable.class)
public interface ItemBlockSlabBlacklistMixin {

    @Definition(id = "stackSize", field = "Lnet/minecraft/core/item/ItemStack;stackSize:I")
    @Expression("?.stackSize <= 0")
    @ModifyExpressionValue(method = "placeOnBlockAccumulatable", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean banBlocksFromDimensionsOne(boolean original, boolean shift, ItemStack selfStack, World world, @Nullable Player player, TilePosc tilePos, Side side, double xHit, double yHit) {
        Block<?> block = ((IPlaceable.PlaceableBlock<?>) this).getBlock();
        return original || this instanceof ItemBlockSlab<?> && world.dimension != AetherDimension.getAether() && AetherDimension.getDimensionBlacklist(world.dimension).contains(block.id());
    }

    @WrapOperation(method = "placeOnBlockAccumulatable", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockTypeDataRaw(Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/block/Block;I)Z"))
    private boolean banBlocksFromDimensionsTwo(World world, TilePosc tilePos, Block<?> block, int data, Operation<Boolean> original, boolean shift, ItemStack selfStack, World ignoredWorld, @Nullable Player player) {
        return placeAccumulatedSlab(world, tilePos, block, data, original, player);
    }

    @WrapOperation(method = "placeOnBlockAccumulatable", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockTypeData(Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/block/Block;I)Z"))
    private boolean banBlocksFromDimensionsThree(World world, TilePosc tilePos, Block<?> block, int data, Operation<Boolean> original, boolean shift, ItemStack selfStack, World ignoredWorld, @Nullable Player player) {
        return placeAccumulatedSlab(world, tilePos, block, data, original, player);
    }

    @Unique
    private boolean placeAccumulatedSlab(World world, TilePosc pos, Block<?> block, int data, Operation<Boolean> original, @Nullable Player player) {
        if (!(this instanceof ItemBlockSlab<?>)) {
            return original.call(world, pos, block, data);
        }

        Block<?> itemBlock = ((IPlaceable.PlaceableBlock<?>) this).getBlock();
        if (!AetherDimension.getDimensionBlacklist(world.dimension).contains(itemBlock.id())) {
            return original.call(world, pos, block, data);
        }

        if (player != null) player.swingItem();
        int replacementId = MixinHelper.BLOCK_TO_BECOME.getOrDefault(itemBlock.id(), -2);
        if (replacementId == -2) {
            ParticleMaker.spawnBlockBreakParticles(world, pos.x(), pos.y(), pos.z(), itemBlock.id());
            return false;
        }

        boolean condition = original.call(world, pos, Blocks.getBlock(replacementId), data);
        if (condition) ParticleMaker.spawnReplacementEffects(world, pos.x(), pos.y(), pos.z());
        return condition;
    }

}
