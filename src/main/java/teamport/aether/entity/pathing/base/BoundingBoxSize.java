package teamport.aether.entity.pathing.base;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;

public class BoundingBoxSize {
    private final int bbWidth;
    private final int bbHeight;

    public BoundingBoxSize(Entity entity) {
        this(entity.bbWidth, entity.bbHeight);
    }

    public BoundingBoxSize(float bbWidth, float bbHeight) {
        this(MathHelper.floor_float(bbWidth + 1.0F), MathHelper.floor_float(bbHeight + 1.0F));
    }

    private BoundingBoxSize(int bbWidth, int bbHeight) {
        this.bbHeight = bbHeight;
        this.bbWidth = bbWidth;
    }

    public int width(){
        return this.bbWidth;
    }

    public int length(){
        return this.bbWidth;
    }

    public int height(){
        return this.bbHeight;
    }
}
