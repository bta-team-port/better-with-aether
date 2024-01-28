package bta.aether.item;

import bta.aether.accessory.API.Accessory;
import net.minecraft.core.item.Item;

public abstract class ItemToolAccessory extends Item implements Accessory {
    public ItemToolAccessory(String name, int id){
        super(name, id);
        this.maxStackSize = 1;
    }
}
