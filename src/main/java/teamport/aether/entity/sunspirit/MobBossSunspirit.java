package teamport.aether.entity.sunspirit;

import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.EnemyBoss;

public class MobBossSunspirit extends MobMonster implements EnemyBoss {
    public MobBossSunspirit(@Nullable World world) {
        super(world);
        this.setSize(2.25F, 2.5F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_sunspirit");
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
