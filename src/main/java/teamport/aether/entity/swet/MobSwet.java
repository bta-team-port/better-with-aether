package teamport.aether.entity.swet;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.mixin.accessors.MobAccessor;

import java.util.List;

public class MobSwet extends Mob implements Enemy {
    public int ticker;
    public int flutter;
    public int hops;
    public int textureNum;
    public boolean textureSet;
    public boolean gotrider;
    public boolean kickoff;
    public boolean friendly;
    public Entity currentTarget;

    public MobSwet(World world) {
        super(world);
        this.heightOffset = 0.0F;
        this.scoreValue = 100;
        this.mobDrops.add(new WeightedRandomLootObject(AetherBlocks.AERCLOUD_BLUE.getDefaultStack(), 0, 2));
        this.setSize(0.8F, 0.8F);
        this.setPos(this.x, this.y, this.z);
        this.hops = 0;
        this.gotrider = false;
        this.flutter = 0;
        this.ticker = 0;

        if (!this.textureSet) {
            if (this.random.nextInt(2) == 0) {
                this.textureNum = 2;
            } else {
                this.textureNum = 1;
            }
            this.textureSet = true;
        }

        if (this.textureNum == 1) {
            this.textureIdentifier = NamespaceID.getPermanent("aether", "swet");
            this.speed = 1.5F;
        } else {
            this.textureIdentifier = NamespaceID.getPermanent("aether", "swet_gold");
            this.speed = 3.0F;
        }

    }

    public int getMaxHealth() {
        return 25;
    }

    public void rideTick() {
        super.rideTick();
        if (this.passenger != null && this.kickoff) {
            this.passenger.startRiding(this);
            this.kickoff = false;
        }

    }

    public double getRideHeight() {
        return this.bbHeight - passenger.heightOffset;
    }

    public void tick() {
        if (this.currentTarget != null) {
            for (int i = 0; i < 3; ++i) {
                double d = (float) this.x + (this.random.nextFloat() - this.random.nextFloat()) * 0.3F;
                double d1 = (float) this.y + this.bbHeight;
                double d2 = (float) this.z + (this.random.nextFloat() - this.random.nextFloat()) * 0.3F;
                this.world.spawnParticle("splash", d, d1 - 0.25, d2, 0.0, 0.0, 0.0, 0);
            }
        }

        super.tick();
        if (this.gotrider) {
            if (this.passenger != null) {
                return;
            }

            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(0.5, 0.75, 0.5));
            int j = 0;
            if (j < list.size()) {
                Mob entity = (Mob) list.get(j);
                this.capturePrey(entity);
            }

            this.gotrider = false;
        }

