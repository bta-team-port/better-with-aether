package teamport.aether.items.itemtool;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherHasCustomDamageType;

import java.util.Random;

public class ItemToolShovelAether extends ItemTool{
    public ItemToolShovelAether(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, 1, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL);
    }

    @Override
    public boolean canHarvestBlock(Mob mob, ItemStack itemStack, Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL);
    }

    @Override
    public boolean onUseItemOnBlock(ItemStack itemstack, Player player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        return this.shovelBlock(itemstack, player, world, blockX, blockY, blockZ, side);
    }

    public boolean shovelBlock(ItemStack itemstack, @Nullable Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side) {
        int blockId = world.getBlockId(blockX, blockY, blockZ);
        int blockAbove = world.getBlockId(blockX, blockY + 1, blockZ);
        if (side != Side.BOTTOM && blockAbove == 0 && (blockId == Blocks.GRASS.id() || blockId == Blocks.DIRT.id() || blockId == Blocks.GRASS_RETRO.id() || blockId == Blocks.FARMLAND_DIRT.id())) {
            world.playBlockSoundEffect(entityplayer, (float) blockX + 0.5F, (float) blockY + 0.5F, (float) blockZ + 0.5F, Blocks.blocksList[blockId], EnumBlockSoundEffectType.PLACE);
            if (!world.isClientSide) {
                world.setBlockWithNotify(blockX, blockY, blockZ, Blocks.PATH_DIRT.id());
                itemstack.damageItem(1, entityplayer);
            }
            return true;
        }
        if (side != Side.BOTTOM && blockAbove == 0 && (blockId == AetherBlocks.GRASS_AETHER.id() || blockId == AetherBlocks.DIRT_AETHER.id())) {
            world.playBlockSoundEffect(entityplayer, (float) blockX + 0.5F, (float) blockY + 0.5F, (float) blockZ + 0.5F, Blocks.blocksList[blockId], EnumBlockSoundEffectType.PLACE);
            if (!world.isClientSide) {
                world.setBlockWithNotify(blockX, blockY, blockZ, AetherBlocks.PATH_DIRT_AETHER.id());
                itemstack.damageItem(1, entityplayer);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUseByActivator(ItemStack itemStack, TileEntityActivator activatorBlock, World world, Random random, int blockX, int blockY, int blockZ, double offX, double offY, double offZ, Direction direction) {
        this.shovelBlock(itemStack, null, world, blockX + direction.getOffsetX(), blockY + direction.getOffsetY(), blockZ + direction.getOffsetZ(), direction.getSide());
    }
}
