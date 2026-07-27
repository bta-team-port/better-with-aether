package teamport.aether.item.item_tool;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLog;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.data.gamerule.TreecapitatorHelper;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.item.AetherItems;

public class ItemToolAxeAether extends ItemTool {
    public ItemToolAxeAether(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, 3, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_AXE);
    }

    @Override
    public boolean canHarvestBlock(ItemStack itemStack, Mob mob, Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
    }

    @Override
    public boolean beforeBlockDestroyed(ItemStack itemStack, World world, Player player, Block<?> block, TilePosc blockPos, Side side) {
        if (!world.isClientSide && world.getGameRuleValue(GameRules.TREECAPITATOR) && !player.isSneaking()) {
            ItemStack held = player.getHeldItem();
            if (Block.hasLogicClass(block, BlockLogicLog.class) && (block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE) || held != null && held.itemID == AetherItems.TOOL_AXE_VALKYRIE.id)) {
                return !(new TreecapitatorHelper(world, blockPos.x(), blockPos.y(), blockPos.z(), player)).chopTree();
            }
        }
        return true;
    }
}
