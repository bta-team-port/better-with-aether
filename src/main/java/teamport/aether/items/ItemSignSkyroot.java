package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.gui.AetherScreens;
import teamport.aether.tile.TileEntitySignSkyroot;

public class ItemSignSkyroot extends Item {
    public ItemSignSkyroot(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
    }

    public boolean onUseItemOnBlock(ItemStack itemstack, Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        int sideHit = side.getId();
        if (side == Side.BOTTOM) {
            return false;
        } else if (!world.getBlockMaterial(blockX, blockY, blockZ).isSolid()) {
            return false;
        } else {
            if (!world.canPlaceInsideBlock(blockX, blockY, blockZ)) {
                blockX += side.getOffsetX();
                blockY += side.getOffsetY();
                blockZ += side.getOffsetZ();
            }

            if (blockY >= 0 && blockY < world.getHeightBlocks()) {
                if (!AetherBlocks.SIGN_POST_PLANKS_SKYROOT.canPlaceBlockAt(world, blockX, blockY, blockZ)) {
                    return false;
                } else {
                    if (sideHit == 1) {
                        world.playBlockSoundEffect(entityplayer, (float)blockX + 0.5F, (float)blockY + 0.5F, (float)blockZ + 0.5F, AetherBlocks.SIGN_POST_PLANKS_SKYROOT, EnumBlockSoundEffectType.PLACE);
                        world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, AetherBlocks.SIGN_POST_PLANKS_SKYROOT.id(), MathHelper.floor((double)((entityplayer.yRot + 180.0F) * 16.0F / 360.0F) + 0.5) & 15);
                    } else {
                        world.playBlockSoundEffect(entityplayer, (float)blockX + 0.5F, (float)blockY + 0.5F, (float)blockZ + 0.5F, AetherBlocks.SIGN_WALL_PLANKS_SKYROOT, EnumBlockSoundEffectType.PLACE);
                        world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, AetherBlocks.SIGN_WALL_PLANKS_SKYROOT.id(), sideHit);
                    }

                    itemstack.consumeItem(entityplayer);
                    TileEntitySignSkyroot tileentitysign = (TileEntitySignSkyroot)world.getTileEntity(blockX, blockY, blockZ);
                    if (tileentitysign != null) {
                        tileentitysign.setOwner(entityplayer);
                        ((AetherScreens) entityplayer).aether$displaySignSkyrootEditorScreen(tileentitysign);
                    }

                    return true;
                }
            } else {
                return false;
            }
        }
    }
}
