package teamport.aether.mixin.item;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import teamport.aether.items.itemtool.AetherToolMaterial;

import static teamport.aether.items.itemtool.AetherToolMaterial.VALKYRIE_TOOL_EXTEND_RANGE_BY;

@Environment(EnvType.SERVER)
@Mixin(value = PacketHandlerServer.class, remap = false)
public class PacketHandlerServerValkToolsReachMixin {

    @Shadow private PlayerServer playerEntity;

    @ModifyVariable(method = "handleBlockDig", at = @At("STORE"), name = "playerDist")
    private double fixReachDistanceForBlocks(double original) {

        if (AetherToolMaterial.isHoldingValkyrieTool(playerEntity)) {
            double playerReach = (playerEntity.gamemode.getBlockReachDistance() + VALKYRIE_TOOL_EXTEND_RANGE_BY);
            double actualMaxDist = Math.ceil(Math.pow(playerReach + 0.6F, 2));
            if (original < actualMaxDist) return 0;
        }

        return original;
    }

    @Redirect(method = "handleUseEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/entity/player/PlayerServer;distanceToSqr(Lnet/minecraft/core/entity/Entity;)D") )
    private double fixReachDistanceForEntities(PlayerServer player, Entity entity) {
        double dist = player.distanceToSqr(entity);

        if (AetherToolMaterial.isHoldingValkyrieTool(player)) {
            double playerReach = (playerEntity.gamemode.getEntityReachDistance() + VALKYRIE_TOOL_EXTEND_RANGE_BY);
            double actualMaxDist = Math.ceil(Math.pow(playerReach + 0.6F, 2));
            if (dist < actualMaxDist) return 0;
        }

        return dist;
    }
}
