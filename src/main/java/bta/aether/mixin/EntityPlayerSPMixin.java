package bta.aether.mixin;

import bta.aether.block.IDimensionSound;
import bta.aether.entity.EntityAetherBossBase;
import bta.aether.entity.IPlayerBossList;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockPortal;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixin extends EntityPlayer implements IPlayerBossList {
    @Unique List<EntityAetherBossBase> bossList = new ArrayList<>();
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
    @Override
    public void onDeath(Entity entity) {
        bossList.clear();
        super.onDeath(entity);
    }
    @Override
    public void attackTargetEntityWithCurrentItem(Entity entity) {
        if (entity instanceof EntityAetherBossBase && !bossList.contains(entity)){
            bossList.add((EntityAetherBossBase) entity);
        }
        super.attackTargetEntityWithCurrentItem(entity);
    }
    @Unique
    public List<EntityAetherBossBase> aether$getBossList(){
        List<EntityAetherBossBase> _bosses = new ArrayList<>(bossList);
        for (EntityAetherBossBase bossBase : bossList){
            if (!bossBase.isAlive()){
                _bosses.remove(bossBase);
            }
        }
        bossList = _bosses;
        return bossList;
    }
}
