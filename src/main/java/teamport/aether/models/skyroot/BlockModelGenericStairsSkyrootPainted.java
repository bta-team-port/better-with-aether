package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGenericStairs;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericStairsSkyrootPainted<T extends BlockLogic> extends BlockModelGenericStairs<T> {
    public final @NonNull StaticBlockModel[] models = new StaticBlockModel[16];

    public BlockModelGenericStairsSkyrootPainted(@NonNull Block<T> block) {
        super(block, BlockModelDispatcher.loadDataModel("aether:block/stairs/planks_skyroot/" + DyeColor.WHITE.colorID));

        for(DyeColor color : DyeColor.blockOrderedColors()) {
            this.models[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/stairs/planks_skyroot/" + color.colorID).asModel();
        }

    }

    @Override
    public @NonNull StaticBlockModel getModelFromData(int data) {
        return this.models[data >> 4 & 15];
    }
}

