package teamport.aether.mixins.mixin.dimension;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.item.AetherItems;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.UUID;

@Mixin(Player.class)
public abstract class ParachuteMixin extends Mob {

    @Shadow
    public UUID uuid;

    @Shadow
    @NonNull
    public Gamemode gamemode;

    protected ParachuteMixin(@NonNull World world) {
        super(world);
    }

    @WrapMethod(method = "tick")
    public void grantChute(Operation<Void> original) {
        if (this.world.dimension.id == AetherDimension.getAether().id && !EnvironmentHelper.isMultiplayerClient() && AetherDimension.canGetParachute(uuid)) {
            if (!this.gamemode.hasInvulnerablePlayer()) {
                EntityItem chute = new EntityItem(world, x, y, z, new ItemStack(AetherItems.PARACHUTE_CLOUD, 1));
                world.entityJoinedWorld(chute);
            }

            AetherDimension.setParachuteReceived(uuid);
        }

        original.call();
    }
}
