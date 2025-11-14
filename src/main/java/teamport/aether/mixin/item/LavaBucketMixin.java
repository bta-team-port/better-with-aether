package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;

import java.util.Random;

@Mixin(value = ItemBucket.class, remap = false)
public abstract class LavaBucketMixin {
    @Shadow
    @Final
    private @Nullable Block<?> blockToPlace;
    @Definition(id = "dimension", field = "Lnet/minecraft/core/world/World;dimension:Lnet/minecraft/core/world/Dimension;")
    @Expression("?.dimension")
    @ModifyExpressionValue(method = "onUseItem", at = @At("MIXINEXTRAS:EXPRESSION"))
    private Dimension aetherAttemptPlaceLavaOne(Dimension original, ItemStack stack, World world, Player player, @Local(name = "rayTraceResult") HitResult rayTraceResult, @Local(name = "x") int x, @Local(name = "y") int y, @Local(name = "z") int z, @Share("hasPlacedAerogel") LocalBooleanRef hasPlacedAerogel) {
        if (original == AetherDimension.getAether() && this.blockToPlace != null && this.blockToPlace.hasTag(BlockTags.IS_LAVA)) {
            hasPlacedAerogel.set(world.canBlockBePlacedAt(AetherBlocks.AEROGEL.id(), x, y, z, false, rayTraceResult.side));
            return Dimension.NETHER;
        }
        hasPlacedAerogel.set(false);
        return original;
    }
    @Definition(id = "blockToPlace", field = "Lnet/minecraft/core/item/ItemBucket;blockToPlace:Lnet/minecraft/core/block/Block;")
    @Expression("?.blockToPlace")
    @ModifyExpressionValue(method = "onUseItem", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 1))
    private @Nullable Block<?> aetherAttemptPlaceLavaTwo(@Nullable Block<?> original, ItemStack stack, World world, Player player) {
        if (world.dimension == AetherDimension.getAether()) return Blocks.FLUID_WATER_FLOWING;
        return original;
    }
    @WrapOperation(method = "onUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playSoundEffect(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/sound/SoundCategory;DDDLjava/lang/String;FF)V", ordinal = 0))
    private void aetherAttemptPlaceLavaThree(World instance, @Nullable Entity player, SoundCategory category, double x, double y, double z, String soundPath, float volume, float pitch, Operation<Void> original, ItemStack stack, World world, Player playerTwo, @Local(name = "x") int xTwo, @Local(name = "y") int yTwo, @Local(name = "z") int zTwo, @Share("hasPlacedAerogel") LocalBooleanRef hasPlacedAerogel) {
        if (instance.dimension == AetherDimension.getAether()) {
            if (!hasPlacedAerogel.get()) return;
            playerTwo.swingItem();
            instance.setBlockWithNotify(xTwo, yTwo, zTwo, AetherBlocks.AEROGEL.id());
            original.call(instance, player, category, z, y, x, "fire.ignite", volume, pitch);
            return;
        }
        original.call(instance, player, category, z, y, x, soundPath, volume, pitch);
    }
    @WrapOperation(method = "onUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;spawnParticle(Ljava/lang/String;DDDDDDI)V"))
    private void aetherAttemptPlaceLavaFour(World instance, String particleKey, double x, double y, double z, double motionX, double motionY, double motionZ, int data, Operation<Void> original, @Local(name = "l") int l, @Local(name = "x") int xTwo, @Local(name = "y") int yTwo, @Local(name = "z") int zTwo, @Share("hasPlacedAerogel") LocalBooleanRef hasPlacedAerogel) {
        if (instance.dimension == AetherDimension.getAether()) {
            if (!hasPlacedAerogel.get()) return;
            double angle = Math.toRadians(l * 45.0);
            ParticleMaker.spawnParticle(instance, "smoke", xTwo + 0.5, yTwo, zTwo + 0.5, -(Math.cos(angle) * 2) / 20.0, 0.03, -(Math.sin(angle) * 2) / 20.0, 0);
            return;
        }
        original.call(instance, particleKey, x, y, z, motionX, motionY, motionZ, data);
    }
    @WrapOperation(method = "onUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/gamemode/Gamemode;consumeBlocks()Z"))
    private boolean aetherAttemptPlaceLavaFive(Gamemode instance, Operation<Boolean> original, ItemStack stack, World world, Player player, @Share("hasPlacedAerogel") LocalBooleanRef hasPlacedAerogel) {
        if (world.dimension == AetherDimension.getAether() && this.blockToPlace != null && this.blockToPlace.hasTag(BlockTags.IS_LAVA) && !hasPlacedAerogel.get()) return false;
        return original.call(instance);
    }
    @WrapOperation(method = "onUseByActivator", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockWithNotify(IIII)Z"))
    private boolean aetherAttemptPlaceLavaSix(World instance, int x, int y, int z, int id, Operation<Boolean> original, ItemStack itemStack, TileEntityActivator activatorBlock, World world, Random random, int blockX, int blockY, int blockZ, double offX, double offY, double offZ, Direction direction, @Share("preventItemChange") LocalBooleanRef preventItemChange) {
        preventItemChange.set(false);
        if (instance.dimension == AetherDimension.getAether() && this.blockToPlace != null && this.blockToPlace.hasTag(BlockTags.IS_LAVA)) {
            if (instance.canBlockBePlacedAt(AetherBlocks.AEROGEL.id(), x, y, z, false, direction.getSide())) {
                original.call(instance, x, y, z, AetherBlocks.AEROGEL.id());
                return true;
            }
            preventItemChange.set(true);
            return false;
        }
        return original.call(instance, x, y, z, id);
    }
    @Expression("?.?")
    @ModifyExpressionValue(method = "onUseByActivator", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 3))
    private int aetherAttemptPlaceLavaSeven(int original, ItemStack itemStack, TileEntityActivator activatorBlock, World world, Random random, int blockX, int blockY, int blockZ, double offX, double offY, double offZ, Direction direction, @Share("preventItemChange") LocalBooleanRef preventItemChange) {
        return preventItemChange.get() ? itemStack.itemID : original;
    }
}
