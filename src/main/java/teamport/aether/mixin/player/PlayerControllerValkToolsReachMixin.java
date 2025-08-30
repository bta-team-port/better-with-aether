package teamport.aether.mixin.player;

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

@Mixin(value = PlayerController.class, remap = false)
public class PlayerControllerValkToolsReachMixin {

    @Final
    @Shadow
    protected Minecraft mc;

    @Unique
    public boolean held_item_is_valk_tool(Player player) {
        ItemStack held = player.getHeldItem();
        if (held == null)
            return false;
        else if (held.getItem() instanceof ItemTool && (((ItemTool) held.getItem()).getMaterial() == AetherToolMaterial.VALKYRIE)) {
            return true;
        } else return held.getItem() instanceof ItemToolSword && ((ItemToolSwordAccessor) held.getItem()).getMaterial() == AetherToolMaterial.VALKYRIE;
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
