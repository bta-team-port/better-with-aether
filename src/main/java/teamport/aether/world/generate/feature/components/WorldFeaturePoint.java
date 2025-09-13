package teamport.aether.world.generate.feature.components;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.Vec3;
import teamport.aether.helper.Pair;

import java.util.Objects;
import java.util.function.Consumer;

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

    public static WorldFeaturePoint wfpoint(Entity e) {
        return new WorldFeaturePoint((int) e.x, (int) e.y, (int) e.z);
    }

    @Override
    public String toString(){
        return String.format("wfp[x:%d y:%d z:%d]",x,y,z);
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y,z);
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(!(o instanceof WorldFeaturePoint)) return false;
        WorldFeaturePoint mem = (WorldFeaturePoint) o;
        return mem.x == this.x && mem.y == this.y && mem.z == this.z;
    }

    public WorldFeaturePoint copy(){
        return new WorldFeaturePoint(this.x, this.y, this.z);
    }

    public void move(int length, int height, int width) {
        this.x += length;
        this.y += height;
        this.z += width;
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
        this.x = (int) Math.round(vec.x + fixPointX);
        this.y = (int) Math.round(vec.y + fixPointY);
        this.z = (int) Math.round(vec.z + fixPointZ);
    }

    public void rotateFixPointZAxis(int fixPointX, int fixPointY, int fixPointZ, float angle) {
        Vec3 vec = Vec3.getPermanentVec3(this.x - fixPointX, this.y - fixPointY, this.z - fixPointZ);
        vec.rotateAroundZ(MathHelper.toRadians(angle));
        this.x = (int) Math.round(vec.x + fixPointX);
        this.y = (int) Math.round(vec.y + fixPointY);
        this.z = (int) Math.round(vec.z + fixPointZ);
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

    public static void iterate3d(Pair<WorldFeaturePoint, WorldFeaturePoint> area, Consumer<WorldFeaturePoint> func) {
        iterate3d(area.first, area.second, func);
    }

    public static void iterate3d(WorldFeaturePoint first, WorldFeaturePoint second, Consumer<WorldFeaturePoint> func) {
        int firstX  = Math.min(first.x, second.x);
        int secondX = Math.max(first.x, second.x);
        int firstY  = Math.min(first.y, second.y);
        int secondY = Math.max(first.y, second.y);
        int firstZ  = Math.min(first.z, second.z);
        int secondZ = Math.max(first.z, second.z);

        for (int x = firstX; x <= secondX; x++) {
            for (int y = firstY; y <= secondY; y++) {
                for (int z = firstZ; z <= secondZ; z++) {
                    func.accept(wfpoint(x, y, z));
                }
            }
        }
    }


    public WorldFeaturePoint add(Side side) {
        WorldFeaturePoint result = this.copy();
        result.x += side.getOffsetX();
        result.y += side.getOffsetY();
        result.z += side.getOffsetZ();

        return result;
    }

    public WorldFeaturePoint add(Direction direction) {
        return this.add(direction.getSide());
    }
}
