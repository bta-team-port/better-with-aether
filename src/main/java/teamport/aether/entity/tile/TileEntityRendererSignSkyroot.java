package teamport.aether.entity.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.render.font.FontRenderer;
import net.minecraft.client.render.font.FontRendererDefault;
import net.minecraft.client.render.font.SF;
import net.minecraft.client.render.model.ModelSign;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.client.render.tileentity.TileEntityRendererSign;
import net.minecraft.client.util.helper.Colors;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSign;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.enums.EnumSignPicture;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.Arrays;

public class TileEntityRendererSignSkyroot extends TileEntityRenderer<TileEntitySignSkyroot> {
    private final @NonNull StringBuilder builder = new StringBuilder();
    private final ModelSign modelSign = new ModelSign();
    private final Minecraft mc = Minecraft.getMinecraft();

    private final TileEntityRendererSign.BufferedTextMeshRenderer textMeshRenderer = new TileEntityRendererSign.BufferedTextMeshRenderer();
    private static final FontRenderer FONT_RENDERER = new FontRendererDefault();

    private final String[] signColorTextures = new String[16];

    private String defaultSignTexture = "MissingNo";
    private Color defaultSignColor = DyeColor.PURPLE.color;

    public TileEntityRendererSignSkyroot() {
        Arrays.fill(this.signColorTextures, "MissingNo");
    }

    @SuppressWarnings("UnusedReturnValue")
    public TileEntityRendererSignSkyroot setColoredTexture(int colorID, String texturePath) {
        this.signColorTextures[colorID] = texturePath;
        return this;
    }

    public TileEntityRendererSignSkyroot setDefaultTexture(String texturePath, Color defaultColor) {
        this.defaultSignTexture = texturePath;
        this.defaultSignColor = defaultColor;
        return this;
    }

    private static float getAngleFromMeta(int meta) {
        float angle;

        switch (meta & 15) {
            case 2:
                angle = 180.0F;
                break;
            case 3:
                angle = 0.0F;
                break;
            case 4:
                angle = 90.0F;
                break;
            case 5:
            default:
                angle = -90.0F;
        }

        return angle;
    }

