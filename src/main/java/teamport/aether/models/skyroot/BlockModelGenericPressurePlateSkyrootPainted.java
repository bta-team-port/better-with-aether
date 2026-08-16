package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGenericPressurePlate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericPressurePlateSkyrootPainted<T extends BlockLogic> extends BlockModelGenericPressurePlate<T> {
    public final @NonNull StaticBlockModel[] inventory = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] idle = new StaticBlockModel[16];
    public final @NonNull StaticBlockModel[] active = new StaticBlockModel[16];

    public BlockModelGenericPressurePlateSkyrootPainted(@NonNull Block<T> block) {
        super(block, "aether:block/pressure_plate/planks_skyroot/white");

        for(DyeColor color : DyeColor.blockOrderedColors()) {
            this.inventory[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/pressure_plate/planks_skyroot/" + color.colorID + "/inventory").asModel();
            this.idle[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/pressure_plate/planks_skyroot/" + color.colorID + "/idle").asModel();
            this.active[color.blockMeta] = BlockModelDispatcher.loadDataModel("aether:block/pressure_plate/planks_skyroot/" + color.colorID + "/active").asModel();
        }

    }

    @Override
    public @NonNull StaticBlockModel getModel(@NonNull WorldSource source, @NonNull TilePosc tilePosc) {
        int data = source.getBlockData(tilePosc);
        int color = data >> 4 & 15;
        boolean pressed = (data & 1) != 0;
        return pressed ? this.active[color] : this.idle[color];
    }

    @Override
    public @NonNull StaticBlockModel getModelFromData(int data) {
        int color = data >> 4 & 15;
        return this.inventory[color];
    }
}
