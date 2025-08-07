package teamport.aether.entity.monster.fireminion;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MobFireMinion extends MobMonster implements Enemy {
    public MobFireMinion(@Nullable World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "fire_minion");
        this.moveSpeed = 1.5F;
        this.attackStrength = 5;
        this.fireImmune = true;
        this.maxFireTicks = 20;
        setSize(1.0f,  2.5f);
    }

    public int getMaxHealth() {
        return 40;
    }

    protected void attackEntity(@NotNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            entity.hurt(this, this.attackStrength, DamageType.FIRE);
            entity.remainingFireTicks = 20;
            entity.maxFireTicks = 20;
        }

    }

    public void tick() {
        super.tick();
        if (this.getHealth() > 0) {
            this.maxFireTicks = 20;
            for (int j = 0; j < 4; ++j) {
                double a = this.random.nextFloat() - 0.5F;
                double b = this.random.nextFloat();
                double c = this.random.nextFloat() - 0.5F;
                double d = this.x + a * b;
                double e = this.bb.minY + b - 0.5;
                double f = this.z + c * b;
                this.world.spawnParticle("flame", d, e, f, 0.0, -0.07500000298023224, 0.0, 0);
            }
        }

    }

    public String getLivingSound() {
        return "mob.zombiepig.zpig";
    }

    public String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }

    public String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }

    public void playLivingSound() {
        this.world.playSoundAtEntity(null, this, this.getLivingSound(), 0.5f, (this.random.nextFloat() + this.random.nextFloat()) * 0.5F - 0.25F);
    }

    public void playHurtSound() {
        this.world.playSoundAtEntity(null, this, this.getHurtSound(), 0.5f, (this.random.nextFloat() + this.random.nextFloat()) * 0.5F - 0.25F);
    }

    public void playDeathSound() {
        this.world.playSoundAtEntity(null, this, this.getDeathSound(), 0.5f, (this.random.nextFloat() + this.random.nextFloat()) * 0.5F - 0.25F);
    }

}
