package teamport.aether.entity;

import net.minecraft.core.item.ItemStack;
import teamport.aether.helper.BlockCoordinate;

import javax.annotation.Nullable;
import java.util.List;

public interface EnemyBoss {
    @Nullable
    Integer dungeonID = null;
    @Nullable
    String bossName = null;

    String getBossTitle();

    @Nullable
    ItemStack trophy = null;

    void returnToHome();

    @Nullable
    BlockCoordinate returnPoint = null;

    @Nullable
    List<BlockCoordinate> blocksDestroyOnDeath = null;
}
