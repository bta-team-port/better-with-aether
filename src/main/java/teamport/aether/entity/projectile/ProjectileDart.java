package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.items.AetherItems;

public class ProjectileDart extends ProjectileArrow {
    public static final int TYPE_GOLDEN = 0;
    public static final int TYPE_POISON = 1;
    public static final int TYPE_ENCHANTED = 2;
    public int mobsHit;
    public int xTile;
    public int yTile;
    public int zTile;
    public int inTile;
    public int shake;
    public int inData;
    public int arrowType;
    public ItemStack stack;
    public boolean inGround;
    public boolean doesArrowBelongToPlayer;

    public ProjectileDart(World world) {
        this(world, 0);
    }

    public ProjectileDart(World world, int arrowType) {
        super(world);
        this.mobsHit = 0;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.shake = 0;
        this.inData = 0;
        this.stack = new ItemStack(AetherItems.AMMO_DART_GOLDEN);
        this.inGround = false;
        this.doesArrowBelongToPlayer = false;
        this.arrowType = arrowType;
    }

    public ProjectileDart(World world, double d, double d1, double d2, int arrowType) {
        super(world, d, d1, d2, arrowType);
        this.mobsHit = 0;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.shake = 0;
        this.inData = 0;
        this.stack = new ItemStack(AetherItems.AMMO_DART_GOLDEN);
        this.inGround = false;
        this.doesArrowBelongToPlayer = false;
        this.arrowType = arrowType;
    }

    public ProjectileDart(World world, boolean doesArrowBelongToPlayer, int arrowType) {
        super(world);
        this.mobsHit = 0;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.shake = 0;
        this.inData = 0;
        this.stack = new ItemStack(AetherItems.AMMO_DART_GOLDEN);
        this.inGround = false;
        this.doesArrowBelongToPlayer = false;
        this.setDoesArrowBelongToPlayer(doesArrowBelongToPlayer);
        this.arrowType = arrowType;
    }

    public void initProjectile() {
        super.initProjectile();
        this.damage = 5;
    }

    public void setDoesArrowBelongToPlayer(boolean flag) {
        this.doesArrowBelongToPlayer = flag;
    }

    public boolean arrowBelongsToPlayer() {
        return this.doesArrowBelongToPlayer;
    }

    public void setGrounded(boolean flag) {
        this.inGround = flag;
    }

    public boolean isGrounded() {
        return this.inGround;
    }

    public void lerpMotion(double xd, double yd, double zd) {
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt(xd * xd + zd * zd);
            this.yRot = (float)(Math.atan2(xd, zd) * 180.0 / Math.PI);
            this.xRot = (float)(Math.atan2(yd, f) * 180.0 / Math.PI);
            this.xRotO = this.xRot;
            this.yRotO = this.yRot;
            this.moveTo(this.x, this.y, this.z, this.yRot, this.xRot);
            this.ticksInGround = 0;
        }

    }

    public void tick() {
        if (this.shake > 0) {
            --this.shake;
        }

        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt(this.xd * this.xd + this.zd * this.zd);
            this.yRotO = this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);
            this.xRotO = this.xRot = (float)(Math.atan2(this.yd, f) * 180.0 / Math.PI);
        }

        Block<?> block = this.world.getBlock(this.xTile, this.yTile, this.zTile);
        if (block != null) {
            AABB aabb = block.getCollisionBoundingBoxFromPool(this.world, this.xTile, this.yTile, this.zTile);
            if (aabb != null && aabb.contains(Vec3.getTempVec3(this.x, this.y, this.z))) {
                this.setGrounded(true);
            }
        }

        if (this.isGrounded()) {
            int id = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
            int meta = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (id == this.inTile && meta == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.remove();
                }

            } else {
                this.setGrounded(false);
                this.xd *= (double)this.random.nextFloat() * 0.2;
                this.yd *= (double)this.random.nextFloat() * 0.2;
                this.zd *= (double)this.random.nextFloat() * 0.2;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
            super.tick();
        }
    }

    public HitResult getHitResult() {
        Vec3 oldPosition = Vec3.getTempVec3(this.x, this.y, this.z);
        Vec3 newPosition = Vec3.getTempVec3(this.x + this.xd, this.y + this.yd, this.z + this.zd);
        return this.world.checkBlockCollisionBetweenPoints(oldPosition, newPosition, false, true, false);
    }

    public void onHit(HitResult hitResult) {
        if (hitResult.entity != null) {
            if (hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT)) {
                if (this.isOnFire()) {
                    hitResult.entity.fireHurt();
                }

                if (!this.world.isClientSide) {
                    this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                }
            }
        } else {
            this.xTile = hitResult.x;
            this.yTile = hitResult.y;
            this.zTile = hitResult.z;
            this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
            this.inData = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            this.xd = (float)(hitResult.location.x - this.x);
            this.yd = (float)(hitResult.location.y - this.y);
            this.zd = (float)(hitResult.location.z - this.z);
            float f1 = MathHelper.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
            this.x -= this.xd / (double)f1 * 0.05;
            this.y -= this.yd / (double)f1 * 0.05;
            this.z -= this.zd / (double)f1 * 0.05;
            this.inGroundAction();
        }
    }

    public void inGroundAction() {
        if (this.world.isClientSide) {
            this.setGrounded(true);
            this.shake = 7;
        } else if (this.arrowBelongsToPlayer()) {
            this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.setGrounded(true);
            this.shake = 7;
        } else {
            this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

            for(int j = 0; j < 4; ++j) {
                this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, AetherItems.AMMO_DART_GOLDEN.id);
            }

            this.remove();
        }
    }

    public int getArrowType() {
        return this.arrowType;
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("xTile", (short)this.xTile);
        tag.putShort("yTile", (short)this.yTile);
        tag.putShort("zTile", (short)this.zTile);
        tag.putShort("inTile", (short)this.inTile);
        tag.putByte("shake", (byte)this.shake);
        tag.putByte("inData", (byte)this.inData);
        tag.putByte("inGround", (byte)(this.isGrounded() ? 1 : 0));
        tag.putBoolean("player", this.arrowBelongsToPlayer());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.xTile = tag.getShort("xTile");
        this.yTile = tag.getShort("yTile");
        this.zTile = tag.getShort("zTile");
        this.inTile = tag.getShort("inTile") & 16383;
        this.shake = tag.getByte("shake") & 255;
        this.inData = tag.getByte("inData") & 255;
        this.setGrounded(tag.getByte("inGround") == 1);
        this.setDoesArrowBelongToPlayer(tag.getBoolean("player"));
    }

    public void playerTouch(Player player) {
        if (!this.world.isClientSide) {
            if (this.isGrounded() && this.arrowBelongsToPlayer() && this.shake <= 0) {
                player.inventory.insertItem(this.stack, true);
                if (this.stack.stackSize <= 0) {
                    this.world.playSoundAtEntity(player, this, "item.pickup", 1.0F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 4.0F);
                    player.onItemPickup(this, this.stack);
                    this.remove();
                }
            }
        }
    }
}
