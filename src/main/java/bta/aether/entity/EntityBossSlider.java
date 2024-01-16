package bta.aether.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class EntityBossSlider extends EntityAetherBossBase{

    boolean awake = false;
    int angerThreshold = 50;
    int attackCoolDown = 30;

    public EntityBossSlider(World world) {
        super(world, 100, "aether.slider.name");
        this.setSize(2f,2f);
        this.viewScale = 2f;
    }

    @Override
    protected void roamRandomPath() {
    }

    public String getEntityTexture() {
        if (this.awake) {
            if ((this.health * 100) / this.maxHealth < angerThreshold) return "/assets/aether/mobs/sliderAwake_red.png";
            return "/assets/aether/mobs/sliderAwake.png";
        }

        if ((this.health * 100) / this.maxHealth < angerThreshold) return "/assets/aether/mobs/sliderSleep_red.png";
        return "/assets/aether/mobs/sliderSleep.png";
    }

    @Override
    protected String getDeathSound() {
        return "aether.sound.bosses.slider.sliderDeath";
    }

    @Override
    protected String getHurtSound() {
        return "step.stone";
    }


    @Override
    public void tick() {
        this.xRot = 0f;
        this.yRot = 0f;

        if ( awake && this.world.loadedEntityList.stream().noneMatch(entity -> entity instanceof EntityPlayer && getDistanceFrom(this.x, this.y, this.z, entity.x, entity.y, entity.z) < 100)) {
            this.awake = false;
            returnToPedestal(this.world, this.x, this.y, this.z);
        }

        super.tick();
    }

    public void returnToPedestal(World world, double x, double y, double z) {
    }

    public void doExplosionEffect(World world, double x, double y, double z){
        double XParticle = x + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
        double YParticle = y + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
        double ZParticle = z + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);

        world.spawnParticle("explode", XParticle, YParticle, ZParticle, 0,0,0);
    }

    @Override
    protected boolean isMovementBlocked() {
        return !this.awake;
    }

    @Override
    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (attacker instanceof EntityPlayer){
            ItemStack item = ((EntityPlayer)attacker).inventory.mainInventory[((EntityPlayer)attacker).inventory.currentItem];
            if (item != null && item.getItem() instanceof ItemToolPickaxe) {
                awake = true;
                return super.hurt(attacker, i, type);
            } else if (!this.awake) {
                String message = "<"+((EntityPlayer)attacker).getDisplayName()+"> "+ I18n.getInstance().translateKey("aether.slider.hit_fail");
                ((EntityPlayer)attacker).addChatMessage(message);
            }
        }

        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putBoolean("awake", awake);
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.awake = tag.getBoolean("awake");
        super.readAdditionalSaveData(tag);
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        for (int boom = 0; boom < 16; boom++) doExplosionEffect(this.world, this.x, this.y, this.z);
        this.awake = !this.awake;
        return super.interact(entityplayer);
    }

    private double getDistanceFrom(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.abs(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2)));
    }

}
