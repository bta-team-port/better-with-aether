package teamport.aether.item;

import net.minecraft.core.util.helper.DamageType;

public interface AetherHasCustomDamageType {
    default DamageType getDamageType() {
        return DamageType.COMBAT;
    }
}
