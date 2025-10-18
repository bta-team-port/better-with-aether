package teamport.aether.mixin.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;

import static teamport.aether.AetherMod.*;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinDevDrops {


    @Shadow
    public int swingProgressInt;

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getGameRuleValue(Lnet/minecraft/core/data/gamerule/GameRule;)Ljava/lang/Object;"))
    public void extraDropsDev(Entity entityKilledBy, CallbackInfo ci) {
        Player asThis = (Player) (Object) this;
        String uuid = asThis.uuid.toString();
        switch (uuid) {
            case UUID_LUKEISSTUFF: // LukeisStuff
                asThis.dropPlayerItemWithRandomChoice(new ItemStack(AetherItems.AMMO_WINDBALL, 1), true);
                break;
            case UUID_OLYPOLYU: // Olypolyu / Kheprep
                asThis.dropPlayerItemWithRandomChoice(new ItemStack(AetherBlocks.BLOCK_GRAVITITE, 1), true);
                break;
            case UUID_TOCININ: // Tocinin
                asThis.dropPlayerItemWithRandomChoice(new ItemStack(Items.FOOD_PORKCHOP_RAW, 1), true);
                break;
            case UUID_REDART15: // Redart15
                asThis.dropPlayerItemWithRandomChoice(new ItemStack(AetherBlocks.CARVED_STONE_LIGHT_LOCKED, 1), true);
                break;
            default:
                break;
        }
    }
}
