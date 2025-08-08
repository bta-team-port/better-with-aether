package teamport.aether.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.GLAllocation;
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
import net.minecraft.client.util.helper.Colors;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSignPainted;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.enums.EnumSignPicture;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import teamport.aether.blocks.BlockLogicSignSkyroot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TileEntityRendererSignSkyroot extends TileEntityRenderer<TileEntitySignSkyroot> {
    private final ModelSign modelSign = new ModelSign();
    private final Minecraft mc = Minecraft.getMinecraft();
    private final @NotNull StringBuilder builder = new StringBuilder();
    private final String[] signColorTextures = new String[16];
    private final TileEntityRendererSignSkyroot.BufferedTextMeshRenderer textMeshRenderer = new TileEntityRendererSignSkyroot.BufferedTextMeshRenderer();
    private static final FontRenderer FONT_RENDERER = new FontRendererDefault();

    public TileEntityRendererSignSkyroot() {
        Arrays.fill(this.signColorTextures, "/assets/aether/textures/entity/sign_skyroot.png");

    }

    public void doRender(@NotNull Tessellator t, @NotNull TileEntitySignSkyroot tileEntity, double x, double y, double z, float partialTick) {
        Block<?> block = tileEntity.getBlock();
        if (block != null && block.getLogic() instanceof BlockLogicSignSkyroot) {
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            float scale = 0.6666667F;
            GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
            int meta = tileEntity.getBlockMeta();
            float height;
            float angle;
            if (((BlockLogicSignSkyroot)block.getLogic()).isFreeStanding) {
                angle = (float)((meta & 15) * 360) / 16.0F;
                GL11.glRotatef(-angle, 0.0F, 1.0F, 0.0F);
                this.modelSign.signStick.visible = true;
                height = 20.0F;
            } else {
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

                GL11.glRotatef(-angle, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
                this.modelSign.signStick.visible = false;
                height = 13.0F;
            }

            GL11.glPushMatrix();
            GL11.glScalef(0.6666667F, -0.6666667F, -0.6666667F);
            int colorSign;
            if (Block.hasLogicClass(block, BlockLogicSignPainted.class)) {
                DyeColor c = ((IPainted)block.getLogic()).fromMetadata(meta);
                this.loadTexture(this.signColorTextures[c.blockMeta]);
                colorSign = c.color.getARGB();
            } else {
                this.loadTexture("/assets/aether/textures/entity/sign_skyroot.png");
                colorSign = 6905411;
            }

            GL11.glDisable(3042);
            this.modelSign.render();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glTranslatef(0.0F, 0.33545F, 0.04375F);
            GL11.glNormal3f(0.0F, 0.0F, 1.0F);
            GL11.glDepthMask(false);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            EnumSignPicture picture = tileEntity.getPicture();
            if (picture != EnumSignPicture.NONE) {
                drawTexturedModalRect(colorSign, TextureRegistry.getTexture(picture.getTextureKey()));
            }

            GL11.glDisable(3042);
            GL11.glPopMatrix();
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
            int r = (int)MathHelper.clamp((float)Color.redFromInt(color) * lightLevel + lightOffset, 0.0F, 255.0F);
            int g = (int)MathHelper.clamp((float)Color.greenFromInt(color) * lightLevel + lightOffset, 0.0F, 255.0F);
            int b = (int)MathHelper.clamp((float)Color.blueFromInt(color) * lightLevel + lightOffset, 0.0F, 255.0F);
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

            int _y = -tileEntity.signText.length * 5;
            if (tileEntity.isGlowing() && this.mc.gameSettings.fancyGraphics.value >= 1) {
                this.textMeshRenderer.render(sr, t, line1, line2, line3, line4, 0, _y, SF.setOutlined(SF.setColor(0L, color)));
            } else {
                this.textMeshRenderer.render(sr, t, line1, line2, line3, line4, 0, _y, SF.setColor(0L, color));
            }

            GL11.glPopMatrix();
            GL11.glDepthMask(true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
            GL11.glDisable(32826);
        }
    }

    private static void drawTexturedModalRect(int color, @NotNull IconCoordinate coordinate) {
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

    public void tick() {
        this.textMeshRenderer.tick();
    }

    public void onWorldChanged(World world) {
        this.textMeshRenderer.flushCaches();
    }

    public boolean isVisible(@NotNull TileEntitySignSkyroot tileEntity, @NotNull ICamera camera, float partialTick) {
        return camera.getFrustum().isVisible(AABB.getTemporaryBB(tileEntity.x, tileEntity.y, tileEntity.z, tileEntity.x + 1, tileEntity.y + 1, tileEntity.z + 1), partialTick);
    }

    @Environment(EnvType.CLIENT)
    public static class BufferedTextMeshRenderer {
        private final @NotNull List<Integer> freeDisplayLists = new ArrayList<>();
        private final @NotNull List<TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry> bufferedMeshes = new ArrayList<>();
        private final @NotNull TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry referenceEntry = new TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry("", "", "", "", 0L);

        public BufferedTextMeshRenderer() {
        }

        public void tick() {
            Iterator<TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry> iterator = this.bufferedMeshes.iterator();

            while(iterator.hasNext()) {
                TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry entry = iterator.next();
                if (System.currentTimeMillis() - entry.lastUse > 120000L) {
                    assert entry.list != -1;

                    this.freeDisplayLists.add(entry.list);
                    iterator.remove();
                }
            }

        }

        public void flushCaches() {
            Iterator var1 = this.bufferedMeshes.iterator();

            while(var1.hasNext()) {
                TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry entry = (TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry)var1.next();
                GLAllocation.deleteDisplayList(entry.list);
            }

            this.bufferedMeshes.clear();
            var1 = this.freeDisplayLists.iterator();

            while(var1.hasNext()) {
                int list = (Integer)var1.next();
                GLAllocation.deleteDisplayList(list);
            }

            this.freeDisplayLists.clear();
        }

        public void render(@NotNull FontRenderer sr, @NotNull Tessellator t, @NotNull CharSequence line1, @NotNull CharSequence line2, @NotNull CharSequence line3, @NotNull CharSequence line4, int x, int y, long config) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, 0.0F);
            this.renderInternal(sr, t, line1, line2, line3, line4, config);
            GL11.glPopMatrix();
        }

        private void renderInternal(@NotNull FontRenderer sr, @NotNull Tessellator t, @NotNull CharSequence line1, @NotNull CharSequence line2, @NotNull CharSequence line3, @NotNull CharSequence line4, long config) {
            this.referenceEntry.line1 = line1.toString();
            this.referenceEntry.line2 = line2.toString();
            this.referenceEntry.line3 = line3.toString();
            this.referenceEntry.line4 = line4.toString();
            this.referenceEntry.config = config;
            TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry entry = null;

            for (MeshEntry e : this.bufferedMeshes) {
                if (this.referenceEntry.equals(e)) {
                    entry = e;
                    break;
                }
            }

            if (entry != null) {
                entry.lastUse = System.currentTimeMillis();
                GL11.glCallList(entry.list);
            } else {
                entry = new TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry(line1, line2, line3, line4, config);
                entry.lastUse = System.currentTimeMillis();
                entry.list = this.getDisplayList();
                GL11.glNewList(entry.list, 4864);
                sr.render(t, line1, -sr.stringWidth(line1) / 2, 0).setConfig(config).call();
                sr.render(t, line2, -sr.stringWidth(line2) / 2, 10).setConfig(config).call();
                sr.render(t, line3, -sr.stringWidth(line3) / 2, 20).setConfig(config).call();
                sr.render(t, line4, -sr.stringWidth(line4) / 2, 30).setConfig(config).call();
                GL11.glEndList();
                GL11.glCallList(entry.list);
                this.bufferedMeshes.add(entry);
            }

        }

        private int getDisplayList() {
            return this.freeDisplayLists.isEmpty() ? GLAllocation.generateDisplayLists(1) : this.freeDisplayLists.remove(0);
        }

        @Environment(EnvType.CLIENT)
        public static class MeshEntry {
            public long config;
            public @NotNull String line1;
            public @NotNull String line2;
            public @NotNull String line3;
            public @NotNull String line4;
            public long lastUse = 0L;
            public int list = -1;

            public MeshEntry(@NotNull CharSequence line1, @NotNull CharSequence line2, @NotNull CharSequence line3, @NotNull CharSequence line4, long config) {
                this.config = config;
                this.line1 = line1.toString();
                this.line2 = line2.toString();
                this.line3 = line3.toString();
                this.line4 = line4.toString();
            }

            public boolean equals(Object o) {
                if (o != null && this.getClass() == o.getClass()) {
                    TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry entry = (TileEntityRendererSignSkyroot.BufferedTextMeshRenderer.MeshEntry)o;
                    return this.config == entry.config && this.line1.equals(entry.line1) && this.line2.equals(entry.line2) && this.line3.equals(entry.line3) && this.line4.equals(entry.line4);
                } else {
                    return false;
                }
            }

            public int hashCode() {
                int result = Long.hashCode(this.config);
                result = 31 * result + this.line1.hashCode();
                result = 31 * result + this.line2.hashCode();
                result = 31 * result + this.line3.hashCode();
                result = 31 * result + this.line4.hashCode();
                return result;
            }
        }
    }
}