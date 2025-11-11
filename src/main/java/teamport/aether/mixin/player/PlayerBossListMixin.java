package teamport.aether.mixin.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.net.message.BossListNetworkMessage;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerBossListMixin extends Mob implements AetherBossList {
    public PlayerBossListMixin(@Nullable World world) {
        super(world);
    }

    @Unique
    List<Mob> aether$bossList = new ArrayList<>();

    @Override
    public List<Mob> aether$getBossList() {
        List<Mob> _bosses = new ArrayList<>(aether$bossList);

        for (Mob boss : aether$bossList) {
            if (
                    (boss instanceof EnemyBoss && !((EnemyBoss) boss).canFight())
                            || !boss.isAlive()
                            || boss.world.dimension != world.dimension
                            || boss.distanceTo(this) > AetherDimension.BOSS_DETECTION_RADIUS
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

            if (EnvironmentHelper.isServerEnvironment()) {
                NetworkHandler.sendToPlayer(
                        Player.class.cast(this),
                        new BossListNetworkMessage(BossListNetworkMessage.Type.ADD, mob)
                );
            }
        }
    }

    @Override
    public void aether$clearBossList() {
        aether$bossList.clear();

        if (EnvironmentHelper.isServerEnvironment()) {
            NetworkHandler.sendToPlayer(
                    Player.class.cast(this),
                    BossListNetworkMessage.clear()
            );
        }
    }

    @Override
    public void aether$removeFromBossList(Mob mob) {
        aether$bossList.remove(mob);

        if (EnvironmentHelper.isServerEnvironment()) {
            NetworkHandler.sendToPlayer(
                    Player.class.cast(this),
                    new BossListNetworkMessage(BossListNetworkMessage.Type.REMOVE, mob)
            );
        }
    }

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"))
    public void attackTargetEntityWithCurrentItem(Entity entity, CallbackInfo ci) {
        if ((entity instanceof EnemyBoss && entity instanceof Mob) && !aether$bossList.contains(entity)) {
            aether$TryAddBossList((Mob) entity);
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(CallbackInfo ci) {
        aether$bossList.clear();
    }
}
