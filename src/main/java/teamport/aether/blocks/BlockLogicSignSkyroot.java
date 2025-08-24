package teamport.aether.blocks;

import net.minecraft.core.achievement.Achievements;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.IPaintable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import teamport.aether.gui.AetherScreens;
import teamport.aether.items.AetherItems;
import teamport.aether.entity.tile.TileEntitySignSkyroot;

public class BlockLogicSignSkyroot extends BlockLogic implements IPaintable {
    public final boolean isFreeStanding;

    public BlockLogicSignSkyroot(Block<?> block, boolean isFreeStanding) {
        super(block, Material.wood);
        this.isFreeStanding = isFreeStanding;
        float f = 0.25F;
        float f1 = 1.0F;
        this.setBlockBounds(0.5F - f, 0.0, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        block.withEntity(TileEntitySignSkyroot::new);
    }

    public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
        return null;
    }

    public AABB getBlockBoundsFromState(WorldSource world, int x, int y, int z) {
        if (this.isFreeStanding) {
            return this.getBounds();
        } else {
            float bottom = 0.28125F;
            float top = 0.78125F;
            float width = 1.0F;
            float thickness = 0.125F;
            switch (world.getBlockMetadata(x, y, z) & 15) {
                case 2:
                    return AABB.getTemporaryBB(0.0, bottom, 1.0F - thickness, width, top, 1.0);
                case 3:
                    return AABB.getTemporaryBB(0.0, bottom, 0.0, width, top, thickness);
                case 4:
                    return AABB.getTemporaryBB(1.0F - thickness, bottom, 0.0, 1.0, top, width);
                case 5:
                    return AABB.getTemporaryBB(0.0, bottom, 0.0, thickness, top, width);
                default:
                    return AABB.getTemporaryBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
        }
    }

    public boolean isCubeShaped() {
        return false;
    }

    public boolean isSolidRender() {
        return false;
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        boolean isUnstable = false;
        if (this.isFreeStanding) {
            if (!world.getBlockMaterial(x, y - 1, z).isSolid()) {
                isUnstable = true;
            }
        } else {
            switch (world.getBlockMetadata(x, y, z) & 15) {
                case 2:
                    isUnstable = !world.getBlockMaterial(x, y, z + 1).isSolid();
                    break;
                case 3:
                    isUnstable = !world.getBlockMaterial(x, y, z - 1).isSolid();
                    break;
                case 4:
                    isUnstable = !world.getBlockMaterial(x + 1, y, z).isSolid();
                    break;
                case 5:
                    isUnstable = !world.getBlockMaterial(x - 1, y, z).isSolid();
                    break;
                default:
                    isUnstable = true;
            }
        }

        if (isUnstable) {
            this.dropBlockWithCause(world, EnumDropCause.WORLD, x, y, z, world.getBlockMetadata(x, y, z), null, null);
            world.setBlockWithNotify(x, y, z, 0);
        }

        super.onNeighborBlockChange(world, x, y, z, blockId);
    }

    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return new ItemStack[]{new ItemStack(AetherItems.SIGN_SKYROOT)};
    }

    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
        TileEntitySignSkyroot signEntity = (TileEntitySignSkyroot)world.getTileEntity(x, y, z);
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

    public int getPistonPushReaction(World world, int x, int y, int z) {
        return 1;
    }

    public void setColor(World world, int x, int y, int z, DyeColor color) {
    }
}
