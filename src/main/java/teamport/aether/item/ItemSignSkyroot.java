package teamport.aether.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.entity.TileEntitySignSkyroot;
import teamport.aether.gui.AetherScreens;

public class ItemSignSkyroot extends Item {
    private final boolean isPainted;

    public ItemSignSkyroot(String name, String namespaceId, int id, boolean isPainted) {
        super(name, namespaceId, id);
        this.isPainted = isPainted;
    }

    private Block<? extends BlockLogic> getBlockWall() {
        if (isPainted) return AetherBlocks.SIGN_WALL_PLANKS_SKYROOT_PAINTED;
        else return AetherBlocks.SIGN_WALL_PLANKS_SKYROOT;
    }

    private Block<? extends BlockLogic> getBlockPost() {
        if (isPainted) return AetherBlocks.SIGN_POST_PLANKS_SKYROOT_PAINTED;
        else return AetherBlocks.SIGN_POST_PLANKS_SKYROOT;
    }

    @Override
    public boolean onUseItemOnBlock(ItemStack itemstack, Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        int sideHit = side.getId();
        if (side == Side.BOTTOM) return false;
        if (!world.getBlockMaterial(blockX, blockY, blockZ).isSolid()) return false;

        if (!world.canPlaceInsideBlock(blockX, blockY, blockZ)) {
            blockX += side.getOffsetX();
            blockY += side.getOffsetY();
            blockZ += side.getOffsetZ();
        }

        if (!(blockY >= 0 && blockY < world.getHeightBlocks())) return false;
        if (!AetherBlocks.SIGN_POST_PLANKS_SKYROOT.canPlaceBlockAt(world, blockX, blockY, blockZ)) return false;

        Block<?> blockToPlace = sideHit == 1 ? getBlockPost() : getBlockWall();
        int meta = sideHit == 1 ? MathHelper.floor(((entityplayer.yRot + 180.0F) * 16.0F / 360.0F) + 0.5) & 15 : sideHit;
        if (isPainted) {
            meta = DyeColor.colorFromItemMeta(itemstack.getMetadata()).blockMeta << 4 | meta;
        }

        world.playBlockSoundEffect(entityplayer, blockX + 0.5F, blockY + 0.5F, blockZ + 0.5F, blockToPlace, EnumBlockSoundEffectType.PLACE);
        world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, blockToPlace.id(), meta);

        itemstack.consumeItem(entityplayer);
        TileEntitySignSkyroot tileEntity = (TileEntitySignSkyroot) world.getTileEntity(blockX, blockY, blockZ);

        if (tileEntity != null) {
            tileEntity.setOwner(entityplayer);
            ((AetherScreens) entityplayer).aether$displaySignSkyrootEditorScreen(tileEntity);
        }

        return true;
    }

    @Override
    public String getLanguageKey(ItemStack itemstack) {
        if (!isPainted) return super.getLanguageKey(itemstack);
        return super.getKey() + "." + DyeColor.colorFromItemMeta(itemstack.getMetadata()).colorID;
    }
}
