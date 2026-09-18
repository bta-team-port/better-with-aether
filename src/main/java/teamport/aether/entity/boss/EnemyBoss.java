package teamport.aether.entity.boss;

import net.minecraft.core.item.ItemStack;
import teamport.aether.world.feature.util.WorldFeaturePoint;

public interface EnemyBoss {

    void setDungeonID(int id);

    String getTranslatedBossTitle();

    String getBossTitleKey();

    String getBossName();

    byte getBossColor();

    void returnToHome();

    void setReturnPoint(WorldFeaturePoint coord);

    boolean canFight();

    void setTrophy(ItemStack itemStack);

    @SuppressWarnings("unused")
    ItemStack getTrophy();
}
