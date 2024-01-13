package bta.aether.block;

import net.minecraft.core.block.BlockPortal;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockPortalAether extends BlockPortal {
    public BlockPortalAether(String key, int id, int targetDimension, int portalMaterialId, int portalTriggerId) {
        super(key, id, targetDimension, portalMaterialId, portalTriggerId);
    }
@Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        if ((meta & 2) > 0 && rand.nextInt(20) == 0) {
            world.playSoundEffect(SoundType.WORLD_SOUNDS, (double)x + 0.5, (double)y + 0.5, (double)z + 0.5, "aether.aetherportal", 0.5F, rand.nextFloat() * 0.4F + 0.8F);
        }

        for(int l = 0; l < 4; ++l) {
            double d = (double)((float)x + rand.nextFloat());
            double d1 = (double)((float)y + rand.nextFloat());
            double d2 = (double)((float)z + rand.nextFloat());
            double d3 = 0.0;
            double d4 = 0.0;
            double d5 = 0.0;
            int i1 = rand.nextInt(2) * 2 - 1;
            d3 = ((double)rand.nextFloat() - 0.5) * 0.5;
            d4 = ((double)rand.nextFloat() - 0.5) * 0.5;
            d5 = ((double)rand.nextFloat() - 0.5) * 0.5;
            if (world.getBlockId(x - 1, y, z) != this.id && world.getBlockId(x + 1, y, z) != this.id) {
                d = (double)x + 0.5 + 0.25 * (double)i1;
                d3 = (double)(rand.nextFloat() * 2.0F * (float)i1);
            } else {
                d2 = (double)z + 0.5 + 0.25 * (double)i1;
                d5 = (double)(rand.nextFloat() * 2.0F * (float)i1);
            }

            world.spawnParticle("portal", d, d1, d2, d3, d4, d5);
        }

    }

}
