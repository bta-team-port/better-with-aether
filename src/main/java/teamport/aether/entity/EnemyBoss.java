package teamport.aether.entity;

import net.minecraft.core.item.ItemStack;

import javax.annotation.Nullable;

public interface EnemyBoss {

    int dungeonID = -1;
    String getBossName = "";
    @Nullable
    ItemStack trophy = null;

    void returnToPedestal();
}
