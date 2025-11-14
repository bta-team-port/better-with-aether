package teamport.aether.blocks.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureOre;
import teamport.aether.items.AetherItems;

public class BlockLogicOreAmbrosium extends BlockLogic {
    public static final WorldFeatureOre.OreMap variantMap = new WorldFeatureOre.OreMap();

    public BlockLogicOreAmbrosium(Block<?> block, Block<?> parentBlock, Material material) {
        super(block, material);
        variantMap.put(parentBlock.id(), block.id());
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, Side side, int meta, Player player, Item item) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem().equals(AetherItems.TOOL_PICKAXE_SKYROOT) && meta == 0 && player.getGamemode().consumeBlocks()) {
            this.harvestBlock(world, player, x, y, z, 0, world.getTileEntity(x, y, z));
        }
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                return new ItemStack[]{new ItemStack(this)};
            case EXPLOSION:
            case PROPER_TOOL:
            case PISTON_CRUSH:
                return new ItemStack[]{new ItemStack(AetherItems.AMBROSIUM)};
            default:
                return null;
        }
    }
}
