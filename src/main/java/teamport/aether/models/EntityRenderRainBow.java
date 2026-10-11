package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.projectile.ProjectileRainBow;

@Environment(EnvType.CLIENT)
public class EntityRenderRainBow extends EntityRenderer<ProjectileRainBow> {

    @Override
    public void render(@NotNull TessellatorGeneral tessellatorGeneral, @NotNull ProjectileRainBow projectileRainBow, double v, double v1, double v2, float v3, float v4) {
        /* the particles are a better indicator of where the projectile is*/
    }
}