    public void doRender(@NonNull Tessellator t, @NonNull TileEntitySignSkyroot tileEntity, double x, double y, double z, float partialTick) {
        Block<?> block = tileEntity.getBlock();
        if (!(block != null && block.getLogic() instanceof BlockLogicSign)) return;

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

        int meta = tileEntity.getBlockMeta();
        float angle = getAngleFromMeta(meta);

        if (((BlockLogicSign) block.getLogic()).isFreeStanding) {
            angle = ((meta & 15) * 360) / 16.0F;
            GL11.glRotatef(-angle, 0.0F, 1.0F, 0.0F);
            this.modelSign.signStick.visible = true;
        } else {
            GL11.glRotatef(-angle, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
            this.modelSign.signStick.visible = false;
        }

        GL11.glPushMatrix();
        GL11.glScalef(0.6666667F, -0.6666667F, -0.6666667F);

        int colorSign;
        if (Block.hasLogicClass(block, IPainted.class)) {
            DyeColor dyeColor = ((IPainted) block.getLogic()).fromMetadata(meta);

            colorSign = dyeColor.color.getARGB();
            this.loadTexture(this.signColorTextures[dyeColor.blockMeta]);
        } else {
            colorSign = defaultSignColor.getARGB();
            this.loadTexture(defaultSignTexture);
        }

        GL11.glDisable(GL11.GL_BLEND);
        this.modelSign.render();
        GL11.glPopMatrix();

        drawSignPicture(tileEntity, colorSign);
        drawSignText(t, tileEntity);

        GL11.glPopMatrix();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    }

    protected void drawSignPicture(TileEntitySignSkyroot tileEntity, int colorSign) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glTranslatef(0.0F, 0.33545F, 0.04375F);
        GL11.glNormal3f(0.0F, 0.0F, 1.0F);
        GL11.glDepthMask(false);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        EnumSignPicture picture = tileEntity.getPicture();
        if (picture != null && picture != EnumSignPicture.NONE) {
            drawTexturedModalRect(colorSign, TextureRegistry.getTexture(picture.getTextureKey()));
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @SuppressWarnings("java:S131")
    protected void drawSignText(Tessellator t, TileEntitySignSkyroot tileEntity) {
        FontRenderer sr = FONT_RENDERER;

        float lightLevel = !LightmapHelper.isLightmapEnabled() && !this.mc.isFullbrightEnabled() ? this.mc.currentWorld.getLightBrightness(tileEntity.x, tileEntity.y, tileEntity.z) : 1.0F;
        float lightOffset = 0.0F;

        if (tileEntity.isGlowing()) {
            lightLevel = 1.0F;
            lightOffset = 96.0F;
            if (LightmapHelper.isLightmapEnabled()) {
                LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
            }
        }

        GL11.glPushMatrix();
        float scale2 = 0.011111113F;
        GL11.glTranslatef(0.0F, 0.33333334F, 0.043333333F);
        GL11.glScalef(scale2, -scale2, scale2);
        GL11.glDepthMask(false);

        int color = Colors.allSignColors[tileEntity.getColor().id].getARGB();
        int r = (int) MathHelper.clamp(Color.redFromInt(color) * lightLevel + lightOffset, 0.0F, 255.0F);
        int g = (int) MathHelper.clamp(Color.greenFromInt(color) * lightLevel + lightOffset, 0.0F, 255.0F);
        int b = (int) MathHelper.clamp(Color.blueFromInt(color) * lightLevel + lightOffset, 0.0F, 255.0F);

        color = Color.intToIntARGB(0, r, g, b);
        CharSequence line1 = tileEntity.signText[0];
        CharSequence line2 = tileEntity.signText[1];
        CharSequence line3 = tileEntity.signText[2];
        CharSequence line4 = tileEntity.signText[3];

        switch (tileEntity.lineBeingEdited) {
            case 0:
                this.builder.setLength(0);
                line1 = this.builder.append("§+§f> §-").append(line1).append("§+§f <§-");
                break;
            case 1:
                this.builder.setLength(0);
                line2 = this.builder.append("§+§f> §-").append(line2).append("§+§f <§-");
                break;
            case 2:
                this.builder.setLength(0);
                line3 = this.builder.append("§+§f> §-").append(line3).append("§+§f <§-");
                break;
            case 3:
                this.builder.setLength(0);
                line4 = this.builder.append("§+§f> §-").append(line4).append("§+§f <§-");
        }

        int y = -tileEntity.signText.length * 5;
        if (tileEntity.isGlowing() && this.mc.gameSettings.fancyGraphics.value >= 1) {
            this.textMeshRenderer.render(sr, t, line1, line2, line3, line4, 0, y, SF.setOutlined(SF.setColor(0L, color)));
        } else {
            this.textMeshRenderer.render(sr, t, line1, line2, line3, line4, 0, y, SF.setColor(0L, color));
        }

        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static void drawTexturedModalRect(int color, @NonNull IconCoordinate coordinate) {
        coordinate.parentAtlas.bind();
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(color, 128);
        tessellator.addVertexWithUV(-1.0 / 2.0, 0.5 / 2.0, 0.0, coordinate.getIconUMin(), coordinate.getIconVMin());
        tessellator.addVertexWithUV(-1.0 / 2.0, -0.5 / 2.0, 0.0, coordinate.getIconUMin(), coordinate.getIconVMax());
        tessellator.addVertexWithUV(1.0 / 2.0, -0.5 / 2.0, 0.0, coordinate.getIconUMax(), coordinate.getIconVMax());
        tessellator.addVertexWithUV(1.0 / 2.0, 0.5 / 2.0, 0.0, coordinate.getIconUMax(), coordinate.getIconVMin());
        tessellator.draw();
    }

    @Override
    public void tick() {
        this.textMeshRenderer.tick();
    }

    @Override
    public void onWorldChanged(World world) {
        this.textMeshRenderer.flushCaches();
    }

    @Override
    public boolean isVisible(@NonNull TileEntitySignSkyroot tileEntity, @NonNull ICamera camera, float partialTick) {
        return camera.getFrustum().isVisible(AABB.getTemporaryBB(tileEntity.x, tileEntity.y, tileEntity.z, tileEntity.x + 1.0, tileEntity.y + 1.0, tileEntity.z + 1.0), partialTick);
    }
}
