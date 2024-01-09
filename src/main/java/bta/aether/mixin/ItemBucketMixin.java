package bta.aether.mixin;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemBucket.class, remap = false)
public class ItemBucketMixin {

    private int idToPlace;
    public ItemBucketMixin(int idToPlace) {
        this.idToPlace = idToPlace;
    }

    @Inject(
            method = "onItemRightClick(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/EntityPlayer;)Lnet/minecraft/core/item/ItemStack;",
            at = @At(value = "TAIL"),
            cancellable = true
    )


    public void aetherAerogel(ItemStack stack, World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir) {
        float f = 1.0F;
        float f1 = player.xRotO + (player.xRot - player.xRotO) * f;
        float f2 = player.yRotO + (player.yRot - player.yRotO) * f;
        double posX = player.xo + (player.x - player.xo) * (double)f;
        double posY = player.yo + (player.y - player.yo) * (double)f + 1.62 - (double)player.heightOffset;
        double posZ = player.zo + (player.z - player.zo) * (double)f;
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f9 = f3 * f5;
        double d3 = 5.0;
        Vec3d vec3d = Vec3d.createVector(posX, posY, posZ);
        Vec3d vec3d1 = vec3d.addVector((double)f7 * d3, (double)f6 * d3, (double)f9 * d3);
        HitResult rayTraceResult = world.checkBlockCollisionBetweenPoints(vec3d, vec3d1, this.idToPlace == 0);
        if (rayTraceResult != null && rayTraceResult.hitType == HitResult.HitType.TILE) {
            int x = rayTraceResult.x;
            int y = rayTraceResult.y;
            int z = rayTraceResult.z;
            if (y >= 0 && y < world.getHeightBlocks()) {
                if (world.isAirBlock(x, y, z) || !world.getBlockMaterial(x, y, z).isSolid()) {
                    if (world.dimension == Aether.dimensionAether && this.idToPlace == Block.fluidLavaFlowing.id) {
                        world.playSoundEffect(SoundType.WORLD_SOUNDS, (double) z + 0.5, (double) y + 0.5, (double) x + 0.5, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                        for (int l = 0; l < 8; ++l) {
                            world.spawnParticle("largesmoke", (double) x + Math.random(), (double) y + Math.random(), (double) z + Math.random(), 0.0, 0.0, 0.0);
                                world.setBlockWithNotify(x, y, z, AetherBlocks.aerogel.id);
                        }
                    }
                }
            }
        }
    }
}
