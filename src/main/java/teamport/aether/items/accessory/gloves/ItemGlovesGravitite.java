package teamport.aether.items.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.aether.helper.MobUtil;
import teamport.aether.items.accessory.ItemGloves;

public class ItemGlovesGravitite extends ItemGloves {
    public final float knockbackStrength;
    public final float lift;

    public ItemGlovesGravitite(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
        this.knockbackStrength = this.lift = 2.0f/5.0f;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.hurtTime == 10) {
            if(attacker.isSneaking() && attacker instanceof Player){
                MobUtil.knockback(target, attacker,knockbackStrength, 0.4f);
            }else{
                MobUtil.knockback(target, attacker, 0.4f, lift);
            }
        }
        return super.hitEntity(itemstack, target, attacker);
    }
}
