package teamport.aether.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.helper.BlockCoordinate;

public class MobBoss extends MobMonster implements EnemyBoss {
    protected static String translationKey = "MISSING";
    public MobBoss(@Nullable World world) {
        super(world);
    }

    @Override
    public void onDeath(Entity entityKilledBy) {
        super.onDeath(entityKilledBy);

        if (blocksDestroyOnDeath != null) {
            world.playBlockEvent(null, 1003, (int) x, (int) y, (int) z, 0);

            for (BlockCoordinate coordinate : blocksDestroyOnDeath) {
                world.spawnParticle("smoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0,0);
                world.spawnParticle("largesmoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0,0);
                world.setBlockAndMetadataWithNotify(coordinate.x, coordinate.y, coordinate.z, 0, 0);
            }
        }
    }

    @Override
    public String getBossTitle() {
        return bossName + ", The " +  I18n.getInstance().translateKey(translationKey);
    }

    @Override
    public void returnToHome() {
        if (returnPoint == null) return;
        moveTo(returnPoint.x, returnPoint.y, returnPoint.z, 0, 0);
    }
}
