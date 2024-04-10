package bta.aether.entity;

import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;

public class EntityPhyg extends EntityAetherAnimal {
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
        return "/assets/aether/mobs/" + this.skinName + "/" + this.getSkinVariant() + ".png";
    }
    @Override
    public int getSkinVariant() {
        int skinVariantCount = 1;
        return this.entityData.getByte(1) % skinVariantCount;
    }

    public String getLivingSound() {
        return "mob.pig";
    }

    protected String getHurtSound() {
        return "mob.pig";
    }

    protected String getDeathSound() {
        return "mob.pigdeath";
    }

    protected int getDropItemId() {
        return Item.foodPorkchopRaw.id;
    }

    protected void dropFewItems() {
        int i = this.random.nextInt(3);

        int k;
        for(k = 0; k < i; ++k) {
            this.spawnAtLocation(Item.foodPorkchopRaw.id, 1);
        }

        i = this.random.nextInt(3);

        for(k = 0; k < i; ++k) {
            this.spawnAtLocation(Item.featherChicken.id, 1);
        }

    }

}
