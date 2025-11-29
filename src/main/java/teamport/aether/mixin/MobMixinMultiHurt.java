package teamport.aether.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.entity.AetherMultiHurt;
import teamport.aether.entity.DamageInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("java:S2160")
@Mixin(value = Mob.class, remap = false)
public abstract class MobMixinMultiHurt extends Entity implements AetherMultiHurt {

	@Shadow
	protected int entityAge;
	@Shadow
	public float walkAnimSpeed;
	@Shadow
	protected int lastDamage;
	@Shadow
	public int hurtTime;
	@Shadow
	public int prevHealth;
	@Shadow
	public int prevBonusHealth;
	@Shadow
	public int bonusHealth;
	@Shadow
	public int maxHurtTime;
	@Shadow
	public float attackedAtYaw;

	@Shadow
	protected abstract void damageEntity(int amount, DamageType type);

	@Shadow
	public abstract int getHealth();

	@Shadow
	public abstract void knockBack(Entity entity, int i, double d, double d1);

	@Shadow
	public abstract void playHurtSound();

	@Shadow
	public abstract void playDeathSound();

	@Shadow
	public abstract void onDeath(Entity entityKilledBy);

	@Shadow
	public int heartsHalvesLife;

	@SuppressWarnings("java:S114")
	private MobMixinMultiHurt(@Nullable World world) {
		super(world);
	}

	@Unique
	public boolean multiHurt(Entity attacker, DamageInstance ... instances) {
		if(instances == null || instances.length == 0 ||this.world == null || this.world.isClientSide) {
			return false;
		}
		int totalDamage = 0;
		for(DamageInstance instance : instances){
			totalDamage += instance.getDamage();
		}
		this.entityAge = 0;
		if (this.getHealth() <= 0) {
			return false;
		}
		this.walkAnimSpeed = 1.5F;
		boolean flag = true;
		if (this.heartsFlashTime > this.heartsHalvesLife / 2.0F) {
			if (totalDamage <= this.lastDamage) {
				return false;
			}
			this.multiDamageEntity(totalDamage - this.lastDamage, instances);
			this.lastDamage = totalDamage;
			flag = false;
		} else {
			this.dealDamageNormally(totalDamage);
			this.multiDamageEntity(totalDamage ,instances);
		}
		this.attackedAtYaw = 0.0F;
		this.performKnockback(attacker, flag);
		this.playSound(attacker, totalDamage, flag);
		return true;
	}

	@Unique
	private void multiDamageEntity(int maxDamageDeals ,DamageInstance[] instances){
		List<DamageInstance> listInstances = new ArrayList<>(Arrays.asList(instances));
		Collections.sort(listInstances);
		for(int i = 0; i < listInstances.size() || maxDamageDeals > 0; i++){
			DamageInstance instance = listInstances.get(i);
            int damage = Math.min(instance.getDamage(), maxDamageDeals);
            maxDamageDeals -= damage;
			this.damageEntity(damage, instance.getType());
		}
	}


	@Unique
	private void performKnockback(Entity attacker, boolean flag) {
		assert this.world != null;
		if (!flag) {
			return;
		}
		this.markHurt();
		if (attacker == null) {
			this.attackedAtYaw = ((int) (Math.random() * 2.0F) * 180);
		} else {
			doKnockback(attacker);
		}
		this.world.sendTrackedEntityStatusUpdatePacket(this, (byte) 2, this.attackedAtYaw);
	}

	@Unique
	private void dealDamageNormally(int damage) {
		this.lastDamage = damage;
		this.prevHealth = this.getHealth();
		this.prevBonusHealth = this.bonusHealth;
		this.heartsFlashTime = this.heartsHalvesLife;
		if (damage > 0) {
			this.hurtTime = this.maxHurtTime = 10;
		}
	}

	@Unique
	private void playSound(Entity attacker, int damage, boolean flag) {
		if (this.getHealth() <= 0) {
			if (flag) {
				this.playDeathSound();
			}
			this.onDeath(attacker);
			return;
		}
		if (flag && damage > 0) {
			this.playHurtSound();
		}
	}

	@Unique
	private void doKnockback(Entity attacker) {
		double dx = attacker.x - this.x;
		double dz = attacker.z - this.z;
		while (dx * dx + dz * dz < 1.0E-4) {
			dx = (Math.random() - Math.random()) * 0.01;
			dz = (Math.random() - Math.random()) * 0.01;
		}
		this.attackedAtYaw = (float) (Math.atan2(dz, dx) * 180.0F / Math.PI) - this.yRot;
		this.knockBack(attacker, 0, dx, dz);
	}
}

