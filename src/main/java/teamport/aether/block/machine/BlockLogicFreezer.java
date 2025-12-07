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
import teamport.aether.block.entity.TileEntityFreezer;
import teamport.aether.gui.AetherScreens;
import teamport.aether.helper.ParticleMaker;

import java.util.Random;

public class BlockLogicFreezer extends BlockLogicRotatable {
    public final boolean isActive;
    private static boolean keepFreezerInventory = false;

    public BlockLogicFreezer(Block<?> block, boolean active) {
        super(block, Material.stone);
        this.isActive = active;
        block.withEntity(TileEntityFreezer::new);
    }

    public static boolean isKeepFreezerInventory() {
        return keepFreezerInventory;
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case PICK_BLOCK:
            case EXPLOSION:
            case PROPER_TOOL:
            case SILK_TOUCH:
            case PISTON_CRUSH:
                return new ItemStack[]{new ItemStack(AetherBlocks.FREEZER_IDLE)};
            default:
                return null;
        }
    }

    @Override
    public void animationTick(World world, int x, int y, int z, Random rand) {
        if (this.isActive) {
            double poxX = x + 0.5;
            double posY = y + 1.0 + (rand.nextDouble() * 6.0 / 16.0);
            double posZ = z + 0.5;
            for (int i = 0; i < 3; i++) {
                double maxSpeedX = rand.nextGaussian() * 0.05;
                double maxSpeedZ = rand.nextGaussian() * 0.05;
                ///  this is not broken, it works, its just that vanilla particles are broken at the time
                ParticleMaker.spawnParticle(world, "snowshovel", poxX, posY, posZ, maxSpeedX, 0.05, maxSpeedZ, 0);
            }
        }
    }


    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
        if (!world.isClientSide) {
            TileEntityFreezer tileEntityFreezer = (TileEntityFreezer) world.getTileEntity(x, y, z);
            ((AetherScreens) player).aether$displayFreezerScreen(tileEntityFreezer);
        }
        return true;
    }

    public static void updateFurnaceBlockState(boolean lit, @NonNull World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null) {
            String msg = "Freezer is missing Tile Entity at x: " + x + " y: " + y + " z: " + z + ", block will be removed!";
            if (Global.BUILD_CHANNEL.isUnstableBuild()) {
                throw new RuntimeException(msg);
            }
            world.setBlockWithNotify(x, y, z, 0);
            return;
        }
        keepFreezerInventory = true;
        if (lit) {
            world.setBlockWithNotify(x, y, z, AetherBlocks.FREEZER_ACTIVE.id());
        } else {
            world.setBlockWithNotify(x, y, z, AetherBlocks.FREEZER_IDLE.id());
        }
        keepFreezerInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, meta);
        tileEntity.validate();
        world.setTileEntity(x, y, z, tileEntity);
    }

}
