package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.Nullable;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;

public class BlockLogicPathDirtAether extends BlockLogic {
    public BlockLogicPathDirtAether(@NonNull Block<?> block) {
        super(block, Materials.DIRT);
        block.setTicking(true);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        block.withLightBlock(255);
    }

    @Override
    public @Nullable AABBdc getCollisionAABB(@NonNull WorldSource source, @NonNull TilePosc tilePos) {
        return new AABBd(tilePos.x(), tilePos.y(), tilePos.z(), tilePos.x() + 1.0, tilePos.y() + 1.0, tilePos.z() + 1.0);
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean isCubeShaped() {
        return false;
    }

    @Override
    public boolean suffocatesEntities(@NonNull WorldSource source, @NonNull TilePosc tilePos, @NonNull Class<? extends Entity> entityClass) {
        return true;
    }

    @Override
    public void onNeighborChanged(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Block<?> block) {
        super.onNeighborChanged(world, tilePos, block);
        TilePos up = tilePos.up(new TilePos());
        Material material = world.getBlockMaterial(up);
        Block<?> b = world.getBlockType(up);
        if (material.isSolid()
            && b != Blocks.FENCE_GATE_PLANKS_OAK
            && b != Blocks.FENCE_GATE_PLANKS_OAK_PAINTED
            && b != Blocks.SIGN_WALL_PLANKS_OAK
            && b != AetherBlocks.SIGN_WALL_PLANKS_SKYROOT
            && b != Blocks.SIGN_WALL_PLANKS_OAK_PAINTED
            && b != AetherBlocks.SIGN_WALL_PLANKS_SKYROOT_PAINTED
            && b != AetherBlocks.FENCEGATE_PLANKS_SKYROOT
            && b != AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED) {
            world.setBlockTypeNotify(tilePos, AetherBlocks.DIRT_AETHER);
        }
    }

    @Override
    public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
        return switch (dropCause) {
            case SILK_TOUCH, PICK_BLOCK -> new ItemStack[]{new ItemStack(this)};
            default -> new ItemStack[]{new ItemStack(AetherBlocks.DIRT_AETHER)};
        };
    }
}
