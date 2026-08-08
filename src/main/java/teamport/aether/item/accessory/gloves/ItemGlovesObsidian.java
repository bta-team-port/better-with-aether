package teamport.aether.item.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.NonNull;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.accessory.ItemGloves;
import teamport.aether.mixin.accessors.EntityAccessor;

public class ItemGlovesObsidian extends ItemGloves {
    public ItemGlovesObsidian(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
    }

    @Override
    public boolean hitEntity(@NonNull ItemStack gloves, @NonNull Mob target, @NonNull Mob attacker) {
        if(super.hitEntity(gloves, target, attacker) && target.hurtTime == 10 && ((EntityAccessor)target).getRandom().nextInt(2) == 0){
            ParticleMaker.spawnSmokeParticles(target);
            return true;
        }
        return false;
    }
}
