package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;

public class BlockLogicChestLocked extends BlockLogicRotatable {
    private final ItemStack key;
    private final Block<?> unlockedChest;
    private final boolean locked;

    public BlockLogicChestLocked(Block<BlockLogic> block, ItemStack key, boolean locked, Block<?> unlockedChest) {
        super(block, Materials.STONE);
        this.key = key;
        this.locked = locked;
        this.unlockedChest = unlockedChest;
        block.withEntity(TileEntityChest::new);
    }

    @Override
    public int getPistonPushReaction(World world, TilePosc pos) {
        return this.locked
            ? Material.PISTON_CANT_PUSH
            : super.getPistonPushReaction(world, pos);
    }

    @Override
    public boolean onInteracted(World world, TilePosc pos, Player player, Side side, double xHit, double yHit) {
        return onBlockRightClicked(world, pos.x(), pos.y(), pos.z(), player, side, xHit, yHit);
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
        if (this.locked && !player.gamemode.hasInvulnerablePlayer()) {
            ItemStack item = player.getHeldItem();

            if (item != null && item.itemID == key.itemID) {
                item.consumeItem(player);
                world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, x + 0.5, y, z + 0.5, "random.door_open", 0.5f, 1.5f);
                world.setBlockAndMetadataRaw(x, y, z, unlockedChest.id(), world.getBlockMetadata(x, y, z));
                world.markBlockNeedsUpdate(x, y, z);
            }
            return true;
        }

        player.displayChestScreen(BlockLogicChest.getInventory(world, new TilePos(x, y, z)), x, y, z);
        return true;
    }

    @Override
    public float blockStrength(World world, int x, int y, int z, Side side, Player player) {
        if (this.block.blockHardness < 0.0F) {
            return 0.0F;
        } else {
            return !player.canHarvestBlock(this.block) ? 1.0F / this.block.blockHardness / 100.0F : player.getCurrentPlayerStrVsBlock(this.block) / this.block.blockHardness / 30.0F;
        }
    }

}
