package teamport.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTorch;
import net.minecraft.core.world.World;
import teamport.aether.helper.ParticleMaker;

import java.util.Random;

public class BlockLogicTorchAmbrosium extends BlockLogicTorch {
    public BlockLogicTorchAmbrosium(Block<?> block) {
        super(block);
        block.setTicking(true);
    }

    @SuppressWarnings("java:S131")
    @Override
    public void animationTick(World world, int x, int y, int z, Random rand) {
        double xPos = x + 0.5;
        double yPos = y + 0.7;
        double zPos = z + 0.5;
        double d3 = 0.22;
        double d4 = 0.27;
        int side = world.getBlockMetadata(x, y, z) & 7;
        switch (side) {
            case 1:
                ParticleMaker.spawnParticle(world, "flameambrosium", xPos - d4, yPos + d3, zPos, 0.0, 0.0, 0.0, 0);
                break;
            case 2:
                ParticleMaker.spawnParticle(world, "flameambrosium", xPos + d4, yPos + d3, zPos, 0.0, 0.0, 0.0, 0);
                break;
            case 3:
                ParticleMaker.spawnParticle(world, "flameambrosium", xPos, yPos + d3, zPos - d4, 0.0, 0.0, 0.0, 0);
                break;
            case 4:
                ParticleMaker.spawnParticle(world, "flameambrosium", xPos, yPos + d3, zPos + d4, 0.0, 0.0, 0.0, 0);
                break;
            case 5:
                ParticleMaker.spawnParticle(world, "flameambrosium", xPos, yPos, zPos, 0.0, 0.0, 0.0, 0);
        }

    }

}
