package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.data.block.BlockModelData;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelFixedAxis<T extends BlockLogic> extends BlockModelGeneric<T> {
    public BlockModelFixedAxis(@NonNull Block<T> block, @NonNull StaticBlockModel staticModel) {
        super(block, staticModel);
    }

    public BlockModelFixedAxis(@NonNull Block<T> block, @NonNull BlockModelData staticModel) {
        super(block, staticModel);
    }

    @Override
    public boolean renderAttached(@NonNull TessellatorGeneral tessellator, @NonNull WorldSource worldSource, @NonNull TilePosc tilePos, boolean cullFaces, @Nullable IconCoordinate overrideTexture) {
        Axis axis = metaToAxis(worldSource.getBlockData(tilePos));
        switch (axis) {
            case X -> {
                return this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 1, 1, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
            }
            case Y -> {
                return this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 0, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
            }
            case Z -> {
                return this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 1, 0, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
            }
            default -> {
                return false;
            }
        }
    }

    public static @NonNull Axis metaToAxis(int meta) {
        return switch (meta & 3) {
            case 2 -> Axis.X;
            case 1 -> Axis.Z;
            default -> Axis.Y;
        };
    }
}
