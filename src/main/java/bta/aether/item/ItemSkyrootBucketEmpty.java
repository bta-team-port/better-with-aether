package bta.aether.item;

import net.minecraft.core.HitResult;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class ItemSkyrootBucketEmpty extends Item {
    public ItemSkyrootBucketEmpty(String name, int id) {
        super(name, id);
        this.maxStackSize = 64;
    }
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        float f = 1.0F;
        float f1 = entityplayer.xRotO + (entityplayer.xRot - entityplayer.xRotO) * f;
        float f2 = entityplayer.yRotO + (entityplayer.yRot - entityplayer.yRotO) * f;
        double d = entityplayer.xo + (entityplayer.x - entityplayer.xo) * (double)f;
        double d1 = entityplayer.yo + (entityplayer.y - entityplayer.yo) * (double)f + 1.62 - (double)entityplayer.heightOffset;
        double d2 = entityplayer.zo + (entityplayer.z - entityplayer.zo) * (double)f;
        Vec3d vec3d = Vec3d.createVector(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f9 = f3 * f5;
        double d3 = 5.0;
        Vec3d vec3d1 = vec3d.addVector((double)f7 * d3, (double)f6 * d3, (double)f9 * d3);
        HitResult movingobjectposition = world.checkBlockCollisionBetweenPoints(vec3d, vec3d1, true);
        if (movingobjectposition != null) {
            if (movingobjectposition.hitType == HitResult.HitType.TILE) {
                int i = movingobjectposition.x;
                int j = movingobjectposition.y;
                int k = movingobjectposition.z;
                if (!world.canMineBlock(entityplayer, i, j, k)) {
                    return itemstack;
                }

                if (world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMetadata(i, j, k) == 0) {
                    if (ItemBucketEmpty.useBucket(entityplayer, new ItemStack(AetherItems.bucketSkyrootWater))) {
                        world.setBlockWithNotify(i, j, k, 0);
                        entityplayer.swingItem();
                    }
                }
            }

        }
        return itemstack;
    }
}
