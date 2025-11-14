package teamport.aether.blocks.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockLogicChestLocked extends BlockLogicRotatable {
    private final ItemStack key;
    private final Block<?> unlockedChest;
    private final boolean locked;

    public BlockLogicChestLocked(Block<BlockLogic> block, ItemStack key, boolean locked, Block<?> unlockedChest) {
        super(block, Material.stone);
        this.key = key;
        this.locked = locked;
        this.unlockedChest = unlockedChest;
        block.withEntity(TileEntityChest::new);
    }

    @SuppressWarnings("java:S3516")
    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
        if (this.locked && !player.gamemode.isPlayerInvulnerable()) {
            ItemStack item = player.getHeldItem();

            if (item != null && item.itemID == key.itemID) {
                item.consumeItem(player);
                world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, x + 0.5, y, z + 0.5, "random.door_open", 0.5f, 1.5f);
                world.setBlockAndMetadataRaw(x, y, z, unlockedChest.id(), world.getBlockMetadata(x, y, z));
                world.markBlockNeedsUpdate(x, y, z);
            }
            return true;
        }

        player.displayChestScreen(BlockLogicChest.getInventory(world, x, y, z), x, y, z);
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
