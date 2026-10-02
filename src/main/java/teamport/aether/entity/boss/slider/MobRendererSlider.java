package teamport.aether.entity.boss.slider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.*;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.Global;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import teamport.aether.AetherGlobals;
import teamport.aether.mixin.accessors.MobRendererAccessor;

@Environment(EnvType.CLIENT)
public class MobRendererSlider extends MobRenderer<MobBossSlider> {

    public MobRendererSlider(float shadowSize) {
        super(0.0f);
    }

    @Override
    public void renderPreview(
        @NonNull TessellatorGeneral tessellator,
        @NonNull MobBossSlider slider,
        double x, double y, double z, float yaw, float partialTick
    ) {
        GLRenderer.pushFrame();
        GLRenderer.modelM4f().scale(0.75F, 0.75F, 0.75F);
        this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_awake.png");
        super.renderPreview(tessellator, slider, x, y + 0.5, z, yaw, partialTick);
        GLRenderer.popFrame();
    }

    private void bindGlowTexture(@NonNull MobBossSlider slider) {
        String state = slider.isAwake() && !slider.doingSlam() ? "awake" : "sleep";
        String anger = slider.isAngry() ? "_red" : "";
        this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_" + state + anger + "_glow.png");
    }

    @Override
    protected int maxRenderLayer(@NonNull MobBossSlider slider) {
        return 3;
    }

    @Override
    protected void preRenderTransform(
        @NonNull MobBossSlider slider,
        double x, double y, double z,
        float yaw, float partialTick
    ) {
        GLRenderer.modelM4f().translate((float) x, (float) y, (float) z);
        GLRenderer.modelM4f().rotateY(-this.getBodyYaw(slider, partialTick));
        GLRenderer.modelM4f().scale(0.062539086F, 0.062539086F, -0.062539086F);
        if (slider.deathTime > 0) {
            float t = slider.deathTime + partialTick;
            float acceleration = 0.1F;
            float phase = t * 0.25F * (1.0F + acceleration * t);
            int step = (int) phase;
            float defY;
            float defZ = switch (step % 4) {
                case 0 -> {
                    defY = 1.0F;
                    yield 0.0F;
                }
                case 1 -> {
                    defY = 0.0F;
                    yield 1.0F;
                }
                case 2 -> {
                    defY = -1.0F;
                    yield 0.0F;
                }
                default -> {
                    defY = 0.0F;
                    yield -1.0F;
                }
            };
            float angle = (float) Math.toRadians(-3F);
            GLRenderer.modelM4f().rotate(angle, defY, 0.0F, defZ);
        }
        if (slider.getDeformX() > 0.01F) {
            GLRenderer.modelM4f().rotate((float) Math.toRadians(slider.getDeformX() * -30.0F), slider.getDeformY(), 0.0F, slider.getDeformZ());
        }
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(
        @NonNull MobBossSlider slider, float brightness,
        float partialTick, int layer
    ) {
        StaticEntityModel model = this.getModel("main");
        model.resetBones();
        if (layer == 1) {
            this.bindGlowTexture(slider);
            GLRenderer.setLightmapCoord2i(15, 15);
            GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
            GLRenderer.enableState(State.BLEND);
        }
        if (layer == 2) {
            GLRenderer.disableState(State.BLEND);
            return null;
        }
        if (layer == 3) {
            float progress = 1.0F - slider.getHealth() / (float) slider.getMaxHealth();
            if (progress > 0.5 && slider.deathTime == 0) {
                float textureProgress = (progress - 0.5F) / 0.5F;
                int index = (int) Math.floor(Math.min(9, MathHelper.lerp(2, 5, textureProgress)));
                // probably better with a custom texture here for the damage states
                this.renderDispatcher.textureManager.loadTexture(String.format("/assets/minecraft/textures/block/breaking/%d.png", index)).bind();
                GLRenderer.textureM4f().translate(0.25F, 0.25F, 0.0F);
                GLRenderer.textureM4f().scale(4f, 2f, 4f);
            }
            if(slider.deathTime > 0) {
                int index = 7;
                if(slider.deathTime > 7) index++;
                if(slider.deathTime > 14) index++;
                this.renderDispatcher.textureManager.loadTexture(String.format("/assets/minecraft/textures/block/breaking/%d.png", index)).bind();
                GLRenderer.textureM4f().scale(4, 2, 4);
            }
        }
        return model;
    }

    // this will change in 8.0.2 so not mucht needed to change her for now.
    private void renderHurt(@NotNull MobBossSlider entity, boolean hasOverlayAlpha, int maxRenderLayer, int argb) {
        if ((hasOverlayAlpha || entity.hurtTime > 0) && entity.deathTime == 0) {
            double maxHealth = entity.getMaxHealth();
            double currentHealth = entity.getHealth();
            int index = (int) Math.floor(Math.min(9, MathHelper.lerp(0, 6, 1.0D - currentHealth / maxHealth)));
            String breakingTexture = String.format("/assets/minecraft/textures/block/breaking/%d.png", index);
            renderOverLayBreakTexture(entity, breakingTexture, hasOverlayAlpha, maxRenderLayer, argb);
        }
    }

    private void renderOverLayBreakTexture(@NotNull MobBossSlider entity, String name, boolean hasOverlayAlpha, int maxRenderLayer, int argb) {
        this.renderDispatcher.textureManager.loadTexture(name).bind();
        GLRenderer.textureM4f().scale(4, 2, 4);
        GLRenderer.pushFrame();
        GLRenderer.setLightmapCoord2i(15, 15);
        GLRenderer.setShader(Shaders.COLOR_WORLD);
        GLRenderer.enableState(State.BLEND);
        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
        GLRenderer.setDepthFunc(CompareFunc.EQUAL);
        if (entity.hurtTime > 0 && entity.deathTime == 0) {
            for (int layer = 0; layer <= maxRenderLayer; ++layer) {
                StaticEntityModel model = ((MobRendererAccessor) this).getSetupModels().get(layer);
                if (model != null) {
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
    public void render(@NotNull TessellatorGeneral tessellator, @NotNull MobBossSlider entity, double x, double y, double z, float yaw, float partialTick) {
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
        this.setTranslucent(translucent);
        this.renderLayers(entity, partialTick, maxRenderLayer, translucent, renderAlpha);
        this.setTranslucent(!translucent);
        this.renderAdditional(tessellator, entity, partialTick);
        int argb = this.getOverlayColor(entity, partialTick);
        boolean hasOverlayAlpha = Color.alphaFromInt(argb) > 0;
        this.renderHurt(entity, hasOverlayAlpha, maxRenderLayer, argb);
        GLRenderer.popFrame();
        GLRenderer.enableState(State.CULL_FACE);
        GLRenderer.disableState(State.BLEND);
        this.renderSpecials(tessellator, entity, x, y, z);
    }

    private void renderLayers(@NotNull MobBossSlider entity, float partialTick, int maxRenderLayer, boolean translucent, float renderAlpha) {
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
    }

    private void setTranslucent(boolean translucent) {
        if (translucent) {
            GLRenderer.setDepthMask(false);
        } else {
            GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GLRenderer.setDepthMask(true);
        }
    }
}
