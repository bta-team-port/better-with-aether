package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.generic.BlockModelGenericChestPainted;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelPaintedSkyrootChest<T extends BlockLogic> extends BlockModelGenericChestPainted<T> {
    public BlockModelPaintedSkyrootChest(Block<T> block) {
        super(block);
        for (DyeColor color : DyeColor.blockOrderedColors()) {
            String root = "aether:block/chest/skyroot/" + color.colorID + "/";
            this.single[color.blockMeta] = load(root + "single");
            this.left[color.blockMeta] = load(root + "left");
            this.right[color.blockMeta] = load(root + "right");
        }
    }

    private @NonNull StaticBlockModel load(String model) {
        return BlockModelDispatcher.loadDataModel(model).asModel();
    }
}
