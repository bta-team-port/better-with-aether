package bta.aether.item;

import net.minecraft.core.item.material.ToolMaterial;

public class AetherToolMaterial {
    public static final ToolMaterial TOOL_SKYROOT   = new ToolMaterial().setDurability(64).setEfficiency(2.0F, 4.0f).setMiningLevel(0).setBlockHitDelay(0);
    public static final ToolMaterial TOOL_HOLYSTONE = new ToolMaterial().setDurability(128).setEfficiency(4.0F, 6.0F).setMiningLevel(1).setBlockHitDelay(0);
    public static final ToolMaterial TOOL_ZANITE    = new ToolMaterial().setDurability(256).setEfficiency(6.0F, 8.0F).setMiningLevel(2).setBlockHitDelay(0);
    public static final ToolMaterial TOOL_GRAVITITE = new ToolMaterial().setDurability(1536).setEfficiency(10.0F, 25.0F).setMiningLevel(3).setBlockHitDelay(2);
    public static final ToolMaterial TOOL_VALKYRIE  = new ToolMaterial().setDurability(1536).setEfficiency(12.0f, 35.0f).setMiningLevel(3).setBlockHitDelay(3);
    public static final ToolMaterial SWORD_SPECIAL  = new ToolMaterial().setDurability(128).setEfficiency(2.0f, 4.0f).setMiningLevel(0);
    public static final ToolMaterial SWORD_HOLY     = new ToolMaterial().setDurability(128).setEfficiency(2.0f, 4.0f).setMiningLevel(0);
}
