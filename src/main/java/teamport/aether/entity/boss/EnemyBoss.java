package teamport.aether.entity.boss;

import net.minecraft.core.item.ItemStack;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

public interface EnemyBoss<Mob> {

    void setDungeonID(int id);
    String getBossTitle();

    String getBossName();

    void returnToHome();

    void setReturnPoint(WorldFeaturePoint coord);

    boolean canFight();

    void setTrophy(ItemStack itemStack);
    ItemStack getTrophy();
}
