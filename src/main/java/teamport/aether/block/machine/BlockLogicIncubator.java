package teamport.aether.block.machine;

import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.entity.TileEntityIncubator;
import teamport.aether.gui.AetherScreens;

import java.util.Random;

public class BlockLogicIncubator extends BlockLogic {
    public final boolean isActive;
    private static boolean keepIncubatorInventory = false;

    public BlockLogicIncubator(Block<?> block, boolean active) {
        super(block, Materials.STONE);
        this.isActive = active;
        block.withEntity(TileEntityIncubator::new);
    }

    public static boolean isKeepIncubatorInventory() {
        return keepIncubatorInventory;
    }

    @Override
    public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return switch (dropCause) {
            case PICK_BLOCK, EXPLOSION, PROPER_TOOL, SILK_TOUCH, PISTON_CRUSH -> new ItemStack[]{new ItemStack(AetherBlocks.INCUBATOR_IDLE)};
            default -> null;
        };
    }

    @Override
    public void animationTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
        if (!this.isActive) {
            return;
        }
        if (rand.nextInt(4) > 0) return;
        double radius = 0.3;
        double angle = 2 * Math.PI * rand.nextDouble();
        double xPos = tilePos.x() + 0.5 + radius * Math.cos(angle);
        double yPos = tilePos.y() + 1.0;
        double zPos = tilePos.z() + 0.5 + radius * Math.sin(angle);
        double dy = (rand.nextGaussian() * 0.5 + 1.0) * 0.01;
        world.spawnParticle("flameambrosium", xPos, yPos, zPos, 0.0, dy, 0.0, 0, false);
    }

    @Override
    public boolean onInteracted(@NonNull World world, @NonNull TilePosc pos, @NonNull Player player, Side side, double xPlaced, double yPlaced) {
        if (!world.isClientSide) {
            TileEntityIncubator tileEntityIncubator = (TileEntityIncubator) world.getTileEntity(pos);
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
