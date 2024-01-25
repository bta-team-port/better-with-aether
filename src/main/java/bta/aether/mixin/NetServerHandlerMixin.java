package bta.aether.mixin;

import bta.aether.api.IAetherPacket;
import bta.aether.entity.EntityAerbunny;
import bta.aether.packet.PuffAerBunnyPacket;
import bta.aether.util.PacketHandler;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NetServerHandler.class, remap = false)
public abstract class NetServerHandlerMixin implements IAetherPacket {
    @Shadow
    private EntityPlayerMP playerEntity;

    @Override
    public void aether$handlePuffBunny(PuffAerBunnyPacket packet) {
        if (playerEntity.passenger instanceof EntityAerbunny) {
            PacketHandler.handleAerBunnyPuffJump(playerEntity, (EntityAerbunny) playerEntity.passenger);
        }
    }
}
