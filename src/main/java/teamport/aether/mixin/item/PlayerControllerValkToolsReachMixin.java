package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.item.tool.ItemToolSword;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.itemtool.AetherToolMaterial;
import teamport.aether.mixin.accessors.ItemToolSwordAccessor;

import static teamport.aether.items.itemtool.AetherToolMaterial.VALKYRIE_TOOL_EXTEND_RANGE_BY;

@Mixin(value = PlayerController.class, remap = false)
public class PlayerControllerValkToolsReachMixin {

    @Final
    @Shadow
    protected Minecraft mc;

    @ModifyReturnValue(method = "getBlockReachDistance", at = @At("RETURN"))
    public float getBlockReachDistance(float original) {
        return original + (AetherToolMaterial.isHoldingValkyrieTool(this.mc.thePlayer) ? VALKYRIE_TOOL_EXTEND_RANGE_BY : 0);
    }

    @ModifyReturnValue(method = "getEntityReachDistance", at = @At("RETURN"))
    public float getEntityReachDistance(float original) {
        return original + (AetherToolMaterial.isHoldingValkyrieTool(this.mc.thePlayer) ? VALKYRIE_TOOL_EXTEND_RANGE_BY : 0);
    }
}
