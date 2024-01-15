package bta.aether.item;

import bta.aether.catalyst.effects.AetherEffects;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.Dimension;
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
        IHasEffects effectPlayer = (IHasEffects) entityplayer;
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
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (this.foodType != 0 && ItemBucketEmpty.useBucket(player, new ItemStack(AetherItems.bucketSkyroot))) {
            applyEffect(player);
            return stack;
        }

        float f9;
        float f6;
        float f8;
        double d3;
        float f5;
        float f = 1.0f;
        float f1 = player.xRotO + (player.xRot - player.xRotO) * f;
        float f2 = player.yRotO + (player.yRot - player.yRotO) * f;
        double posX = player.xo + (player.x - player.xo) * (double) f;
        double posY = player.yo + (player.y - player.yo) * (double) f + 1.62 - (double) player.heightOffset;
        double posZ = player.zo + (player.z - player.zo) * (double) f;
        Vec3d vec3d = Vec3d.createVector(posX, posY, posZ);
        float f3 = MathHelper.cos(-f2 * 0.01745329f - 3.141593f);
        float f4 = MathHelper.sin(-f2 * 0.01745329f - 3.141593f);
        float f7 = f4 * (f5 = -MathHelper.cos(-f1 * 0.01745329f));
        Vec3d vec3d1 = vec3d.addVector((double) f7 * (d3 = 5.0), (double) (f8 = (f6 = MathHelper.sin(-f1 * 0.01745329f))) * d3, (double) (f9 = f3 * f5) * d3);
        HitResult rayTraceResult = world.checkBlockCollisionBetweenPoints(vec3d, vec3d1, this.idToPlace == 0);
        if (rayTraceResult == null || rayTraceResult.hitType != HitResult.HitType.TILE) {
            return stack;
        }
        int x = rayTraceResult.x;
        int y = rayTraceResult.y;
        int z = rayTraceResult.z;
        if (!world.canMineBlock(player, x, y, z)) {
            return stack;
        }
        if (this.idToPlace < 0) {
            return new ItemStack(Item.bucket);
        }
        Block block = world.getBlock(x, y, z);
        if (block != null && !block.hasTag(BlockTags.PLACE_OVERWRITES) && !block.hasTag(BlockTags.BROKEN_BY_FLUIDS)) {
            Side side = rayTraceResult.side;
            x += side.getOffsetX();
            y += side.getOffsetY();
            z += side.getOffsetZ();
        }
        if (y < 0 || y >= world.getHeightBlocks()) {
            return stack;
        }
        if (world.isAirBlock(x, y, z) || !world.getBlockMaterial(x, y, z).isSolid()) {
            if (world.dimension == Dimension.nether && this.idToPlace == Block.fluidWaterFlowing.id) {
                world.playSoundEffect(SoundType.WORLD_SOUNDS, (float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
                for (int l = 0; l < 8; ++l) {
                    world.spawnParticle("largesmoke", (double)x + Math.random(), (double)y + Math.random(), (double)z + Math.random(), 0.0, 0.0, 0.0);
                }
            } else {
                if (this.idToPlace == Block.fluidWaterFlowing.id) {
                    world.playSoundEffect(SoundType.WORLD_SOUNDS, (float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f, "liquid.splash", 0.5f, 1.0f);
                }
                player.swingItem();
                Block block1 = world.getBlock(x, y, z);
                if (block1 != null) {
                    block1.dropBlockWithCause(world, EnumDropCause.WORLD, x, y, z, world.getBlockMetadata(x, y, z), null);
                }
                world.setBlockAndMetadataWithNotify(x, y, z, this.idToPlace, 0);
            }
            if (player.getGamemode().consumeBlocks()) {
                return new ItemStack(AetherItems.bucketSkyroot);
            }
        }
        player.swingItem();
        return stack;
    }
}
