package teamport.aether.mixin.player;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.net.packet.PacketSetRiding;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.world.AetherDimension;

@Environment(EnvType.SERVER)
@Mixin(value = PlayerList.class)
public abstract class WorldBunnyMixin {

    @Shadow
    public abstract void sendPacketToPlayersAroundPoint(double x, double y, double z, double radius, int dimension, Packet packet);

    @Shadow
    public abstract void sendPacketToAllPlayers(Packet packet);

    @WrapMethod(method = "playerLoggedIn")
    public void spawnBunny(PlayerServer player, Operation<Void> original) {
        original.call(player);
        MobAerbunny mobAerbunny = AetherDimension.popBunnyFromPlayer(player.uuid, player.world);

        if (mobAerbunny == null) return;

        PacketAddEntity addBunny = new PacketAddEntity(mobAerbunny);
        sendPacketToAllPlayers(addBunny);
        mobAerbunny.startRiding(player);
        player.positionRider();

        sendPacketToAllPlayers(new PacketSetRiding(mobAerbunny, player));
    }

    @WrapMethod(method = "playerLoggedOut")
    public void removeBunny(PlayerServer player, Operation<Void> original) {
        if (player.passenger instanceof MobAerbunny) {
            AetherDimension.addBunnyToPlayer(player.uuid, (MobAerbunny) player.passenger);
        }
        original.call(player);
    }

}
