package teamport.aether.item.item_tool.item_tool_valkyrie;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import redart15.commandly.veincapitator.VeinMining;
import teamport.aether.AetherMod;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.compat.commandly.AetherCommandlyRules;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.item.item_tool.ItemToolPickaxeAether;

public class ItemToolPickaxeValkyrie extends ItemToolPickaxeAether implements AetherHasCustomDamageType {
    public ItemToolPickaxeValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean canHarvestBlock(ItemStack itemStack, Mob mob, Block<?> block) {
        Integer miningLevel = aetherMiningLevels.get(block);
        if (miningLevel != null) {
            return this.material.getMiningLevel() >= miningLevel;
        } else {
            return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE) || block.hasTag(BlockTags.MINEABLE_BY_PICKAXE);
        }
    }

    @Override
    public boolean beforeBlockDestroyed(ItemStack selfStack, World world, Player player, Block<?> block, TilePosc blockPos, Side side) {
        if (!world.isClientSide && AetherCommandlyRules.canVeinMine(world) && !player.isSneaking()) {
            return !VeinMining
                .veinMining(world, selfStack, blockPos, player)
                .setDropCause(PlayerUtil.isSilkTouchPendant(player) ? EnumDropCause.SILK_TOUCH : EnumDropCause.PROPER_TOOL)
                .setMiningTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.MINEABLE_BY_PICKAXE)
                .mine(block, side);
        }
        return true;
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE) || block.hasTag(BlockTags.MINEABLE_BY_PICKAXE) ? this.material.getEfficiency(false) : 1.0F;
    }

    @Override
    public DamageType getDamageType() {
        return AetherMod.HOLY;
    }
}
