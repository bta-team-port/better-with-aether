package bta.aether.mixin.accessory;

import bta.aether.accessory.API.VariableHealthPlayer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityLiving.class, remap = false)
public abstract class EntityLivingMixin extends Entity {

	@Shadow
	public int health;

	@Shadow
	public int heartsHalvesLife;

	public EntityLivingMixin(World world) {
		super(world);
	}

	@Inject(method = "heal", at = @At("HEAD"), cancellable = true)
	public void heal(int health, CallbackInfo ci)
	{
		int extraHP = 0;
		if (this instanceof VariableHealthPlayer)
		{
			extraHP = ((VariableHealthPlayer)(Object) this).better_with_aether$getExtraHP();
		}

		if (this.health > 0) {
			this.health += health;
			if (this.health > 20 + extraHP) {
				this.health = 20 + extraHP;
			}

			this.heartsFlashTime = this.heartsHalvesLife / 2;
		}

		ci.cancel();
	}
}
