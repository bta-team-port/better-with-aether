package bta.aether.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pathfinder.Path;

import java.util.List;

public class EntityAerbunny extends EntityAetherAnimal {
    public int age;
    public int mate;
    public boolean grab;
    public boolean fear;
    public boolean gotrider;
    public Entity runFrom;
    public float puffiness;

    public EntityAerbunny(World world) {
        super(world);
        this.moveSpeed = 2.5F;
        this.heightOffset = -0.16F;
        this.setSize(0.4F, 0.4F);
        if (this.renderYawOffset < 5.0) {
            this.renderYawOffset = 5.0f;
        }

        this.age = this.random.nextInt(64);
        this.mate = 0;
    }

    public int getMaxHealth() {
        return 6;
    }

    @Override
    protected void init() {
        super.init();
        this.entityData.define(16, 0);
    }

    @Override
    public String getEntityTexture() {
        return "/assets/aether/mobs/aerbunny/aerbunny.png";
    }

    public void tick() {
        if (this.gotrider) {
            this.gotrider = false;
            if (this.vehicle == null) {
                EntityPlayer entityplayer = (EntityPlayer) this.findPlayerToRunFrom();
                if (entityplayer != null && this.distanceTo(entityplayer) < 2.0F && entityplayer.passenger == null) {
                    this.startRiding(entityplayer);
                }
            }
        }

        if (this.age < 1023) {
            ++this.age;
        } else if (this.mate < 127) {
            ++this.mate;
        } else {
            int i = 0;
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(16.0, 16.0, 16.0));

            for (Entity entity : list) {
                if (entity instanceof EntityAerbunny) {
                    ++i;
                }
            }

            if (i > 12) {
                this.proceed();
                return;
            }

            List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(1.0, 1.0, 1.0));
            boolean flag = false;

            for (int k = 0; k < list.size(); ++k) {
                Entity entity1 = list1.get(k);
                if (entity1 instanceof EntityAerbunny && entity1 != this) {
                    EntityAerbunny entitybunny = (EntityAerbunny) entity1;
                    if (entitybunny.vehicle == null && entitybunny.age >= 1023) {
                        EntityAerbunny entitybunny1 = new EntityAerbunny(this.world);
                        entitybunny1.setPos(this.x, this.y, this.z);
                        this.world.entityJoinedWorld(entitybunny1);
                        this.world.playSoundAtEntity(null, this, "mob.chickenplop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                        this.proceed();
                        entitybunny.proceed();
                        flag = true;
                        break;
                    }
                }
            }

            if (!flag) {
                this.mate = this.random.nextInt(16);
            }
        }

        if (this.puffiness > 0.0F) {
            this.puffiness -= 0.1F;
        } else {
            this.puffiness = 0.0F;
        }

        super.tick();
    }

    protected void causeFallDamage(float f) {
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Fear", this.fear);
        if (this.passenger != null) {
            this.gotrider = true;
        }

        tag.putBoolean("GotRider", this.gotrider);
        tag.putShort("RepAge", (short) this.age);
        tag.putShort("RepMate", (short) this.mate);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.fear = tag.getBoolean("Fear");
        this.gotrider = tag.getBoolean("GotRider");
        this.age = tag.getShort("RepAge");
        this.mate = tag.getShort("RepMate");
    }

    public void cloudPoop() {
        double a = this.random.nextFloat() - 0.5F;
        double d = this.x + a * 0.4000000059604645;
        double e = this.bb.minY;
        double f = this.z + a * 0.4000000059604645;
        this.world.spawnParticle("explode", d, e, f, 0.0, -0.07500000298023224, 0.0);
    }

    public boolean onGround() {
        return this.moveForward != 0.0F;
    }

    public Entity findPlayerToRunFrom() {
        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 12.0);
        return entityplayer != null && this.onGround() ? entityplayer : null;
    }


    public void runLikeHell() {
        double a = this.x - this.runFrom.x;
        double b = this.z - this.runFrom.z;
        double crazy = Math.atan2(a, b);
        crazy += (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.75;
        double c = this.x + Math.sin(crazy) * 8.0;
        double d = this.z + Math.cos(crazy) * 8.0;
        int x = MathHelper.floor_double(c);
        int y = MathHelper.floor_double(this.bb.minY);
        int z = MathHelper.floor_double(d);

        for (int q = 0; q < 16; ++q) {
            int i = x + this.random.nextInt(4) - this.random.nextInt(4);
            int j = y + this.random.nextInt(4) - this.random.nextInt(4) - 1;
            int k = z + this.random.nextInt(4) - this.random    .nextInt(4);
            if (j > 4 && (this.world.getBlockId(i, j, k) == 0 || this.world.getBlockId(i, j, k) == Block.blockSnow.id) && this.world.getBlockId(i, j - 1, k) != 0) {
                Path dogs = this.world.getEntityPathToXYZ(this, i, j, k, 16.0F);
                this.setTarget(null);
                break;
            }
        }

    }

    public boolean interact(EntityPlayer entityplayer) {
        this.zd = entityplayer.zd;
        if (this.vehicle != null) {
            this.gotrider = this.vehicle.getPassenger().horizontalCollision;
            this.zd = this.vehicle.getPassenger().z;
        }

        this.startRiding(entityplayer);
        if (this.vehicle == null) {
            this.grab = true;
        } else {
            this.world.playSoundAtEntity(null, this, "aether:mobs.aerbunny.aerbunnylift", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }

        this.isJumping = false;
        this.moveForward = 0.0F;
        this.moveStrafing = 0.0F;
        this.setTarget(null);
        this.xd = entityplayer.xd * 5.0;
        this.yd = entityplayer.yd / 2.0 + 0.5;
        this.zd = entityplayer.zd * 5.0;
        return true;
    }

    public double getRidingHeight() {
        return this.vehicle != null ? (double) (this.heightOffset - 1.15F) : (double) this.heightOffset;
    }


    public void proceed() {
        this.mate = 0;
        this.age = this.random.nextInt(64);
    }

    public boolean canClimb() {
        return this.onGround;
    }

    public String getHurtSound() {
        return "aether:mobs.aerbunny.aerbunnyhurt";
    }

    public String getDeathSound() {
        return "aether:mobs.aerbunny.aerbunnydeath";
    }

    public String getLivingSound() {
        return null;
    }

    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere();
    }
}