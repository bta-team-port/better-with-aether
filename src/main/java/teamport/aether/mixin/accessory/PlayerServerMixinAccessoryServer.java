package teamport.aether.mixin.accessory;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.world.ServerPlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(value = PlayerServer.class, remap = false)
public abstract class PlayerServerMixinAccessoryServer {
    @Shadow
    private ItemStack[] playerInventory;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void increaseInventorySizeK(
            MinecraftServer minecraftserver,
            World world,
            String username,
            UUID uuid,
            ServerPlayerController serverPlayerController,
            CallbackInfo ci
    ) {
        this.playerInventory = new ItemStack[9];
    }

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 5), require = 1)
    public int modifyContainerSize(int constant){
        return constant + 4;
    }
}
