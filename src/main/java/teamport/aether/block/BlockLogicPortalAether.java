package teamport.aether.block;

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

    @Override
    public void animationTick(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        if ((meta & 2) > 0 && rand.nextInt(20) == 0) {
            world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x + 0.5, y + 0.5, z + 0.5, "aether:portal", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
        }

        for (int l = 0; l < 4; ++l) {
            double px = x + rand.nextDouble();
            double py = y + rand.nextDouble();
            double pz = z + rand.nextDouble();
            int i1 = rand.nextInt(2) * 2 - 1;
            double xd = (rand.nextDouble() - 0.5) * 0.5;
            double yd = (rand.nextDouble() - 0.5) * 0.5;
            double zd = (rand.nextDouble() - 0.5) * 0.5;
            if (world.getBlockId(x - 1, y, z) != this.block.id() && world.getBlockId(x + 1, y, z) != this.block.id()) {
                px = x + 0.5 + 0.25 * i1;
                xd = rand.nextDouble() * 2.0 * i1;
            } else {
                pz = z + 0.5 + 0.25 * i1;
                zd = rand.nextDouble() * 2.0 * i1;
            }

            ParticleMaker.spawnParticle(world, "portal", px, py, pz, xd, yd, zd, this.fromMetadata(meta).blockMeta);
        }

    }

    @Override
    public DyeColor fromMetadata(int meta) {
        return (meta & 8) == 0 ? DyeColor.BLUE : DyeColor.colorFromBlockMeta((meta & 240) >> 4);
    }
}
