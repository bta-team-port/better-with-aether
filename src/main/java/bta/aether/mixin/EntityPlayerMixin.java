package bta.aether.mixin;

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
        teleportDelay--;
        if (teleportDelay < 0 && Dimension.getDimensionList().get(dimension) == AetherDimension.dimensionAether){
            if (this.y < world.worldType.getMinY() - 10){
                teleportDelay = 20;
                AetherDimension.dimensionShift((EntityPlayer) (Object)this, Dimension.overworld.id);
            }
        }
    }
}
