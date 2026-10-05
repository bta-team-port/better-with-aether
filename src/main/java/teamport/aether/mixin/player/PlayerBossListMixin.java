package teamport.aether.mixin.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
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

@Mixin(Player.class)
public abstract class PlayerBossListMixin extends Mob implements AetherBossList {

    @Unique
    private List<Mob> bossList = new ArrayList<>();

    protected PlayerBossListMixin(@NonNull World world) {
        super(world);
    }

    @Override
    public List<Mob> aether$getBossList() {
        List<Mob> bosses = new ArrayList<>(bossList);
        for (Mob boss : bossList) {
            if (boss instanceof EnemyBoss enemyBoss && !enemyBoss.canFight() || !boss.isAlive() || boss.world.dimension != world.dimension || boss.distanceTo(this) > AetherDimension.BOSS_DETECTION_RADIUS) {
                bosses.remove(boss);
            }
        }
        bossList = bosses;
        return bossList;
    }

    @Override
    public void aether$TryAddBossList(Mob mob) {
        if (!bossList.contains(mob)) {
            bossList.add(mob);
            if (EnvironmentHelper.isMultiplayerServer()) {
                NetworkHandler.sendToPlayer(
                    (Player) (Object) this,
                    new BossListNetworkMessage(BossListNetworkMessage.Type.ADD, mob)
                );
            }
        }
    }

    @Override
    public void aether$clearBossList() {
        bossList.clear();
        if (EnvironmentHelper.isMultiplayerServer()) {
            NetworkHandler.sendToPlayer(
                (Player) (Object) this,
                BossListNetworkMessage.clear()
            );
        }
    }

    @Override
    public void aether$removeFromBossList(Mob mob) {
        bossList.remove(mob);
        if (EnvironmentHelper.isMultiplayerServer()) {
            NetworkHandler.sendToPlayer(
                (Player) (Object) this,
                new BossListNetworkMessage(BossListNetworkMessage.Type.REMOVE, mob)
            );
        }
    }

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"))
    private void attackTargetEntityWithCurrentItem(Entity entity, CallbackInfo ci) {
        if ((entity instanceof EnemyBoss && entity instanceof Mob) && !bossList.contains(entity)) {
            aether$TryAddBossList((Mob) entity);
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(CallbackInfo ci) {
        bossList.clear();
    }
}
