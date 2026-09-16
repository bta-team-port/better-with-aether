package teamport.aether.mixin.fix.catalyst;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Minecraft.class, priority = 1)
public abstract class FixCatalystHitPositionMixin {

    @WrapOperation(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/controller/PlayerController;useOrPlaceItemStackOnTile(Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/World;Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Side;DD)Z"))
    private boolean restoreVanillaHitPosition(PlayerController instance, Player player, World world, ItemStack itemStack, TilePosc tilePos, Side side, double xPlaced, double yPlaced, Operation<Boolean> original) {
        HitResult hitResult = Minecraft.getMinecraft().objectMouseOver;
        if (hitResult instanceof HitResult.Tile hitTile) {
            double vanillaY = hitTile.location.y() - (double) hitTile.tilePos.y();
            double vanillaX;
            if (hitTile.side.axis() == Axis.X) {
                vanillaX = hitTile.location.x() - (double) hitTile.tilePos.x();
            } else if (hitTile.side.axis() == Axis.Z) {
                vanillaX = hitTile.location.z() - (double) hitTile.tilePos.z();
            } else {
                vanillaX = hitTile.location.x() - (double) hitTile.tilePos.x();
            }

            return original.call(instance, player, world, itemStack, tilePos, side, vanillaX, vanillaY);
        }
        return original.call(instance, player, world, itemStack, tilePos, side, xPlaced, yPlaced);
    }
}
