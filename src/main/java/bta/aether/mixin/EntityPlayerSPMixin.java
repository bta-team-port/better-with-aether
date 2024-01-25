package bta.aether.mixin;

import bta.aether.api.IAetherPuff;
import bta.aether.block.IPortalExtras;
import bta.aether.entity.*;
import bta.aether.gui.*;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import bta.aether.tile.TileEntityIncubator;
import bta.aether.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockPortal;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixin extends EntityPlayer implements IPlayerBossList, IAetherGuis, IAetherAccessories, IAetherPuff {

    @Shadow
    protected Minecraft mc;
    @Unique
    List<EntityAetherBossBase> bossList = new ArrayList<>();

    @Unique
    int jumpAmount = 0;
    @Unique
    int jumpMaxAmount = 0;
    private boolean invisible;
    @Unique
    int puffCooldown = 0;
    @Unique
    boolean puff;

    public EntityPlayerSPMixin(World world) {
        super(world);
    }

    @Redirect(method = "onLivingUpdate()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundManager;playSound(Ljava/lang/String;Lnet/minecraft/core/sound/SoundType;FF)V"))
    private void customPortalSounds(SoundManager soundManager, String soundPath, SoundType soundType, float volume, float pitch) {
        BlockPortal portal = (BlockPortal) Block.blocksList[portalID];
        if (portal instanceof IPortalExtras) {
            IPortalExtras dimensionSound = (IPortalExtras) portal;
            if (soundPath.equals("portal.trigger")) {
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
        if (entity instanceof EntityAetherBossBase && !bossList.contains(entity)) {
            bossList.add((EntityAetherBossBase) entity);
        }
        super.attackTargetEntityWithCurrentItem(entity);
    }

    @Unique
    public List<EntityAetherBossBase> aether$getBossList() {
        List<EntityAetherBossBase> _bosses = new ArrayList<>(bossList);
        for (EntityAetherBossBase bossBase : bossList) {
            if (!bossBase.isAlive() || (bossBase instanceof EntityBossSlider && !((EntityBossSlider) bossBase).awake)) {
                _bosses.remove(bossBase);
            }
        }
        bossList = _bosses;
        return bossList;
    }

    @Unique
    public void aether$displayGUIEnchanter(TileEntityEnchanter tile) {
        mc.displayGuiScreen(new GuiEnchanter(inventory, tile));
    }

    @Unique
    public void aether$displayGUIIncubator(TileEntityIncubator tile) {
        mc.displayGuiScreen(new GuiIncubator(inventory, tile));
    }

    @Unique
    public void aether$displayGUIFreeze(TileEntityFreezer tile) {
        mc.displayGuiScreen(new GuiFreezer(inventory, tile));
    }

    @Unique
    public void aether$displayGUILoreBook(String loreId) {
        mc.displayGuiScreen(new GuiLorebook(this, loreId));
    }


    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void fallIntoOverworldFromAether(CallbackInfo ci) {
        puff = false;
        if (passenger instanceof EntityAerbunny) {
            GameSettings gameSettings = Minecraft.getMinecraft(this.getClass()).gameSettings;

            if (this.puffCooldown <= 0) {
                if (gameSettings.keyJump.isPressed()) {
                    PacketHandler.handleAerBunnyPuffJump(this, (EntityAerbunny) this.passenger);
                    this.puffCooldown = 12;
                    puff = true;
                }
            } else {
                --this.puffCooldown;
            }
        }
    }

    @Override
    public boolean shouldRender(Vec3d vec3d) {
        return invisible ? false : super.shouldRender(vec3d);
    }

    @Unique
    public void aether$setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    @Unique
    public boolean aether$getInvisible() {
        return invisible;
    }

    @Override
    public int getPuffCoolDown() {
        return this.puffCooldown;
    }

    @Override
    public boolean isPuff() {
        return puff;
    }
}
