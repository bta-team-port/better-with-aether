package teamport.aether.entity.renderer;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.*;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import teamport.aether.AetherGlobals;
import teamport.aether.mixin.accessors.MobRendererAccessor;

public abstract class MobBreakableRender<T extends Mob> extends MobRenderer<T> {
    protected int scaleX;
    protected int scaleY;
    protected int scaleZ;

    public MobBreakableRender(float shadowSize, int scaleX, int scaleY, int scaleZ) {
        super(shadowSize);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    protected void renderHurt(@NotNull T entity, boolean hasOverlayAlpha, int maxRenderLayer, int argb) {
        if (hasOverlayAlpha || entity.hurtTime > 0 || entity.deathTime > 0) {
            double currentHealth = entity.getHealth();
            int maxHealth = Math.max(entity.getHealth(), entity.getMaxHealth());
            int index = (int) Math.floor(Math.min(9, MathHelper.lerp(0, 6, 1.0D - currentHealth / maxHealth)));
            String breakingTexture = String.format("/assets/minecraft/textures/block/breaking/%d.png", index);
            this.renderOverLayBreakTexture(entity, breakingTexture, hasOverlayAlpha, maxRenderLayer, argb);
        }
    }

    protected void renderOverLayBreakTexture(@NotNull T entity, String name, boolean hasOverlayAlpha, int maxRenderLayer, int argb) {
        this.renderDispatcher.textureManager.loadTexture(name).bind();
        GLRenderer.textureM4f().scale(scaleX, scaleY, scaleZ);
        GLRenderer.pushFrame();
        GLRenderer.setLightmapCoord2i(8, 8);
        GLRenderer.setShader(Shaders.COLOR_WORLD);
        GLRenderer.enableState(State.BLEND);
        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
        GLRenderer.setDepthFunc(CompareFunc.EQUAL);
        if (entity.hurtTime > 0 || entity.deathTime > 0) {
            for (int layer = 0; layer <= maxRenderLayer; ++layer) {
                StaticEntityModel model = ((MobRendererAccessor) this).getSetupModels().get(layer);
                if (model != null) {
                    GLRenderer.setColor4f(1f, 1f, 1f, 1.0f);
                    model.render();
                }
            }
        }
        if (hasOverlayAlpha) {
            for (int layer = 0; layer <= maxRenderLayer; ++layer) {
                StaticEntityModel model = ((MobRendererAccessor) this).getSetupModels().get(layer);
                if (model != null) {
                    GLRenderer.setColor1i(argb);
                    model.render();
                }
            }
        }
        GLRenderer.setDepthFunc(CompareFunc.LESS_EQUAL);
        GLRenderer.disableState(State.BLEND);
        GLRenderer.popFrame();
    }

    // we dont care about any of this as long it work
    @Override
    public void render(@NotNull TessellatorGeneral tessellator, @NotNull T entity, double x, double y, double z, float yaw, float partialTick) {
        this.loadEntityTexture(entity);
        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
        GLRenderer.enableState(State.BLEND);
        GLRenderer.disableState(State.CULL_FACE);
        GLRenderer.pushFrame();
        this.preRenderTransform(entity, x, y, z, yaw, partialTick);
        ((MobRendererAccessor) this).getSetupModels().clear();
        int maxRenderLayer = this.maxRenderLayer(entity);
        float renderAlpha = this.getRenderAlpha(entity, partialTick);
        boolean translucent = renderAlpha < 1.0F;
        this.renderLayers(entity, partialTick, maxRenderLayer, translucent, renderAlpha);
        this.renderAdditional(tessellator, entity, partialTick);
        int argb = this.getOverlayColor(entity, partialTick);
        boolean hasOverlayAlpha = Color.alphaFromInt(argb) > 0;
        this.renderHurt(entity, hasOverlayAlpha, maxRenderLayer, argb);
        GLRenderer.popFrame();
        GLRenderer.enableState(State.CULL_FACE);
        GLRenderer.disableState(State.BLEND);
        this.renderSpecials(tessellator, entity, x, y, z);
    }


    protected void renderLayers(@NotNull T entity, float partialTick, int maxRenderLayer, boolean translucent, float renderAlpha) {
        if (translucent) {
            GLRenderer.setDepthMask(false);
        }
        for (int layer = 0; layer <= maxRenderLayer; ++layer) {
            try {
                GLRenderer.pushFrame();
                StaticEntityModel model = this.getAndSetupModelForLayer(entity, 1.0F, partialTick, layer);
                ((MobRendererAccessor) this).getSetupModels().add(model);
                if (model != null) {
                    if (translucent) {
                        GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, renderAlpha);
                    }
                    model.render();
                }
                GLRenderer.popFrame();
            } catch (Exception e) {
                AetherGlobals.LOGGER.error("Error setting up model on layer '{}' in renderer '{}'", layer, this.getClass().getSimpleName(), e);
            }
        }
        if (translucent) {
            GLRenderer.setDepthMask(false);
        }
    }

}
