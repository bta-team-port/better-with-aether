package teamport.aether.helper.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.packet.PacketSetRiding;
import net.minecraft.server.MinecraftServer;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;

@Environment(EnvType.SERVER)
public final class AerbunnyServerHelper {
    private AerbunnyServerHelper() { }

    public static void syncRiding(MobAerbunny bunny) {
        if (bunny.world == null || bunny.vehicle == null) return;
        MinecraftServer.getInstance().playerList.sendPacketToPlayersAroundPoint(
            bunny.x, bunny.y, bunny.z, 32, bunny.world.dimension.id,
            new PacketSetRiding(bunny, (Entity) bunny.vehicle)
        );
    }
}
