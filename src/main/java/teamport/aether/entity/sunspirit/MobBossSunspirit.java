package teamport.aether.entity.sunspirit;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.EnemyBoss;
import teamport.aether.entity.MobBoss;
import teamport.aether.entity.projectile.ProjectileFireElement;

public class MobBossSunspirit extends MobBoss implements EnemyBoss {
    public MobBossSunspirit(@Nullable World world) {
        super(world);
        this.setSize(2.25F, 2.5F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_sunspirit");
        this.fireImmune = true;
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
        if (distance < 10.0F) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            if (this.attackTime == 0) {
                if (!this.world.isClientSide) {
                    ProjectileFireElement dart = new ProjectileFireElement(this.world, this);
                    this.world.entityJoinedWorld(dart);
                }

                this.attackTime = 30;
            }

            this.yRot = (float)(Math.atan2(d1, d) * 180.0 / Math.PI) - 90.0F;
            this.hasAttacked = true;
        }

    }


    public String getEntityTexture() {
        if (this.hurtTime > 0) {
            return "/assets/aether/textures/entity/boss_sunspirit/sunspirit_hurt.png";
        }
        return "/assets/aether/textures/entity/boss_sunspirit/sunspirit.png";
    }


    public @NotNull String getDefaultEntityTexture() {
        if (this.hurtTime > 0) {
            return "/assets/aether/textures/entity/boss_sunspirit/sunspirit_hurt.png";
        }
        return "/assets/aether/textures/entity/boss_sunspirit/sunspirit.png";
    }

}
