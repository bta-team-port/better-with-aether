package teamport.aether.item.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.NonNull;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.accessory.HumanAccessoryShape;

public class ItemGlovesObsidian extends ItemGloves {
    public ItemGlovesObsidian(String translationKey, String namespaceId, int id, @NonNull ArmorMaterial material, HumanAccessoryShape humanAccessoryShape) {
        super(translationKey, namespaceId, id, material, humanAccessoryShape);
    }

    @Override
    public boolean hitEntity(@NonNull ItemStack gloves, @NonNull Mob target, @NonNull Mob attacker) {
        if (super.hitEntity(gloves, target, attacker)) {
            if (itemRand.nextInt(2) == 0) {
                ParticleMaker.spawnSmokeParticles(target);
            }
            return true;
        }
        return false;
    }
}
