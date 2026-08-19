package teamport.aether.item.item_tool;

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
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;

import java.util.Random;

public class ItemToolShovelAether extends ItemTool {
    public ItemToolShovelAether(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, 1, enumtoolmaterial, AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL);
    }

    @Override
    public boolean canHarvestBlock(@NonNull ItemStack itemStack, @NonNull Mob mob, @NonNull Block<?> block) {
        return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL);
    }

    @Override
    public boolean onUseOnBlock(@NonNull ItemStack selfStack, @NonNull World world, @Nullable Player player, @NonNull TilePosc blockPos, @NonNull Side side, double xHit, double yHit) {
        return this.shovelBlock(selfStack, world, player, blockPos, side);
    }

    @Override
    public void onUseByActivator(@NonNull ItemStack selfStack, @NonNull World world, @NonNull TileEntityActivator activator, @NonNull Random random, @NonNull TilePosc blockPos, @NonNull Direction direction, double offX, double offY, double offZ) {
        this.shovelBlock(selfStack, world, null, blockPos.add(direction, new TilePos()), direction.side());
    }

    public boolean shovelBlock(@NonNull ItemStack selfStack, @NonNull World world, @Nullable Player entityplayer, @NonNull TilePosc blockPos, @NonNull Side side) {
        if (side == Side.BOTTOM) {
            return false;
        }

        Block<?> blockAbove = world.getBlockType(blockPos.up(new TilePos()));
        if (blockAbove != Blocks.AIR) {
            return false;
        }

        Block<?> block = world.getBlockType(blockPos);
        Block<?> targetPathBlock;

        if (block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.GRASS_RETRO || block == Blocks.FARMLAND_DIRT) {
            targetPathBlock = Blocks.PATH_DIRT;
        } else if (block == AetherBlocks.GRASS_AETHER || block == AetherBlocks.DIRT_AETHER) {
            targetPathBlock = AetherBlocks.PATH_DIRT_AETHER;
        } else {
            return false;
        }

        world.playBlockSoundEffect(entityplayer, blockPos.x() + 0.5F, blockPos.y() + 0.5F, blockPos.z() + 0.5F, block, EnumBlockSoundEffectType.PLACE);

        if (!world.isClientSide) {
            world.setBlockTypeNotify(blockPos, targetPathBlock);
            selfStack.damageItem(1, entityplayer);
        }

        return true;
    }
}
