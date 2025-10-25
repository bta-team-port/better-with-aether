package teamport.aether.items;

import net.minecraft.core.util.helper.DamageType;

public interface AetherHasCustomDamageType {
    default DamageType getDamageTypes(){return DamageType.COMBAT;}
}
