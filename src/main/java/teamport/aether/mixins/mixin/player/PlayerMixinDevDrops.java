package teamport.aether.mixins.mixin.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherGlobals;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;

@Mixin(Player.class)
public abstract class PlayerMixinDevDrops {
    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getGameRuleValue(Lnet/minecraft/core/data/gamerule/GameRule;)Ljava/lang/Object;"))
    private void extraDropsDev(Entity entityKilledBy, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        String uuid = player.uuid.toString();

        switch (uuid) {
            case AetherGlobals.UUID_LUKEISSTUFF: // LukeisStuff
                player.dropPlayerItem(new ItemStack(AetherItems.AMMO_WINDBALL, 1));
                break;
            case AetherGlobals.UUID_OLYPOLYU: // Olypolyu / Kheprep
                player.dropPlayerItem(new ItemStack(AetherItems.PARACHUTE_CLOUD, 1));
                break;
            case AetherGlobals.UUID_TOCININ: // Tocinin
                player.dropPlayerItem(new ItemStack(Items.FOOD_PORKCHOP_RAW, 1));
                break;
            case AetherGlobals.UUID_REDART15: // Redart15
                player.dropPlayerItem(new ItemStack(AetherBlocks.CARVED_STONE_LIGHT, 1));
                break;
            case AetherGlobals.UUID_SMUSHYTACO: // SmushyTaco
                player.dropPlayerItem(new ItemStack(AetherItems.AMMO_HAMMER_HEAD, 1));
                break;
            default:
                break;
        }
    }
}
