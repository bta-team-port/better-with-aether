package teamport.aether.mixin.accessors;

import net.minecraft.server.world.ServerPlayerController;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayerController.class)
public interface ServerPlayerControllerAccessor {
	@Accessor
	WorldServer getThisWorld();
}
