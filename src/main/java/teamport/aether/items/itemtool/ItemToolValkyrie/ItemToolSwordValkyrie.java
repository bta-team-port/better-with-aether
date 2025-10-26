package teamport.aether.items.itemtool.ItemToolValkyrie;

import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;
import teamport.aether.items.AetherHasCustomDamageType;
import teamport.aether.items.itemtool.ItemToolSwordAether;

public class ItemToolSwordValkyrie extends ItemToolSwordAether implements AetherHasCustomDamageType {

    public ItemToolSwordValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public DamageType getDamageType() {
        return AetherMod.HOLY;
    }
}
