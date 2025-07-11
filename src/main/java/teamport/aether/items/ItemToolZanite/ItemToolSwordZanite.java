package teamport.aether.items.ItemToolZanite;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import teamport.aether.items.AetherToolMaterial;
import teamport.aether.items.ItemToolSwordAether;

public class ItemToolSwordZanite extends ItemToolSwordAether {
    // to keep it consistent with other tools, and keep not a const incase it changes
    public static float factor = AetherToolMaterial.ZANITE.getEfficiency(true) / AetherToolMaterial.ZANITE.getEfficiency(false);


    public ItemToolSwordZanite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);

    }

    @Override
    public int getDamageVsEntity(Entity entity, ItemStack is) {
        // we will 'lerp' between the starting damage and starting damage time ration of efficiency
        float durability_progress = (float) is.getMetadata() / this.getMaxDamage();
        float starting_damage = (float) super.getDamageVsEntity(entity, is);
        float ending_damage = starting_damage * factor;
        return Math.round(starting_damage * (1.0F - durability_progress) + (ending_damage * durability_progress));
    }

}
