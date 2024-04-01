package bta.aether.entity;

import net.minecraft.core.item.ItemStack;

public interface IAetherBoss {
    String getBossTitle();

    // is this needed? might just remove it.
    void setMaxHealth(int health);

    void setToDungeon(int ID);
    int getDungeon();

    // this item will be dropped on death.
    void setKeychain(ItemStack key);
    ItemStack getKeyChain();
}
