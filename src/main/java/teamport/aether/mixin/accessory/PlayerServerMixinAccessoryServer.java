package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.world.ServerPlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(value = PlayerServer.class, remap = false)
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
}
