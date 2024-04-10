package bta.aether.entity;

import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;

public class EntityPhow extends EntityAetherAnimal {
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

    public String getLivingSound() {
        return "mob.cow";
    }

    protected String getHurtSound() {
        return "mob.cowhurt";
    }

    protected String getDeathSound() {
        return "mob.cowhurt";
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected int getDropItemId() {
        return Item.leather.id;
    }

    protected void dropFewItems() {
        int i = this.random.nextInt(3);

        int k;
        for(k = 0; k < i; ++k) {
            this.spawnAtLocation(Item.leather.id, 1);
        }

        i = this.random.nextInt(3);

        for(k = 0; k < i; ++k) {
            this.spawnAtLocation(Item.featherChicken.id, 1);
        }

    }
}
