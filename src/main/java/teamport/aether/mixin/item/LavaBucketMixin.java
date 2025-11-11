package teamport.aether.mixin.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;

@Mixin(value = ItemBucket.class, remap = false)
public abstract class LavaBucketMixin extends Item {

    @Shadow
    @Final
    private @Nullable Block<?> blockToPlace;

    public LavaBucketMixin(NamespaceID namespaceId, int id) {
        super(namespaceId, id);
    }

    @Inject(method = "onUseItem", at = @At("HEAD"), cancellable = true)
    public void netherAttemptPlaceWater(ItemStack stack, World world, Player player, CallbackInfoReturnable<ItemStack> info) {
        if (world.dimension == Dimension.NETHER && blockToPlace != null && blockToPlace.hasTag(BlockTags.IS_WATER)) {

            float pitch = player.xRotO + (player.xRot - player.xRotO);
            float yaw = player.yRotO + (player.yRot - player.yRotO);

            double playerPosX = player.xo + (player.x - player.xo);
            double playerPosY = player.yo + (player.y - player.yo) + 1.62 - (double) player.heightOffset;
            double playerPosZ = player.zo + (player.z - player.zo);

            Vec3 playerPosition = Vec3.getTempVec3(playerPosX, playerPosY, playerPosZ);

            float pitchCos = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
            float pitchSin = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
            float yawCos = -MathHelper.cos(-pitch * 0.01745329F);
            float yawSin = MathHelper.sin(-pitch * 0.01745329F);

            float pitchYawCos = pitchSin * yawCos;
            float pitchYawSin = pitchCos * yawCos;

            double distance = 7.0f;
            Vec3 endPosition = playerPosition.add((double) pitchYawCos * distance, (double) yawSin * distance, (double) pitchYawSin * distance);

            HitResult rayTraceResult = world.checkBlockCollisionBetweenPoints(playerPosition, endPosition, blockToPlace == null);

            if (rayTraceResult != null && rayTraceResult.hitType == HitResult.HitType.TILE) {

                int x = rayTraceResult.side.getOffsetX() + rayTraceResult.x;
                int y = rayTraceResult.side.getOffsetY() + rayTraceResult.y;
                int z = rayTraceResult.side.getOffsetZ() + rayTraceResult.z;

                if (world.getBlockId(x, y, z) != 0) {
                    info.setReturnValue(stack);
                    return;
                }

                player.swingItem();
                world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
                for (int i = 0; i < 8; ++i) {
                    ParticleMaker.spawnParticle(world, "largesmoke", (double) x + Math.random(), (double) y + .2, (double) z + Math.random(), 0.0, 0.0, 0.0, 0);
                }

                world.setBlockWithNotify(x, y, z, 0);

                if (player.getGamemode().consumeBlocks()) {
                    info.setReturnValue(new ItemStack(Items.BUCKET));
                    return;
                }

                info.setReturnValue(stack);
            }
        }
    }

    @Inject(method = "onUseItem", at = @At("HEAD"), cancellable = true)
    public void aetherAttemptPlaceLava(ItemStack stack, World world, Player player, CallbackInfoReturnable<ItemStack> info) {
        if (world.dimension == AetherDimension.AETHER && blockToPlace != null && blockToPlace.hasTag(BlockTags.IS_LAVA)) {

            float pitch = player.xRotO + (player.xRot - player.xRotO);
            float yaw = player.yRotO + (player.yRot - player.yRotO);

            double playerPosX = player.xo + (player.x - player.xo);
            double playerPosY = player.yo + (player.y - player.yo) + 1.62 - (double) player.heightOffset;
            double playerPosZ = player.zo + (player.z - player.zo);

            Vec3 playerPosition = Vec3.getTempVec3(playerPosX, playerPosY, playerPosZ);

            float pitchCos = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
            float pitchSin = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
            float yawCos = -MathHelper.cos(-pitch * 0.01745329F);
            float yawSin = MathHelper.sin(-pitch * 0.01745329F);

            float pitchYawCos = pitchSin * yawCos;
            float pitchYawSin = pitchCos * yawCos;

            double distance = 7.0f;
            Vec3 endPosition = playerPosition.add((double) pitchYawCos * distance, (double) yawSin * distance, (double) pitchYawSin * distance);

            HitResult rayTraceResult = world.checkBlockCollisionBetweenPoints(playerPosition, endPosition, blockToPlace == null);

            if (rayTraceResult != null && rayTraceResult.hitType == HitResult.HitType.TILE) {

                int x = rayTraceResult.side.getOffsetX() + rayTraceResult.x;
                int y = rayTraceResult.side.getOffsetY() + rayTraceResult.y;
                int z = rayTraceResult.side.getOffsetZ() + rayTraceResult.z;

                if (world.getBlockId(x, y, z) != 0) {
                    info.setReturnValue(stack);
                    return;
                }

                player.swingItem();
                world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                for (int l = 0; l < 8; ++l) {
                    double angle = Math.toRadians(l * 45);
                    ParticleMaker.spawnParticle(world, "smoke", (double) x + 0.5, y, (double) z + 0.5, -(Math.cos(angle) * 2) / 20.0, 0.03, -(Math.sin(angle) * 2) / 20.0, 0);
                }

                world.setBlockWithNotify(x, y, z, AetherBlocks.AEROGEL.id());

                if (player.getGamemode().consumeBlocks()) {
                    info.setReturnValue(new ItemStack(Items.BUCKET));
                    return;
                }

                info.setReturnValue(stack);
            }
        }
    }
}
