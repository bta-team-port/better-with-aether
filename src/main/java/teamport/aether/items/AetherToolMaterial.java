package teamport.aether.items;

import net.minecraft.core.item.material.ToolMaterial;

public class AetherToolMaterial {
    public static final ToolMaterial SKYROOT = new ToolMaterial().setDurability(64).setEfficiency(2.0F, 4.0f).setMiningLevel(0);
    public static final ToolMaterial HOLYSTONE = new ToolMaterial().setDurability(128).setEfficiency(4.0F, 6.0F).setMiningLevel(1);
    public static final ToolMaterial ZANITE = new ToolMaterial().setDurability(384).setEfficiency(6.0F, 12.0F).setMiningLevel(2);
    public static final ToolMaterial GRAVITITE = new ToolMaterial().setDurability(1536).setEfficiency(10.0F, 25.0F).setMiningLevel(3).setDamage(4);
    public static final ToolMaterial VALKYRIE = new ToolMaterial().setDurability(768).setEfficiency(12.0f, 35.0f).setMiningLevel(3).setDamage(5).setBlockHitDelay(4);
    public static final ToolMaterial SPECIAL = new ToolMaterial().setDurability(256).setEfficiency(2.0f, 4.0f).setMiningLevel(0).setDamage(2);
}
