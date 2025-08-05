package teamport.aether.entity.boss;

import net.minecraft.core.item.ItemStack;
import teamport.aether.helper.BlockCoordinate;

public interface EnemyBoss {

    void setDungeonID(int id);
    String getBossTitle();

    String getBossName();

    void returnToHome();

    void setReturnPoint(BlockCoordinate coord);

    void addDestroyOnDeathBlock(BlockCoordinate coord);

    boolean canFight();

    void setTrophy(ItemStack itemStack);
    ItemStack getTrophy();
}
