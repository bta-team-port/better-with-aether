package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureOre;

public class BlockLogicOreAmbrosium extends BlockLogic {
    public static WorldFeatureOre.OreMap variantMap = new WorldFeatureOre.OreMap();

    public BlockLogicOreAmbrosium(Block<?> block, Block<?> parentBlock, Material material) {
        super(block, material);
        variantMap.put(parentBlock.id(), block.id());
    }

    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                return new ItemStack[]{new ItemStack(this)};
            case EXPLOSION:
            case PROPER_TOOL:
            case PISTON_CRUSH:
                return new ItemStack[]{new ItemStack(AetherBlocks.BLOCK_AMBROSIUM)};
            default:
                return null;
        }
    }
}
