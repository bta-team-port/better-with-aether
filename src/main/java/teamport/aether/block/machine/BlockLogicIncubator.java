package teamport.aether.block.machine;

import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.entity.TileEntityIncubator;
import teamport.aether.gui.AetherScreens;

import java.util.Random;

public class BlockLogicIncubator extends BlockLogicRotatable {
    public final boolean isActive;
    private static boolean keepIncubatorInventory = false;

    public BlockLogicIncubator(Block<?> block, boolean active) {
        super(block, Material.stone);
        this.isActive = active;
        block.withEntity(TileEntityIncubator::new);
    }

    public static boolean isKeepIncubatorInventory() {
        return keepIncubatorInventory;
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case PICK_BLOCK:
            case EXPLOSION:
            case PROPER_TOOL:
            case SILK_TOUCH:
            case PISTON_CRUSH:
                return new ItemStack[]{new ItemStack(AetherBlocks.INCUBATOR_IDLE)};
            default:
                return null;
        }
    }

    @Override
    public void animationTick(World world, int x, int y, int z, Random rand) {
        if (!this.isActive) {
            return;
        }
        if (rand.nextInt(4) > 0) return;
        double radius = 0.3;
        double angle = 2 * Math.PI * rand.nextDouble();
        double xPos = x + 0.5 + radius * Math.cos(angle);
        double yPos = y + 1.0;
        double zPos = z + 0.5 + radius * Math.sin(angle);
        double dy = (rand.nextGaussian() * 0.5 + 1.0) * 0.01;
        world.spawnParticle("flameambrosium", xPos, yPos, zPos, 0.0, dy, 0.0, 0);
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
        if (!world.isClientSide) {
            TileEntityIncubator tileEntityIncubator = (TileEntityIncubator) world.getTileEntity(x, y, z);
            ((AetherScreens) player).aether$displayIncubatorScreen(tileEntityIncubator);
        }
        return true;
    }

    public static void updateFurnaceBlockState(boolean lit, @NonNull World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null) {
            String msg = "Incubator is missing Tile Entity at x: " + x + " y: " + y + " z: " + z + ", block will be removed!";
            if (Global.BUILD_CHANNEL.isUnstableBuild()) {
                throw new RuntimeException(msg);
            }
            world.setBlockWithNotify(x, y, z, 0);
            return;
        }
        keepIncubatorInventory = true;
        if (lit) {
            world.setBlockWithNotify(x, y, z, AetherBlocks.INCUBATOR_ACTIVE.id());
        } else {
            world.setBlockWithNotify(x, y, z, AetherBlocks.INCUBATOR_IDLE.id());
        }
        keepIncubatorInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, meta);
        tileEntity.validate();
        world.setTileEntity(x, y, z, tileEntity);
    }

}
