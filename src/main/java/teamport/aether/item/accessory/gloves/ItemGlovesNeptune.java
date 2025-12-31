package teamport.aether.item.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.accessory.ItemGloves;

public class ItemGlovesNeptune extends ItemGloves {
    public ItemGlovesNeptune(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
    }

    @Override
    public boolean hitEntity(ItemStack gloves, Mob target, Mob attacker) {
        if(super.hitEntity(gloves, target, attacker)){
            ParticleMaker.spawnDowningBubbles(target);
            return true;
        }
        return false;
    }
}
