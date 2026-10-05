package teamport.aether.mixin.accessors;

import net.minecraft.client.render.entity.MobRenderer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.useless.dragonfly.models.entity.StaticEntityModel;

import java.util.List;

@Mixin(MobRenderer.class)
public interface MobRendererAccessor {
    @Accessor
    List<@Nullable StaticEntityModel> getSetupModels();
}
