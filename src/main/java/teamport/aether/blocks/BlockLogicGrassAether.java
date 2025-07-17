package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.IBonemealable;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.items.AetherItems;

import java.util.Random;

public class BlockLogicGrassAether extends BlockLogic implements IBonemealable {
    public final Block<?> dirt;

    public BlockLogicGrassAether(Block<?> block, Block<?> dirt) {
        super(block, Material.grass);
        block.setTicking(true);
        this.dirt = dirt;
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isClientSide) {
            if (world.getBlockLightValue(x, y + 1, z) < 4 && Blocks.lightBlock[world.getBlockId(x, y + 1, z)] > 2) {
                if (rand.nextInt(4) != 0) {
                    return;
                }

                world.setBlockWithNotify(x, y, z, this.dirt.id());
            } else if (world.getBlockLightValue(x, y + 1, z) >= 9) {
                int idToSpawn;
                int r;
                for(idToSpawn = 0; idToSpawn < 4; ++idToSpawn) {
                    r = x + rand.nextInt(3) - 1;
                    int y1 = y + rand.nextInt(5) - 3;
                    int z1 = z + rand.nextInt(3) - 1;
                    if (world.getBlockId(r, y1, z1) == this.dirt.id() && world.getBlockLightValue(r, y1 + 1, z1) >= 4 && Blocks.lightBlock[world.getBlockId(r, y1 + 1, z1)] <= 2) {
                        world.setBlockWithNotify(r, y1, z1, this.block.id());
                    }
                }

                //TODO Add condition later to check if its in the aether dimension
                if (world.getGameRuleValue(GameRules.DO_SEASONAL_GROWTH) && world.getBlockId(x, y + 1, z) == 0 && rand.nextInt(256) == 0) {
                    r = rand.nextInt(400);
                    if (r < 200) {
                        idToSpawn = AetherBlocks.FLOWER_PURPLE.id();
                    } else if (r > 200) {
                        idToSpawn = AetherBlocks.FLOWER_WHITE.id();
                    } else if (rand.nextInt(8) == 0) {
                        idToSpawn = AetherBlocks.TALLGRASS_AETHER.id();
                    } else {
                        idToSpawn = AetherBlocks.TALLGRASS_AETHER.id();
                    }

                    world.setBlockWithNotify(x, y + 1, z, idToSpawn);
                }
            }

        }
    }

    public boolean onBonemealUsed(ItemStack itemstack,  Player player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        Random random = new Random();
        for (int l = 0; l < 16; ++l) {
            Block<?> plantBlock = new Block[]{AetherBlocks.FLOWER_PURPLE, AetherBlocks.TALLGRASS_AETHER, AetherBlocks.FLOWER_WHITE, AetherBlocks.TALLGRASS_AETHER}[random.nextInt(4)];

            int x = blockX + random.nextInt(8) - random.nextInt(8);
            int y = blockY + random.nextInt(4) - random.nextInt(4);
            int z = blockZ + random.nextInt(8) - random.nextInt(8);

            if (world.isAirBlock(x, y, z) && (plantBlock.canBlockStay(world, x, y, z))) {
                world.setBlockWithNotify(x, y, z, plantBlock.id());
            }
        }
        if (player.getGamemode().consumeBlocks()) {
            --itemstack.stackSize;
        }
        player.swingItem();
        return false;
    }

    public void onBlockPlacedByMob(World world, int x, int y, int z, @NotNull Side side, Mob mob, double xPlaced, double yPlaced) {
        world.setBlockMetadataWithNotify(x, y, z, 1);
    }

    public int getPlacedBlockMetadata(@Nullable Player player, ItemStack stack, World world, int x, int y, int z, Side side, double xPlaced, double yPlaced) {
        return 1;
    }

    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, Side side, int meta, Player player, Item item) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem().equals(AetherItems.TOOL_SHOVEL_SKYROOT) && meta == 0 && player.getGamemode().consumeBlocks()) {
            this.harvestBlock(world, player, x, y, z, 1, world.getTileEntity(x, y, z));
        }
    }


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
