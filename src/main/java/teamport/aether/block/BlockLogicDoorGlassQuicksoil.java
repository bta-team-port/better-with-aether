package teamport.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicDoor;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

import static teamport.aether.AetherConfig.QUICK_SOIL_SPEED_CAP;

public class BlockLogicDoorGlassQuicksoil extends BlockLogicDoor {
    public BlockLogicDoorGlassQuicksoil(Block<?> block, Material material, boolean isTop, boolean requireTool, @Nullable Supplier<Item> droppedItem) {
        super(block, material, isTop, requireTool, droppedItem);
        double f = 0.5;
        if (isTop) {
            this.setBlockBounds(0.5 - f, -1.0, 0.5 - f, 0.5 + f, 1.0, 0.5 + f);
        } else {
            this.setBlockBounds(0.5 - f, 0.0, 0.5 - f, 0.5 + f, 2.0, 0.5 + f);
        }
        block.friction = 1.05f;
    }

    @Override
    public void onEntityWalkedOn(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity walker) {
        walker.xd = Math.max(Math.min(walker.xd, QUICK_SOIL_SPEED_CAP), -QUICK_SOIL_SPEED_CAP);
        walker.zd = Math.max(Math.min(walker.zd, QUICK_SOIL_SPEED_CAP), -QUICK_SOIL_SPEED_CAP);
        super.onEntityWalkedOn(world, tilePos, walker);
    }

}
