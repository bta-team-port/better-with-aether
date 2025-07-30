package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicPortal;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockLogicPortalAether extends BlockLogicPortal {

    public BlockLogicPortalAether(Block<?> block, Dimension targetDimension, Block<?> portalMaterial, Block<?> portalTrigger) {
        super(block, targetDimension, portalMaterial, portalTrigger);
    }

    public boolean tryToCreatePortal(World world, int x, int y, int z, @Nullable DyeColor color) {
        if (color == null) {
            color = DyeColor.LIGHT_BLUE;
        }

        int[] bounds = this.getPortalDims(world, x, y, z, false);
        if (bounds == null) {
            return false;
        } else {
            x = bounds[1];
            y = bounds[2];
            z = bounds[3];
            world.noNeighborUpdate = true;

            int ra;
            int ry;
            int _x;
            for(ra = 1; ra < bounds[4]; ++ra) {
                for(ry = 1; ry < bounds[5]; ++ry) {
                    _x = x + (bounds[0] == 0 ? ra : 0);
                    int _y = y + ry;
                    int _z = z + (bounds[0] == 1 ? ra : 0);
                    world.setBlockAndMetadata(_x, _y, _z, this.block.id(), bounds[0] & 1);
                }
            }

            ra = x + (bounds[0] == 0 ? 1 : 0);
            ry = y + 1;
            _x = z + (bounds[0] == 1 ? 1 : 0);
            world.setBlockMetadata(ra, ry, _x, bounds[0] & 15 | 2);
            this.setColor(world, ra, ry, _x, color);
            world.markBlocksDirty(x + (bounds[0] == 0 ? 1 : 0), y + 1, z + (bounds[0] == 1 ? 1 : 0), x + (bounds[0] == 0 ? bounds[4] : 0), y + bounds[5], z + (bounds[0] == 1 ? bounds[4] : 0));
            world.noNeighborUpdate = false;
            return true;
        }
    }

    public void animationTick(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        if ((meta & 2) > 0 && rand.nextInt(20) == 0) {
            world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (double)x + 0.5, (double)y + 0.5, (double)z + 0.5, "aether:portal", 0.5F, rand.nextFloat() * 0.4F + 0.8F);
        }

        for(int l = 0; l < 4; ++l) {
            double px = (double)x + (double)rand.nextFloat();
            double py = (double)y + (double)rand.nextFloat();
            double pz = (double)z + (double)rand.nextFloat();
            int i1 = rand.nextInt(2) * 2 - 1;
            double xd = (rand.nextDouble() - 0.5) * 0.5;
            double yd = (rand.nextDouble() - 0.5) * 0.5;
            double zd = (rand.nextDouble() - 0.5) * 0.5;
            if (world.getBlockId(x - 1, y, z) != this.block.id() && world.getBlockId(x + 1, y, z) != this.block.id()) {
                px = (double)x + 0.5 + 0.25 * (double)i1;
                xd = (double)rand.nextFloat() * 2.0 * (double)i1;
            } else {
                pz = (double)z + 0.5 + 0.25 * (double)i1;
                zd = (double)rand.nextFloat() * 2.0 * (double)i1;
            }

            world.spawnParticle("portal", px, py, pz, xd, yd, zd, this.fromMetadata(meta).blockMeta);
        }

    }

    public DyeColor fromMetadata(int meta) {
        return (meta & 8) == 0 ? DyeColor.LIGHT_BLUE : DyeColor.colorFromBlockMeta((meta & 240) >> 4);
    }
}
