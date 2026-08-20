package teamport.aether.item.item_tool;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.item.tool.ItemToolSword;
import org.jspecify.annotations.NonNull;
import teamport.aether.mixins.mixin.accessors.ItemToolSwordAccessor;

public class AetherToolMaterial {
    public static final ToolMaterial skyroot = new ToolMaterial().setDurability(64).setEfficiency(2.0F, 4.0f).setMiningLevel(0);
    public static final ToolMaterial holystone = new ToolMaterial().setDurability(128).setEfficiency(4.0F, 6.0F).setMiningLevel(1);
    public static final ToolMaterial zanite = new ToolMaterial().setDurability(384).setEfficiency(6.0F, 12.0F).setMiningLevel(2);
    public static final ToolMaterial gravitite = new ToolMaterial().setDurability(1536).setEfficiency(10.0F, 25.0F).setMiningLevel(3).setBlockHitDelay(4);
    public static final ToolMaterial valkyrie = new ToolMaterial().setDurability(768).setEfficiency(12.0f, 35.0f).setMiningLevel(3).setDamage(4).setBlockHitDelay(4);
    public static final ToolMaterial special = new ToolMaterial().setDurability(768).setEfficiency(5.0f, 7.0f).setMiningLevel(3);

    public static final int VALKYRIE_TOOL_EXTEND_RANGE_BY = 6;

    public static boolean isHoldingValkyrieTool(@NonNull Player player) {
        ItemStack held = player.getHeldItem();
        if (held == null) return false;
        if (held.getItem() instanceof ItemTool && (((ItemTool) held.getItem()).getMaterial() == AetherToolMaterial.valkyrie)) return true;
        return held.getItem() instanceof ItemToolSword && ((ItemToolSwordAccessor) held.getItem()).getMaterial() == AetherToolMaterial.valkyrie;
    }
}
