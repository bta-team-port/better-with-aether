package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.world.generate.feature.components.WorldFeatureBlock;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.*;
import java.util.stream.Collectors;

import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawShell;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;

public abstract class BaseBronzeRoom  extends WorldFeature {

    public World world;
    public Random random;
    public WorldFeatureComponent room;
    public WorldFeatureComponent chest;
    public WorldFeatureComponent air;
    public int x;
    public int y;
    public int z;
    public int height;

    @FunctionalInterface
    public interface RoomBuilder<T extends BaseBronzeRoom> {
        T build(World world, Random random, int x, int y, int z);
    }

    public BaseBronzeRoom() {
        this.height = 12;
        this.room = new WorldFeatureComponent();
        this.chest = new WorldFeatureComponent();
        this.air = new WorldFeatureComponent();
    }

//    public BaseBronzeRoom(World world, Random random, int x, int y, int z) {
//        this.world = world;
//        this.random = random;
//        this.x = x;
//        this.y = y;
//        this.z = z;
//        this.height = 12;
//        this.room = new WorldFeatureComponent();
//        this.chest = new WorldFeatureComponent();
//        this.air = new WorldFeatureComponent();
//    }

    public static <T extends BaseBronzeRoom> T getRoom(
            RoomBuilder<T> builder,
            World world,
            Random random,
            int x, int y, int z
    ) {
        return builder.build(world, random, x, y, z);
    }

    public boolean canPlace(){
        int airCount = 0;
        for(WorldFeaturePoint p : room.blockList){
            Block<?> block = world.getBlock(p.x,p.y,p.z);
            if(block == null || block.id() == 0){
                airCount++;
                air.add(WorldFeatureBlock.wfb(p.x, p.y, p.z));
            }
        }
        return airCount >= room.blockList.size() * 0.35;
    }

    public abstract void makeRoom();

    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.random = random;
        this.world = world;
        this.height = 13;
        this.room = new WorldFeatureComponent();
        this.chest = new WorldFeatureComponent();
        this.air = new WorldFeatureComponent();
        this.makeRoom();
//        if(!canPlace()) return false;
        room.place(world);
        chest.place(world);
        air.place(world);
        return true;
    }
}
