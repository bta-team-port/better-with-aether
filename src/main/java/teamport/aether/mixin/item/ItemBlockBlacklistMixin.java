package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IPlaceable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.MixinHelper;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.SunSpiritDeath;
import turniplabs.halplibe.helper.EnvironmentHelper;

@Mixin(value = IPlaceable.PlaceableBlock.class, remap = false)
public interface ItemBlockBlacklistMixin {

    @Shadow
    @NonNull
    public abstract Block<?> getBlock();

    @Definition(id = "stackSize", field = "Lnet/minecraft/core/item/ItemStack;stackSize:I")
    @Expression("?.stackSize <= 0")
    @ModifyExpressionValue(method = "placeDirectly", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean banBlocksFromDimensionsOne(boolean original, ItemStack stack, World world, @Nullable Player player, TilePosc pos, int data, Side side, double xPlaced, double yPlaced) {
        Block<?> block = getBlock();
        return original || world.dimension != AetherDimension.getAether() && AetherDimension.getDimensionBlacklist(world.dimension).contains(block.id());
    }

    @WrapOperation(method = "canPlaceDirectlyAtPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/IPlaceable;canPlaceBlockDirectlyAtPosition(Lnet/minecraft/core/block/Block;Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Side;)Z"))
    private boolean banBlocksFromDimensionsTwo(Block<?> block, World world, TilePosc pos, Side side, Operation<Boolean> original, ItemStack stack, World ignoredWorld, @Nullable Player player, TilePosc ignoredPos, Side ignoredSide, double xPlaced, double yPlaced) {
        if (stack.stackSize <= 0 || !AetherDimension.getDimensionBlacklist(world.dimension).contains(block.id())) {
            return original.call(block, world, pos, side);
        }
        if (world.dimension != AetherDimension.getAether()) {
            return false;
        }

        int replacementId = block == Blocks.COBBLE_NETHERRACK_CRYSTALLINE || block == Blocks.PUMICE_WET && !SunSpiritDeath.isDead()
            ? -2
            : MixinHelper.BLOCK_TO_BECOME.getOrDefault(block.id(), -2);
        if (player != null) player.swingItem();
        if (replacementId == -2) {
            ParticleMaker.spawnBlockBreakParticles(world, pos.x(), pos.y(), pos.z(), block.id());
            return false;
        }
        return original.call(Blocks.getBlock(replacementId), world, pos, side);
    }

    @WrapOperation(method = "placeDirectly", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockTypeDataRaw(Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/block/Block;I)Z"))
    private boolean banBlocksFromDimensionsThree(World world, TilePosc pos, Block<?> block, int data, Operation<Boolean> original) {
        int replacementId = AetherDimension.getDimensionBlacklist(world.dimension).contains(block.id())
            ? MixinHelper.BLOCK_TO_BECOME.getOrDefault(block.id(), -2)
            : -1;
        Block<?> placedBlock = replacementId == -1 ? block : Blocks.getBlock(replacementId);
        boolean condition = original.call(world, pos, placedBlock, data);
        if (replacementId != -1 && condition) {
            ParticleMaker.spawnReplacementEffects(world, pos.x(), pos.y(), pos.z());
            if (!EnvironmentHelper.isClientWorld()) {
                world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, pos.x() + 0.5, pos.y() + 0.5, pos.z() + 0.5, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
                world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, pos.x() + 0.5F, pos.y() + 0.5F, pos.z() + 0.5F, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
            }
        }
        return condition;
    }
}
