package teamport.aether.mixins.mixin.accessory;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.world.ServerPlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.ducks.IContainerInventoryAether;

import java.util.UUID;

@Environment(EnvType.SERVER)
@Mixin(PlayerServer.class)
public abstract class PlayerServerMixinAccessoryServer {
    @Shadow
    private ItemStack[] playerInventory;
    @Inject(method = "<init>", at = @At("TAIL"))
    private void increaseInventorySizeK(MinecraftServer minecraftserver, World world, String username, UUID uuid, ServerPlayerController serverPlayerController, CallbackInfo ci) {
        this.playerInventory = new ItemStack[9];
    }
    @ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "intValue=5"))
    private int modifyContainerSize(int original){
        return original + 4;
    }

    @Inject(method = "getEquipmentInSlot", at = @At("HEAD"), cancellable = true)
    private void getAccessoryEquipment(int slot, CallbackInfoReturnable<ItemStack> cir) {
        if (slot >= 5 && slot < 9) {
            Player player = (Player) (Object) this;
            cir.setReturnValue(((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory()[slot - 5]);
        }
    }
}
