package teamport.aether.items.itemtool;

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
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.items.AetherItems;

public class ItemToolAxeAether extends ItemTool {
    public ItemToolAxeAether(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, 3, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_AXE);
    }

    public boolean canHarvestBlock(Mob mob, ItemStack itemStack, Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
    }

    public boolean beforeDestroyBlock(World world, ItemStack itemStack, int blockId, int x, int y, int z, Side side, Player player) {
        if (!world.isClientSide && world.getGameRuleValue(GameRules.TREECAPITATOR) && !player.isSneaking()) {
            ItemStack held = player.getHeldItem();
            if (
                    Block.hasLogicClass(Blocks.getBlock(blockId), BlockLogicLog.class)
                            && (Blocks.getBlock(blockId) != null
                            && (Blocks.getBlock(blockId).hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE))
                            || held.itemID == AetherItems.TOOL_AXE_VALKYRIE.id)

            ) {
                return !(new TreecapitatorHelper(world, x, y, z, player)).chopTree();
            }
        }

        return true;
    }
}
