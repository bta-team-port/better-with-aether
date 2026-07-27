package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGenericLeaves;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.enums.LeavesQuality;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelLeavesAether<T extends BlockLogic> extends BlockModelGenericLeaves<T> {
    private final StaticBlockModel retroFancy;
    private final StaticBlockModel retroSmart;
    private final StaticBlockModel retroFast;

    public BlockModelLeavesAether(Block<T> block, String normalModel, String retroModel) {
        super(block, normalModel);
        this.retroFancy = BlockModelDispatcher.loadDataModel(retroModel + "_fancy").asModel();
        this.retroSmart = BlockModelDispatcher.loadDataModel(retroModel + "_smart").asModel();
        this.retroFast = BlockModelDispatcher.loadDataModel(retroModel + "_fast").asModel();
    }

    @Override
    public StaticBlockModel getModelFromData(int data) {
        return isRetro() ? getRetroModel() : super.getModelFromData(data);
    }

    @Override
    public StaticBlockModel getModel(WorldSource world, TilePosc pos) {
        return isRetro() ? getRetroModel() : super.getModel(world, pos);
    }

    private StaticBlockModel getRetroModel() {
        LeavesQuality quality = GameSettings.LEAVES_QUALITY.value;
        if (quality == LeavesQuality.FAST) return this.retroFast;
        if (quality == LeavesQuality.SMART) return this.retroSmart;
        return this.retroFancy;
    }
}
