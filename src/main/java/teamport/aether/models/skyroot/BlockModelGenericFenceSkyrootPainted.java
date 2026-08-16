package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGenericFence;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFence;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericFenceSkyrootPainted<T extends BlockLogicFence> extends BlockModelGenericFence<T> {
    public final @NonNull StaticBlockModel[] inventory = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] post = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] side = new StaticBlockModel[16];

    public BlockModelGenericFenceSkyrootPainted(@NonNull Block<T> block) {
        super(block, "aether:block/fence/white");

        for(DyeColor color : DyeColor.blockOrderedColors()) {
            this.inventory[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/fence/" + color.colorID + "/inventory").asModel();
            this.post[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/fence/" + color.colorID + "/post").asModel();
            this.side[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/fence/" + color.colorID + "/side").asModel();
        }

    }

    @Override
    public boolean renderAttached(@NonNull TessellatorGeneral tessellator, @NonNull WorldSource worldSource, @NonNull TilePosc tilePos, boolean cullFaces, @Nullable IconCoordinate overrideTexture) {
        int color = worldSource.getBlockData(tilePos) & 15;
        boolean north = this.block.getLogic().canConnectTo(worldSource, tilePos, Side.NORTH);
        boolean south = this.block.getLogic().canConnectTo(worldSource, tilePos, Side.SOUTH);
        boolean west = this.block.getLogic().canConnectTo(worldSource, tilePos, Side.WEST);
        boolean east = this.block.getLogic().canConnectTo(worldSource, tilePos, Side.EAST);
        if (north) {
            this.side[color].renderAttached(this, tessellator, worldSource, tilePos, 0, 0, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
        }

        if (west) {
            this.side[color].renderAttached(this, tessellator, worldSource, tilePos, 0, 1, 0, 0.0F, 0.0F, 0.0F, true, cullFaces, overrideTexture);
        }

        if (south) {
            this.side[color].renderAttached(this, tessellator, worldSource, tilePos, 0, 2, 0, 0.0F, 0.0F, 0.0F, true, cullFaces, overrideTexture);
        }

        if (east) {
            this.side[color].renderAttached(this, tessellator, worldSource, tilePos, 0, 3, 0, 0.0F, 0.0F, 0.0F, true, cullFaces, overrideTexture);
        }

        return this.post[color].renderAttached(this, tessellator, worldSource, tilePos, 0, 0, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
    }

    @Override
    public @NonNull StaticBlockModel getModelFromData(int data) {
        return this.inventory[data & 15];
    }
}
