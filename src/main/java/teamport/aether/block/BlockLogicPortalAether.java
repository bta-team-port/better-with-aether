package teamport.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicPortal;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class BlockLogicPortalAether extends BlockLogicPortal {

    public BlockLogicPortalAether(Block<?> block, Dimension targetDimension, Block<?> portalMaterial, Block<?> portalTrigger) {
        super(block, targetDimension, portalMaterial, portalTrigger);
    }

    @Override
    public void animationTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
        int meta = world.getBlockData(tilePos);
        if ((meta & 2) != 0 && rand.nextInt(20) == 0) {
            world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (double) tilePos.x() + (double) 0.5F, (double) tilePos.y() + (double) 0.5F, (double) tilePos.z() + (double) 0.5F, "aether:portal", 0.5F, rand.nextFloat() * 0.4F + 0.8F);
        }

        for (int l = 0; l < 4; ++l) {
            double px = (double) tilePos.x() + (double) rand.nextFloat();
            double py = (double) tilePos.y() + (double) rand.nextFloat();
            double pz = (double) tilePos.z() + (double) rand.nextFloat();
            int i1 = rand.nextInt(2) * 2 - 1;
            double xd = (rand.nextDouble() - (double) 0.5F) * (double) 0.5F;
            double yd = (rand.nextDouble() - (double) 0.5F) * (double) 0.5F;
            double zd = (rand.nextDouble() - (double) 0.5F) * (double) 0.5F;
            switch (meta & 1) {
                case 0:
                    pz = (double) tilePos.z() + (double) 0.5F + (double) 0.25F * (double) i1;
                    zd = (double) rand.nextFloat() * (double) 2.0F * (double) i1;
                    break;
                case 1:
                    px = (double) tilePos.x() + (double) 0.5F + (double) 0.25F * (double) i1;
                    xd = (double) rand.nextFloat() * (double) 2.0F * (double) i1;
            }

            world.spawnParticle("portal", px, py, pz, xd, yd, zd, this.fromMetadata(meta).blockMeta, false);
        }

    }

    @Override
    public @NonNull DyeColor fromMetadata(int meta) {
        return (meta & 8) == 0 ? DyeColor.BLUE : DyeColor.colorFromBlockMeta((meta & 240) >> 4);
    }
}
