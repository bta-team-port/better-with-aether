package teamport.aether.items;

import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicChestMimic;
import teamport.aether.entity.tile.TileEntityMimic;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.entity.monster.mimic.MobMimic.VARIANT_OAK;
import static teamport.aether.entity.monster.mimic.MobMimic.VARIANT_SKYROOT;

public class ItemStaffHaunting extends Item {

    public ItemStaffHaunting(String translationKey, String namespaceId, int id) {
        super(translationKey, namespaceId, id);
        setMaxStackSize(1);
    }

    @Override
    public boolean onUseItemOnBlock(ItemStack itemstack, Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        int blockID = world.getBlockId(blockX, blockY, blockZ);
        int blockMeta = world.getBlockMetadata(blockX, blockY, blockZ);

        if (!(blockID == Blocks.CHEST_PLANKS_OAK.id()
                || blockID == Blocks.CHEST_PLANKS_OAK_PAINTED.id()
                || blockID == AetherBlocks.CHEST_PLANKS_SKYROOT.id()
        )) return false;

        TileEntityChest chest = (TileEntityChest) world.getTileEntity(blockX, blockY, blockZ);

        TileEntityMimic mimic = new TileEntityMimic();
        for (int i = 0; i < chest.getContainerSize(); i++) {
            mimic.setItem(i, chest.getItem(i));
        }

        world.removeBlockTileEntity(blockX, blockY, blockZ);

        world.setBlockRaw(blockX, blockY, blockZ, AetherBlocks.CHEST_MIMIC.id());

        int newMeta = getNewMeta(blockMeta, blockID);

        world.setBlockMetadata(blockX, blockY, blockZ, newMeta);

        world.setTileEntity(blockX, blockY, blockZ, mimic);
        world.markBlockNeedsUpdate(blockX, blockY, blockZ);

        if (!EnvironmentHelper.isServerEnvironment()) {
            for (int i = 0; i < 8; i++) {
                world.spawnParticle("largesmoke", blockX+.5, blockY + 1, blockZ+.5, 0f, 0.10f, 0f, 0);
            }
        }

        world.playSoundAtEntity(entityplayer, entityplayer, "ambient.cave.cave", 1.0F, 1.0f);
        return true;
    }

    public static int getNewMeta(int blockMeta, int blockID) {
        int newMeta = BlockLogicChestMimic.setDirection(0, BlockLogicChest.getDirectionFromMeta(blockMeta));

        if (blockID == Blocks.CHEST_PLANKS_OAK.id()) {
            newMeta = BlockLogicChestMimic.setVariantToMeta(newMeta, VARIANT_OAK);
        }
        else if (blockID == Blocks.CHEST_PLANKS_OAK_PAINTED.id()) {
            newMeta = BlockLogicChestMimic.setVariantToMeta(newMeta, DyeColor.colorFromBlockMeta((blockMeta & 240) >> 4).blockMeta + 2);
        }
        else newMeta = BlockLogicChestMimic.setVariantToMeta(newMeta, VARIANT_SKYROOT);
        return newMeta;
    }
}
