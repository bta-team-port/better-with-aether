package bta.aether.item;

import bta.aether.catalyst.effects.AetherEffects;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFlower;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;

public class ItemSkyrootBucket extends Item {
    private final int idToPlace;
    private final int foodType;

    public ItemSkyrootBucket(int id, Block blockToPlace, int foodType) {
        super(id);
        this.maxStackSize = 1;
        if (blockToPlace == null) {
            this.idToPlace = -1;
        } else {
            this.idToPlace = blockToPlace.id;
        }
        this.foodType = foodType;
    }

    void applyEffect(EntityPlayer entityplayer) {
        IHasEffects effectPlayer = (IHasEffects)entityplayer;
        EffectStack stack;
        switch (foodType) {
            case 1:
                entityplayer.heal(4);
                break;
            case 2:
                int amount = 10;
                if (effectPlayer.getContainer().hasEffect(AetherEffects.poisonEffect)) {
                    effectPlayer.getContainer().remove(AetherEffects.poisonEffect);
                }
                stack = new EffectStack(effectPlayer, AetherEffects.poisonEffect, amount);
                effectPlayer.getContainer().add(stack);
                stack.start(effectPlayer.getContainer());
                break;
            case 3:
                if (effectPlayer.getContainer().hasEffect(AetherEffects.remedyEffect)) {
                    effectPlayer.getContainer().remove(AetherEffects.remedyEffect);
                }
                stack = new EffectStack(effectPlayer, AetherEffects.remedyEffect, 1);
                effectPlayer.getContainer().add(stack);
                stack.start(effectPlayer.getContainer());
                break;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (this.foodType != 0 && ItemBucketEmpty.useBucket(entityplayer, new ItemStack(AetherItems.bucketSkyroot))) {
            applyEffect(entityplayer);
            return itemstack;
        }

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

                switch (movingobjectposition.side) {
                    case TOP:
                        j++;
                        break;
                    case BOTTOM:
                        j--;
                        break;
                    case EAST:
                        i++;
                        break;
                    case WEST:
                        i--;
                        break;
                    case SOUTH:
                        k++;
                        break;
                    case NORTH:
                        k--;
                        break;
                }

                if (!(world.getBlock(i, j, k) == null || world.getBlock(i, j, k) instanceof BlockFlower)) {
                    return itemstack;
                } else if (!world.canMineBlock(entityplayer, i, j, k)) {
                    return itemstack;
                }

                if (idToPlace != 0) {
                    if (ItemBucketEmpty.useBucket(entityplayer, new ItemStack(AetherItems.bucketSkyroot))) {
                        world.setBlockWithNotify(i, j, k, idToPlace);
                        entityplayer.swingItem();
                    }
                }
            }

        }
        return itemstack;
    }

}

