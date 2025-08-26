package teamport.aether.world.generate.feature.components;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;

public class WorldFeaturePoint {
    public int x;
    public int y;
    public int z;

    public WorldFeaturePoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static WorldFeaturePoint wfpoint(int x, int y, int z) {
        return new WorldFeaturePoint(x, y, z);
    }

    public float distanceTo(WorldFeaturePoint cord) {
        return distanceTo(cord.x, cord.y, cord.z);
    }

    public float distanceTo(int x, int y, int z) {
        return (float) Math.sqrt(
                Math.pow(Math.abs(((float) this.x - x)), 2) +
                        Math.pow(Math.abs(((float) this.y - y)), 2) +
                        Math.pow(Math.abs(((float) this.z - z)), 2)
        );
    }

    public void move() {
    }

    public void rotateFixPointYAxis(int fixPointX, int fixPointY, int fixPointZ, float angle) {
        Vec3 vec = Vec3.getPermanentVec3(this.x - fixPointX, this.y - fixPointY, this.z - fixPointZ);
        vec.rotateAroundY(MathHelper.toRadians(angle));
        this.x = (int) Math.round(vec.x + fixPointX);
        this.y = (int) Math.round(vec.y + fixPointY);
        this.z = (int) Math.round(vec.z + fixPointZ);
    }

    public void rotateFixPointXAxis(int fixPointX, int fixPointY, int fixPointZ, float angle) {
        Vec3 vec = Vec3.getPermanentVec3(this.x - fixPointX, this.y - fixPointY, this.z - fixPointZ);
        vec.rotateAroundX(MathHelper.toRadians(angle));
    }

    public void rotateFixPointZAxis(int fixPointX, int fixPointY, int fixPointZ, float angle) {
        Vec3 vec = Vec3.getPermanentVec3(this.x - fixPointX, this.y - fixPointY, this.z - fixPointZ);
        vec.rotateAroundZ(MathHelper.toRadians(angle));
        this.x = (int) Math.round(vec.x + fixPointX);
        this.y = (int) Math.round(vec.y + fixPointY);
        this.z = (int) Math.round(vec.z + fixPointZ);
    }

    public void rotateSkewCenterYAxis(int fixPointX, int fixPointY, int fixPointZ, float angle) {
        int dx = this.x - fixPointX;
        int dy = this.y - fixPointY;
        int dz = this.z - fixPointZ;
        dx = (int) Math.round(dx - Math.tan(angle / 2) * dz);
        dz = (int) Math.round(dx * Math.sin(angle) + dz);
        dx = (int) Math.round(dx - Math.tan(angle / 2) * dz);
        this.x = dx + fixPointX;
        this.y = dy + fixPointY;
        this.z = dz + fixPointZ;
    }

    public void pointMirror(int fixPointX, int fixPointY, int fixPointZ) {
    }


    public CompoundTag toCompoundTag() {
        CompoundTag result = new CompoundTag();
        result.put("x", new IntTag(x));
        result.put("y", new IntTag(y));
        result.put("z", new IntTag(z));

        return result;
    }

    public static WorldFeaturePoint fromCompoundTag(CompoundTag tag) {
        return new WorldFeaturePoint(
                tag.getInteger("x"),
                tag.getInteger("y"),
                tag.getInteger("z")
        );
    }
}
