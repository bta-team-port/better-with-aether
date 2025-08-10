package teamport.aether.world.generate.feature.components;

import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class WorldFeatureComponent {
    public double startX;
    public double startY;
    public double startZ;
    List<WorldFeatureBlock> blockList;

    public WorldFeatureComponent(){
        this.blockList = new ArrayList<>();
        this.startX = 0;
        this.startY = 0;
        this.startZ = 0;
    }

    public WorldFeatureComponent(int startX, int startY, int startZ){
        this.blockList = new ArrayList<>();
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
    }

    public WorldFeatureComponent(List<WorldFeatureBlock> blockList){
        this.blockList = blockList;
        WorldFeatureBlock wfb = this.blockList.get(0);
        this.startX = WorldFeatureBlock.conv2Int(wfb.x);
        this.startY = WorldFeatureBlock.conv2Int(wfb.y);
        this.startZ = WorldFeatureBlock.conv2Int(wfb.z);
    }

    public WorldFeatureComponent(int startX, int startY, int startZ, List<WorldFeatureBlock> blockList){
        this.blockList = blockList;
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
    }

    public void add(WorldFeatureComponent component){
        this.blockList.addAll(component.blockList);
        this.startX = component.startX;
        this.startY = component.startY;
        this.startZ = component.startZ;
    }

    public void add(List<WorldFeatureBlock> list){
        this.blockList.addAll(list);
    }

    public void add(WorldFeatureBlock wfb){
        this.blockList.add(wfb);
    }

    public void addStart(int startX, int startY, int startZ){
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
    }

    public void rotateYPlane(Direction direction){
        float angleInDegree = direction.getHorizontalIndex() * 90.0F;
        for(WorldFeatureBlock wfb : this.blockList){
            double wfbX = wfb.x - this.startX;
            double wfbY = wfb.y - this.startY;
            double wfbZ = wfb.z - this.startZ;
            Vec3 vec = Vec3.getPermanentVec3(wfbX, wfbY, wfbZ);
            vec.rotateAroundY(MathHelper.toRadians(angleInDegree));
            wfb.x = vec.x + this.startX;
            wfb.y = vec.y + this.startY;
            wfb.z = vec.z + this.startZ;
        }
    }

    public void rotateYPlane(int fixPointX, int fixPointY, int fixPointZ, Direction direction){
        float angleInDegree = direction.getHorizontalIndex() * 90.0F;
        for(WorldFeatureBlock wfb : this.blockList){
            double wfbX = wfb.x - fixPointX;
            double wfbY = wfb.y - fixPointY;
            double wfbZ = wfb.z - fixPointZ;
            Vec3 vec = Vec3.getPermanentVec3(wfbX, wfbY, wfbZ);
            vec.rotateAroundY(MathHelper.toRadians(angleInDegree));
            wfb.x = vec.x + fixPointX;
            wfb.y = vec.y + fixPointY;
            wfb.z = vec.z + fixPointZ;
        }
    }

    // TODO make them also have mirrored versions
    public void mirrorYPlane(int fixPointX, int fixPointY, int fixPointZ, Direction direction){
    }

    public int iX(){
        return WorldFeatureBlock.conv2Int(this.startX);
    }

    public int iY(){
        return WorldFeatureBlock.conv2Int(this.startY);
    }

    public int iZ(){
        return WorldFeatureBlock.conv2Int(this.startZ);
    }

}
