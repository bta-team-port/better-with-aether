package teamport.aether.item.item_tool;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import redart15.commandly.veincapitator.VeinMining;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.compat.commandly.AetherCommandlyRules;
import teamport.aether.entity.player.PlayerUtil;

import java.util.HashMap;
import java.util.Map;

import static teamport.aether.block.AetherBlocks.*;

public class ItemToolPickaxeAether extends ItemTool {
    public static Map<Block<?>, Integer> aetherMiningLevels = new HashMap<>();

    public ItemToolPickaxeAether(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, 2, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
    }

    @Override
    public boolean canHarvestBlock(@NonNull ItemStack itemStack, @NonNull Mob mob, @NonNull Block<?> block) {
        Integer miningLevel = aetherMiningLevels.get(block);
        if (miningLevel != null) {
            return this.material.getMiningLevel() >= miningLevel;
        } else {
            return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        }
    }

    @Override
    public boolean beforeBlockDestroyed(@NonNull ItemStack selfStack, @NonNull World world, @NonNull Player player, @NonNull Block<?> block, @NonNull TilePosc blockPos, @NonNull Side side) {
        if (!world.isClientSide && AetherCommandlyRules.canVeinMine(world) && !player.isSneaking()) {
            return !VeinMining
                .veinMining(world, selfStack, blockPos, player)
                .setDropCause(PlayerUtil.isSilkTouchPendant(player) ? EnumDropCause.SILK_TOUCH : EnumDropCause.PROPER_TOOL)
                .setMiningTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
                .mine(block, side);
        }
        return true;
    }

    static {
        aetherMiningLevels.put(ICESTONE, 1);
        aetherMiningLevels.put(CARVED_STONE, 1);
        aetherMiningLevels.put(SLAB_CARVED_STONE, 1);
        aetherMiningLevels.put(STAIRS_CARVED_STONE, 1);
        aetherMiningLevels.put(CARVED_STONE_LIGHT, 1);
        aetherMiningLevels.put(CHEST_DUNGEON_BRONZE, 1);
        aetherMiningLevels.put(CHEST_MIMIC_BRONZE, 1);
        aetherMiningLevels.put(CARVED_STONE_TRAPPED, 1);

        aetherMiningLevels.put(CARVED_ANGELIC, 1);
        aetherMiningLevels.put(SLAB_CARVED_ANGELIC, 1);
        aetherMiningLevels.put(STAIRS_CARVED_ANGELIC, 1);
        aetherMiningLevels.put(CARVED_ANGELIC_LIGHT, 1);
        aetherMiningLevels.put(CHEST_DUNGEON_SILVER, 1);
        aetherMiningLevels.put(CHEST_MIMIC_SILVER, 1);

        aetherMiningLevels.put(CARVED_HELLFIRE, 1);
        aetherMiningLevels.put(SLAB_CARVED_HELLFIRE, 1);
        aetherMiningLevels.put(STAIRS_CARVED_HELLFIRE, 1);
        aetherMiningLevels.put(CARVED_HELLFIRE_LIGHT, 1);
        aetherMiningLevels.put(CHEST_DUNGEON_GOLD, 1);
        aetherMiningLevels.put(CHEST_MIMIC_GOLD, 1);

        aetherMiningLevels.put(PILLAR, 1);
        aetherMiningLevels.put(PILLAR_CAPSTONE, 1);

        aetherMiningLevels.put(BLOCK_ZANITE, 1);
        aetherMiningLevels.put(ORE_ZANITE_HOLYSTONE, 1);
        aetherMiningLevels.put(BRICK_ZANITE, 1);
        aetherMiningLevels.put(SLAB_BRICK_ZANITE, 1);
        aetherMiningLevels.put(STAIRS_BRICK_ZANITE, 1);


        aetherMiningLevels.put(BLOCK_GRAVITITE, 2);
        aetherMiningLevels.put(ORE_GRAVITITE_HOLYSTONE, 2);
        aetherMiningLevels.put(BRICK_GRAVITITE, 2);
        aetherMiningLevels.put(SLAB_BRICK_GRAVITITE, 2);
        aetherMiningLevels.put(STAIRS_BRICK_GRAVITITE, 2);

        aetherMiningLevels.put(AEROGEL, 3);
    }
}
