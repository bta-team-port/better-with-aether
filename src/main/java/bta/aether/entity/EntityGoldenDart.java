package bta.aether.entity;

import bta.aether.item.AetherItems;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

import java.util.List;

public class EntityGoldenDart extends Entity {

        protected int xTile;
        protected int yTile;
        protected int zTile;
        protected int inTile;
        protected int field_28019_h;
        protected boolean inGround;
        public boolean doesArrowBelongToPlayer;
        public int arrowShake;
        public EntityLiving owner;
        protected int ticksInGround;
        protected int ticksInAir;
        protected float arrowSpeed;
        protected float arrowGravity;
        protected int arrowDamage;
        protected int arrowType;
    protected ItemStack stack;

    public EntityGoldenDart(World world) {
            this(world, 0);
        }

    public EntityGoldenDart(World world, int arrowType) {
            super(world);
        this.stack = new ItemStack(AetherItems.dartGolden);
            this.xTile = -1;
            this.yTile = -1;
            this.zTile = -1;
            this.inTile = 0;
            this.field_28019_h = 0;
            this.inGround = false;
            this.doesArrowBelongToPlayer = false;
            this.arrowType = arrowType;
            this.arrowShake = 0;
            this.ticksInAir = 0;
            this.setSize(0.5F, 0.5F);
        }

    public EntityGoldenDart(World world, double d, double d1, double d2, int arrowType) {
            super(world);
        this.stack = new ItemStack(AetherItems.dartGolden);
            this.xTile = -1;
            this.yTile = -1;
            this.zTile = -1;
            this.inTile = 0;
            this.field_28019_h = 0;
            this.inGround = false;
            this.doesArrowBelongToPlayer = false;
            this.arrowType = arrowType;
            this.arrowShake = 0;
            this.ticksInAir = 0;
            this.setSize(0.5F, 0.5F);
            this.setPos(d, d1, d2);
            this.heightOffset = 0.0F;
        }

    public EntityGoldenDart(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer, int arrowType) {
            super(world);
        this.stack = new ItemStack(AetherItems.dartGolden);
            this.xTile = -1;
            this.yTile = -1;
            this.zTile = -1;
            this.inTile = 0;
            this.field_28019_h = 0;
            this.inGround = false;
            this.doesArrowBelongToPlayer = doesArrowBelongToPlayer;
            this.arrowType = arrowType;
            this.arrowShake = 0;
            this.ticksInAir = 0;
            this.owner = entityliving;
            this.setSize(0.5F, 0.5F);
            this.moveTo(entityliving.x, entityliving.y + (double)entityliving.getHeadHeight(), entityliving.z, entityliving.yRot, entityliving.xRot);
            this.x -= (double)(MathHelper.cos(this.yRot / 180.0F * 3.141593F) * 0.16F);
            this.y -= 0.10000000149011612;
            this.z -= (double)(MathHelper.sin(this.yRot / 180.0F * 3.141593F) * 0.16F);
            this.setPos(this.x, this.y, this.z);
            this.heightOffset = 0.0F;
            this.xd = (double)(-MathHelper.sin(this.yRot / 180.0F * 3.141593F) * MathHelper.cos(this.xRot / 180.0F * 3.141593F));
            this.zd = (double)(MathHelper.cos(this.yRot / 180.0F * 3.141593F) * MathHelper.cos(this.xRot / 180.0F * 3.141593F));
            this.yd = (double)(-MathHelper.sin(this.xRot / 180.0F * 3.141593F));
            this.setArrowHeading(this.xd, this.yd, this.zd, 1.5F, 1.0F);
        }

        protected void init() {
            this.arrowGravity = 0.03F;
            this.arrowSpeed = 0.99F;
            this.arrowDamage = 5;
            if (!(this.owner instanceof EntityPlayer)) {
                this.doesArrowBelongToPlayer = false;
            }

        }

        public void setArrowHeading(double d, double d1, double d2, float f, float f1) {
            float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
            d /= (double)f2;
            d1 /= (double)f2;
            d2 /= (double)f2;
            d += this.random.nextGaussian() * 0.007499999832361937 * (double)f1;
            d1 += this.random.nextGaussian() * 0.007499999832361937 * (double)f1;
            d2 += this.random.nextGaussian() * 0.007499999832361937 * (double)f1;
            d *= (double)f;
            d1 *= (double)f;
            d2 *= (double)f;
            this.xd = d;
            this.yd = d1;
            this.zd = d2;
            float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
            this.yRotO = this.yRot = (float)(Math.atan2(d, d2) * 180.0 / 3.1415927410125732);
            this.xRotO = this.xRot = (float)(Math.atan2(d1, (double)f3) * 180.0 / 3.1415927410125732);
            this.ticksInGround = 0;
        }

