package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGrassAether<T extends BlockLogic> extends BlockModelGeneric<T> {
    public final @NonNull StaticBlockModel snowy = BlockModelDispatcher.loadDataModel("aether:block/grass_aether/snowy").asModel();
    public final @NonNull StaticBlockModel ashy = BlockModelDispatcher.loadDataModel("aether:block/grass_aether/ashy").asModel();

    public BlockModelGrassAether(@NonNull Block<T> block) {
        super(block, BlockModelDispatcher.loadDataModel("aether:block/grass_aether/grass_aether"));
    }

    @Override
    public @NonNull StaticBlockModel getModel(@NonNull WorldSource source, @NonNull TilePosc tilePosc) {
        TilePos up = tilePosc.up(new TilePos());
        Block<?> topBlock = source.getBlockType(up);
        if (topBlock == Blocks.LAYER_SNOW) {
            return this.snowy;
        }
        if (topBlock == Blocks.LAYER_ASH) {
            return this.ashy;
        }
        return super.getModel(source, tilePosc);
    }

}
