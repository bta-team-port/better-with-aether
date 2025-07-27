package teamport.aether.mixin.accessory.render;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(value = Player.class, remap = false)
abstract public class PlayerAgilityCapeMixin extends Mob {

//     @Shadow public MenuInventory inventory;

    public PlayerAgilityCapeMixin(World world) {
        super(world);
    }
//TODO Figure out how to make sure this.inventory isnt null

//    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
//    private void changeStepHeight(CallbackInfo ci) {
//        ItemStack itemStack = inventory.inventory.armorItemInSlot(5);
//        assert itemStack != null;
//        if (itemStack.itemID == AetherItems.ARMOR_CAPE_AGILITY.id) {
//            footSize = 1.0f;
//        } else {
//            footSize = 0.5f;
//        }
//    }
}
