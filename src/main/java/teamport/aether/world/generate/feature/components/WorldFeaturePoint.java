package teamport.aether.world.generate.feature.components;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;

import java.util.Objects;

public class WorldFeaturePoint {
    public int x;
    public int y;
    public int z;

    public WorldFeaturePoint() {
        this.x = this.y = this.z = 0;
    }

    public WorldFeaturePoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public WorldFeaturePoint(int length) {
        this.x = length;
        this.y = length;
        this.z = length;
    }

    public static WorldFeaturePoint wfp(int x, int y, int z) {
        return new WorldFeaturePoint(x, y, z);
    }

    public static WorldFeaturePoint wfpoint(Entity e) {
        return new WorldFeaturePoint((int) e.x, (int) e.y, (int) e.z);
    }

    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("(x:%d y:%d z:%d)", x, y, z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof WorldFeaturePoint)) return false;
        WorldFeaturePoint mem = (WorldFeaturePoint) o;
        return mem.x == this.x && mem.y == this.y && mem.z == this.z;
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

    public void writeFromNBT(CompoundTag tag) {
        tag.putInt("x", this.x);
        tag.putInt("y", this.y);
        tag.putInt("z", this.z);
    }

    public void readFromNBT(CompoundTag tag) {
        this.x = tag.getInteger("x");
        this.y = tag.getInteger("y");
        this.z = tag.getInteger("z");
    }

    public WorldFeaturePoint copy() {
        return new WorldFeaturePoint(this.x, this.y, this.z);
    }

    public WorldFeaturePoint add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public WorldFeaturePoint add(WorldFeaturePoint point) {
        this.x += point.x;
        this.y += point.y;
        this.z += point.z;
        return this;
    }

    public WorldFeaturePoint subtract(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public WorldFeaturePoint subtract(WorldFeaturePoint point) {
        this.x -= point.x;
        this.y -= point.y;
        this.z -= point.z;
        return this;
    }

    public WorldFeaturePoint multiply(int scala) {
        this.x *= scala;
        this.y *= scala;
        this.z *= scala;
        return this;
    }

    public WorldFeaturePoint divide(int scala) {
        this.x /= scala;
        this.y /= scala;
        this.z /= scala;
        return this;
    }

    public WorldFeaturePoint moveInDirection(Direction direction) {
        Side side = direction.getSide();
        this.x += side.getOffsetX();
        this.y += side.getOffsetY();
        this.z += side.getOffsetZ();
        return this;
    }

    public double distanceTo(WorldFeaturePoint point) {
        double dx = (double) this.x - point.x;
        double dy = (double) this.y - point.y;
        double dz = (double) this.z - point.z;
        return MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceTo(int x, int y, int z) {
        double dx = (double) this.x - x;
        double dy = (double) this.y - y;
        double dz = (double) this.z - z;
        return MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public WorldFeaturePoint rotateY(float radian) {
        float cos = MathHelper.cos(radian);
        float sin = MathHelper.sin(radian);
        int x = (int) Math.round((double) this.x * (double) cos - (double) this.z * (double) sin);
        int z = (int) Math.round((double) this.x * (double) sin + (double) this.z * (double) cos);
        this.x = x;
        this.z = z;
        return this;
    }

    public WorldFeaturePoint rotateY(Direction direction) {
        switch (direction) {
            case EAST: {
                this.set(this.z, this.y, this.x);
                break;
            }
            case SOUTH: {
                this.set(-this.x, this.y, -this.z);
                break;
            }
            case WEST: {
                this.set(-this.z, this.y, this.x);
                break;
            }
            case NORTH:
            default: {
                break;
            }
        }
        return this;
    }

    public WorldFeaturePoint rotateX(float radian) {
        float cos = MathHelper.cos(radian);
        float sin = MathHelper.sin(radian);
        int y = (int) Math.round((double) this.y * (double) cos - (double) this.z * (double) sin);
        int z = (int) Math.round((double) this.y * (double) sin + (double) this.z * (double) cos);
        this.y = y;
        this.z = z;
        return this;
    }

    public WorldFeaturePoint rotateX(Direction direction) {
        switch (direction) {
            case UP: {
                this.set(this.x, this.z, this.y);
                break;
            }
            case SOUTH: {
                this.set(this.x, -this.y, -this.z);
                break;
            }
            case DOWN: {
                this.set(this.x, this.z, -this.y);
                break;
            }
            case NORTH:
            default: {
                break;
            }
        }
        return this;
    }

    public WorldFeaturePoint rotateZ(float radian) {
        float cos = MathHelper.cos(radian);
        float sin = MathHelper.sin(radian);
        int x = (int) Math.round((double) this.x * (double) cos - (double) this.y * (double) sin);
        int y = (int) Math.round((double) this.x * (double) sin + (double) this.y * (double) cos);
        this.x = x;
        this.y = y;
        return this;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public WorldFeaturePoint rotateZ(Direction direction) {
        switch (direction) {
            case UP: {
                this.set(this.y, this.x, this.z);
                break;
            }
            case WEST: {
                this.set(-this.x, -this.y, this.z);
                break;
            }
            case DOWN: {
                this.set(this.y, -this.x, this.z);
                break;
            }
            case EAST:
            default: {
                break;
            }
        }
        return this;
    }

    public WorldFeaturePoint rotateYAroundPivot(WorldFeaturePoint pivotPoint, float angle) {
        return this.subtract(pivotPoint).rotateY(MathHelper.toRadians(angle)).add(pivotPoint);
    }

    public WorldFeaturePoint rotateXAroundPivot(WorldFeaturePoint pivotPoint, float angle) {
        return this.subtract(pivotPoint).rotateX(MathHelper.toRadians(angle)).add(pivotPoint);
    }

    public WorldFeaturePoint rotateZAroundPivot(WorldFeaturePoint pivotPoint, float angle) {
        return this.subtract(pivotPoint).rotateZ(MathHelper.toRadians(angle)).add(pivotPoint);
    }

    public WorldFeaturePoint rotateYAroundPivot(int pivotX, int pivotY, int pivotZ, float angle) {
        return this.subtract(pivotX, pivotY, pivotZ).rotateY(MathHelper.toRadians(angle)).add(pivotX, pivotY, pivotZ);
    }

    public WorldFeaturePoint rotateXAroundPivot(int pivotX, int pivotY, int pivotZ, float angle) {
        return this.subtract(pivotX, pivotY, pivotZ).rotateX(MathHelper.toRadians(angle)).add(pivotX, pivotY, pivotZ);
    }

    public WorldFeaturePoint rotateZAroundPivot(int pivotX, int pivotY, int pivotZ, float angle) {
        return this.subtract(pivotX, pivotY, pivotZ).rotateZ(MathHelper.toRadians(angle)).add(pivotX, pivotY, pivotZ);
    }

    public WorldFeaturePoint rotateYAroundPivot(WorldFeaturePoint pivotPoint, Direction direction) {
        switch (direction) {
            case EAST: {
                this.subtract(pivotPoint);
                this.set(this.z, this.y, this.x);
                this.add(pivotPoint);
                break;
            }
            case SOUTH: {
                this.subtract(pivotPoint);
                this.set(-this.x, this.y, -this.z);
                this.add(pivotPoint);
                break;
            }
            case WEST: {
                this.subtract(pivotPoint);
                this.set(-this.z, this.y, this.x);
                this.add(pivotPoint);
                break;
            }
            case NORTH:
            default: {
                break;
            }
        }
        return this;
    }

}
