package teamport.aether.world.generate.feature.components;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.world.World;
import teamport.aether.helper.unboxed.IntPair;
import teamport.aether.helper.Pair;

public class WorldFeatureBlock extends WorldFeaturePoint {
    public int blockID = 0;
    public int metadata = 0;
    public boolean withNotify = false;


    WorldFeatureBlock(int x, int y, int z, int blockID, int metadata, boolean withNotify) {
        super(x, y, z);
        this.blockID = blockID;
        this.metadata = metadata;
        this.withNotify = withNotify;
    }

    WorldFeatureBlock(int x, int y, int z, Pair<Integer, Integer> blockAndMeta, boolean withNotify) {
        super(x, y, z);
        this.blockID = blockAndMeta.first;
        this.metadata = blockAndMeta.second;
        this.withNotify = withNotify;
    }

    WorldFeatureBlock(int x, int y, int z, IntPair blockAndMeta, boolean withNotify) {
        super(x, y, z);
        this.blockID = blockAndMeta.first;
        this.metadata = blockAndMeta.second;
        this.withNotify = withNotify;
    }

    public static WorldFeatureBlock wfb(WorldFeaturePoint point, int blockID, int metadata, boolean withNotify) {
        return new WorldFeatureBlock(point.x, point.y, point.z, blockID, metadata, withNotify);
    }

    public static WorldFeatureBlock wfb(WorldFeaturePoint point, int blockID, int metadata) {
        return new WorldFeatureBlock(point.x, point.y, point.z, blockID, metadata, false);
    }

    public static WorldFeatureBlock wfb(WorldFeaturePoint point) {
        return new WorldFeatureBlock(point.x, point.y, point.z, 0, 0, false);
    }

    public static WorldFeatureBlock wfb(int x, int y, int z) {
        return new WorldFeatureBlock(x, y, z, 0, 0, false);
    }

    public static WorldFeatureBlock wfb(int x, int y, int z, int blockID) {
        return new WorldFeatureBlock(x, y, z, blockID, 0, false);
    }

    public static WorldFeatureBlock wfb(int x, int y, int z, int blockID, int metadata) {
        return new WorldFeatureBlock(x, y, z, blockID, metadata, false);
    }

    public static WorldFeatureBlock wfb(int x, int y, int z, int blockID, boolean withNotify) {
        return new WorldFeatureBlock(x, y, z, blockID, 0, withNotify);
    }

    public static WorldFeatureBlock wfb(int x, int y, int z, int blockID, int metadata, boolean withNotify) {
        return new WorldFeatureBlock(x, y, z, blockID, metadata, withNotify);
    }

    public static WorldFeatureBlock wfb(int x, int y, int z, IntPair blockAndMeta, boolean withNotify) {
        return new WorldFeatureBlock(x, y, z, blockAndMeta, withNotify);
    }

    public static WorldFeatureBlock wfb(int x, int y, int z, Pair<Integer, Integer> blockAndMeta, boolean withNotify) {
        return new WorldFeatureBlock(x, y, z, blockAndMeta, withNotify);
    }

    public void place(World world) {
        this.place(world, x, y, z);
    }

    private void place(World world, int ix, int iy, int iz) {
        if (this.withNotify) {
            world.setBlockAndMetadataWithNotify(ix, iy, iz, this.blockID, this.metadata);
        } else {
            world.setBlockAndMetadata(ix, iy, iz, this.blockID, this.metadata);
        }
    }


    // TODO make horizontally rotating blocks also rotate
    @Override
    public void rotateYAroundPivot(int pivotX, int pivotY, int pivotZ, float angle) {
        super.rotateYAroundPivot(pivotX, pivotY, pivotZ, angle);
        /// For future reference, when I want to rotate the block as well.
        //BlockLogicChest
        //BlockLogicRotatable: furnace, trommle
        //BlockLogicStairs
        //BlockLogicLadder
        //BlockLogicFence
        //BlockLogicAxisAligned: log
        //BlockLogicFlower
        //BlockLogicTorch: redstone torch
        //BlockLogicButton
        //BlockLogicPressurePlate
        //BlockLogicVeryRotatable: motion sensor, dispenser, activator
        //BlockLogicPistonBase
        //BlockLogicTrapDoor
        //BlockLogicDoor
        //BlockLogicSign
    }

    @Override
    public CompoundTag toCompoundTag() {
        CompoundTag tag = super.toCompoundTag();
        tag.putInt("blockID", blockID);
        tag.putInt("blockMetadata", metadata);
        tag.putBoolean("withNotify", withNotify);
        return tag;
    }

    public static WorldFeatureBlock fromCompoundTag(CompoundTag tag) {
        return wfb(WorldFeaturePoint.fromCompoundTag(tag), tag.getInteger("blockID"), tag.getInteger("blockMetadata"), tag.getBoolean("withNotify"));
    }
}