        if (this.isUnderLiquid(Material.water)) {
            this.dissolve();
        }

    }

    public void onGround() {
        if (!this.friendly) {
            if (this.hops >= 3 && this.getHealth() > 0) {
                this.dissolve();
            }
        }
    }

    public void knockBack(Entity entity, int i, double d, double d1) {
        if (this.passenger == null || entity != this.passenger) {
            super.knockBack(entity, i, d, d1);
        }
    }

    public void dissolve() {
        for (int i = 0; i < 50; ++i) {
            float f = this.random.nextFloat() * 3.141593F * 2.0F;
            float f1 = this.random.nextFloat() * 0.5F + 0.25F;
            float f2 = MathHelper.sin(f) * f1;
            float f3 = MathHelper.cos(f) * f1;
            this.world.spawnParticle("splash", this.x + (double) f2, this.bb.minY + 1.25, this.z + (double) f3, (double) f2 * 1.5 + this.xd, 4.0, (double) f3 * 1.5 + this.zd, 0);
        }

        if (this.passenger != null) {
            Entity var10000 = this.passenger;
            var10000.y += this.passenger.heightOffset - 0.3F;
            this.passenger.startRiding(this);
        }

        this.remove();
    }

    public void capturePrey(Entity entity) {
        this.splorch();
        this.xo = entity.x;
        this.yo = entity.y + 0.009999999776482582;
        this.zo = this.z = entity.z;
        this.yRotO = (float) (this.y = entity.y);
        this.xRotO = (float) (this.x = entity.x);
        this.xd = entity.xd;
        this.yd = entity.yd;
        this.zd = entity.zd;
        this.setSize(entity.bbWidth, entity.bbHeight);
        this.setPos(this.x, this.y, this.z);
        entity.startRiding(this);
        this.y = this.random.nextFloat() * 360.0F;
    }

    public boolean hurt(Entity entity, int damage, DamageType damageType) {
        if (this.hops == 3 && entity == null && this.getHealth() > 1) {
            this.setHealthRaw(1);
        }

        boolean flag = super.hurt(entity, damage, DamageType.COMBAT);
        if (flag && this.passenger != null && this.passenger instanceof Mob) {
            if (entity != null && this.passenger == entity) {
                if (this.random.nextInt(3) == 0) {
                    this.kickoff = true;
                }
            } else {
                this.passenger.hurt(null, damage, DamageType.COMBAT);
                if (this.getHealth() <= 0) {
                    this.kickoff = true;
                }
            }
        }

        if (flag && this.getHealth() <= 0) {
            this.dissolve();
        } else if (flag && entity instanceof Mob) {
            Mob mob = (Mob) entity;
            if (mob.getHealth() > 0 && (this.passenger == null || mob != this.passenger)) {
                this.currentTarget = entity;
                this.lookAt(entity, 180.0F, 180.0F);
                this.kickoff = true;
            }
        }

        if (this.friendly && this.currentTarget instanceof Player) {
            this.currentTarget = null;
        }

        return flag;
    }

    public void d_2() {
        if (this.passenger != null && this.passenger instanceof Mob) {
            this.moveForward = 0.0F;
            this.moveStrafing = 0.0F;
            this.isJumping = false;
            ((EntityAccessor) this.passenger).setFallDistance(0.0F);
            this.yRotO = (float) (this.y = this.passenger.y);
            this.xRotO = (float) (this.x = 0.0F);
            Mob mob = (Mob) this.passenger;
            float f = 3.141593F;
            float f1 = f / 180.0F;
            float f2 = (float) (mob.y * f1);
            if (!this.onGround) {
                if (((MobAccessor) mob).getForwardVelocity() > 0.1F) {
                    if (this.textureNum == 1) {
                        this.xd += (double) ((MobAccessor) mob).getForwardVelocity() * -Math.sin(f2) * 0.125;
                    } else {
                        this.xd += (double) ((MobAccessor) mob).getForwardVelocity() * -Math.sin(f2) * 0.325;
                    }
                    this.zd += (double) ((MobAccessor) mob).getForwardVelocity() * Math.cos(f2) * 0.125;
                } else if (((MobAccessor) mob).getForwardVelocity() < -0.1F) {
                    if (this.textureNum == 1) {
                        this.xd += (double) ((MobAccessor) mob).getForwardVelocity() * -Math.sin(f2) * 0.125;
                    } else {
                        this.xd += (double) ((MobAccessor) mob).getForwardVelocity() * -Math.sin(f2) * 0.325;
                    }
                    this.zd += (double) ((MobAccessor) mob).getForwardVelocity() * Math.cos(f2) * 0.125;
                }

                if (((MobAccessor) mob).getHorizontalVelocity() > 0.1F) {
                    if (this.textureNum == 1) {
                        this.xd += (double) ((MobAccessor) mob).getHorizontalVelocity() * Math.cos(f2) * 0.125;
                    } else {
                        this.xd += (double) ((MobAccessor) mob).getHorizontalVelocity() * Math.cos(f2) * 0.325;
                    }
                    this.zd += (double) ((MobAccessor) mob).getHorizontalVelocity() * Math.sin(f2) * 0.125;
                } else if (((MobAccessor) mob).getHorizontalVelocity() < -0.1F) {
                    if (this.textureNum == 1) {
                        this.xd += (double) ((MobAccessor) mob).getHorizontalVelocity() * Math.cos(f2) * 0.125;
                    } else {
                        this.xd += (double) ((MobAccessor) mob).getHorizontalVelocity() * Math.cos(f2) * 0.325;
                    }
                    this.zd += (double) ((MobAccessor) mob).getHorizontalVelocity() * Math.sin(f2) * 0.125;
                }

                if (this.yd < 0.05000000074505806 && this.flutter > 0 && ((MobAccessor) mob).getJumping()) {
                    this.yd += 0.07000000029802322;
                    --this.flutter;
                }
            } else {
                if (((MobAccessor) mob).getJumping()) {
                    if (this.hops == 0) {
                        this.onGround = false;
                        this.yd = 0.8500000238418579;
                        this.hops = 1;
                        this.flutter = 5;
                    } else if (this.hops == 1) {
                        this.onGround = false;
                        this.yd = 1.0499999523162842;
                        this.hops = 2;
                        this.flutter = 5;
                    } else if (this.hops == 2) {
                        this.onGround = false;
                        this.yd = 1.25;
                        this.flutter = 5;
                    }
                } else if (!(((MobAccessor) mob).getForwardVelocity() > 0.125F) && !(((MobAccessor) mob).getForwardVelocity() < -0.125F) && !(((MobAccessor) mob).getHorizontalVelocity() > 0.125F) && !(((MobAccessor) mob).getHorizontalVelocity() < -0.125F)) {
                    if (this.hops > 0) {
                        this.hops = 0;
                    }
                } else {
                    this.onGround = false;
                    this.yd = 0.3499999940395355;
                    this.hops = 0;
                    this.flutter = 0;
                }

                ((MobAccessor) mob).setForwardVelocity(0.0F);
                ((MobAccessor) mob).setHorizontalVelocity(0.0F);
            }

            double d = Math.abs(Math.sqrt(this.xd * this.xd + this.zd * this.zd));
            if (d > 0.2750000059604645) {
                double d1 = 0.275 / d;
                this.xd *= d1;
                this.zd *= d1;
            }
        }

    }

    protected void updateAI() {
        ++this.entityAge;
        this.tryToDespawn();
        if (this.friendly && this.passenger != null) {
            this.d_2();
        } else {
            if (!this.onGround && this.isJumping) {
                this.isJumping = false;
            } else if (this.onGround) {
                if (this.moveForward > 0.05F) {
                    this.moveForward *= 0.75F;
                } else {
                    this.moveForward = 0.0F;
                }
            }

            if (this.currentTarget != null && this.passenger == null && this.getHealth() > 0) {
                this.lookAt(this.currentTarget, 10.0F, 10.0F);
            }

            if (this.currentTarget != null && this.currentTarget.removed) {
                this.currentTarget = null;
            }

            if (!this.onGround && this.yd < 0.05000000074505806 && this.flutter > 0) {
                this.yd += 0.07000000029802322;
                --this.flutter;
            }

            if (this.ticker < 4) {
                ++this.ticker;
            } else {
                if (this.onGround && this.passenger == null && this.hops != 0 && this.hops != 3) {
                    this.hops = 0;
                }

                if (this.currentTarget == null && this.passenger == null) {
                    Entity entity = this.getPrey();
                    if (entity != null) {
                        this.currentTarget = entity;
                    }
                } else if (this.currentTarget != null && this.passenger == null) {
                    if (this.distanceTo(this.currentTarget) <= 9.0F) {
                        if (this.onGround && this.canEntityBeSeen(this.currentTarget)) {
                            this.splotch();
                            this.flutter = 10;
                            this.isJumping = true;
                            this.moveForward = 1.0F;
                            this.y += 5.0F * (this.random.nextFloat() - this.random.nextFloat());
                        }
                    } else {
                        this.currentTarget = null;
                        this.isJumping = false;
                        this.moveForward = 0.0F;
                    }
                } else if (this.onGround) {
                    if (this.hops == 0) {
                        this.splotch();
                        this.onGround = false;
                        this.yd = 0.3499999940395355;
                        this.moveForward = 0.8F;
                        this.hops = 1;
                        this.flutter = 5;
                        this.y += 20.0F * (this.random.nextFloat() - this.random.nextFloat());
                    } else if (this.hops == 1) {
                        this.splotch();
                        this.onGround = false;
                        this.yd = 0.44999998807907104;
                        this.moveForward = 0.9F;
                        this.hops = 2;
                        this.flutter = 5;
                        this.y += 20.0F * (this.random.nextFloat() - this.random.nextFloat());
                    } else if (this.hops == 2) {
                        this.splotch();
                        this.onGround = false;
                        this.yd = 1.25;
                        this.moveForward = 1.25F;
                        this.hops = 3;
                        this.flutter = 5;
                        this.y += 20.0F * (this.random.nextFloat() - this.random.nextFloat());
                    }
                }

                this.ticker = 0;
            }

            if (this.onGround && this.hops >= 3) {
                this.dissolve();
            }

        }
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("Hops", (short) this.hops);
        tag.putShort("Flutter", (short) this.flutter);
        if (this.passenger != null) {
            this.gotrider = true;
        }

        tag.putBoolean("GotRider", this.gotrider);
        tag.putBoolean("Friendly", this.friendly);
        tag.putBoolean("textureSet", this.textureSet);
        tag.putShort("textureNum", (short) this.textureNum);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.hops = tag.getShort("Hops");
        this.flutter = tag.getShort("Flutter");
        this.gotrider = tag.getBoolean("GotRider");
        this.friendly = tag.getBoolean("Friendly");
        this.textureSet = tag.getBoolean("textureSet");
        this.textureNum = tag.getShort("textureNum");
        if (this.textureNum == 1) {
            this.textureIdentifier = NamespaceID.getPermanent("aether", "swet");
            this.speed = 1.5F;
        } else {
            this.textureIdentifier = NamespaceID.getPermanent("aether", "swet_gold");
            this.speed = 3.0F;
        }

    }

    public void splorch() {
        this.world.playSoundAtEntity(null, this, "mob.slimeattack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    public void splotch() {
        this.world.playSoundAtEntity(null, this, "mob.slimeattack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    public String getHurtSound() {
        return "mob.slime";
    }

    public String getDeathSound() {
        return "mob.slime";
    }

    public boolean collidesWith(Entity entity) {
        if (this.hops == 0 && this.passenger == null && this.currentTarget != null && entity != null && entity == this.currentTarget && (!(entity.vehicle instanceof MobSwet))) {
            if (entity.passenger != null) {
                entity.passenger.startRiding(entity);
            }

            this.capturePrey(entity);
        }

        super.collidesWith(entity);
        return true;
    }

    public boolean interact(@NotNull Player entityplayer) {
        if (!this.world.isClientSide) {
            if (!this.friendly) {
                this.friendly = true;
                this.currentTarget = null;
                return true;
            }

            if (this.passenger == null || this.passenger == entityplayer) {
                this.capturePrey(entityplayer);
            }
        }

        return true;
    }

    public Entity getPrey() {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(6.0, 6.0, 6.0));
        int i = 0;

        Entity entity;
        while (true) {
            if (i >= list.size()) {
                return null;
            }

            entity = list.get(i);
            if (entity instanceof Mob && !(entity instanceof MobSwet)) {
                if (this.friendly) {
                    if (!(entity instanceof Player)) {
                        break;
                    }
                } else if (!(entity instanceof MobMonster)) {
                    break;
                }
            }

            ++i;
        }

        return entity;
    }

}
