package teamport.aether.world.generate.feature.components;

import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import teamport.aether.helper.Pair;

public class WorldFeatureBlock {

    public double x,y,z;
    public int blockID;
    public int metadata = 0;
    boolean withNotify;

    WorldFeatureBlock(double x, double y, double z, int blockID, boolean withNotify){
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockID = blockID;
        this.withNotify = withNotify;
    }

    WorldFeatureBlock( double x, double y, double z, int blockID, int metadata, boolean withNotify) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockID = blockID;
        this.metadata = metadata;
        this.withNotify = withNotify;
    }

    WorldFeatureBlock(double x, double y, double z, Pair<Integer, Integer> blockAndMeta, boolean withNotify) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockID = blockAndMeta.first;
        this.metadata = blockAndMeta.second;
        this.withNotify = withNotify;
    }

    public static WorldFeatureBlock wfb(double x, double y, double z, int blockID, int metadata, boolean withNotify){
        return new WorldFeatureBlock(x, y, z, blockID, metadata, withNotify);
    }
    public static WorldFeatureBlock wfb(double x, double y, double z, int blockID, boolean withNotify){
        return new WorldFeatureBlock(x, y, z, blockID, withNotify);
    }
    public static WorldFeatureBlock wfb(double x, double y, double z, Pair<Integer, Integer> blockAndMeta, boolean withNotify){
        return new WorldFeatureBlock(x, y, z, blockAndMeta, withNotify);
    }


    public void translateXZ(Direction direction){
        float angleInDegree = direction.getHorizontalIndex() * 90.0F;
        Vec3 vec = Vec3.getPermanentVec3(this.x, this.y, this.z);
        vec.rotateAroundY(MathHelper.toRadians(angleInDegree));
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
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

    public static int conv2Int(double coordinate) {
        if(coordinate < 0){
            return MathHelper.floor(coordinate);
        }else{
            return MathHelper.ceil(coordinate);
        }
    }

}
