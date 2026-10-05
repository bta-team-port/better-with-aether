package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.data.block.BlockModelData;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericPlanksSkyrootPainted<T extends BlockLogic> extends BlockModelGeneric<T> {
    public static final IconCoordinate[] texCoords = new IconCoordinate[16];
    public final StaticBlockModel[] models = new StaticBlockModel[16];

    public BlockModelGenericPlanksSkyrootPainted(@NonNull Block<T> block, @NonNull StaticBlockModel staticModel) {
        super(block, staticModel);

        for(DyeColor c : DyeColor.blockOrderedColors()) {
            this.models[c.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/planks_skyroot/" + c.colorID).asModel();
        }

    }

    public BlockModelGenericPlanksSkyrootPainted(@NonNull Block<T> block, @NonNull BlockModelData staticModel) {
        super(block, staticModel);

        for(DyeColor c : DyeColor.blockOrderedColors()) {
            this.models[c.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/planks_skyroot/" + c.colorID).asModel();
        }

    }

    @Override
    public @NonNull StaticBlockModel getModelFromData(int data) {
        return this.models[data & 15];
    }

    static {
        for(DyeColor c : DyeColor.blockOrderedColors()) {
            texCoords[c.blockMeta] = TextureRegistry.getTexture("aether:block/planks_skyroot/" + c.colorID);
        }

    }
}
