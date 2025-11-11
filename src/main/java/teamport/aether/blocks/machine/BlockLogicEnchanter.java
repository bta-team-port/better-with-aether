package teamport.aether.blocks.machine;

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
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.tile.TileEntityEnchanter;
import teamport.aether.gui.AetherScreens;
import teamport.aether.helper.ParticleMaker;

import java.util.Random;

public class BlockLogicEnchanter extends BlockLogicRotatable {
    public final boolean isActive;
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

    public void animationTick(World world, int x, int y, int z, Random rand) {
        if (this.isActive) {
            int l = world.getBlockMetadata(x, y, z);
            double poxX = (double) x + (double) 0.5F;
            double posY = (double) y + (double) 0.5F + (double) (rand.nextFloat() * 6.0F / 16.0F);
            double posZ = (double) z + (double) 0.5F;
            double f3 = 0.52F;
            double f4 = rand.nextFloat() * 0.6F - 0.3F;
            if (l == 4) {
                ParticleMaker.spawnParticle(world, "flameenchanter", poxX - f3, posY, posZ + f4, 0.0F, 0.0F, 0.0F, 0);
            } else if (l == 5) {
                ParticleMaker.spawnParticle(world, "flameenchanter", poxX + f3, posY, posZ + f4, 0.0F, 0.0F, 0.0F, 0);
            } else if (l == 2) {
                ParticleMaker.spawnParticle(world, "flameenchanter", poxX + f4, posY, posZ - f3, 0.0F, 0.0F, 0.0F, 0);
            } else if (l == 3) {
                ParticleMaker.spawnParticle(world, "flameenchanter", poxX + f4, posY, posZ + f3, 0.0F, 0.0F, 0.0F, 0);
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
