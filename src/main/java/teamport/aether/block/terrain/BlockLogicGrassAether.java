package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.data.gamerule.GameRules;
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
    public final @NonNull Block<?> dirt;

    public BlockLogicGrassAether(@NonNull Block<?> block, @NonNull Block<?> dirt) {
        super(block, Materials.GRASS);
        block.setTicking(true);
        this.dirt = dirt;
    }

    @Override
    @SuppressWarnings("java:S5411")
    public void updateTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand, boolean isRandomTick) {
        if (!world.isClientSide) {
            TilePos queryPos = new TilePos();
            if (world.getBlockLightValue(tilePos.up(queryPos)) < 4 && world.getBlockType(tilePos.up(queryPos)).lightBlock() > 2) {
                world.setBlockTypeNotify(tilePos, this.dirt);
            } else if (world.getBlockLightValue(tilePos.up(queryPos)) >= 9) {
                for (int i = 0; i < 4; ++i) {
                    int checkX = tilePos.x() + rand.nextInt(3) - 1;
                    int checkY = tilePos.y() + rand.nextInt(5) - 3;
                    int checkZ = tilePos.z() + rand.nextInt(3) - 1;
                    if (world.getBlockType(queryPos.set(checkX, checkY, checkZ)) == this.dirt && world.getBlockLightValue(queryPos.set(checkX, checkY + 1, checkZ)) >= 4 && world.getBlockType(queryPos.set(checkX, checkY + 1, checkZ)).lightBlock() <= 2) {
                        world.setBlockTypeNotify(queryPos.set(checkX, checkY, checkZ), this.block);
                    }
                }

                if (world.getGameRuleValue(GameRules.DO_SEASONAL_GROWTH) && world.getBlockType(tilePos.up(queryPos)) == Blocks.AIR && rand.nextInt(512) == 0 && (world.dimension == AetherDimension.getAether())) {
                    Block<?> blockToSpawn = null;
                    int r = rand.nextInt(400);
                    if (r < 26) {
                        blockToSpawn = AetherBlocks.FLOWER_PURPLE;
                    } else if (r < 41) {
                        blockToSpawn = AetherBlocks.FLOWER_WHITE;
                    } else {
                        blockToSpawn = Blocks.TALLGRASS;
                    }

                    if (blockToSpawn != null) {
                        world.setBlockTypeNotify(tilePos.up(queryPos), blockToSpawn);
                    }
                }
            }

        }
    }

    @Override
    @SuppressWarnings("java:S1119")
    public boolean onBonemealUsed(@NonNull ItemStack itemStack, @Nullable Player player, @NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, double xHit, double yHit) {
        int blockX = tilePos.x();
        int blockY = tilePos.y();
        int blockZ = tilePos.z();
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
                --itemStack.stackSize;
            }
        }
        player.swingItem();
        return false;
    }

    @Override
    public int getPlacedData(@Nullable Player player, @NonNull ItemStack itemStack, @NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, double xHit, double yHit) {
        return 1;
    }

    @Override
    public void onDestroyedByPlayer(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, int data, @NonNull Player player, @Nullable Item item) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem().equals(AetherItems.TOOL_SHOVEL_SKYROOT) && data == 0 && player.getGamemode().hasBlockConsumption()) {
            this.onHarvest(world, player, tilePos, 1, world.getTileEntity(tilePos));
        }
    }


    @Override
    public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
        return switch (dropCause) {
            case SILK_TOUCH, PICK_BLOCK -> new ItemStack[]{new ItemStack(this)};
            default -> new ItemStack[]{new ItemStack(this.dirt)};
        };
    }
}
