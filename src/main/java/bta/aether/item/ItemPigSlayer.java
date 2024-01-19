package bta.aether.item;

import net.minecraft.core.item.Item;

public class ItemPigSlayer extends Item {
    public ItemPigSlayer(String name, int i) {
        super(name, i);
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setFull3D();
    }


}
