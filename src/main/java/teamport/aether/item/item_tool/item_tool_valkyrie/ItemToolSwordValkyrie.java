package teamport.aether.item.item_tool.item_tool_valkyrie;

import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.item.item_tool.ItemToolSwordAether;

public class ItemToolSwordValkyrie extends ItemToolSwordAether implements AetherHasCustomDamageType {
    public ItemToolSwordValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public DamageType getDamageType() {
        return AetherMod.HOLY;
    }
}
