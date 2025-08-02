package teamport.aether.helper;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;

public class BlockCoordinate {
    public Integer x;
    public Integer y;
    public Integer z;

    public BlockCoordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int[] values(){
        return new int[] {x, y, z};
    }

    public float distanceTo(BlockCoordinate cord) {
        return distanceTo(cord.x, cord.y, cord.z);
    }

    public float distanceTo(int x, int y, int z) {
        return (float) Math.sqrt(
            Math.pow(Math.abs(((float) this.x - x)), 2) +
            Math.pow(Math.abs(((float) this.y - y)), 2) +
            Math.pow(Math.abs(((float) this.z - z)), 2)
        );
    }

    public CompoundTag toCompoundTag() {
        CompoundTag result = new CompoundTag();
        result.put("x", new IntTag(x));
        result.put("y", new IntTag(y));
        result.put("z", new IntTag(z));

        return  result;
    }

    public static BlockCoordinate fromCompoundTag(CompoundTag tag) {
        return new BlockCoordinate(
            tag.getInteger("x"),
            tag.getInteger("y"),
            tag.getInteger("z")
        );
    }
}
