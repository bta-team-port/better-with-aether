package teamport.aether.helper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.DrawMode;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;

@Environment(EnvType.CLIENT)
public final class ClientRenderHelper {
    private ClientRenderHelper() {
    }

    public static void renderShieldVignette(TextureManager textureManager, int xSize, int ySize) {
        GLRenderer.pushFrame();
        try {
            GLRenderer.enableState(State.BLEND);
            GLRenderer.disableState(State.DEPTH_TEST);
            GLRenderer.setDepthMask(false);
            GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
            GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GLRenderer.setAlphaTest(0.0F);

            textureManager.loadTexture("/assets/aether/textures/other/shieldvignette.png").bind();

            TessellatorGeneral tessellator = GLRenderer.getTessellator();
            tessellator.startDrawing(DrawMode.QUADS);
            tessellator.addVertexWithUV(0.0, ySize, -90.0, 0.0, 1.0);
            tessellator.addVertexWithUV(xSize, ySize, -90.0, 1.0, 1.0);
            tessellator.addVertexWithUV(xSize, 0.0, -90.0, 1.0, 0.0);
            tessellator.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
            tessellator.draw();
        } finally {
            GLRenderer.popFrame();
        }
    }

}
