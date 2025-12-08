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
import teamport.aether.block.entity.TileEntityEnchanter;
import teamport.aether.gui.AetherScreens;

import java.util.Random;

public class BlockLogicEnchanter extends BlockLogicRotatable {
    public final boolean isActive;
    private static boolean keepEnchanterInventory = false;

    public BlockLogicEnchanter(Block<?> block, boolean active) {
        super(block, Material.stone);
        this.isActive = active;
        block.withEntity(TileEntityEnchanter::new);
    }

    public static boolean isKeepEnchanterInventory() {
        return keepEnchanterInventory;
    }

    @Override
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

    @Override
    public void animationTick(World world, int x, int y, int z, Random rand) {
        if (this.isActive) {
            int l = world.getBlockMetadata(x, y, z);
            double poxX = x + 0.5;
            double posY = y + 0.5 + (rand.nextDouble() * 6.0 / 16.0);
            double posZ = z + 0.5;
            double f3 = 0.52;
            double f4 = rand.nextDouble() * 0.6 - 0.3;
            if (l == 4) {
                world.spawnParticle("flameenchanter", poxX - f3, posY, posZ + f4, 0.0, 0.0, 0.0, 0);
            } else if (l == 5) {
                world.spawnParticle("flameenchanter", poxX + f3, posY, posZ + f4, 0.0, 0.0, 0.0, 0);
            } else if (l == 2) {
                world.spawnParticle("flameenchanter", poxX + f4, posY, posZ - f3, 0.0, 0.0, 0.0, 0);
            } else if (l == 3) {
                world.spawnParticle("flameenchanter", poxX + f4, posY, posZ + f3, 0.0, 0.0, 0.0, 0);
            }

        }
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
        if (!world.isClientSide) {
            TileEntityEnchanter tileEntityEnchanter = (TileEntityEnchanter) world.getTileEntity(x, y, z);
            ((AetherScreens) player).aether$displayEnchanterScreen(tileEntityEnchanter);
        }
        return true;
    }

    public static void updateFurnaceBlockState(boolean lit, @NonNull World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null) {
            String msg = "Enchanter is missing Tile Entity at x: " + x + " y: " + y + " z: " + z + ", block will be removed!";
            if (Global.BUILD_CHANNEL.isUnstableBuild()) {
                throw new RuntimeException(msg);
            }
            world.setBlockWithNotify(x, y, z, 0);
            return;
        }
        keepEnchanterInventory = true;
        if (lit) {
            world.setBlockWithNotify(x, y, z, AetherBlocks.ENCHANTER_ACTIVE.id());
        } else {
            world.setBlockWithNotify(x, y, z, AetherBlocks.ENCHANTER_IDLE.id());
        }
        keepEnchanterInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, meta);
        tileEntity.validate();
        world.setTileEntity(x, y, z, tileEntity);
    }

}
