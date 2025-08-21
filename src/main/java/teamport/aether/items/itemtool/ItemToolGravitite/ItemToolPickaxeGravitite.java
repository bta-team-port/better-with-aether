package teamport.aether.items.itemtool.ItemToolGravitite;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.entity.EntityFloatingBlock;
import teamport.aether.items.itemtool.ItemToolPickaxeAether;

public class ItemToolPickaxeGravitite extends ItemToolPickaxeAether {

    public ItemToolPickaxeGravitite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean onUseItemOnBlock(
            ItemStack itemstack,
            Player player,
            World world,
            int blockX, int blockY, int blockZ,
            Side side,
            double xPlaced, double yPlaced
    ) {
        Block<?> block = world.getBlock(blockX, blockY, blockZ);
        Block<?> nextBlock = world.getBlock(blockX, blockY + 1, blockZ);
        if (block == null
                || !block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
                || !player.isSneaking()
                || block.getHardness() < 0
                || nextBlock != null
                && nextBlock.id() != Blocks.COBWEB.id()
                && !nextBlock.hasTag(BlockTags.PLACE_OVERWRITES)) {
            return false;
        }

        EntityFloatingBlock entityFloatingBlock = new EntityFloatingBlock(
                world,
                (double) blockX + 0.5F, (double) blockY + 0.5F, (double) blockZ + 0.5F,
                block.id(), world.getBlockMetadata(blockX, blockY, blockZ), null);
        world.entityJoinedWorld(entityFloatingBlock);
        world.setBlockWithNotify(blockX, blockY, blockZ, 0);
        itemstack.damageItem(1, player);
        return true;
    }
}