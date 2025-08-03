package teamport.aether.mixin;

import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.entity.AetherBossList;
import teamport.aether.entity.EnemyBoss;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = PlayerLocal.class, remap = false)
public abstract class PlayerLocalBossListMixin extends Player implements AetherBossList {
    public PlayerLocalBossListMixin(@Nullable World world) {
        super(world);
    }
    @Unique
    List<Mob> aether$bossList = new ArrayList<>();

    @Override
    public List<Mob> aether$getBossList() {
        List<Mob> _bosses = new ArrayList<>(aether$bossList);

        for (Mob boss : aether$bossList) {
            if (
                (boss instanceof EnemyBoss && !((EnemyBoss) boss).canFight()) ||
                !boss.isAlive()
            ) {
                _bosses.remove(boss);
            }
        }

        aether$bossList = _bosses;
        return aether$bossList;
    }

    @Override
    public void aether$TryAddBossList(Mob mob) {
        if (!aether$bossList.contains(mob)) {
            aether$bossList.add(mob);
        }
    }

    @Override
    public void attackTargetEntityWithCurrentItem(Entity entity) {
        if ((entity instanceof EnemyBoss && entity instanceof Mob) && !aether$bossList.contains(entity))  {
            aether$bossList.add((Mob) entity);
        }

        super.attackTargetEntityWithCurrentItem(entity);
    }

    @Override
    public void onDeath(Entity entityKilledBy) {
        aether$bossList.clear();

        super.onDeath(entityKilledBy);
    }
}
