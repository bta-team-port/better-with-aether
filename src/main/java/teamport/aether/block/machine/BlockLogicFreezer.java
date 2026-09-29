package teamport.aether.block.machine;

import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.entity.TileEntityFreezer;
import teamport.aether.gui.AetherScreens;

import java.util.Random;

public class BlockLogicFreezer extends BlockLogicRotatable {
    public final boolean isActive;

    public BlockLogicFreezer(Block<?> block, boolean active) {
        super(block, Materials.STONE);
        this.isActive = active;
        block.withEntity(TileEntityFreezer::new);
    }

    @Override
    public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return switch (dropCause) {
            case PICK_BLOCK, EXPLOSION, PROPER_TOOL, SILK_TOUCH, PISTON_CRUSH ->
                new ItemStack[]{new ItemStack(AetherBlocks.FREEZER_IDLE)};
            default -> null;
        };
    }

    @Override
    public void animationTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
        if (!this.isActive) {
            return;
        }

        double poxX = tilePos.x() + 0.5;
        double posY = tilePos.y() + 1.1;
        double posZ = tilePos.z() + 0.5;
        for (int i = 0; i < 3; i++) {
            double maxSpeedX = rand.nextGaussian() * 0.05;
            double maxSpeedZ = rand.nextGaussian() * 0.05;
            ///  this is not broken, it works, its just that vanilla particles are broken at the time
            world.spawnParticle("snowshovel", poxX, posY, posZ, maxSpeedX, 0.05, maxSpeedZ, 0, false);
        }
    }


    @Override
    public boolean onInteracted(@NonNull World world, @NonNull TilePosc pos, @NonNull Player player, Side side, double xPlaced, double yPlaced) {
        if (!world.isClientSide) {
            TileEntityFreezer tileEntityFreezer = (TileEntityFreezer) world.getTileEntity(pos);
            ((AetherScreens) player).aether$displayFreezerScreen(tileEntityFreezer);
        }
        return true;
    }

    public static void updateFurnaceBlockState(@NonNull World world, @NonNull TilePos tilePos, boolean lit) {
        if (!(world.getTileEntity(tilePos) instanceof TileEntityFreezer)) {
            String msg = "Freezer is missing Tile Entity at " + tilePos + ", block will be removed!";
            if (Global.BUILD_CHANNEL.isUnstableBuild()) {
                throw new RuntimeException(msg);
            } else {
                world.setBlockTypeNotify(tilePos, Blocks.AIR);
            }
        } else {
            int meta = world.getBlockData(tilePos);
            Block<? extends BlockLogic> block = lit ? AetherBlocks.FREEZER_ACTIVE : AetherBlocks.FREEZER_IDLE;
            world.setBlockTypeDataRaw(tilePos, block, meta);
            world.notifyBlockChange(tilePos, block);
        }
    }

}
