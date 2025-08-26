package teamport.aether.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.util.helper.Color;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.generate.feature.dungeon.map.DungeonMapEntry;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.awt.image.BufferedImage;

public class DynamicTextureDungeonCompass extends DynamicTexture {
    public Minecraft mc;
    public byte[] compassImageData;
    public double angleFinal;
    public double delta;
    public double scaleFactor;

    public final Color needleColor = new Color().setRGB(255, 222, 60);

    public DynamicTextureDungeonCompass(Minecraft minecraft, IconCoordinate iconCoordinate) {
        super(iconCoordinate);
        this.mc = minecraft;
    }

    public void postInit() {
        this.initTexture();
        BufferedImage atlas = this.targetTexture.parentAtlas.atlas;
        this.compassImageData = new byte[this.targetTexture.getArea() * 4];

        for(int x = 0; x < this.targetTexture.width; ++x) {
            for(int y = 0; y < this.targetTexture.height; ++y) {
                putPixel(this.compassImageData, y * this.targetTexture.width + x, atlas.getRGB(this.targetTexture.iconX + x, this.targetTexture.iconY + y));
            }
        }

        this.scaleFactor = (double)this.targetTexture.width / 16.0;
    }

    public boolean runUpdates(boolean isPaused) {
        return !isPaused;
    }

    public double getAngle() {
        if (this.mc.currentWorld == null || this.mc.thePlayer == null) return 0.0;

        if (this.mc.currentWorld.dimension.id != AetherDimension.AETHER.id || AetherDimension.dungeonMap.isEmpty()) {
            return Math.random() * Math.PI * 2.0;
        }

        PlayerLocal player = mc.thePlayer;

        WorldFeaturePoint closestCoord = null;
        for (DungeonMapEntry dungeon : AetherDimension.dungeonMap.values()) {
            WorldFeaturePoint coord = dungeon.getPosition();
            if (closestCoord == null) {
                closestCoord = coord;
                continue;
            }

            if (player.distanceTo(coord.x, coord.y, coord.z) < player.distanceTo(closestCoord.x, closestCoord.y, closestCoord.z)) {
                closestCoord = coord;
            }
        }

        if (closestCoord == null || player.distanceTo(closestCoord.x, player.y, closestCoord.z) < 16) {
            return Math.random() * Math.PI * 2.0;
        }

        double var23 = (double)closestCoord.x - this.mc.thePlayer.x;
        double var25 = (double)closestCoord.z - this.mc.thePlayer.z;
        return (double)(this.mc.thePlayer.yRot - 90.0F) * Math.PI / 180.0 - Math.atan2(var25, var23);
    }

    public void update() {
        for(int _x = 0; _x < this.targetTexture.width; ++_x) {
            for(int _y = 0; _y < this.targetTexture.height; ++_y) {
                int i = _y * this.targetTexture.width + _x;
                int a = this.compassImageData[i * 4 + 3] & 255;
                int r = this.compassImageData[i * 4 + 0] & 255;
                int g = this.compassImageData[i * 4 + 1] & 255;
                int b = this.compassImageData[i * 4 + 2] & 255;
                this.imageData[i * 4 + 0] = (byte)r;
                this.imageData[i * 4 + 1] = (byte)g;
                this.imageData[i * 4 + 2] = (byte)b;
                this.imageData[i * 4 + 3] = (byte)a;
            }
        }

        double angle = getAngle();

        double angleSmooth = angle - this.angleFinal;
        while (angleSmooth < -Math.PI) {
            angleSmooth += Math.PI * 2;
        }

        while(angleSmooth >= Math.PI) {
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

        double xs = (double)this.targetTexture.width / 2.0 + 0.5;
        double ys = (double)this.targetTexture.height / 2.0 - 0.5;

        int r, g, b;
        short a;

        int x2;
        int y2;
        int j;

        int i;
        for (i = (int)(-4.0 * this.scaleFactor); i <= (int)(4.0 * this.scaleFactor); ++i) {
            x2 = (int)(xs + y * (double)i * 0.3);
            y2 = (int)(ys - x * (double)i * 0.3 * 0.5);
            j = y2 * this.targetTexture.width + x2;
            r = 100;
            g = 100;
            b = 100;
            a = 255;
            this.imageData[j * 4 + 0] = (byte)r;
            this.imageData[j * 4 + 1] = (byte)g;
            this.imageData[j * 4 + 2] = (byte)b;
            this.imageData[j * 4 + 3] = (byte)a;
        }

        for (i = (int)(-8.0 * this.scaleFactor); i <= (int)(16.0 * this.scaleFactor); ++i) {
            r = i >= 0 ? needleColor.getRed() : 100;
            g = i >= 0 ? needleColor.getGreen() : 100;
            b = i >= 0 ? needleColor.getBlue() : 100;
            a = 255;

            x2 = (int)(xs + x * (double)i * 0.3);
            y2 = (int)(ys + y * (double)i * 0.3 * 0.5);
            j = y2 * this.targetTexture.width + x2;

            this.imageData[j * 4 + 0] = (byte)r;
            this.imageData[j * 4 + 1] = (byte)g;
            this.imageData[j * 4 + 2] = (byte)b;
            this.imageData[j * 4 + 3] = (byte)a;
        }
    }
}
