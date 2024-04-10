package bta.aether.entity;

import net.minecraft.core.entity.animal.EntityCow;
import net.minecraft.core.world.World;

public class EntityPhow extends EntityCow {
    public boolean getSaddled = false;
    public float wingFold;
    public float wingAngle;
    private float aimingForFold;
    public int jumps;
    public int jrem;
    private boolean jpress;
    private int ticks;
    public EntityPhow(World world) {
        super(world);
        this.skinName = "phow";
    }

    public String getEntityTexture() {
        return "/assets/aether/mobs/" + this.skinName + "/" + this.getSkinVariant() + ".png";
    }
    @Override
    public int getSkinVariant() {
        int skinVariantCount = 1;
        return this.entityData.getByte(1) % skinVariantCount;
    }
}
