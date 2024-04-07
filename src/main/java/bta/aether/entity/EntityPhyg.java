package bta.aether.entity;

import net.minecraft.core.entity.animal.EntityPig;
import net.minecraft.core.world.World;

public class EntityPhyg extends EntityPig {
    public boolean getSaddled = false;
    public float wingFold;
    public float wingAngle;
    private float aimingForFold;
    public int jumps;
    public int jrem;
    private boolean jpress;
    private int ticks;
    public EntityPhyg(World world) {
        super(world);
        this.skinName = "phyg";
    }

    public String getEntityTexture() {
        return "/assets/aether/mob/" + this.skinName + "/" + this.getSkinVariant() + ".png";
    }
    @Override
    public int getSkinVariant() {
        int skinVariantCount = 1;
        return this.entityData.getByte(1) % skinVariantCount;
    }

}
