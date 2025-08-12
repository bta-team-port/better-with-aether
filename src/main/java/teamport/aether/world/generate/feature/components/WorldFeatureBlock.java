package teamport.aether.world.generate.feature.components;

import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import teamport.aether.helper.Pair;

public class WorldFeatureBlock extends WorldFeaturePoint {
    public int blockID = 0;
    public int metadata = 0;
    boolean withNotify = false;


    WorldFeatureBlock( int x, int y, int z, int blockID, int metadata, boolean withNotify) {
        super(x,y,z);
        this.blockID = blockID;
        this.metadata = metadata;
        this.withNotify = withNotify;
    }

    WorldFeatureBlock(int x, int y, int z, Pair<Integer, Integer> blockAndMeta, boolean withNotify) {
        super(x,y,z);
        this.blockID = blockAndMeta.first;
        this.metadata = blockAndMeta.second;
        this.withNotify = withNotify;
    }

    public static WorldFeatureBlock wfb(int x, int y, int z){
        return new WorldFeatureBlock(x, y, z, 0, 0, false);
    }
    public static WorldFeatureBlock wfb(int x, int y, int z, int blockID){
        return new WorldFeatureBlock(x, y, z, blockID, 0, false);
    }
    public static WorldFeatureBlock wfb(int x, int y, int z, int blockID, int metadata){
        return new WorldFeatureBlock(x, y, z, blockID, metadata, false);
    }
    public static WorldFeatureBlock wfb(int x, int y, int z, int blockID, boolean withNotify){
        return new WorldFeatureBlock(x, y, z, blockID, 0, withNotify);
    }
    public static WorldFeatureBlock wfb(int x, int y, int z, int blockID, int metadata, boolean withNotify){
        return new WorldFeatureBlock(x, y, z, blockID, metadata, withNotify);
    }
    public static WorldFeatureBlock wfb(int x, int y, int z, Pair<Integer, Integer> blockAndMeta, boolean withNotify){
        return new WorldFeatureBlock(x, y, z, blockAndMeta, withNotify);
    }

    public void place(World world){
        int ix = WorldFeatureBlock.conv2Int(this.x);
        int iy = WorldFeatureBlock.conv2Int(this.y);
        int iz = WorldFeatureBlock.conv2Int(this.z);
        this.place(world, ix, iy, iz);
    }

    private void place(World world, int ix, int iy, int iz){
        if (this.withNotify){
            world.setBlockAndMetadataWithNotify(ix, iy, iz, this.blockID, this.metadata);
        } else {
            world.setBlockAndMetadata(ix, iy, iz, this.blockID, this.metadata);
        }
    }
}
