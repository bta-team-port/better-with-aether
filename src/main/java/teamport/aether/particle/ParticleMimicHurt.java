package teamport.aether.particle;

import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.particle.ParticleBlockBreaking;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.PackedField;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.mixin.accessors.ParticleBlockBreakingAccessor;

public class ParticleMimicHurt extends ParticleBlockBreaking {
    public static final PackedField packedMetadata = new PackedField(0, 8);
    public static final PackedField packedBlockId = new PackedField(8, 24);

    public ParticleMimicHurt(World world, double x, double y, double z, double xa, double ya, double za, int data) {
        super(world, x, y, z, xa, ya, za, Blocks.AIR, Side.NONE, 0);
        int blockId = packedBlockId.get(data);
        int metadata = packedMetadata.get(data);
        Block<?> block = Blocks.getBlock(blockId);
        ((ParticleBlockBreakingAccessor)this).setBlock(block);
        this.tex = BlockModelDispatcher.getInstance().getDispatch(block).getParticleTexture(Side.NORTH, metadata);
    }

    public static int setData(int id, int metadata){
        return packedBlockId.set(packedMetadata.set(0, metadata & 0b1111_1111), id);
    }
}
