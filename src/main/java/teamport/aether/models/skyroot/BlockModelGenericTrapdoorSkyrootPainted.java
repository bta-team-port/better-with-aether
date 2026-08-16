package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericTrapdoorSkyrootPainted<T extends BlockLogic> extends BlockModelGeneric<T> {
    public final @NonNull StaticBlockModel[] bottom = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] open = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] top = new StaticBlockModel[16];

    public BlockModelGenericTrapdoorSkyrootPainted(@NonNull Block<T> block) {
        super(block, BlockModelDispatcher.loadDataModel("aether:block/trapdoor/planks_skyroot/white/bottom"));

        for(DyeColor color : DyeColor.blockOrderedColors()) {
            this.bottom[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/trapdoor/planks_skyroot/" + color.colorID + "/bottom").asModel();
            this.open[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/trapdoor/planks_skyroot/" + color.colorID + "/open").asModel();
            this.top[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/trapdoor/planks_skyroot/" + color.colorID + "/top").asModel();
        }

    }

    @Override
    public boolean renderAttached(@NonNull TessellatorGeneral tessellator, @NonNull WorldSource worldSource, @NonNull TilePosc tilePos, boolean cullFaces, @Nullable IconCoordinate overrideTexture) {
        int rotation = worldSource.getBlockData(tilePos) & 3;
        boolean var10000;
        switch (rotation & 3) {
            case 1 -> var10000 = this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 2, 0, (double)0.0F, (double)0.0F, (double)0.0F, false, cullFaces, overrideTexture);
            case 2 -> var10000 = this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 1, 0, (double)0.0F, (double)0.0F, (double)0.0F, false, cullFaces, overrideTexture);
            case 3 -> var10000 = this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 3, 0, (double)0.0F, (double)0.0F, (double)0.0F, false, cullFaces, overrideTexture);
            default -> var10000 = this.getModel(worldSource, tilePos).renderAttached(this, tessellator, worldSource, tilePos, 0, 0, 0, (double)0.0F, (double)0.0F, (double)0.0F, false, cullFaces, overrideTexture);
        }

        return var10000;
    }

    @Override
    public @NonNull StaticBlockModel getModelFromData(int data) {
        int color = data >> 4 & 15;
        if ((data & 4) != 0) {
            return this.open[color];
        } else {
            return (data & 8) != 0 ? this.top[color] : this.bottom[color];
        }
    }
}
