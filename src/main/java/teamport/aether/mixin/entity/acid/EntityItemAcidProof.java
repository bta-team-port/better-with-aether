package teamport.aether.mixin.entity.acid;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.item.AetherItemTags;

@Mixin(EntityItem.class)
public abstract class EntityItemAcidProof extends EntityAcidProof{

    @Override
    protected boolean acidDamage(Entity instance, Entity attacker, int baseDamage, DamageType type, Operation<Boolean> original){
        EntityItem entityItem = (EntityItem) (Object) this;
        Item item = entityItem.item.getItem();
        if (!item.hasTag(AetherItemTags.IS_ACID_PROOF)) {
            return original.call(instance, attacker, baseDamage, type);
        }
        return false;
    }
}
