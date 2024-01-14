package bta.aether.entity;

import net.minecraft.core.item.ItemStack;

public interface IAetherBoss {
    String getBossTitle();

    void setMaxHealth(int health);
    int getMaxHealth();

    void setToDungeon(int ID);
    int getDungeon();

    // key item to drop on death.
    void setKeychain(ItemStack key);
    ItemStack getKeyChain();
}
