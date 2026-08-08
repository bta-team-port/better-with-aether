package teamport.aether.block.machine;

import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
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
import teamport.aether.block.entity.TileEntityEnchanter;
import teamport.aether.gui.AetherScreens;

import java.util.Random;

public class BlockLogicEnchanter extends BlockLogicRotatable {
    public final boolean isActive;
    private static boolean keepEnchanterInventory = false;

    public BlockLogicEnchanter(Block<?> block, boolean active) {
        super(block, Materials.STONE);
        this.isActive = active;
        block.withEntity(TileEntityEnchanter::new);
    }

    public static boolean isKeepEnchanterInventory() {
        return keepEnchanterInventory;
    }

    @Override
    public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return switch (dropCause) {
            case PICK_BLOCK, EXPLOSION, PROPER_TOOL, SILK_TOUCH, PISTON_CRUSH ->
                new ItemStack[]{new ItemStack(AetherBlocks.ENCHANTER_IDLE)};
            default -> null;
        };
    }

    @Override
    public void animationTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
        if (this.isActive) {
            double poxX = (double) tilePos.x() + (double) 0.5F;
            double posY = (double) tilePos.y() + (double) 0.0F + (double) (rand.nextFloat() * 6.0F / 16.0F);
            double posZ = (double) tilePos.z() + (double) 0.5F;
            double f3 = 0.52F;
            double f4 = rand.nextFloat() * 0.6F - 0.3F;
            switch (BlockLogicRotatable.getDirectionFromMeta(world.getBlockData(tilePos))) {
                case WEST:
                    world.spawnParticle("flameenchanter", poxX - f3, posY, posZ + f4, 0.0F, 0.0F, 0.0F, 0, false);
                    break;
                case EAST:
                    world.spawnParticle("flameenchanter", poxX + f3, posY, posZ + f4, 0.0F, 0.0F, 0.0F, 0, false);
                    break;
                case NORTH:
                    world.spawnParticle("flameenchanter", poxX + f4, posY, posZ - f3, 0.0F, 0.0F, 0.0F, 0, false);
                    break;
                case SOUTH:
                    world.spawnParticle("flameenchanter", poxX + f4, posY, posZ + f3, 0.0F, 0.0F, 0.0F, 0, false);
            }

        }
    }

    @Override
    public boolean onInteracted(@NonNull World world, @NonNull TilePosc pos, @NonNull Player player, Side side, double xPlaced, double yPlaced) {
        if (!world.isClientSide) {
            TileEntityEnchanter tileEntityEnchanter = (TileEntityEnchanter) world.getTileEntity(pos);
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
