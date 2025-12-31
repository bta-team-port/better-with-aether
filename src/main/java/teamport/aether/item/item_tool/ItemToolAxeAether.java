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
import teamport.aether.block.AetherBlockTags;
import teamport.aether.item.AetherItems;

public class ItemToolAxeAether extends ItemTool {
    public ItemToolAxeAether(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, 3, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_AXE);
    }

    @Override
    public boolean canHarvestBlock(Mob mob, ItemStack itemStack, Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
    }

    @SuppressWarnings("java:S5411")
    @Override
    public boolean beforeDestroyBlock(World world, ItemStack itemStack, int blockId, int x, int y, int z, Side side, Player player) {
        if (!world.isClientSide && world.getGameRuleValue(GameRules.TREECAPITATOR) && !player.isSneaking()) {
            ItemStack held = player.getHeldItem();
            Block<?> block = Blocks.blocksList[blockId];
            if (Block.hasLogicClass(block, BlockLogicLog.class) && (block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE) || held != null && held.itemID == AetherItems.TOOL_AXE_VALKYRIE.id)) {
                return !(new TreecapitatorHelper(world, x, y, z, player)).chopTree();
            }
        }
        return true;
    }
}
