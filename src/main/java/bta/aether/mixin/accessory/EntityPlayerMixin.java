package bta.aether.mixin.accessory;


import bta.aether.accessory.API.HealthHelper;
import bta.aether.accessory.API.TickableWhileWorn;
import bta.aether.accessory.API.VariableHealthPlayer;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends EntityLiving implements VariableHealthPlayer {

	public EntityPlayerMixin(World world) {
		super(world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo ci) {
		EntityPlayer player = (EntityPlayer) ((Object) this);
		for (int i = 0; i < player.inventory.armorInventory.length; i++) {
			ItemStack item = player.inventory.armorInventory[i];
			if (item != null) {
				if (item.getItem() instanceof TickableWhileWorn) {
					ItemStack newItem = ((TickableWhileWorn) item.getItem()).tickWhileWorn(player, item, i);
					if (newItem != item) {
						player.inventory.armorInventory[i] = newItem;
					}
				}
			}
		}
	}

	// heal extra hearts in peaceful
	@Inject(method = "onLivingUpdate", at = @At("HEAD"))
	public void onLivingUpdate(CallbackInfo ci) {
		if (this.health < 20 || this.world.difficultySetting != 0)
			return;

		if (this.health < 20 + HealthHelper.getExtraHealth((EntityPlayer) (Object) this) && this.tickCount % 20 * 12 == 0) {
			this.heal(1);
		}
	}

	@Inject(method = "init", at = @At("TAIL"))
	public void initExtraHP(CallbackInfo ci)
	{
		this.entityData.define(31, 0);
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void writeExtraHp(CompoundTag tag, CallbackInfo ci) {
		tag.putInt("ExtraHP", better_with_aether$getExtraHP());
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readExtraHp(CompoundTag tag, CallbackInfo ci) {
		if (tag.containsKey("ExtraHP")) {
			better_with_aether$setExtraHP(tag.getInteger("ExtraHP"));
		} else {
			better_with_aether$setExtraHP(0);
		}
	}

	public int better_with_aether$getExtraHP() {
		return this.entityData.getInt(31);
	}

	public void better_with_aether$setExtraHP(int extraHP) {
		this.entityData.set(31, extraHP);
		// if we would have more health that our new max, give us the new max
		if (health > 20 + extraHP) {
			health = 20 + extraHP;
		}
	}

	public void better_with_aether$addExtraHP(int extraHP) {
		better_with_aether$setExtraHP(better_with_aether$getExtraHP() + extraHP);
	}
}
