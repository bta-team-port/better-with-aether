package teamport.aether.achievements;

import net.minecraft.client.gui.achievements.ScreenAchievements;

public interface AetherAchievementPageExtras {
    float getShadowScale(int layer);

    void drawBeforeTiles(ScreenAchievements gui, double shiftX, double shiftY, int mouseX, int mouseY, int left, int top, int right, int bottom);
}
