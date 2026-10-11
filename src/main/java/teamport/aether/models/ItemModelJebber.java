package teamport.aether.models;

import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.LightIndexHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Matrix4f;
import org.useless.dragonfly.DisplayPos;
import teamport.aether.helper.ColorHelper;
import teamport.aether.particle.ParticleChaotic;

public class ItemModelJebber extends ItemModelStandard {
	private IconCoordinate overlay;

	public ItemModelJebber(Item item) {
		super(item);
	}

	public ItemModelStandard setOverlay(String overlay) {
		this.overlay = TextureRegistry.getTexture(overlay);
		return this;
	}

	private IconCoordinate getOverlayIcon(Entity entity, ItemStack itemStack) {
		return this.overlay;
	}

    public void render(@NotNull TessellatorGeneral tessellator, @Nullable Entity holder, @NotNull ItemStack itemStack, @NotNull String displayPosId, boolean items3d, int clusterSize, byte lightIndex, float partialTick, boolean leftHanded) {
        random.setSeed(187L);
        DisplayPos displayPos = this.getDisplayPos(displayPosId);
        Matrix4f model = GLRenderer.modelM4f();
        model.translate(leftHanded ? -displayPos.tx : displayPos.tx, displayPos.ty, displayPos.tz);
        model.rotateX(org.joml.Math.toRadians(displayPos.rx));
        model.rotateY(org.joml.Math.toRadians(leftHanded ? -displayPos.ry : displayPos.ry));
        model.rotateZ(Math.toRadians(leftHanded ? -displayPos.rz : displayPos.rz));
        model.scale(displayPos.sx, displayPos.sy, displayPos.sz);
        ColorHelper.ColorF colorF = ColorHelper.colorLerp(ColorHelper.getPartileColor(itemStack));
        if (items3d) {
            for(int i = 0; i < clusterSize; ++i) {
                float offX = 0.0F;
                float offY = 0.0F;
                float offZ = 0.1F * (float)i;
                model.translate(0.0F, 0.0F, offZ);
                this.renderSingle(tessellator, holder, itemStack, true, lightIndex, this.getColor(itemStack), partialTick, false);
                this.renderOverlay(tessellator, holder, itemStack, true, LightIndexHelper.lightIndex2i(15, 15), Color.floatToIntARGB(1.0F, colorF.r(), colorF.g(), colorF.b()), partialTick, false);
                model.translate(-0.0F, -0.0F, -offZ);
            }
        } else {
            for(int i = 0; i < clusterSize; ++i) {
                float rOffX = 0.0F;
                float rOffY = 0.0F;
                float rOffZ = 0.0F;
                if (i > 0) {
                    rOffX = (random.nextFloat() * 2.0F - 1.0F) * 0.2F / displayPos.sx;
                    rOffY = (random.nextFloat() * 2.0F - 1.0F) * 0.2F / displayPos.sy;
                    rOffZ = (random.nextFloat() * 2.0F - 1.0F) * 0.2F / displayPos.sz;
                }

                model.translate(rOffX, rOffY, rOffZ);
                this.renderSingle(tessellator, holder, itemStack, false, lightIndex, this.getColor(itemStack), partialTick, false);
                this.renderOverlay(tessellator, holder, itemStack, true, LightIndexHelper.lightIndex2i(15, 15), Color.floatToIntARGB(1.0F, colorF.r(), colorF.g(), colorF.b()), partialTick, false);
                model.translate(-rOffX, -rOffY, -rOffZ);
            }
        }

    }

    protected void renderOverlay(
        @NotNull TessellatorGeneral tessellator,
        @Nullable Entity holder, @NotNull ItemStack itemStack,
        boolean items3d,
        byte lightIndex, int color,
        float partialTick, boolean mirrorX
    ) {
        this.renderCoordinate(tessellator, this.getOverlayIcon(holder, itemStack), lightIndex, color, items3d, mirrorX);
    }

}
