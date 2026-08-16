package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericIncubator<T extends BlockLogic> extends BlockModelGeneric<T> {
    public final StaticBlockModel filled;

    public BlockModelGenericIncubator(@NotNull Block<T> block, @NotNull String baseKey) {
        super(block, BlockModelDispatcher.loadDataModel(baseKey));
        this.filled = BlockModelDispatcher.loadDataModel(baseKey + "_filled").asModel();
    }

    @Override
    public @NotNull StaticBlockModel getModel(@NotNull WorldSource source, @NotNull TilePosc tilePosc) {
        TileEntity var4 = source.getTileEntity(tilePosc);
        if (var4 instanceof Container container) {
            boolean hasOutput;
            hasOutput = container.getItem(0) != null;

            if (hasOutput) {
                return this.filled;
            }
        }

        return super.getModel(source, tilePosc);
    }
}
