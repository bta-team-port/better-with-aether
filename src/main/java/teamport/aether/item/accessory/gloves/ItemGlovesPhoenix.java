package teamport.aether.item.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.aether.item.accessory.ItemGloves;
import teamport.aether.mixin.accessors.ItemAccessor;

public class ItemGlovesPhoenix extends ItemGloves {
    public ItemGlovesPhoenix(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
    }

    @Override
    public boolean hitEntity(ItemStack gloves, Mob target, Mob attacker) {
        Item item = gloves.getItem();
        if(((ItemAccessor)item).getItemRand().nextInt(4) == 0 && target.hurtTime == 10 && super.hitEntity(gloves, target, attacker)){
            target.maxFireTicks = 200;
            target.remainingFireTicks = 200;
            return true;
        }
        return false;
    }
}
