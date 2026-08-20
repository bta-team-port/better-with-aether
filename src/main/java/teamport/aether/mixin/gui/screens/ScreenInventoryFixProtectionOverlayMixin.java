package teamport.aether.mixin.gui.screens;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.mixin.accessors.ScreenContainerAbstractAccessor;

@Environment(EnvType.CLIENT)
@Mixin(ScreenInventory.class)
public abstract class ScreenInventoryFixProtectionOverlayMixin extends ScreenContainerAbstract {
    protected ScreenInventoryFixProtectionOverlayMixin(@NonNull Player player) {
        super(player.containerMenu);
    }

    @ModifyExpressionValue(method = "drawProtectionOverlay", at = @At(value = "CONSTANT", args = "intValue=44", ordinal = 1))
    private int adjustMaxHeight(int original) {
        int visibleCount = 0;
        for (DamageType dt : DamageType.values()) {
            if (dt.shouldDisplay()) {
                ++visibleCount;
            }
        }
        return Math.max(visibleCount * 10 + 4, original);
    }

    @WrapOperation(method = "drawProtectionOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/container/ScreenInventory;drawGradientRect(IIIIII)V"))
    private void fixBoxOverlappingInCreative(ScreenInventory instance, int minX, int minY, int maxX, int maxY, int argb1, int argb2, Operation<Void> original) {
        if (this.mc.thePlayer.gamemode.hasInstantPortalTravel() && this.mc.thePlayer.gamemode.canInteract()) {
            original.call(instance, minX - 5, minY, maxX - 5, maxY, argb1, argb2);
            return;
        }
        original.call(instance, minX, minY, maxX, maxY, argb1, argb2);
    }

    @WrapOperation(method = "drawProtectionOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/PlayerLocal;getTotalProtectionAmount(Lnet/minecraft/core/util/helper/DamageType;)F", ordinal = 0))
    private float boundsMinProtectionValue(PlayerLocal instance, DamageType armor, @NonNull Operation<Float> original) {
        float originalFloat = original.call(instance, armor);
        return Math.max(originalFloat, -1.0F);
    }

    @Expression("255 - ? << 16 | ? << 8 | -16777216")
    @ModifyExpressionValue(method = "drawProtectionOverlay", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int colorChangeForNegatives(int original, @Local(name = "protection") float protection, @Local(name = "l") int l, @Share("barWidth") @NonNull LocalIntRef barWidth, @Local(name = "w2") int w2) {
        barWidth.set(Math.max(0, (int) (Math.abs(protection) * w2)));
        if (protection >= 0.0F) return original;
        return 0xff_ff_00_ff + (l << 16);
    }

    @WrapOperation(method = "drawProtectionOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/container/ScreenInventory;drawGuiIcon(IIIILnet/minecraft/client/render/texture/stitcher/IconCoordinate;)V"))
    private void moveIconForCreativeFix(ScreenInventory instance, int x, int y, int width, int height, IconCoordinate coordinate, Operation<Void> original) {
        if (this.mc.thePlayer.gamemode.hasInstantPortalTravel() && this.mc.thePlayer.gamemode.canInteract()) {
            original.call(instance, x - 5, y, width, height, coordinate);
            return;
        }
        original.call(instance, x, y, width, height, coordinate);
    }

    @WrapOperation(method = "drawProtectionOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/container/ScreenInventory;drawRectWidthHeight(IIIII)V", ordinal = 0))
    private void moveProtectionBarForCreativeFix(ScreenInventory instance, int x, int y, int width, int height, int argb, Operation<Void> original) {
        if (this.mc.thePlayer.gamemode.hasInstantPortalTravel() && this.mc.thePlayer.gamemode.canInteract()) {
            original.call(instance, x - 5, y, width, height, argb);
            return;
        }
        original.call(instance, x, y, width, height, argb);
    }

    @WrapOperation(method = "drawProtectionOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/container/ScreenInventory;drawRectWidthHeight(IIIII)V", ordinal = 1))
    private void moveFilledProtectionBarForCreativeFix(ScreenInventory instance, int x, int y, int width, int height, int argb, Operation<Void> original, @Share("barWidth") LocalIntRef barWidth) {
        if (this.mc.thePlayer.gamemode.hasInstantPortalTravel() && this.mc.thePlayer.gamemode.canInteract()) {
            original.call(instance, x - 5, y, barWidth.get(), height, argb);
            return;
        }
        original.call(instance, x, y, barWidth.get(), height, argb);
    }

    @Definition(id = "hoveredDamageType", local = @Local(ordinal = 0, type = DamageType.class))
    @Expression("hoveredDamageType != null")
    @ModifyExpressionValue(method = "drawProtectionOverlay", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean boundNegativePercentValues(boolean original) {
        return original && ((ScreenContainerAbstractAccessor) this).getTooltipElement() != null;
    }

    @Expression("? < 0")
    @ModifyExpressionValue(method = "drawProtectionOverlay", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean boundNegativePercentValues(boolean original, @Local(name = "protection") @NonNull LocalIntRef protection) {
        if (protection.get() < -100) protection.set(-100);
        return false;
    }

    @WrapOperation(method = "drawProtectionOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/helper/DamageType;shouldDisplay()Z"))
    private boolean modifyIndex(DamageType instance, @NonNull Operation<Boolean> original, @Local(name = "i") LocalIntRef i) {
        boolean shouldDisplay = original.call(instance);
        if (!shouldDisplay) i.set(i.get() - 1);
        return shouldDisplay;
    }
}
