package teamport.aether.entity;

import net.minecraft.core.item.ItemStack;
import teamport.aether.helper.BlockCoordinate;

import javax.annotation.Nullable;
import java.util.List;

public interface EnemyBoss {
    String getBossTitle();

    String getBossName();

    void returnToHome();

    void addDestroyOnDeathBlock(BlockCoordinate coord);

    boolean canFight();
}
