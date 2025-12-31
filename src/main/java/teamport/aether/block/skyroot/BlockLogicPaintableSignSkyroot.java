package teamport.aether.block.skyroot;

import net.minecraft.core.achievement.Achievements;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSign;
import net.minecraft.core.block.IPaintable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntitySign;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.block.AetherBlocks;
import teamport.aether.gui.AetherScreens;
import teamport.aether.item.AetherItems;

public class BlockLogicPaintableSignSkyroot extends BlockLogicSign implements IPaintable {

    public BlockLogicPaintableSignSkyroot(Block<?> block, boolean isFreeStanding) {
        super(block, isFreeStanding);
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return new ItemStack[]{new ItemStack(AetherItems.SIGN_SKYROOT)};
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
        TileEntitySign signEntity = (TileEntitySign) world.getTileEntity(x, y, z);
        if (signEntity != null && player != null) {
            if (player.getHeldItem() != null && player.getHeldItem().itemID == Items.DUST_GLOWSTONE.id && !signEntity.isGlowing()) {
                signEntity.setGlowing(true);
                if (player.getGamemode().consumeBlocks()) {
                    player.getHeldItem().stackSize--;
                }
                player.addStat(Achievements.LIGHT_SIGN, 1);
                return true;
            } else if (player.getHeldItem() != null && (player.getHeldItem().itemID == Items.DYE.id || player.getHeldItem().itemID == Items.PAINTBRUSH.id)) {
                return false;
            } else if (signEntity.isEditableBy(player)) {
                ((AetherScreens) player).aether$displaySignSkyrootEditorScreen(signEntity);
                return true;
            }
        }
        return false;
    }

    @Override
    public void setColor(World world, int x, int y, int z, DyeColor color) {
        world.setBlockRaw(x, y, z, this.isFreeStanding ? AetherBlocks.SIGN_POST_PLANKS_SKYROOT_PAINTED.id() : AetherBlocks.SIGN_WALL_PLANKS_SKYROOT_PAINTED.id());
        world.setBlockMetadataWithNotify(x, y, z, color.blockMeta << 4 | world.getBlockMetadata(x, y, z) & 15);
    }
}
