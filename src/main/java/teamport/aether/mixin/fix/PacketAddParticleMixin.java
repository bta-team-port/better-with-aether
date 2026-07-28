package teamport.aether.mixin.fix;

import net.minecraft.core.net.packet.PacketAddParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Mixin(value = PacketAddParticle.class)
public abstract class PacketAddParticleMixin {
    @Shadow
    public int data;
    @Inject(method = "write", at = @At("HEAD"))
    private void writeData(DataOutputStream dos, CallbackInfo ci) throws IOException {
        dos.writeInt(data);
    }
    @Inject(method = "read", at = @At("HEAD"))
    private void writeData(DataInputStream dis, CallbackInfo ci) throws IOException {
        data = dis.readInt();
    }
}
