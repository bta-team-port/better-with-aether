package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityDispenser;
import net.minecraft.core.item.Items;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.components.WorldFeatureBlock;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawShell;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class ThunderRoom extends BaseBronzeRoom {
    public static BlockPallet ROOM_PALLET = new BlockPallet();
    public WorldFeatureComponent dispenser;
    public static WeightedRandomBag<WeightedRandomLootObject> AMMO = new WeightedRandomBag<>();
    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);

        AMMO.addEntry(new WeightedRandomLootObject(null), 5);
        AMMO.addEntry(new WeightedRandomLootObject(Items.AMMO_ARROW.getDefaultStack(), 4, 8), 5);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_GOLDEN.getDefaultStack(), 4, 8), 5);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_POISON.getDefaultStack(), 4, 8), 3);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_ENCHANTED.getDefaultStack(), 4, 8), 3);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_KNIFE_LIGHTNING.getDefaultStack(), 4, 8), 2);
    }
    public ThunderRoom() {
        super();
        this.height = 14;
        this.dispenser = new WorldFeatureComponent();
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);
    }
    public void makeShell() {
        room.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y - 2, z, true));
        room.add(drawVolume(0, 0, SOUTH, width - 2, UP, height - 2, EAST, width - 2, x + 1, y - 1, z + 1, true));
    }

    public void makeTraps() {
        decoration.add(drawVolume(random, ROOM_PALLET, SOUTH, width - 2, UP, 1, EAST, width - 2, x + 1, y, z + 1, true));;

        decoration.add(drawVolume(AetherBlocks.AERCLOUD_WHITE.id(), 0, SOUTH, width - 2, UP, 1, EAST, 4, x + 4, y, z + 1, true));
        decoration.add(drawVolume(AetherBlocks.AERCLOUD_WHITE.id(), 0, SOUTH, 4, UP, 1, EAST, width - 2, x + 1, y, z + 4, true));

        decoration.add(wfb(x + 4, y - 1, z + 1, Blocks.MOTION_SENSOR_IDLE.id(), 1));
        decoration.add(wfb(x + 5, y - 1, z + 1, Blocks.DISPENSER_COBBLE_STONE.id(), 1));
        dispenser.add(wfb(x + 5, y - 1, z + 1, Blocks.DISPENSER_COBBLE_STONE.id(), 1));

        decoration.add(wfb(x + 10, y - 1, z + 4, Blocks.MOTION_SENSOR_IDLE.id(), 1));
        decoration.add(wfb(x + 10, y - 1, z + 5, Blocks.DISPENSER_COBBLE_STONE.id(), 1));
        dispenser.add(wfb(x + 10, y - 1, z + 5, Blocks.DISPENSER_COBBLE_STONE.id(), 1));

        decoration.add(wfb(x + 4, y - 1, z + 10, Blocks.MOTION_SENSOR_IDLE.id(), 1));
        decoration.add(wfb(x + 5, y - 1, z + 10, Blocks.DISPENSER_COBBLE_STONE.id(), 1));
        dispenser.add(wfb(x + 5, y - 1, z + 10, Blocks.DISPENSER_COBBLE_STONE.id(), 1));

        decoration.add(wfb(x + 1, y - 1, z + 4, Blocks.MOTION_SENSOR_IDLE.id(), 1));
        decoration.add(wfb(x + 1, y - 1, z + 5, Blocks.DISPENSER_COBBLE_STONE.id(), 1));
        dispenser.add(wfb(x + 1, y - 1, z + 5, Blocks.DISPENSER_COBBLE_STONE.id(), 1));
    }

    @Override
    public void makeRoom() {
        this.makeShell();
        this.makeTraps();
    }

    @Override
    public void placeRoom(){
        super.placeRoom();
        for(WorldFeatureBlock block : dispenser.blockList){
            TileEntity tileEntity = world.getTileEntity(block.x, block.y, block.z);
            if(!(tileEntity instanceof TileEntityDispenser)){
                continue;
            }
            TileEntityDispenser disp = (TileEntityDispenser) tileEntity;
            for(int i = 0; i < disp.getContainerSize(); i++){
                disp.setItem(i, AMMO.getRandom(random).getItemStack());
            }
        }
    }
}
