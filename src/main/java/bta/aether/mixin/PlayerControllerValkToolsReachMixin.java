package bta.aether.mixin;

import bta.aether.item.AetherToolMaterial;
import bta.aether.mixin.accessors.ItemToolSwordAccessor;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.item.tool.ItemToolSword;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = PlayerController.class, remap = false)
public class PlayerControllerValkToolsReachMixin {

    @Final
    @Shadow
    protected Minecraft mc;

    @Unique
    public boolean held_item_is_valk_tool(EntityPlayer player) {
        ItemStack held = player.getHeldItem();
        if (held == null)
            return false;
        else if (held.getItem() instanceof ItemTool && (((ItemTool) held.getItem()).getMaterial() == AetherToolMaterial.TOOL_VALKYRIE)) {
            return true;
        } else if (held.getItem() instanceof ItemToolSword && ((ItemToolSwordAccessor) held.getItem()).getMaterial() == AetherToolMaterial.TOOL_VALKYRIE) {
            return true;
        }
        return false;
    }

    @ModifyReturnValue(method = "getBlockReachDistance", at=@At("RETURN"))
    public float getBlockReachDistance(float original) {
        return original + (held_item_is_valk_tool(this.mc.thePlayer) ? 6 : 0);
    }

    @ModifyReturnValue(method = "getEntityReachDistance", at=@At("RETURN"))
    public float getEntityReachDistance(float original) {
        return original + (held_item_is_valk_tool(this.mc.thePlayer) ? 6 : 0);
    }
}
