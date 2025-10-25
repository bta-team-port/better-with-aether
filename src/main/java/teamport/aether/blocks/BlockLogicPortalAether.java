package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicPortal;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import teamport.aether.helper.ParticleMaker;

import java.util.Random;

public class BlockLogicPortalAether extends BlockLogicPortal {

    public BlockLogicPortalAether(Block<?> block, Dimension targetDimension, Block<?> portalMaterial, Block<?> portalTrigger) {
        super(block, targetDimension, portalMaterial, portalTrigger);
    }

    public void animationTick(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        if ((meta & 2) > 0 && rand.nextInt(20) == 0) {
            world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, "aether:portal", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
        }

        for (int l = 0; l < 4; ++l) {
            double px = (double) x + (double) rand.nextFloat();
            double py = (double) y + (double) rand.nextFloat();
            double pz = (double) z + (double) rand.nextFloat();
            int i1 = rand.nextInt(2) * 2 - 1;
            double xd = (rand.nextDouble() - 0.5) * 0.5;
            double yd = (rand.nextDouble() - 0.5) * 0.5;
            double zd = (rand.nextDouble() - 0.5) * 0.5;
            if (world.getBlockId(x - 1, y, z) != this.block.id() && world.getBlockId(x + 1, y, z) != this.block.id()) {
                px = (double) x + 0.5 + 0.25 * (double) i1;
                xd = (double) rand.nextFloat() * 2.0 * (double) i1;
            } else {
                pz = (double) z + 0.5 + 0.25 * (double) i1;
                zd = (double) rand.nextFloat() * 2.0 * (double) i1;
            }

            ParticleMaker.spawnParticle(world, "portal", px, py, pz, xd, yd, zd, this.fromMetadata(meta).blockMeta);
        }

    }

    public DyeColor fromMetadata(int meta) {
        return (meta & 8) == 0 ? DyeColor.BLUE : DyeColor.colorFromBlockMeta((meta & 240) >> 4);
    }
}
