package teamport.aether.item.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.aether.entity.MobUtil;
import teamport.aether.item.accessory.ItemGloves;

public class ItemGlovesGravitite extends ItemGloves {
    private final float knockbackStrength;
    private final float lift;

    public ItemGlovesGravitite(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
        this.knockbackStrength = this.lift = 2.0f/5.0f;
    }

    @Override
    public boolean hitEntity(ItemStack gloves, Mob target, Mob attacker) {
        if (target instanceof Mob && target.hurtTime == 10 && super.hitEntity(gloves, target, attacker))  {
            if(attacker.isSneaking() && attacker instanceof Player){
                MobUtil.knockback(target, attacker,knockbackStrength, 0.4f);
            }else{
                MobUtil.knockback(target, attacker, 0.4f, lift);
            }
            return true;
        }
        return false;
    }
}
