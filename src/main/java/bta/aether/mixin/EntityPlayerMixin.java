package bta.aether.mixin;

import bta.aether.entity.EntityAerbunny;
import bta.aether.entity.IPlayerJumpAmount;
import bta.aether.gui.IAetherGuis;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import bta.aether.tile.TileEntityIncubator;
import bta.aether.world.AetherDimension;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends EntityLiving implements IAetherGuis, IPlayerJumpAmount {
    @Shadow public int dimension;
    @Unique
    private int jumpMaxAmount;
    @Unique
    private int jumpAmount;

    public EntityPlayerMixin(World world) {
        super(world);
    }

    @Override
    public void aether$displayGUIEnchanter(TileEntityEnchanter tile) {
    }

    @Override
    public void aether$displayGUIIncubator(TileEntityIncubator tile) {
    }

    @Override
    public void aether$displayGUIFreeze(TileEntityFreezer tile) {
    }

    @Override
    public void aether$displayGUILoreBook(String loreId) {
    }

    @Unique
    public int aether$getJumpMaxAmount() {
        return this.jumpMaxAmount;
    }

    @Unique
    public int aether$getJumpAmount() {
        return this.jumpAmount;
    }

    @Unique
    public void aether$setJumpMaxAmount(int jumpMaxAmount) {
        this.jumpMaxAmount = jumpMaxAmount;
        if (this.jumpMaxAmount > this.jumpAmount) this.jumpAmount = this.jumpMaxAmount;
    }

    @Unique
    public void aether$setJumpAmount(int jumpAmount) {
        this.jumpAmount = jumpAmount;
    }

    @Unique
    public int teleportDelay = 0;
    @Inject(method = "tick()V", at = @At("HEAD"))
    private void fallIntoOverworldFromAether(CallbackInfo ci){

        if (passenger instanceof EntityAerbunny) {
            this.fallDistance = 0.0F;
            if (!this.onGround && !this.isInWater() && this.yd < 0.0 && !this.collision) {
                this.yd *= 0.6;
            }
        }

        teleportDelay--;
        if (teleportDelay < 0 && Dimension.getDimensionList().get(dimension) == AetherDimension.dimensionAether){
            if (this.y < world.worldType.getMinY() - 10){
                teleportDelay = 20;
                AetherDimension.dimensionShift((EntityPlayer) (Object)this, Dimension.overworld.id);
            }
        }
    }

    // A fix for the Aerbunny holding. -Cookie
    @Override
    public double getRideHeight() {
        return bbHeight * 0.15F;
    }

    // Sets the jump height to two and a half blocks if the Aerbunny is a passenger. -Cookie
    @Inject(method = "jump", at = @At("TAIL"))
    private void aether_aerbunnyJump(CallbackInfo ci) {
        if (passenger != null && passenger instanceof EntityAerbunny) {
            yd = 0.64;
        }
    }

    // Sets the movement speed if an Aerbunny is held. -Cookie
    @Inject(method = "moveEntityWithHeading", at = @At("TAIL"))
    private void aether_aerbunnyMoveSpeed(float moveStrafing, float moveForward, CallbackInfo ci) {
        if (passenger != null && passenger instanceof EntityAerbunny) {
            if (y < -0.225) {
                y += 0.5F;
            }
        }
    }
}
