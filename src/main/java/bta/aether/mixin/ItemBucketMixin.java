package bta.aether.mixin;

import bta.aether.block.AetherBlocks;
import bta.aether.world.AetherDimension;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemBucket.class, remap = false)
public class ItemBucketMixin extends Item{

    @Shadow
    private int idToPlace;

    public ItemBucketMixin(String name, int id) {
        super(name, id);
    }

    @Inject(method = "onItemRightClick", at = @At("HEAD"), cancellable = true)
    public void callOnItemRightClick(ItemStack stack, World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> info) {
        if (world.dimension == AetherDimension.dimensionAether && Block.getBlock(idToPlace).hasTag(BlockTags.IS_LAVA)) {

            float pitch = player.xRotO + (player.xRot - player.xRotO);
            float yaw = player.yRotO + (player.yRot - player.yRotO);

            double playerPosX = player.xo + (player.x - player.xo);
            double playerPosY = player.yo + (player.y - player.yo) + 1.62 - (double)player.heightOffset;
            double playerPosZ = player.zo + (player.z - player.zo);

            Vec3d playerPosition = Vec3d.createVector(playerPosX, playerPosY, playerPosZ);

            float pitchCos = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
            float pitchSin = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
            float yawCos = -MathHelper.cos(-pitch * 0.01745329F);
            float yawSin = MathHelper.sin(-pitch * 0.01745329F);

            float pitchYawCos = pitchSin * yawCos;
            float pitchYawSin = pitchCos * yawCos;

            double distance = 7.0f;
            Vec3d endPosition = playerPosition.addVector((double)pitchYawCos * distance, (double)yawSin * distance, (double)pitchYawSin * distance);

            HitResult rayTraceResult = world.checkBlockCollisionBetweenPoints(playerPosition, endPosition, this.idToPlace == 0);

            if (rayTraceResult != null && rayTraceResult.hitType == HitResult.HitType.TILE) {

                int x = rayTraceResult.side.getOffsetX() + rayTraceResult.x;
                int y = rayTraceResult.side.getOffsetY() + rayTraceResult.y;
                int z = rayTraceResult.side.getOffsetZ() + rayTraceResult.z;

                if (world.getBlockId(x,y, z) != 0) {
                    info.setReturnValue(stack);
                    return;
                }

                player.swingItem();
                world.playSoundEffect(SoundType.WORLD_SOUNDS, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                for (int l = 0; l < 8; ++l) {
                    double angle = Math.toRadians(l * 45);
                    world.spawnParticle("largesmoke", (double) x + 0.5, (double) y, (double) z + 0.5, -(Math.cos(angle) * 2) / 20.0, 0.03, -(Math.sin(angle) * 2)/ 20.0);
                }

                world.setBlockWithNotify(x, y, z, AetherBlocks.aerogel.id);

                if (player.getGamemode().consumeBlocks()) {
                    info.setReturnValue(new ItemStack(Item.bucket));
                    return;
                }

                info.setReturnValue(stack);
            }
        }
    }

}
