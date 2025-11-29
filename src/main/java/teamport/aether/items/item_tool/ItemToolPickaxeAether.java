package teamport.aether.items.item_tool;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import redart15.commandly.veincapitator.VeinMining;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.compat.commandly.AetherCommandlyRules;
import teamport.aether.entity.player.PlayerUntil;

import java.util.HashMap;
import java.util.Map;

import static teamport.aether.blocks.AetherBlocks.*;

public class ItemToolPickaxeAether extends ItemTool {
    protected static final Map<Block<?>, Integer> MINING_LEVELS = new HashMap<>();

    public ItemToolPickaxeAether(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, 2, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
    }

    @Override
    public boolean canHarvestBlock(Mob mob, ItemStack itemStack, Block<?> block) {
        Integer miningLevel = MINING_LEVELS.get(block);
        if (miningLevel != null) {
            return this.material.getMiningLevel() >= miningLevel;
        } else {
            return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        }
    }

    @Override
    public boolean beforeDestroyBlock(World world, ItemStack itemStack, int blockId, int x, int y, int z, Side side, Player player) {
        if (!world.isClientSide && AetherCommandlyRules.canVeinMine(world) && !player.isSneaking()) {
            return !VeinMining
                .veinMining(world, itemStack, x, y, z, player)
                .setDropCause(PlayerUntil.isSilkTouch(player) ? EnumDropCause.SILK_TOUCH : EnumDropCause.PROPER_TOOL)
                .setMiningTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
                .mine(blockId, side);
        }
        return true;
    }

    static {
        MINING_LEVELS.put(ICESTONE, 1);
        MINING_LEVELS.put(CARVED_STONE, 1);
        MINING_LEVELS.put(SLAB_CARVED_STONE, 1);
        MINING_LEVELS.put(STAIRS_CARVED_STONE, 1);
        MINING_LEVELS.put(CARVED_STONE_LIGHT, 1);
        MINING_LEVELS.put(CHEST_DUNGEON_BRONZE, 1);
        MINING_LEVELS.put(CHEST_MIMIC_BRONZE, 1);
        MINING_LEVELS.put(CARVED_STONE_TRAPPED, 1);

        MINING_LEVELS.put(CARVED_ANGELIC, 1);
        MINING_LEVELS.put(SLAB_CARVED_ANGELIC, 1);
        MINING_LEVELS.put(STAIRS_CARVED_ANGELIC, 1);
        MINING_LEVELS.put(CARVED_ANGELIC_LIGHT, 1);
        MINING_LEVELS.put(CHEST_DUNGEON_SILVER, 1);
        MINING_LEVELS.put(CHEST_MIMIC_SILVER, 1);

        MINING_LEVELS.put(CARVED_HELLFIRE, 1);
        MINING_LEVELS.put(SLAB_CARVED_HELLFIRE, 1);
        MINING_LEVELS.put(STAIRS_CARVED_HELLFIRE, 1);
        MINING_LEVELS.put(CARVED_HELLFIRE_LIGHT, 1);
        MINING_LEVELS.put(CHEST_DUNGEON_GOLD, 1);
        MINING_LEVELS.put(CHEST_MIMIC_GOLD, 1);

        MINING_LEVELS.put(PILLAR, 1);
        MINING_LEVELS.put(PILLAR_CAPSTONE, 1);

        MINING_LEVELS.put(BLOCK_ZANITE, 1);
        MINING_LEVELS.put(ORE_ZANITE_HOLYSTONE, 1);
        MINING_LEVELS.put(BRICK_ZANITE, 1);
        MINING_LEVELS.put(SLAB_BRICK_ZANITE, 1);
        MINING_LEVELS.put(STAIRS_BRICK_ZANITE, 1);


        MINING_LEVELS.put(BLOCK_GRAVITITE, 2);
        MINING_LEVELS.put(ORE_GRAVITITE_HOLYSTONE, 2);

        MINING_LEVELS.put(AEROGEL, 3);
    }
}
