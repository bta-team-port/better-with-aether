package bta.aether.mixin.entity.entityplayer;

import bta.aether.api.IAetherPuff;
import bta.aether.entity.*;
import bta.aether.gui.*;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import bta.aether.tile.TileEntityIncubator;
import bta.aether.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixin extends EntityPlayer implements IPlayerBossList, IAetherGuis, IAetherAccessories, IAetherPuff {

    @Shadow
    protected Minecraft mc;

    @Unique
    int jumpAmount = 0;
    @Unique
    int jumpMaxAmount = 0;
    @Unique
    private boolean invisible;
    @Unique
    int puffCooldown = 0;
    @Unique
    boolean puff;

    public EntityPlayerSPMixin(World world) {
        super(world);
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
