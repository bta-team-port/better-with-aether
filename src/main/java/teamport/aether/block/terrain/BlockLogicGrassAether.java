package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.data.gamerule.GameRule;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.IBonemealable;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;
import teamport.aether.world.AetherDimension;

import java.util.Random;

public class BlockLogicGrassAether extends BlockLogic implements IBonemealable {
    public final Block<?> dirt;

    public BlockLogicGrassAether(Block<?> block, Block<?> dirt) {
        super(block, Materials.GRASS);
        block.setTicking(true);
        this.dirt = dirt;
    }

    @Override
    public void updateTick(World world, TilePosc pos, Random rand, boolean scheduled) {
        int x = pos.x();
        int y = pos.y();
        int z = pos.z();
        if (!world.isClientSide) {
            if (world.getBlockLightValue(x, y + 1, z) < 4 && Blocks.lightBlock[world.getBlockId(x, y + 1, z)] > 2) {
                if (rand.nextInt(4) != 0) {
                    return;
                }

                world.setBlockWithNotify(x, y, z, this.dirt.id());
            } else if (world.getBlockLightValue(x, y + 1, z) >= 9) {
                int idToSpawn;
                for (idToSpawn = 0; idToSpawn < 4; ++idToSpawn) {
                    int x1 = x + rand.nextInt(3) - 1;
                    int y1 = y + rand.nextInt(5) - 3;
                    int z1 = z + rand.nextInt(3) - 1;
                    if (world.isBlockLoaded(x1, y1, z1)
                        && world.getBlockId(x1, y1, z1) == this.dirt.id()
                        && world.getBlockLightValue(x1, y1 + 1, z1) >= 4
                        && Blocks.lightBlock[world.getBlockId(x1, y1 + 1, z1)] <= 2
                        && canGrassSpread(world)
                    ) {
                        world.setBlockWithNotify(x1, y1, z1, this.block.id());
                    }
                }

                if (world.getGameRuleValue(GameRules.DO_SEASONAL_GROWTH) && world.getBlockId(x, y + 1, z) == 0 && rand.nextInt(512) == 0 && (world.dimension == AetherDimension.getAether())) {
                    int r = rand.nextInt(400);
                    if (r < 26) {
                        idToSpawn = AetherBlocks.FLOWER_PURPLE.id();
                    } else if (r < 41) {
                        idToSpawn = AetherBlocks.FLOWER_WHITE.id();
                    } else {
                        idToSpawn = AetherBlocks.TALLGRASS_AETHER.id();
                    }

                    world.setBlockWithNotify(x, y + 1, z, idToSpawn);
                }
            }

        }
    }

    private static boolean canGrassSpread(World world) {
        GameRule<?> grassSpreading = Registries.GAME_RULES.getItem("doGrassSpreading");
        return grassSpreading == null || Boolean.TRUE.equals(world.getGameRuleValue(grassSpreading));
    }

    @Override
    public boolean onBonemealUsed(ItemStack itemstack, Player player, World world, TilePosc pos, Side side, double xPlaced, double yPlaced) {
        int blockX = pos.x();
        int blockY = pos.y();
        int blockZ = pos.z();
        if (!world.isClientSide) {
            Random random = world.rand;
            label175:
            for (int i = 0; i < 128; ++i) {
                int x = blockX;
                int y = blockY + 1;
                int z = blockZ;

                for (int j = 0; j < i / 16; ++j) {
                    x += random.nextInt(3) - 1;
                    y += (random.nextInt(3) - 1) * random.nextInt(3) / 2;
                    z += random.nextInt(3) - 1;

                    int blockBelowId = world.getBlockId(x, y - 1, z);
                    Block<?> blockBelow = Blocks.blocksList[blockBelowId];
                    if (blockBelow == null || !blockBelow.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS)) {
                        continue label175;
                    }
                }

                if (world.isAirBlock(x, y, z)) {
                    int rand = random.nextInt(10);
                    Block<?> plantBlock;
                    if (rand < 8) {
                        plantBlock = AetherBlocks.TALLGRASS_AETHER;
                    } else if (rand < 9) {
                        plantBlock = AetherBlocks.FLOWER_PURPLE;
                    } else {
                        plantBlock = AetherBlocks.FLOWER_WHITE;
                    }

                    if (plantBlock.canStay(world, new TilePos(x, y, z))) {
                        world.setBlockWithNotify(x, y, z, plantBlock.id());
                    }
                }
            }

            if (player.getGamemode().hasBlockConsumption()) {
                --itemstack.stackSize;
            }
        }
        player.swingItem();
        return false;
    }

    @Override
    public void onBlockPlacedByMob(World world, int x, int y, int z, @NonNull Side side, Mob mob, double xPlaced, double yPlaced) {
        world.setBlockMetadataWithNotify(x, y, z, 1);
    }

    @Override
    public int getPlacedBlockMetadata(@Nullable Player player, ItemStack stack, World world, int x, int y, int z, Side side, double xPlaced, double yPlaced) {
        return 1;
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, Side side, int meta, Player player, Item item) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem().equals(AetherItems.TOOL_SHOVEL_SKYROOT) && meta == 0 && player.getGamemode().hasBlockConsumption()) {
            this.onHarvest(world, player, new TilePos(x, y, z), 1, world.getTileEntity(x, y, z));
        }
    }


    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                return new ItemStack[]{new ItemStack(this)};
            default:
                return new ItemStack[]{new ItemStack(this.dirt)};
        }
    }
}