        public void lerpMotion(double xd, double yd, double zd) {
            this.xd = xd;
            this.yd = yd;
            this.zd = zd;
            if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
                float f = MathHelper.sqrt_double(xd * xd + zd * zd);
                this.yRotO = this.yRot = (float)(Math.atan2(xd, zd) * 180.0 / 3.1415927410125732);
                this.xRotO = this.xRot = (float)(Math.atan2(yd, (double)f) * 180.0 / 3.1415927410125732);
                this.xRotO = this.xRot;
                this.yRotO = this.yRot;
                this.moveTo(this.x, this.y, this.z, this.yRot, this.xRot);
                this.ticksInGround = 0;
            }

        }

        public void tick() {
            super.tick();
            if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
                float f = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
                this.yRotO = this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / 3.1415927410125732);
                this.xRotO = this.xRot = (float)(Math.atan2(this.yd, (double)f) * 180.0 / 3.1415927410125732);
            }

            int i = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
            if (i > 0) {
                Block.blocksList[i].setBlockBoundsBasedOnState(this.world, this.xTile, this.yTile, this.zTile);
                AABB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(this.world, this.xTile, this.yTile, this.zTile);
                if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3d.createVector(this.x, this.y, this.z))) {
                    this.inGround = true;
                }
            }

            if (this.arrowShake > 0) {
                --this.arrowShake;
            }

            if (this.inGround) {
                int j = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
                int k = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                if (j == this.inTile && k == this.field_28019_h) {
                    ++this.ticksInGround;
                    if (this.ticksInGround == 1200) {
                        this.remove();
                    }

                } else {
                    this.inGround = false;
                    this.xd *= (double)(this.random.nextFloat() * 0.2F);
                    this.yd *= (double)(this.random.nextFloat() * 0.2F);
                    this.zd *= (double)(this.random.nextFloat() * 0.2F);
                    this.ticksInGround = 0;
                    this.ticksInAir = 0;
                }
            } else {
                if (this instanceof EntityPoisonDart) {
                    this.world.spawnParticle("arrowtrail", this.x, this.y, this.z, this.xd * 0.05000000074505806, this.yd * 0.05000000074505806 - 0.10000000149011612, this.zd * 0.05000000074505806);
                    this.world.spawnParticle("arrowtrail", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05000000074505806, this.yd * 0.05000000074505806 - 0.10000000149011612, this.zd * 0.05000000074505806);
                }

                ++this.ticksInAir;
                Vec3d oldPos = Vec3d.createVector(this.x, this.y, this.z);
                Vec3d newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
                HitResult movingobjectposition = this.world.checkBlockCollisionBetweenPoints(oldPos, newPos, false, true);
                oldPos = Vec3d.createVector(this.x, this.y, this.z);
                newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
                if (movingobjectposition != null) {
                    newPos = Vec3d.createVector(movingobjectposition.location.xCoord, movingobjectposition.location.yCoord, movingobjectposition.location.zCoord);
                }

                Entity entity = null;
                List list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.addCoord(this.xd, this.yd, this.zd).expand(1.0, 1.0, 1.0));
                double d = 0.0;

                float f5;
                for(int l = 0; l < list.size(); ++l) {
                    Entity entity1 = (Entity)list.get(l);
                    if (entity1.isPickable() && (entity1 != this.owner || this.ticksInAir >= 5)) {
                        f5 = 0.3F;
                        AABB axisalignedbb1 = entity1.bb.expand((double)f5, (double)f5, (double)f5);
                        HitResult movingobjectposition1 = axisalignedbb1.func_1169_a(oldPos, newPos);
                        if (movingobjectposition1 != null) {
                            double d1 = oldPos.distanceTo(movingobjectposition1.location);
                            if (d1 < d || d == 0.0) {
                                entity = entity1;
                                d = d1;
                            }
                        }
                    }
                }

                if (entity != null) {
                    movingobjectposition = new HitResult(entity);
                }

                float f1;
                if (movingobjectposition != null) {
                    if (movingobjectposition.entity != null) {
                        if (movingobjectposition.entity.hurt(this.owner, this.arrowDamage, DamageType.COMBAT)) {
                            if (this.isOnFire()) {
                                movingobjectposition.entity.fireHurt();
                            }

                            this.world.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                            if (!(this instanceof EntityPoisonDart)) {
                                this.remove();
                            }
                        } else if (!(this instanceof EntityPoisonDart)) {
                            this.xd *= -0.10000000149011612;
                            this.yd *= -0.10000000149011612;
                            this.zd *= -0.10000000149011612;
                            this.yRot += 180.0F;
                            this.yRotO += 180.0F;
                            this.ticksInAir = 0;
                        }
                    } else {
                        this.xTile = movingobjectposition.x;
                        this.yTile = movingobjectposition.y;
                        this.zTile = movingobjectposition.z;
                        this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
                        this.field_28019_h = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                        this.xd = (double)((float)(movingobjectposition.location.xCoord - this.x));
                        this.yd = (double)((float)(movingobjectposition.location.yCoord - this.y));
                        this.zd = (double)((float)(movingobjectposition.location.zCoord - this.z));
                        f1 = MathHelper.sqrt_double(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
                        this.x -= this.xd / (double)f1 * 0.05000000074505806;
                        this.y -= this.yd / (double)f1 * 0.05000000074505806;
                        this.z -= this.zd / (double)f1 * 0.05000000074505806;
                        this.inGroundAction();
                    }
                }

                this.x += this.xd;
                this.y += this.yd;
                this.z += this.zd;
                f1 = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
                this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / 3.1415927410125732);

                for(this.xRot = (float)(Math.atan2(this.yd, (double)f1) * 180.0 / 3.1415927410125732); this.xRot - this.xRotO < -180.0F; this.xRotO -= 360.0F) {
                }

                while(this.xRot - this.xRotO >= 180.0F) {
                    this.xRotO += 360.0F;
                }

                while(this.yRot - this.yRotO < -180.0F) {
                    this.yRotO -= 360.0F;
                }

                while(this.yRot - this.yRotO >= 180.0F) {
                    this.yRotO += 360.0F;
                }

                this.xRot = this.xRotO + (this.xRot - this.xRotO) * 0.2F;
                this.yRot = this.yRotO + (this.yRot - this.yRotO) * 0.2F;
                float f3 = this.arrowSpeed;
                f5 = this.arrowGravity;
                if (this.isInWater()) {
                    for(int i1 = 0; i1 < 4; ++i1) {
                        float f6 = 0.25F;
                        this.world.spawnParticle("bubble", this.x - this.xd * (double)f6, this.y - this.yd * (double)f6, this.z - this.zd * (double)f6, this.xd, this.yd, this.zd);
                    }

                    f3 = 0.8F;
                }

                this.xd *= (double)f3;
                this.yd *= (double)f3;
                this.zd *= (double)f3;
                this.yd -= (double)f5;
                this.setPos(this.x, this.y, this.z);
            }
        }

        protected void inGroundAction() {
            this.world.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.inGround = true;
            this.arrowShake = 7;
        }

        public int getArrowType() {
            return this.arrowType;
        }

        public void addAdditionalSaveData(CompoundTag tag) {
            tag.putShort("xTile", (short)this.xTile);
            tag.putShort("yTile", (short)this.yTile);
            tag.putShort("zTile", (short)this.zTile);
            tag.putShort("inTile", (short)((byte)this.inTile));
            tag.putByte("inData", (byte)this.field_28019_h);
            tag.putByte("shake", (byte)this.arrowShake);
            tag.putByte("inGround", (byte)(this.inGround ? 1 : 0));
            tag.putBoolean("player", this.doesArrowBelongToPlayer);
        }

        public void readAdditionalSaveData(CompoundTag tag) {
            this.xTile = tag.getShort("xTile");
            this.yTile = tag.getShort("yTile");
            this.zTile = tag.getShort("zTile");
            this.inTile = tag.getShort("inTile") & 16383;
            this.field_28019_h = tag.getByte("inData") & 255;
            this.arrowShake = tag.getByte("shake") & 255;
            this.inGround = tag.getByte("inGround") == 1;
            this.doesArrowBelongToPlayer = tag.getBoolean("player");
        }

    public void playerTouch(EntityPlayer player) {
        if (!this.world.isClientSide) {
            if (this.inGround && this.doesArrowBelongToPlayer && this.arrowShake <= 0) {
                player.inventory.insertItem(this.stack, true);
                if (this.stack.stackSize <= 0) {
                    this.world.playSoundAtEntity(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    player.onItemPickup(this, AetherItems.dartGolden.id);
                    this.remove();
                }
            }

        }
    }

        public float getShadowHeightOffs() {
            return 0.0F;
        }
    }