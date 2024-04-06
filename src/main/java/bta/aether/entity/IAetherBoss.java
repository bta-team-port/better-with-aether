package bta.aether.entity;

import bta.aether.util.AetherBlockCoord;
import net.minecraft.core.item.ItemStack;

public interface IAetherBoss {
    String getBossTitle();

    // is this needed? might just remove it.
    void setMaxHealth(int health);

    void setToDungeon(int ID);
    int getDungeon();

    void setReturnPoint(AetherBlockCoord coordinate);
    AetherBlockCoord getReturnPoint();

    void setBlocksDestroyOnDeath(AetherBlockCoord[] CoordinateArray);
    AetherBlockCoord[] getBlocksDestroyOnDeath();

    // this item will be dropped on death.
    void setKeychain(ItemStack key);
    ItemStack getKeyChain();
}
