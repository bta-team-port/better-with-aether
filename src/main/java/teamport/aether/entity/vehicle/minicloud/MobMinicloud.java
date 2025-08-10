package teamport.aether.entity.vehicle.minicloud;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.projectile.ProjectileWindball;

public class MobMinicloud extends MobFlying {
    public int shotTimer;
    public int lifeSpan;
    public boolean gotPlayer;
    public boolean toLeft;
    public Player dude;
    public double targetX;
    public double targetY;
    public double targetZ;

    public MobMinicloud(World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "minicloud");
        this.setSize(0.5F, 0.45F);
        this.noPhysics = true;
        this.pushTime = 1.75F;
        this.animateHurt();
    }

    public MobMinicloud(World world, Player ep, boolean flag) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "minicloud");
        this.setSize(0.5F, 0.45F);
        this.dude = ep;
        this.toLeft = flag;
        this.lifeSpan = 3600;
        this.getTargetPos();
        this.setPos(this.targetX, this.targetY, this.targetZ);
        this.xRot = this.dude.xRot;
        this.yRot = this.dude.yRot;
        this.noPhysics = true;
        this.pushTime = 1.75F;
        this.animateHurt();
    }

    public boolean collidesWith(Entity entity) {
        return false;
    }

    public void getTargetPos() {
        if (this.distanceTo(this.dude) > 2.0F) {
            this.targetX = this.dude.x;
            this.targetY = this.dude.y - 0.10000000149011612;
            this.targetZ = this.dude.z;
        } else {
            double angle = this.dude.yRot;
            if (this.toLeft) {
                angle -= 90.0;
            } else {
                angle += 90.0;
            }

            angle /= -57.29577319531843;
            this.targetX = this.dude.x + Math.sin(angle) * 1.05;
            this.targetY = this.dude.y - 0.10000000149011612;
            this.targetZ = this.dude.z + Math.cos(angle) * 1.05;
        }

    }

    public boolean atShoulder() {
        double a = this.x - this.targetX;
        double b = this.y - this.targetY;
        double c = this.z - this.targetZ;
        return Math.sqrt(a * a + b * b + c * c) < 0.3;
    }

    public void approachTarget() {
        double a = this.targetX - this.x;
        double b = this.targetY - this.y;
        double c = this.targetZ - this.z;
        double d = Math.sqrt(a * a + b * b + c * c) * 3.25;
        this.xd = (this.xd + a / d) / 2.0;
        this.yd = (this.yd + b / d) / 2.0;
        this.zd = (this.zd + c / d) / 2.0;
        Math.atan2(a, c);
    }

    public Entity findPlayer() {
        return this.world.getClosestPlayerToEntity(this, 16.0);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("LifeSpan", (short) this.lifeSpan);
        tag.putShort("ShotTimer", (short) this.shotTimer);
        this.gotPlayer = this.dude != null;
        tag.putBoolean("GotPlayer", this.gotPlayer);
        tag.putBoolean("ToLeft", this.toLeft);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.lifeSpan = tag.getShort("LifeSpan");
        this.shotTimer = tag.getShort("ShotTimer");
        this.gotPlayer = tag.getBoolean("GotPlayer");
        this.toLeft = tag.getBoolean("ToLeft");
    }


    public void onLivingUpdate() {
        super.onLivingUpdate();
        --this.lifeSpan;
        if (this.lifeSpan <= 0) {
            this.animateHurt();
            this.dead = true;
        } else {
            if (this.shotTimer > 0) {
                --this.shotTimer;
            }

            if (this.dead) {
                this.remove();
            }

            if (this.gotPlayer && this.dude == null) {
                this.gotPlayer = false;
                this.dude = (Player) this.findPlayer();
            }

            if (this.dude != null && this.dude.isAlive()) {
                this.getTargetPos();
                if (this.atShoulder()) {
                    this.xd *= 0.65;
                    this.yd *= 0.65;
                    this.zd *= 0.65;
                    this.yRot = this.dude.yRot + (this.toLeft ? 1.0F : -1.0F);
                    this.xRot = this.dude.xRot;
                    if (this.shotTimer <= 0 && this.dude instanceof Player && this.dude.isSwinging) {
                        float spanish = this.yRot - (this.toLeft ? 1.0F : -1.0F);
                        double a = this.x + Math.sin((double) spanish / -57.29577319531843) * 1.6;
                        double b = this.y - 0.25;
                        double c = this.z + Math.cos((double) spanish / -57.29577319531843) * 1.6;
                        ProjectileWindball eh = new ProjectileWindball(this.world, this, a, b, c);
                        this.world.entityJoinedWorld(eh);
                        Vec3 vec3d = this.getLookAngle();
                        if (vec3d != null) {
                            eh.xd = vec3d.x * 1.5;
                            eh.yd = vec3d.y * 1.5;
                            eh.zd = vec3d.z * 1.5;
                        }

                        eh.hurtMarked = true;
                        this.world.playSoundAtEntity(null, this, "aether:mob.zephyr.shoot", 0.75F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                        this.shotTimer = 40;
                    }
                } else {
                    this.approachTarget();
                }

            } else {
                this.animateHurt();
                this.dead = true;
            }
        }
    }

    public boolean hurt(Entity attacker, int damage, DamageType type) {
        return (attacker == null || attacker != this.dude) && super.hurt(attacker, damage, type);
    }

    public String getLivingSound() {
        return null;
    }

    public String getHurtSound() {
        return "aether:mob.zephyr.call";
    }

    public String getDeathSound() {
        return "aether:mob.zephyr.call";
    }

    public void playHurtSound() {
        this.world.playSoundAtEntity(null, this, this.getHurtSound(), 0.5f, (this.random.nextFloat() + this.random.nextFloat()) * 1.5F + 0.25F);
    }

    public void playDeathSound() {
        this.world.playSoundAtEntity(null, this, this.getDeathSound(), 0.5f, (this.random.nextFloat() + this.random.nextFloat()) * 1.5F + 0.25F);
    }


}
