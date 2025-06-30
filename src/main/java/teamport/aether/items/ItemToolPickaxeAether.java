package teamport.aether.items;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import teamport.aether.blocks.AetherBlockTags;

import java.util.HashMap;
import java.util.Map;

import static teamport.aether.blocks.AetherBlocks.*;

public class ItemToolPickaxeAether extends ItemTool {
    public static Map<Block<?>, Integer> miningLevels = new HashMap<>();
    public ItemToolPickaxeAether(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, 2, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
    }

    public boolean canHarvestBlock(Mob mob, ItemStack itemStack, Block<?> block) {
        Integer miningLevel = miningLevels.get(block);
        if (miningLevel != null) {
            return this.material.getMiningLevel() >= miningLevel;
        } else {
            return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        }
    }

    static {
        miningLevels.put(COBBLE_HOLYSTONE, 0);
        miningLevels.put(SLAB_COBBLE_HOLYSTONE, 0);
        miningLevels.put(STAIRS_COBBLE_HOLYSTONE, 0);
        miningLevels.put(COBBLE_HOLYSTONE_MOSSY, 0);
        miningLevels.put(HOLYSTONE, 0);
        miningLevels.put(HOLYSTONE_CARVED, 0);
        miningLevels.put(HOLYSTONE_POLISHED, 0);
        miningLevels.put(SLAB_HOLYSTONE_POLISHED, 0);
        miningLevels.put(BRICK_HOLYSTONE, 0);
        miningLevels.put(SLAB_BRICK_HOLYSTONE, 0);
        miningLevels.put(STAIRS_BRICK_HOLYSTONE, 0);

        miningLevels.put(ICESTONE, 1);
        miningLevels.put(CARVED_STONE, 1);
        miningLevels.put(SLAB_CARVED_STONE, 1);
        miningLevels.put(STAIRS_CARVED_STONE, 1);
        miningLevels.put(CARVED_STONE_LIGHT, 1);
        miningLevels.put(CARVED_ANGELIC, 1);
        miningLevels.put(SLAB_CARVED_ANGELIC, 1);
        miningLevels.put(STAIRS_CARVED_ANGELIC, 1);
        miningLevels.put(CARVED_ANGELIC_LIGHT, 1);
        miningLevels.put(CARVED_HELLFIRE, 1);
        miningLevels.put(SLAB_CARVED_HELLFIRE, 1);
        miningLevels.put(STAIRS_CARVED_HELLFIRE, 1);
        miningLevels.put(CARVED_HELLFIRE_LIGHT, 1);
        miningLevels.put(PILLAR, 1);
        miningLevels.put(PILLAR_CAPSTONE, 1);
        miningLevels.put(BLOCK_ZANITE, 1);
        miningLevels.put(ORE_ZANITE_HOLYSTONE, 1);
        miningLevels.put(BRICK_ZANITE, 1);
        miningLevels.put(SLAB_BRICK_ZANITE, 1);
        miningLevels.put(STAIRS_BRICK_ZANITE, 1);


        miningLevels.put(BLOCK_GRAVITITE, 2);
        miningLevels.put(ORE_GRAVITITE_HOLYSTONE, 2);

        miningLevels.put(AEROGEL, 3);
    }
}
