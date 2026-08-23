package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherItems;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(Mob.class)
public abstract class PlayerMixinPendantFallDamageModifier {
    @Unique
    private boolean shouldSuppressFallSound = false;

    @WrapOperation(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"))
    private boolean modifyDamageTaken(Mob mob, Entity attacker, int damage, DamageType type, Operation<Boolean> original) {
        this.shouldSuppressFallSound = false;

        if (mob instanceof Player player) {
            int pendants = 0;
            ItemStack trinketOne = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_1_SLOT);
            ItemStack trinketTwo = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_2_SLOT);

            if (trinketOne != null && trinketOne.itemID == AetherItems.ARMOR_TALISMAN_GRAVITITE.id) {
                pendants++;
            }
            if (trinketTwo != null && trinketTwo.itemID == AetherItems.ARMOR_TALISMAN_GRAVITITE.id) {
                pendants++;
            }

            if (pendants > 0) {
                damage -= pendants;

                if (damage <= 0) {
                    this.shouldSuppressFallSound = true;
                    return false;
                }
            }
        }
        return original.call(mob, attacker, damage, type);
    }

    @WrapOperation(method = "causeFallDamage",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playBlockSoundEffect(Lnet/minecraft/core/entity/Entity;DDDLnet/minecraft/core/block/Block;Lnet/minecraft/core/enums/EnumBlockSoundEffectType;)V"))
    private void suppressFallSoundEffect(World world, Entity player, double x, double y, double z, Block<?> block, EnumBlockSoundEffectType soundType, Operation<Void> original) {
        if (this.shouldSuppressFallSound) {
            this.shouldSuppressFallSound = false;
            return;
        }
        original.call(world, player, x, y, z, block, soundType);
    }
}
