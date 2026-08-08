package teamport.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTorch;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class BlockLogicTorchAmbrosium extends BlockLogicTorch {
    public BlockLogicTorchAmbrosium(@NonNull Block<?> block) {
        super(block);
        block.setTicking(true);
    }

    @Override
    @SuppressWarnings("java:S131")
    public void animationTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
        double xPos = (double) tilePos.x() + (double) 0.5F;
        double yPos = (double) tilePos.y() + 0.7;
        double zPos = (double) tilePos.z() + (double) 0.5F;
        double d3 = 0.22;
        double d4 = 0.27;
        int side = world.getBlockData(tilePos) & 7;
        switch (side) {
            case 1:
                world.spawnParticle("flameambrosium", xPos - d4, yPos + d3, zPos, 0.0F, 0.0F, 0.0F, 0, false);
                break;
            case 2:
                world.spawnParticle("flameambrosium", xPos + d4, yPos + d3, zPos, 0.0F, 0.0F, 0.0F, 0, false);
                break;
            case 3:
                world.spawnParticle("flameambrosium", xPos, yPos + d3, zPos - d4, 0.0F, 0.0F, 0.0F, 0, false);
                break;
            case 4:
                world.spawnParticle("flameambrosium", xPos, yPos + d3, zPos + d4, 0.0F, 0.0F, 0.0F, 0, false);
                break;
            case 5:
                world.spawnParticle("flameambrosium", xPos, yPos, zPos, 0.0F, 0.0F, 0.0F, 0, false);
        }

    }

}
