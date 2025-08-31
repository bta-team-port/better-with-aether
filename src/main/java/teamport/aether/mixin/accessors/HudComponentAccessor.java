package teamport.aether.mixin.accessors;

import net.minecraft.client.gui.hud.component.HudComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = HudComponent.class, remap = false)
public interface HudComponentAccessor {
	@Accessor
	void setYSize(int ySize);
}
