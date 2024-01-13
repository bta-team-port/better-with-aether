package bta.aether.mixin;

import bta.aether.block.IDimensionSound;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockPortal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixin extends EntityPlayer {
    public EntityPlayerSPMixin(World world) {
        super(world);
    }
    @Redirect(method = "onLivingUpdate()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundManager;playSound(Ljava/lang/String;Lnet/minecraft/core/sound/SoundType;FF)V"))
    private void customPortalSounds(SoundManager soundManager, String soundPath, SoundType soundType, float volume, float pitch){
        BlockPortal portal = (BlockPortal) Block.blocksList[portalID];
        if (portal instanceof IDimensionSound){
            IDimensionSound dimensionSound = (IDimensionSound) portal;
            if (soundPath.equals("portal.trigger")){
                soundManager.playSound(dimensionSound.portalTrigger(), soundType, volume, pitch);
                return;
            } else if (soundPath.equals("portal.travel")) {
                soundManager.playSound(dimensionSound.portalTransport(), soundType, volume, pitch);
                return;
            }
        }
        soundManager.playSound(soundPath, soundType, volume, pitch);
    }
}
