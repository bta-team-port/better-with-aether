package teamport.aether.world.type;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.render.worldtype.WorldTypeFX;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldType;

@Environment(EnvType.CLIENT)
public class WorldTypeFXAether extends WorldTypeFX {

    public WorldTypeFXAether(WorldType worldType) {
        super(worldType);
    }

    @Override
    public boolean hasGround() {
        return false;
    }

    @Override
    public WorldTypeFX setCloudHeight(float cloudHeight) {
        return this;
    }

    @Override
    public float getCloudHeight() {
        return 8;
    }

    @Override
    public boolean hasAurora() {
        return true;
    }

    @Override
    public float[] getSunriseColor(float timeOfDay, float partialTick) {
        float[] colorsSunriseSunset = new float[4];
        float f2 = 0.4F;
        float r = MathHelper.cos(timeOfDay * 3.141593F * 2.0F) - 0.0F;
        float g = -0F;
        if (r >= g - f2 && r <= g + f2) {
            float f5 = ((r - g) / f2) * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
            f6 *= f6;
            colorsSunriseSunset[0] = f5 * 0.3F + 0.1F;
            colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
            colorsSunriseSunset[2] = f5 * f5 * 0.7F + 0.2F;
            colorsSunriseSunset[3] = f6;
            return colorsSunriseSunset;
        } else
            return new float[4];
    }

    @Override
    public Vec3 getFogColor(World world, double x, double y, double z, float celestialAngle, float partialTick) {
        float f2 = MathHelper.cos(celestialAngle * 3.1415927F * 2.0F) * 2.0F + 0.5F;
        f2 = MathHelper.clamp(f2, 0.0F, 1.0F);
        float r;
        float g;
        float b;
        int x1;
        if (Colorizers.fog.isEnabled()) {
            x1 = MathHelper.floor(x);
            int y1 = MathHelper.floor(y);
            int z1 = MathHelper.floor(z);
            int color = Colorizers.fog.getColor(world, x1, y1, z1);
            r = (color >> 16 & 255) / 255F;
            g = (color >> 8 & 255) / 255F;
            b = (color & 255) / 255F;
        } else {
            x1 = 0x8080a0;
            r = (x1 >> 16 & 255) / 255.0F;
            g = (x1 >> 8 & 255) / 255.0F;
            b = (x1 & 255) / 255.0F;
        }

        if (f2 < 0.0F)
            f2 = 0.0F;
        if (f2 > 1.0F)
            f2 = 1.0F;

        r *= f2 * 0.94F + 0.06F;
        g *= f2 * 0.94F + 0.06F;
        b *= f2 * 0.91F + 0.09F;
        return Vec3.getTempVec3(r, g, b);
    }
}
