package bta.aether.item;

import bta.aether.Aether;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;

public class ItemSkyrootBucket extends Item {
    private int idToPlace;

    public ItemSkyrootBucket(int id, Block blockToPlace) {
        super(id);
        this.maxStackSize = 1;
        if (blockToPlace == null) {
            this.idToPlace = -1;
        } else {
            this.idToPlace = blockToPlace.id;
        }

    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        float f = 1.0F;
        float f1 = player.xRotO + (player.xRot - player.xRotO) * f;
        float f2 = player.yRotO + (player.yRot - player.yRotO) * f;
        double posX = player.xo + (player.x - player.xo) * (double)f;
        double posY = player.yo + (player.y - player.yo) * (double)f + 1.62 - (double)player.heightOffset;
        double posZ = player.zo + (player.z - player.zo) * (double)f;
        Vec3d vec3d = Vec3d.createVector(posX, posY, posZ);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f9 = f3 * f5;
        double d3 = 5.0;
        Vec3d vec3d1 = vec3d.addVector((double)f7 * d3, (double)f6 * d3, (double)f9 * d3);
        HitResult rayTraceResult = world.checkBlockCollisionBetweenPoints(vec3d, vec3d1, this.idToPlace == 0);
        if (rayTraceResult != null && rayTraceResult.hitType == HitResult.HitType.TILE) {
            int x = rayTraceResult.x;
            int y = rayTraceResult.y;
            int z = rayTraceResult.z;
            if (!world.canMineBlock(player, x, y, z)) {
                return stack;
            } else if (this.idToPlace < 0) {
                return new ItemStack(Aether.bucketSkyroot);
            } else {
                Block block = world.getBlock(x, y, z);
                if (block != null && !block.hasTag(BlockTags.PLACE_OVERWRITES) && !block.hasTag(BlockTags.BROKEN_BY_FLUIDS)) {
                    Side side = rayTraceResult.side;
                    x += side.getOffsetX();
                    y += side.getOffsetY();
                    z += side.getOffsetZ();
                }

                if (y >= 0 && y < world.getHeightBlocks()) {
                    if (world.isAirBlock(x, y, z) || !world.getBlockMaterial(x, y, z).isSolid()) {
                        if (world.dimension == Dimension.nether && this.idToPlace == Block.fluidWaterFlowing.id) {
                            world.playSoundEffect(SoundType.WORLD_SOUNDS, (double)z + 0.5, (double)y + 0.5, (double)x + 0.5, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                            for(int l = 0; l < 8; ++l) {
                                world.spawnParticle("largesmoke", (double)x + Math.random(), (double)y + Math.random(), (double)z + Math.random(), 0.0, 0.0, 0.0);
                            }
                        } else {
                            if (this.idToPlace == Block.fluidWaterFlowing.id) {
                                world.playSoundEffect(SoundType.WORLD_SOUNDS, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "liquid.splash", 0.5F, 1.0F);
                            }

                            player.swingItem();
                            Block block1 = world.getBlock(x, y, z);
                            if (block1 != null) {
                                block1.dropBlockWithCause(world, EnumDropCause.WORLD, x, y, z, world.getBlockMetadata(x, y, z), (TileEntity)null);
                            }

                            world.setBlockAndMetadataWithNotify(x, y, z, this.idToPlace, 0);
                        }

                        if (player.getGamemode().consumeBlocks) {
                            return new ItemStack(Aether.bucketSkyroot);
                        }
                    }

                    return stack;
                } else {
                    return stack;
                }
            }
        } else {
            return stack;
        }
    }
}

