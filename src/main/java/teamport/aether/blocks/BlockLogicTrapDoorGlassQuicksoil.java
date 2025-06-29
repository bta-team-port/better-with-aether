package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTrapDoor;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;

public class BlockLogicTrapDoorGlassQuicksoil extends BlockLogicTrapDoor {

    public BlockLogicTrapDoorGlassQuicksoil(Block<?> block, Material material) {
        super(block, material);
        float f = 0.5F;
        float f1 = 1.0F;
        this.setBlockBounds(0.5F - f, 0.0, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        block.friction = 1.05f;
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        block.friction = 1.05f;
        entity.slide = true;
    }
}
