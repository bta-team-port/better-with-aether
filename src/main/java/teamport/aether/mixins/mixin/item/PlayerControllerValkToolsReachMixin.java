package teamport.aether.mixins.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.player.gamemode.Gamemode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.item_tool.AetherToolMaterial;

import static teamport.aether.item.item_tool.AetherToolMaterial.VALKYRIE_TOOL_EXTEND_RANGE_BY;

@Environment(EnvType.CLIENT)
@Mixin(Gamemode.class)
public abstract class PlayerControllerValkToolsReachMixin {
    @ModifyReturnValue(method = "getBlockReachDistance", at = @At("RETURN"))
    private float getBlockReachDistance(float original) {
        return extendRangeForValkyrieTool(original);
    }

    @ModifyReturnValue(method = "getEntityReachDistance", at = @At("RETURN"))
    private float getEntityReachDistance(float original) {
        return extendRangeForValkyrieTool(original);
    }

    @Unique
    private static float extendRangeForValkyrieTool(float original) {
        Minecraft minecraft = Minecraft.getMinecraft();
        return original + (minecraft.thePlayer != null && AetherToolMaterial.isHoldingValkyrieTool(minecraft.thePlayer) ? VALKYRIE_TOOL_EXTEND_RANGE_BY : 0);
    }
}
