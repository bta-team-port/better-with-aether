package teamport.aether.mixin.accessory.trinket;

import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.accessory.pendant.ItemPendant;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = BlockLogic.class, remap = false)
public class BlockLogicPendantDamage {

    /**
     * @implNote This is the common method for all the ways that a player can harvest blocks
     * be it by hand or tool. Two exception are made to handle gold pendant differently.
     * */
    @Inject(method = "harvestBlock", at = @At("TAIL"))
    public void pendantEffect(
            World world, Player player,
            int x, int y, int z, int meta,
            TileEntity tileEntity,
            CallbackInfo ci
    ){
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        if(trinketOne != null && trinketOne.getItem() instanceof ItemPendant){
            if(((ItemPendant) trinketOne.getItem()).canTakeHarvestDamage()){
                trinketOne.damageItem(1, player);
            }
        }
        if(trinketTwo != null && trinketTwo.getItem() instanceof ItemPendant){
            if(((ItemPendant) trinketTwo.getItem()).canTakeHarvestDamage()){
                trinketTwo.damageItem(1, player);
            }
        }
    }
}
