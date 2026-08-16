package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericChestSkyrootPainted<T extends BlockLogic> extends BlockModelGeneric<T> {
    public final @NonNull StaticBlockModel[] single = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] left = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] right = new StaticBlockModel[16];

    public BlockModelGenericChestSkyrootPainted(@NonNull Block<T> block) {
        super(block, BlockModelDispatcher.loadDataModel("aether:block/chest/single/" + DyeColor.WHITE.colorID));

        for(DyeColor c : DyeColor.blockOrderedColors()) {
            this.single[c.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/chest/single/" + c.colorID).asModel();
            this.left[c.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/chest/left/" + c.colorID).asModel();
            this.right[c.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/chest/right/" + c.colorID).asModel();
        }

    }

    @Override
    public boolean renderAttached(@NonNull TessellatorGeneral tessellator, @NonNull WorldSource worldSource, @NonNull TilePosc tilePos, boolean cullFaces, @Nullable IconCoordinate overrideTexture) {
        Direction direction = BlockLogicChest.getDirectionFromMeta(worldSource.getBlockData(tilePos));
        switch (direction) {
            case NORTH -> {
                return this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 0, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
            }
            case WEST -> {
                return this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 1, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
            }
            case EAST -> {
                return this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 3, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
            }
            case SOUTH -> {
                return this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 2, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
            }
            default -> {
                return this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 0, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
            }
        }
    }

    @Override
    public @NonNull StaticBlockModel getModel(@NonNull WorldSource source, @NonNull TilePosc tilePosc) {
        int data = source.getBlockData(tilePosc);
        int color = data >> 4;
        BlockLogicChest.Type type = BlockLogicChest.getTypeFromMeta(data);
        StaticBlockModel var10000;
        switch (type) {
            case LEFT -> var10000 = this.left[color];
            case RIGHT -> var10000 = this.right[color];
            case SINGLE -> var10000 = this.single[color];
            default -> throw new IncompatibleClassChangeError();
        }

        return var10000;
    }

    @Override
    public @NonNull StaticBlockModel getModelFromData(int data) {
        int color = data >> 4;
        return this.single[color];
    }
}
