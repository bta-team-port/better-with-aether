package teamport.aether.mixin.player;

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

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixinDevDrops {
    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getGameRuleValue(Lnet/minecraft/core/data/gamerule/GameRule;)Ljava/lang/Object;"))
    private void extraDropsDev(Entity entityKilledBy, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        String uuid = player.uuid.toString();

        switch (uuid) {
            case AetherGlobals.UUID_LUKEISSTUFF: // LukeisStuff
                player.dropPlayerItemWithRandomChoice(new ItemStack(AetherItems.AMMO_WINDBALL, 1), true);
                break;
            case AetherGlobals.UUID_OLYPOLYU: // Olypolyu / Kheprep
                player.dropPlayerItemWithRandomChoice(new ItemStack(AetherItems.PARACHUTE_CLOUD, 1), true);
                break;
            case AetherGlobals.UUID_TOCININ: // Tocinin
                player.dropPlayerItemWithRandomChoice(new ItemStack(Items.FOOD_PORKCHOP_RAW, 1), true);
                break;
            case AetherGlobals.UUID_REDART15: // Redart15
                player.dropPlayerItemWithRandomChoice(new ItemStack(AetherBlocks.CARVED_STONE_LIGHT, 1), true);
                break;
            case AetherGlobals.UUID_SMUSHYTACO: // SmushyTaco
                player.dropPlayerItemWithRandomChoice(new ItemStack(AetherItems.AMMO_HAMMER_HEAD, 1), true);
                break;
            case AetherGlobals.UUID_RIN: // Rin
                player.dropPlayerItemWithRandomChoice(new ItemStack(Items.FOOD_FISH_COOKED, 1), true);
                break;
            default:
                break;
        }
    }
}
