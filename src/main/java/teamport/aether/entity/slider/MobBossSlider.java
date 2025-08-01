package teamport.aether.entity.slider;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.EnemyBoss;

public class MobBossSlider extends MobFlying implements EnemyBoss {
    public int moveTimer;
    public int dennis;
    public int rennis;
    public int chatTime;
    public Entity target;
    public boolean awake;
    public boolean gotMovement;
    public boolean crushed;
    public float speedy;
    public float harvey;
    public int direction;
    private int dungeonX;
    private int dungeonY;
    private int dungeonZ;
    public String bossName;
    public boolean isBoss = false;
    public int areaOfEffect = 50;
    public MobBossSlider(World world) {
        super(world);
        this.yRot = 0.0f;
        this.xRot = 0.0F;
        this.setSize(2.0F, 2.0F);
        this.dennis = 1;
        this.chatTime = 60;
//        this.bossName = NameGen.gen();
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_slider");

    }

    public int getMaxHealth() {
        return 500;
    }

    public String getEntityTexture() {
        if (this.awake) {
            if (this.criticalCondition()) {
                return "/assets/aether/textures/entity/boss_slider/slider_awake_red.png";
            } else {
                return "/assets/aether/textures/entity/boss_slider/slider_awake.png";
            }
        } else {
            if (this.criticalCondition()) {
                return "/assets/aether/textures/entity/boss_slider/slider_sleep_red.png";
            } else {
                return "/assets/aether/textures/entity/boss_slider/slider_sleep.png";
            }
        }
    }


    public @NotNull String getDefaultEntityTexture() {
        if (this.awake) {
            if (this.criticalCondition()) {
                return "/assets/aether/textures/entity/boss_slider/slider_awake_red.png";
            } else {
                return "/assets/aether/textures/entity/boss_slider/slider_awake.png";
            }
        } else {
            if (this.criticalCondition()) {
                return "/assets/aether/textures/entity/boss_slider/slider_sleep_red.png";
            } else {
                return "/assets/aether/textures/entity/boss_slider/slider_sleep.png";
            }
        }
    }

    public boolean criticalCondition() {
        return this.getHealth() <= 125;
    }
}
