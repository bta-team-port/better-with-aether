package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.util.helper.Color;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import teamport.aether.world.feature.util.map.DungeonLogic;
import teamport.aether.world.feature.util.map.DungeonMap;

import java.awt.image.BufferedImage;

import static teamport.aether.world.feature.util.WorldFeaturePoint.wfpoint;

@Environment(EnvType.CLIENT)
public class DynamicTextureDungeonCompass extends DynamicTexture {
    private final Minecraft minecraft;
    private byte[] compassImageData;
    private double angleFinal;
    private double delta;
    private double scaleFactor;

    public final Color needleColor = new Color().setRGB(255, 222, 60);

    public DynamicTextureDungeonCompass(Minecraft minecraft, IconCoordinate iconCoordinate) {
        super(iconCoordinate);
        this.minecraft = minecraft;
    }

    public void postInit() {
        this.initTexture();
        BufferedImage atlas = this.targetTexture.parentAtlas.colorImage;
        this.compassImageData = new byte[this.targetTexture.getArea() * 4];

        for (int x = 0; x < this.targetTexture.width; ++x) {
            for (int y = 0; y < this.targetTexture.height; ++y) {
                putPixel(this.compassImageData, y * this.targetTexture.width + x, atlas.getRGB(this.targetTexture.iconX + x, this.targetTexture.iconY + y));
            }
        }

        this.scaleFactor = this.targetTexture.width / 16.0;
    }

    @Override
    public boolean runUpdates(boolean isPaused) {
        return !isPaused;
    }

    private static final int POSITION_UPDATE_COOLDOWN = 1000;
    private long lastPositionUpdateStamp = 0;
    private static WorldFeaturePoint positionCache = null;

    public double getAngle() {
        if (this.minecraft.currentWorld == null || this.minecraft.thePlayer == null) return 0.0;

        if (this.minecraft.currentWorld.dimension.id == AetherDimension.getAether().id) {
            WorldFeaturePoint coord = null;
            PlayerLocal player = minecraft.thePlayer;

            long time = System.currentTimeMillis();
            if (time - lastPositionUpdateStamp > POSITION_UPDATE_COOLDOWN) {

                WorldFeaturePoint playerPos = wfpoint(player);
                for (DungeonLogic entry : DungeonMap.getDungeonList()) {
                    if (entry == null) continue;

                    WorldFeaturePoint point = entry.getPosition();
                    if (point == null) continue;

                    if (coord == null || point.distanceTo(playerPos) < coord.distanceTo(playerPos)) {
                        coord = point;
                    }
                }

                positionCache = coord;
                lastPositionUpdateStamp = time;
            } else coord = positionCache;

            if (coord != null && player.distanceTo(coord.getX(), player.y, coord.getZ()) > 16) {
                double distX = coord.getX() - player.x;
                double distZ = coord.getZ() - player.z;
                return (player.yRot - 90.0F) * Math.PI / 180.0 - Math.atan2(distZ, distX);
            }
        }

        return Math.random() * Math.PI * 2.0;
    }

    public void update() {
        for (int _x = 0; _x < this.targetTexture.width; ++_x) {
            for (int _y = 0; _y < this.targetTexture.height; ++_y) {
                int i = _y * this.targetTexture.width + _x;
                int a = this.compassImageData[i * 4 + 3] & 255;
                int r = this.compassImageData[i * 4] & 255;
                int g = this.compassImageData[i * 4 + 1] & 255;
                int b = this.compassImageData[i * 4 + 2] & 255;
                this.getImageData(0)[i * 4] = (byte) r;
                this.getImageData(0)[i * 4 + 1] = (byte) g;
                this.getImageData(0)[i * 4 + 2] = (byte) b;
                this.getImageData(0)[i * 4 + 3] = (byte) a;
            }
        }

        double angle = getAngle();

        double angleSmooth = angle - this.angleFinal;
        while (angleSmooth < -Math.PI) {
            angleSmooth += Math.PI * 2;
        }

        while (angleSmooth >= Math.PI) {
            angleSmooth -= Math.PI * 2;
        }

        if (angleSmooth < -1.0) {
            angleSmooth = -1.0;
        }

        if (angleSmooth > 1.0) {
            angleSmooth = 1.0;
        }

        this.delta += angleSmooth * 0.1;
        this.delta *= 0.8;
        this.angleFinal += this.delta;

        double x = Math.sin(this.angleFinal);
        double y = Math.cos(this.angleFinal);

        double xs = this.targetTexture.width / 2.0 + 0.5;
        double ys = this.targetTexture.height / 2.0 - 0.5;

        int r;
        int g;
        int b;
        short a;

        int x2;
        int y2;
        int j;

        int i;
        for (i = (int) (-4.0 * this.scaleFactor); i <= (int) (4.0 * this.scaleFactor); ++i) {
            x2 = (int) (xs + y * i * 0.3);
            y2 = (int) (ys - x * i * 0.3 * 0.5);
            j = y2 * this.targetTexture.width + x2;
            r = 100;
            g = 100;
            b = 100;
            a = 255;
            this.getImageData(0)[j * 4] = (byte) r;
            this.getImageData(0)[j * 4 + 1] = (byte) g;
            this.getImageData(0)[j * 4 + 2] = (byte) b;
            this.getImageData(0)[j * 4 + 3] = (byte) a;
        }

        for (i = (int) (-8.0 * this.scaleFactor); i <= (int) (16.0 * this.scaleFactor); ++i) {
            r = i >= 0 ? needleColor.getRed() : 100;
            g = i >= 0 ? needleColor.getGreen() : 100;
            b = i >= 0 ? needleColor.getBlue() : 100;
            a = 255;

            x2 = (int) (xs + x * i * 0.3);
            y2 = (int) (ys + y * i * 0.3 * 0.5);
            j = y2 * this.targetTexture.width + x2;

            this.getImageData(0)[j * 4] = (byte) r;
            this.getImageData(0)[j * 4 + 1] = (byte) g;
            this.getImageData(0)[j * 4 + 2] = (byte) b;
            this.getImageData(0)[j * 4 + 3] = (byte) a;
        }
    }
}
