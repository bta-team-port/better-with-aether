package teamport.aether.blocks;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import teamport.aether.gui.IAetherScreens;
import teamport.aether.tile.TileEntityEnchanter;

import java.util.Random;

public class BlockLogicEnchanter extends BlockLogicRotatable {
    // TODO figure out what logger is needed here, if any at all
    private static final Logger LOGGER = LogUtils.getLogger();
    private final boolean isActive;
    public static boolean keepEnchanterInventory = false;

    public BlockLogicEnchanter(Block<?> block, boolean active) {
        super(block, Material.stone);
        this.isActive = active;
        block.withEntity(TileEntityEnchanter::new);
    }

    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case PICK_BLOCK:
            case EXPLOSION:
            case PROPER_TOOL:
            case SILK_TOUCH:
            case PISTON_CRUSH:
                return new ItemStack[]{new ItemStack(AetherBlocks.ENCHANTER_IDLE)};
            default:
                return null;
        }
    }

    // TODO echanter is missing the animation during opperation
    public void animationTick(World world, int x, int y, int z, Random rand) {}

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
        if (!world.isClientSide) {
            TileEntityEnchanter tileEntityEnchanter = (TileEntityEnchanter) world.getTileEntity(x, y, z);
            ((IAetherScreens)player).aether$displayEnchanterScreen(tileEntityEnchanter);
        }
        return true;
    }

    public static void updateFurnaceBlockState(boolean lit, @NotNull World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null) {
            String msg = "Enchanter is missing Tile Entity at x: " + x + " y: " + y + " z: " + z + ", block will be removed!";
            if (Global.BUILD_CHANNEL.isUnstableBuild()) {
                throw new RuntimeException(msg);
            }
            world.setBlockWithNotify(x, y, z, 0);
            LOGGER.warn(msg);
            return;
        }
        keepEnchanterInventory = true;
        if (lit) {
            world.setBlockWithNotify(x, y, z, Blocks.FURNACE_STONE_ACTIVE.id());
        } else {
            world.setBlockWithNotify(x, y, z, Blocks.FURNACE_STONE_IDLE.id());
        }
        keepEnchanterInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, meta);
        tileEntity.validate();
        world.setTileEntity(x, y, z, tileEntity);
    }

}
