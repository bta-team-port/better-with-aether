package teamport.aether.world.feature.dungeon.bronze.component;

import teamport.aether.block.AetherBlocks;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureComponent.drawPlane;
import static teamport.aether.world.feature.util.WorldFeatureComponent.drawVolume;

public class DisplayRoom extends StorageRoom{

    public DisplayRoom() {
        super();
    }

    @Override
    public void makeRoom() {
        super.makeRoom();
        // center deco
        decoration.add(drawPlane(AetherBlocks.SLAB_CARVED_STONE.id(), 0, SOUTH, 4, EAST, 2, x + 5, y + 1, z + 4, false));
        decoration.add(drawPlane(AetherBlocks.SLAB_CARVED_STONE.id(), 0, SOUTH, 2, EAST, 4, x + 4, y + 1, z + 5, false));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 5, y + 1, z + 5, false));
        decoration.add(drawVolume(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, SOUTH, 2, EAST, 2, UP, 2, x + 5, y + 4, z + 5, false));
    }

}
