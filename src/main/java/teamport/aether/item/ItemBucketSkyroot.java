package teamport.aether.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.Nullable;
import teamport.aether.helper.ParticleMaker;

import java.util.Random;

public class ItemBucketSkyroot extends Item {
    public final @Nullable Block<?> blockToPlace;

    public ItemBucketSkyroot(String name, String namespaceId, int id, @Nullable Block<?> blockToPlace) {
        super(name, namespaceId, id);
        this.maxStackSize = 1;
        this.blockToPlace = blockToPlace;
    }

    @Override
    public ItemStack onUse(ItemStack stack, World world, Player player) {
        if (this.blockToPlace == null) {
            return new ItemStack(AetherItems.BUCKET_SKYROOT);
        } else {
            double reachDistance = player.getGamemode().getBlockReachDistance();
            HitResult rayTraceResult = player.rayCast(reachDistance, 1.0F, false, false, false);
            if (rayTraceResult instanceof HitResult.Tile) {
                HitResult.Tile tileHit = (HitResult.Tile) rayTraceResult;
                int x = tileHit.tilePos.x();
                int y = tileHit.tilePos.y();
                int z = tileHit.tilePos.z();
                if (world.canMineBlock(player, x, y, z)) {
                    Block<?> block = world.getBlock(x, y, z);
                    if (block != null && !block.hasTag(BlockTags.PLACE_OVERWRITES) && !block.hasTag(BlockTags.BROKEN_BY_FLUIDS)) {
                        Side side = tileHit.side;
                        x += side.offsetX();
                        y += side.offsetY();
                        z += side.offsetZ();
                    }

                    if (y >= 0 && y < world.getHeightBlocks()) {
                        if (world.isAirBlock(x, y, z) || !world.getBlockMaterial(x, y, z).isSolid()) {
                            if (world.dimension == Dimension.NETHER && blockToPlace.hasTag(BlockTags.IS_WATER)) {

                                if (world.getBlockId(x, y, z) != 0) {
                                    return stack;
                                }

                                player.swingItem();
                                world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x + 0.5f, y + 0.5f, z + 0.5f, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
                                for (int i = 0; i < 8; ++i) {
                                    ParticleMaker.spawnParticle(world, "largesmoke", x + Math.random(), y + .2, z + Math.random(), 0.0, 0.0, 0.0, 0);
                                }

                                world.setBlockWithNotify(x, y, z, 0);

                                if (player.getGamemode().hasBlockConsumption()) {
                                    return new ItemStack(AetherItems.BUCKET_SKYROOT);
                                }
                            } else {
                                if (this.blockToPlace == Blocks.FLUID_WATER_FLOWING) {
                                    world.playSoundEffect(player, SoundCategory.WORLD_SOUNDS, x + 0.5F, y + 0.5F, z + 0.5F, "liquid.splash", 0.5F, 1.0F);
                                }

                                player.swingItem();
                                Block<?> block1 = world.getBlock(x, y, z);
                                if (block1 != null) {
                                    block1.dropBlockWithCause(world, EnumDropCause.WORLD, x, y, z, world.getBlockMetadata(x, y, z), null, null);
                                }

                                world.setBlockAndMetadataWithNotify(x, y, z, this.blockToPlace.id(), 0);
                            }

                            if (player.getGamemode().hasBlockConsumption()) {
                                return new ItemStack(AetherItems.BUCKET_SKYROOT);
                            }
                        }

                    }
                }
            }
            return stack;
        }
    }

    @Override
    public void onUseByActivator(ItemStack itemStack, World world, TileEntityActivator activatorBlock, Random random, TilePosc blockPos, Direction direction, double offX, double offY, double offZ) {
        int blockX = blockPos.x();
        int blockY = blockPos.y();
        int blockZ = blockPos.z();
        if (this.blockToPlace == null) {
            itemStack.itemID = AetherItems.BUCKET_SKYROOT.id;
        } else {
            int x = blockX + direction.offsetX();
            int y = blockY + direction.offsetY();
            int z = blockZ + direction.offsetZ();
            Block<?> b = world.getBlock(x, y, z);
            if (b == null || BlockTags.PLACE_OVERWRITES.appliesTo(b) || BlockTags.BROKEN_BY_FLUIDS.appliesTo(b)) {
                world.setBlockWithNotify(x, y, z, this.blockToPlace.id());
                itemStack.itemID = AetherItems.BUCKET_SKYROOT.id;
            }

        }
    }
}
