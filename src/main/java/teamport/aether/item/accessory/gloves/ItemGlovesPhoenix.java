package teamport.aether.item.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.accessory.ItemGloves;

public class ItemGlovesPhoenix extends ItemGloves {
    public ItemGlovesPhoenix(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
    }

    @Override
    public boolean hitEntity(@NonNull ItemStack gloves, @NonNull Mob target, @NonNull Mob attacker) {
        if (super.hitEntity(gloves, target, attacker) && target.hurtTime == 10 && itemRand.nextInt(4) == 0) {
            target.maxFireTicks = 200;
            target.remainingFireTicks = 200;
            return true;
        }
        return false;
    }
}
