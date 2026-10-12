package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.LevelListener;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.entity.DamageInstance;
import teamport.aether.entity.MobUtil;
import teamport.aether.helper.ParticleMaker;

import java.util.Locale;

public class ProjectileRainBow extends Projectile {
	public int samples;
	protected DamageInstance[] damageInstances;
    private int data;

	public ProjectileRainBow(World world) {
		super(world);
		this.setSize(0.25F, 0.25F);
		this.initProjectile();
	}


	public ProjectileRainBow(World world, Mob mob, DamageInstance... instances) {
		super(world, mob);
		this.damageInstances = instances;
	}

	@Override
	public void setHeading(double newMotionX, double newMotionY, double newMotionZ, float speed, float randomness) {
		super.setHeading(newMotionX, newMotionY, newMotionZ, speed * 2.0f, randomness);
	}

	@Override
	protected void initProjectile() {
		this.defaultProjectileSpeed = 1.0F;
		this.samples = (int) Math.ceil(12 * this.defaultProjectileSpeed);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.tickCount > 1 && !this.removed) {
			this.spawnBeamParticles();
		}
		if (this.tickCount >= Global.TICKS_PER_SECOND) {
			this.doEffect();
			this.remove();
		}
	}

    public void setName(@Nullable String customName, byte customColor) {
        if(customName != null){
            this.data = (TextFormatting.get(customColor) + customName).toLowerCase(Locale.ROOT).hashCode();
            return;
        }
        this.data = 0;
    }

	private void spawnBeamParticles() {
		for (int i = 0; i < samples; i++) {
			float progress = (float) i / samples;
			ParticleMaker.spawnParticle(world, "chaotic",
				MathHelper.lerp(this.x, this.x - this.xd, progress),
				MathHelper.lerp(this.y, this.y - this.yd, progress),
				MathHelper.lerp(this.z, this.z - this.zd, progress),
				0, 0, 0, this.data, 256);
		}
	}

	protected void doEffect() {
        for (int j = 0; j < 12; ++j) {
			double dx = this.x + this.xd * 0.2f + (random.nextFloat() - 0.5f);
			double dy = this.y + this.yd * 0.2f + (random.nextFloat() - 0.5f);
			double dz = this.z + this.zd * 0.2f + (random.nextFloat() - 0.5f);
			ParticleMaker.spawnParticle(this.world, "explode", dx, dy, dz, 0.0F, 0.0F, 0.0F, 0, 256);
			ParticleMaker.spawnParticle(this.world, "smoke", dx, dy, dz, 0.0F, 0.0F, 0.0F, 0, 256);
			ParticleMaker.spawnParticle(this.world, "largesmoke", dx, dy, dz, 0.0F, 0.0F, 0.0F, 0, 256);
		}
	}


	@Override
	public void lerpMotion(double xd, double yd, double zd) {
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			float f = MathHelper.sqrt(xd * xd + zd * zd);
			this.yRot = (float) (Math.atan2(xd, zd) * 180.0F / Math.PI);
			this.xRot = (float) (Math.atan2(yd, f) * 180.0F / Math.PI);
			this.xRotO = this.xRot;
			this.yRotO = this.yRot;
			this.moveTo(this.x, this.y, this.z, this.yRot, this.xRot);
		}
	}

	@Override
	public void onHit(@NotNull HitResult hitResult) {
        if(hitResult instanceof HitResult.Entity hitEntity){
            MobUtil.multiHit(this.owner, hitEntity.entity, this.damageInstances);
        }
        if(hitResult instanceof HitResult.Tile hitTile){
            TilePosc pos = hitTile.tilePos;
			Block<?> block = this.world.getBlockType(pos);
			if (block.hasTag(AetherBlockTags.BRIDLE)) {
				this.world.setBlockTypeNotify(pos, Blocks.AIR);
				this.world.playBlockEvent(pos, LevelListener.EVENT_BLOCK_BREAK, block.id());
			}
			this.doEffect();
		}
		this.remove();
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("instanceSize", this.damageInstances.length);
		for (int i = 0; i < damageInstances.length; i++) {
			DamageInstance instance = damageInstances[i];
			tag.putInt("damage_" + i, instance.getDamage());
			tag.putString("type_" + i, instance.getType().getLanguageKey());
		}
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.damageInstances = new DamageInstance[tag.getInteger("instanceSize")];
		for (int i = 0; i < damageInstances.length; i++) {
			int damage = tag.getInteger("damage_" + i);
			String type = tag.getString("type_" + i);
			for (DamageType damageType : DamageType.values()) {
				if (damageType.getLanguageKey().equalsIgnoreCase(type)) {
					damageInstances[i] = DamageInstance.inst(damage, damageType);
					break;
				}
			}
		}
	}

	@Override
	public void waterTick() {
		super.waterTick();
		this.projectileSpeed = this.defaultProjectileSpeed;
	}

	@Override
	public HitResult getHitResult() {
		Vector3dc oldPosition = new Vector3d(this.x, this.y, this.z);
        Vector3dc newPosition = new Vector3d(this.x + this.xd, this.y + this.yd, this.z + this.zd);
		return this.world.checkBlockCollisionBetweenPoints(oldPosition, newPosition, false, false, false);
	}

}
