package teamport.aether.models;

import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.item.Item;
import org.jspecify.annotations.NonNull;

public class ItemModelTransparent extends ItemModelStandard {

    public ItemModelTransparent(@NonNull Item item) {
        super(item);
    }

    @Override
    protected void renderCoordinate(@NonNull TessellatorGeneral tessellator, @NonNull IconCoordinate coordinate, byte lightIndex, int color, boolean items3d, boolean leftHanded) {
        double cUMin = leftHanded ? coordinate.getIconUMax() : coordinate.getIconUMin();
        double cUMax = leftHanded ? coordinate.getIconUMin() : coordinate.getIconUMax();
        double cVMin = coordinate.getIconVMin();
        double cVMax = coordinate.getIconVMax();
        coordinate.parentAtlas.bind();

        if (items3d) {
            float halfThickness = 0.03125F;
            int pxH = coordinate.height;
            int pxW = coordinate.width;

            tessellator.startDrawingQuads();
            tessellator.setLightmapCoord1i(lightIndex);
            tessellator.setColor1i(color);

            if (!leftHanded) {
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                tessellator.addVertexWithUV(-0.5, 0.5, halfThickness, cUMax, cVMin);
                tessellator.addVertexWithUV(-0.5, -0.5, halfThickness, cUMax, cVMax);
                tessellator.addVertexWithUV(0.5, -0.5, halfThickness, cUMin, cVMax);
                tessellator.addVertexWithUV(0.5, 0.5, halfThickness, cUMin, cVMin);

                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                tessellator.addVertexWithUV(-0.5, 0.5, -halfThickness, cUMin, cVMin);
                tessellator.addVertexWithUV(0.5, 0.5, -halfThickness, cUMax, cVMin);
                tessellator.addVertexWithUV(0.5, -0.5, -halfThickness, cUMax, cVMax);
                tessellator.addVertexWithUV(-0.5, -0.5, -halfThickness, cUMin, cVMax);
            } else {
                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                tessellator.addVertexWithUV(-0.5, 0.5, -halfThickness, cUMin, cVMin);
                tessellator.addVertexWithUV(0.5, 0.5, -halfThickness, cUMax, cVMin);
                tessellator.addVertexWithUV(0.5, -0.5, -halfThickness, cUMax, cVMax);
                tessellator.addVertexWithUV(-0.5, -0.5, -halfThickness, cUMin, cVMax);

                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                tessellator.addVertexWithUV(-0.5, 0.5, halfThickness, cUMax, cVMin);
                tessellator.addVertexWithUV(-0.5, -0.5, halfThickness, cUMax, cVMax);
                tessellator.addVertexWithUV(0.5, -0.5, halfThickness, cUMin, cVMax);
                tessellator.addVertexWithUV(0.5, 0.5, halfThickness, cUMin, cVMin);
            }

            for (int h = 0; h < pxH; ++h) {
                double y1 = (double) h / pxH;
                double y2 = (double) (h + 1) / pxH;
                double vMin = coordinate.getSubIconV(y1);
                double vMax = coordinate.getSubIconV(y2);
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                tessellator.addVertexWithUV(-0.5, 0.5 - y1, -halfThickness, cUMin, vMin);
                tessellator.addVertexWithUV(-0.5, 0.5 - y1, halfThickness, cUMin, vMax);
                tessellator.addVertexWithUV(0.5, 0.5 - y1, halfThickness, cUMax, vMax);
                tessellator.addVertexWithUV(0.5, 0.5 - y1, -halfThickness, cUMax, vMin);
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                tessellator.addVertexWithUV(-0.5, 0.5 - y2, -halfThickness, cUMin, vMin);
                tessellator.addVertexWithUV(0.5, 0.5 - y2, -halfThickness, cUMax, vMin);
                tessellator.addVertexWithUV(0.5, 0.5 - y2, halfThickness, cUMax, vMax);
                tessellator.addVertexWithUV(-0.5, 0.5 - y2, halfThickness, cUMin, vMax);
            }

            for (int w = 0; w < pxW; ++w) {
                double x1 = (double) w / pxW;
                double x2 = (double) (w + 1) / pxW;
                double uMin = coordinate.getSubIconU(leftHanded ? 1.0 - x1 : x1);
                double uMax = coordinate.getSubIconU(leftHanded ? 1.0 - x2 : x2);
                tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                tessellator.addVertexWithUV(x1 - 0.5, 0.5, -halfThickness, uMin, cVMin);
                tessellator.addVertexWithUV(x1 - 0.5, -0.5, -halfThickness, uMax, cVMax);
                tessellator.addVertexWithUV(x1 - 0.5, -0.5, halfThickness, uMax, cVMax);
                tessellator.addVertexWithUV(x1 - 0.5, 0.5, halfThickness, uMin, cVMin);
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                tessellator.addVertexWithUV(x2 - 0.5, 0.5, -halfThickness, uMin, cVMin);
                tessellator.addVertexWithUV(x2 - 0.5, 0.5, halfThickness, uMin, cVMin);
                tessellator.addVertexWithUV(x2 - 0.5, -0.5, halfThickness, uMax, cVMax);
                tessellator.addVertexWithUV(x2 - 0.5, -0.5, -halfThickness, uMax, cVMax);
            }

            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            tessellator.addVertexWithUV(-0.5F, 0.5F, halfThickness, cUMin, cVMin);
            tessellator.addVertexWithUV(-0.5F, -0.5F, halfThickness, cUMin, cVMax);
            tessellator.addVertexWithUV(0.5F, -0.5F, halfThickness, cUMax, cVMax);
            tessellator.addVertexWithUV(0.5F, 0.5F, halfThickness, cUMax, cVMin);
            tessellator.draw();
        } else {
            tessellator.startDrawingQuads();
            tessellator.setLightmapCoord1i(lightIndex);
            tessellator.setColor1i(color);
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            tessellator.addVertexWithUV(-0.5F, 0.5F, 0.0F, cUMin, cVMin);
            tessellator.addVertexWithUV(-0.5F, -0.5F, 0.0F, cUMin, cVMax);
            tessellator.addVertexWithUV(0.5F, -0.5F, 0.0F, cUMax, cVMax);
            tessellator.addVertexWithUV(0.5F, 0.5F, 0.0F, cUMax, cVMin);
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            tessellator.addVertexWithUV(-0.5F, 0.5F, 0.0F, cUMin, cVMin);
            tessellator.addVertexWithUV(0.5F, 0.5F, 0.0F, cUMax, cVMin);
            tessellator.addVertexWithUV(0.5F, -0.5F, 0.0F, cUMax, cVMax);
            tessellator.addVertexWithUV(-0.5F, -0.5F, 0.0F, cUMin, cVMax);
            tessellator.draw();
        }
    }

}
