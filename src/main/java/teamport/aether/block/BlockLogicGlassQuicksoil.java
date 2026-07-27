package teamport.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTransparent;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import static teamport.aether.AetherConfig.QUICK_SOIL_SPEED_CAP;

public class BlockLogicGlassQuicksoil extends BlockLogicTransparent {
    public BlockLogicGlassQuicksoil(Block<?> block) {
        super(block, Materials.GLASS);
        block.friction = 1.05f;
    }

    @Override
    public boolean collidesWithEntity(Entity entity, World world, int x, int y, int z) {
        entity.xd = Math.max(Math.min(entity.xd, QUICK_SOIL_SPEED_CAP), -QUICK_SOIL_SPEED_CAP);
        entity.zd = Math.max(Math.min(entity.zd, QUICK_SOIL_SPEED_CAP), -QUICK_SOIL_SPEED_CAP);
        return super.collidesWithEntity(entity, world, x, y, z);
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                return new ItemStack[]{new ItemStack(this)};
            default:
                return null;
        }
    }
}
