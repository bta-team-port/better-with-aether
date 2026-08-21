package teamport.aether.item.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.accessory.HumanAccessoryShape;

public class ItemGlovesPhoenix extends ItemGloves {
    public ItemGlovesPhoenix(String translationKey, String namespaceId, int id, @NonNull ArmorMaterial material, HumanAccessoryShape humanAccessoryShape) {
        super(translationKey, namespaceId, id, material, humanAccessoryShape);
    }

    @Override
    public boolean hitEntity(@NonNull ItemStack gloves, @NonNull Mob target, @NonNull Mob attacker) {
        if (super.hitEntity(gloves, target, attacker)) {
            if (itemRand.nextInt(4) == 0) {
                target.maxFireTicks = 200;
                target.remainingFireTicks = 200;
            }
            return true;
        }
        return false;
    }
}
