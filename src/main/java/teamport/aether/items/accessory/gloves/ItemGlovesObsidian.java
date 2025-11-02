package teamport.aether.items.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.items.accessory.ItemGloves;
import teamport.aether.mixin.accessors.EntityAccessor;

public class ItemGlovesObsidian extends ItemGloves {
    public ItemGlovesObsidian(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if(((EntityAccessor)target).getRandom().nextInt(2) == 0 && target.hurtTime == 10){
            ParticleMaker.spawnSmokeParticles(target);
        }
        return super.hitEntity(itemstack, target, attacker);
    }
}
