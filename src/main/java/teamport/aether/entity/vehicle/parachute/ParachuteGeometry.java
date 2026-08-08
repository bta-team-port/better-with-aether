package teamport.aether.entity.vehicle.parachute;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
final class ParachuteGeometry {
    private static final float MIN_X = -0.5F;
    private static final float MAX_X = 0.5F;
    private static final float MIN_Y = -1.25F;
    private static final float MAX_Y = -0.25F;
    private static final float MIN_Z = -0.5F;
    private static final float MAX_Z = 0.5F;

    private ParachuteGeometry() {
    }

    static void render(@NonNull TessellatorGeneral tessellator) {
        tessellator.startDrawingQuads();

        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        face(tessellator, 0.5F, 0.5F, 0.75F, 1.0F,
            MAX_X, MIN_Y, MIN_Z, MIN_X, MIN_Y, MIN_Z, MIN_X, MAX_Y, MIN_Z, MAX_X, MAX_Y, MIN_Z);

        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        face(tessellator, 0.25F, 0.5F, 0.5F, 1.0F,
            MIN_X, MIN_Y, MAX_Z, MAX_X, MIN_Y, MAX_Z, MAX_X, MAX_Y, MAX_Z, MIN_X, MAX_Y, MAX_Z);

        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        face(tessellator, 0.5F, 0.0F, 0.75F, 0.5F,
            MAX_X, MIN_Y, MAX_Z, MIN_X, MIN_Y, MAX_Z, MIN_X, MIN_Y, MIN_Z, MAX_X, MIN_Y, MIN_Z);

        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        face(tessellator, 0.25F, 0.0F, 0.5F, 0.5F,
            MIN_X, MAX_Y, MAX_Z, MAX_X, MAX_Y, MAX_Z, MAX_X, MAX_Y, MIN_Z, MIN_X, MAX_Y, MIN_Z);

        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        face(tessellator, 0.0F, 0.5F, 0.25F, 1.0F,
            MIN_X, MIN_Y, MAX_Z, MIN_X, MIN_Y, MIN_Z, MIN_X, MAX_Y, MIN_Z, MIN_X, MAX_Y, MAX_Z);

        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        face(tessellator, 0.75F, 0.5F, 1.0F, 1.0F,
            MAX_X, MIN_Y, MIN_Z, MAX_X, MIN_Y, MAX_Z, MAX_X, MAX_Y, MAX_Z, MAX_X, MAX_Y, MIN_Z);

        tessellator.draw();
    }

    private static void face(@NonNull TessellatorGeneral tessellator, float minU, float minV, float maxU, float maxV,
                             float x1, float y1, float z1, float x2, float y2, float z2,
                             float x3, float y3, float z3, float x4, float y4, float z4) {
        tessellator.addVertexWithUV(x1, y1, z1, maxU, minV);
        tessellator.addVertexWithUV(x2, y2, z2, minU, minV);
        tessellator.addVertexWithUV(x3, y3, z3, minU, maxV);
        tessellator.addVertexWithUV(x4, y4, z4, maxU, maxV);
    }
}
