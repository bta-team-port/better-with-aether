package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGenericDoor;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericDoorSkyrootPainted<T extends BlockLogic> extends BlockModelGenericDoor<T> {
    public final @NonNull StaticBlockModel[] left = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] left_open = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] right = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] right_open = new StaticBlockModel[16];

    public BlockModelGenericDoorSkyrootPainted(@NonNull Block<T> block, boolean bottom) {
        super(block, "aether:block/door/planks_skyroot/white", bottom);
        String side = bottom ? "bottom" : "top";

        for(DyeColor color : DyeColor.blockOrderedColors()) {
            this.left[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/door/planks_skyroot/%s/%s_left".formatted(color.colorID, side)).asModel();
            this.left_open[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/door/planks_skyroot/%s/%s_left_open".formatted(color.colorID, side)).asModel();
            this.right[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/door/planks_skyroot/%s/%s_right".formatted(color.colorID, side)).asModel();
            this.right_open[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/door/planks_skyroot/%s/%s_right_open".formatted(color.colorID, side)).asModel();
        }

    }

    @Override
    public @NonNull StaticBlockModel getModelFromData(int data) {
        int color = data >> 4 & 15;
        boolean isLeft = (data & 8) != 0;
        boolean isOpen = (data & 4) != 0;
        if (isLeft) {
            return isOpen ? this.left_open[color] : this.left[color];
        } else {
            return isOpen ? this.right[color] : this.right_open[color];
        }
    }
}
