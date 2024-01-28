package bta.aether.mixin.client;

import bta.aether.api.IAetherPuff;
import bta.aether.entity.EntityAerbunny;
import bta.aether.packet.PuffAerBunnyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityClientPlayerMP;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.player.Session;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityClientPlayerMP.class, remap = false)
public abstract class EntityClientPlayerMPMixin extends EntityPlayerSP implements IAetherPuff {
    @Unique
    public boolean swimmingOld;

    public EntityClientPlayerMPMixin(Minecraft minecraft, World world, Session session, NetClientHandler netclienthandler) {
        super(minecraft, world, session, 0);
    }


    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        if (this.passenger instanceof EntityAerbunny) {
            if (isPuff()) {
                EntityClientPlayerMP clientPlayerMP = (EntityClientPlayerMP) (Object) this;
                clientPlayerMP.sendQueue.addToSendQueue(new PuffAerBunnyPacket());
            }
        }
    }
}