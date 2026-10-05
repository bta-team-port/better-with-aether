package teamport.aether.entity.pathing.base;

import net.minecraft.core.entity.Entity;

public class BoundingBoxSize {
    private final double bbWidth;
    private final double bbHeight;

    public BoundingBoxSize(Entity entity) {
        this.bbHeight = entity.bbHeight;
        this.bbWidth = entity.bbWidth;
    }

    public double width(){
        return this.bbWidth;
    }

    public double length(){
        return this.bbWidth;
    }

    public double height(){
        return this.bbHeight;
    }
}
