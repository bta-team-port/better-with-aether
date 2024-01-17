package bta.aether.mixin;

import bta.aether.entity.IAetherAccessories;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockPortal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayerMP.class, remap = false)
public abstract class EntityPlayerMPMixin extends EntityPlayer implements IAetherAccessories {
    @Shadow public MinecraftServer mcServer;

    @Shadow public abstract void usePersonalCraftingInventory();

    @Unique
    boolean invisible = false;

    public EntityPlayerMPMixin(World world) {
        super(world);
    }

    @Inject(method = "onUpdateEntity()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/net/PropertyManager;getBooleanProperty(Ljava/lang/String;Z)Z", shift = At.Shift.AFTER, ordinal = 1))
    private void allowOtherDimensionsInMultiplayer(CallbackInfo ci){
        int targetDim = ((BlockPortal) Block.blocksList[this.portalID]).targetDimension;
        if (!(targetDim == Dimension.nether.id || targetDim == Dimension.paradise.id)) { // Use the hardcoded dimension transfer when applicable
            if (this.craftingInventory != this.inventorySlots) {
                this.usePersonalCraftingInventory();
            }
            if (this.vehicle != null) {
                this.startRiding(this.vehicle);
            } else {
                this.timeInPortal += 0.0125f;
                if (this.timeInPortal >= 1.0f || this.getGamemode().instantPortalTravel()) {
                    this.timeInPortal = 1.0f;
                    this.timeUntilPortal = 10;
                    if (this.dimension == targetDim) {
                        this.mcServer.playerList.sendPlayerToOtherDimension((EntityPlayerMP)(Object) this, 0);
                    } else {
                        this.mcServer.playerList.sendPlayerToOtherDimension((EntityPlayerMP)(Object) this, targetDim);
                    }
                }
            }
            this.inPortal = false;
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
}
