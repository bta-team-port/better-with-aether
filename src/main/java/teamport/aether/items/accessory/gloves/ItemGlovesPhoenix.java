package teamport.aether.items.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.aether.items.accessory.ItemGloves;
import teamport.aether.mixin.accessors.ItemAccessor;

public class ItemGlovesPhoenix extends ItemGloves {
    public ItemGlovesPhoenix(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        Item item = itemstack.getItem();
        if(((ItemAccessor)item).getItemRand().nextInt(4) == 0 && target.hurtTime == 10){
            target.maxFireTicks = 200;
            target.remainingFireTicks = 200;
        }
        return super.hitEntity(itemstack, target, attacker);
    }
}
