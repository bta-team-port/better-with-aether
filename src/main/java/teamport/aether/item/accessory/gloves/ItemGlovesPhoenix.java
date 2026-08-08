package teamport.aether.item.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.accessory.ItemGloves;
import teamport.aether.mixin.accessors.ItemAccessor;

public class ItemGlovesPhoenix extends ItemGloves {
    public ItemGlovesPhoenix(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
    }

    @Override
    public boolean hitEntity(@NonNull ItemStack gloves, @NonNull Mob target, @NonNull Mob attacker) {
        if (super.hitEntity(gloves, target, attacker) && target.hurtTime == 10) {
            ItemAccessor.getItemRand().nextInt(4);
        }
        return false;
    }
}
