package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGenericFenceGate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericFenceGateSkyrootPainted<T extends BlockLogic> extends BlockModelGenericFenceGate<T> {
    public @NonNull StaticBlockModel[] open = new StaticBlockModel[16];
    public @NonNull StaticBlockModel[] closed = new StaticBlockModel[16];

    public BlockModelGenericFenceGateSkyrootPainted(@NonNull Block<T> block) {
        super(block, "aether:block/fencegate/white");

        for(DyeColor color : DyeColor.blockOrderedColors()) {
            this.closed[color.blockMeta] = BlockModelDispatcher.loadDataModel(String.format("aether:block/fencegate/%s_closed", color.colorID)).asModel();
            this.open[color.blockMeta] = BlockModelDispatcher.loadDataModel(String.format("aether:block/fencegate/%s_open", color.colorID)).asModel();
        }

    }

    @Override
    public @NonNull StaticBlockModel getModelFromData(int data) {
        int color = data >> 4 & 15;
        boolean open = (data & 4) != 0;
        return open ? this.open[color] : this.closed[color];
    }
}
