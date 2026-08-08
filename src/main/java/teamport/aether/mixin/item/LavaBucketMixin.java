package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.block.AetherBlocks;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;

import java.util.Random;
import java.util.function.BiConsumer;
import org.jspecify.annotations.Nullable;

@Mixin(value = ItemBucket.class, remap = false)
public abstract class LavaBucketMixin {
    private static boolean aether$isAetherLava(ItemStack stack, World world) {
        return world.getDimension() == AetherDimension.getAether()
            && ItemBucket.STATE_LAVA.equals(ItemBucket.getState(stack));
    }

    @WrapOperation(
        method = "tryPlaceFluid",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/world/World;canBlockIdBePlacedAt(ILnet/minecraft/core/world/pos/TilePosc;ZLnet/minecraft/core/util/helper/Side;)Z"
        )
    )
    private boolean aether$validateAerogelPlacement(
        World instance,
        int blockId,
        TilePosc pos,
        boolean ignoreEntities,
        Side side,
        Operation<Boolean> original,
        ItemStack stack,
        World world
    ) {
        if (aether$isAetherLava(stack, world)) {
            blockId = AetherBlocks.AEROGEL.id();
        }
        return original.call(instance, blockId, pos, ignoreEntities, side);
    }

    @WrapOperation(
        method = "tryPlaceFluid",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/world/World;setBlockTypeNotify(Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/block/Block;)Z"
        )
    )
    private boolean aether$placeAerogel(
        World instance,
        TilePosc pos,
        Block<?> fluidBlock,
        Operation<Boolean> original,
        ItemStack stack,
        World world
    ) {
        if (aether$isAetherLava(stack, world)) {
            fluidBlock = AetherBlocks.AEROGEL;
        }
        return original.call(instance, pos, fluidBlock);
    }

    @WrapOperation(
        method = "tryPlaceFluid",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/world/World;playSoundEffect(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/sound/SoundCategory;DDDLjava/lang/String;FF)V"
        )
    )
    private void aether$playAerogelPlacementSound(
        World instance,
        @Nullable Entity source,
        SoundCategory category,
        double x,
        double y,
        double z,
        String soundPath,
        float volume,
        float pitch,
        Operation<Void> original,
        ItemStack stack,
        World world
    ) {
        if (aether$isAetherLava(stack, world)) {
            soundPath = "fire.ignite";
            pitch = instance.rand.nextFloat() * 0.4f + 0.8f;
        }
        original.call(instance, source, category, x, y, z, soundPath, volume, pitch);
    }

    @WrapOperation(
        method = "tryPlaceFluid",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/function/BiConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;)V"
        )
    )
    private void aether$spawnAerogelPlacementParticles(
        BiConsumer<World, TilePosc> placementAction,
        Object worldArgument,
        Object positionArgument,
        Operation<Void> original,
        ItemStack stack,
        World world
    ) {
        if (!aether$isAetherLava(stack, world)) {
            original.call(placementAction, worldArgument, positionArgument);
            return;
        }

        TilePosc pos = (TilePosc) positionArgument;
        for (int direction = 0; direction < 8; direction++) {
            double angle = Math.toRadians(direction * 45.0);
            ParticleMaker.spawnParticle(
                world,
                "smoke",
                pos.x() + 0.5,
                pos.y(),
                pos.z() + 0.5,
                -Math.cos(angle) / 10.0,
                0.03,
                -Math.sin(angle) / 10.0,
                0
            );
        }
    }

    @WrapOperation(
        method = "onUseByActivator",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/world/World;setBlockTypeNotify(Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/block/Block;)Z",
            ordinal = 1
        )
    )
    private boolean aether$placeAerogelFromActivator(
        World instance,
        TilePosc pos,
        Block<?> fluidBlock,
        Operation<Boolean> original,
        ItemStack stack,
        World world,
        TileEntityActivator activator,
        Random random,
        TilePosc activatorPos,
        Direction direction,
        double offX,
        double offY,
        double offZ,
        @Share("aether$aerogelPlaced") LocalBooleanRef aerogelPlaced
    ) {
        if (!aether$isAetherLava(stack, world)) {
            return original.call(instance, pos, fluidBlock);
        }

        boolean canPlace = instance.canBlockIdBePlacedAt(AetherBlocks.AEROGEL.id(), pos, true, direction.side());
        boolean placed = canPlace && original.call(instance, pos, AetherBlocks.AEROGEL);
        aerogelPlaced.set(placed);
        return placed;
    }

    @Inject(
        method = "onUseByActivator",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/world/World;setBlockTypeNotify(Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/block/Block;)Z",
            ordinal = 1,
            shift = At.Shift.AFTER
        ),
        cancellable = true
    )
    private void aether$doNotConsumeFailedActivatorPlacement(
        ItemStack stack,
        World world,
        TileEntityActivator activator,
        Random random,
        TilePosc activatorPos,
        Direction direction,
        double offX,
        double offY,
        double offZ,
        CallbackInfo ci,
        @Share("aether$aerogelPlaced") LocalBooleanRef aerogelPlaced
    ) {
        if (aether$isAetherLava(stack, world) && !aerogelPlaced.get()) {
            ci.cancel();
        }
    }
}
