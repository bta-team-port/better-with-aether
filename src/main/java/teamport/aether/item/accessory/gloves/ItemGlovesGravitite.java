package teamport.aether.item.accessory.gloves;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.MobUtil;
import teamport.aether.item.accessory.HumanAccessoryShape;

public class ItemGlovesGravitite extends ItemGloves {
    private final float knockbackStrength;
    private final float lift;

    public ItemGlovesGravitite(String translationKey, String namespaceId, int id, @NonNull ArmorMaterial material, HumanAccessoryShape humanAccessoryShape) {
        super(translationKey, namespaceId, id, material, humanAccessoryShape);
        this.knockbackStrength = this.lift = 2.0f / 5.0f;
    }

    @Override
    public boolean hitEntity(@NonNull ItemStack gloves, @NonNull Mob target, @NonNull Mob attacker) {
        if (super.hitEntity(gloves, target, attacker)) {
            if (attacker.isSneaking() && attacker instanceof Player) {
                MobUtil.knockback(target, attacker, knockbackStrength, 0.4f);
            } else {
                MobUtil.knockback(target, attacker, 0.4f, lift);
            }
            return true;
        }
        return false;
    }
}
