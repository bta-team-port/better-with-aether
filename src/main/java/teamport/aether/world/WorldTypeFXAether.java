package teamport.aether.world;

import net.minecraft.client.render.worldtype.WorldTypeFX;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldType;

public class WorldTypeFXAether extends WorldTypeFX {

    public WorldTypeFXAether(WorldType worldType) {
        super(worldType);
    }

    public WorldTypeFX setCloudHeight(float cloudHeight) {
        return this;
    }

    public boolean hasGround() {
        return false;
    }

    public float[] getSunriseColor(float timeOfDay, float partialTick) {
        float[] colorsSunriseSunset = new float[4];
        float f2 = 0.4F;
        float f3 = MathHelper.cos(timeOfDay * 3.141593F * 2.0F) - 0.0F;
        float f4 = -0F;
        if (f3 >= f4 - f2 && f3 <= f4 + f2) {
            float f5 = ((f3 - f4) / f2) * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
            f6 *= f6;
            colorsSunriseSunset[0] = f5 * 0.3F + 0.1F;
            colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
            colorsSunriseSunset[2] = f5 * f5 * 0.7F + 0.2F;
            colorsSunriseSunset[3] = f6;
            return colorsSunriseSunset;
        } else
            return null;
    }

    public Vec3 getFogColor(World world, double x, double y, double z, float celestialAngle, float partialTick) {
        int i = 0x8080a0;
        float f2 = MathHelper.cos(celestialAngle * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F)
            f2 = 0.0F;
        if (f2 > 1.0F)
            f2 = 1.0F;
        float f3 = (float) (i >> 16 & 0xff) / 255F;
        float f4 = (float) (i >> 8 & 0xff) / 255F;
        float f5 = (float) (i & 0xff) / 255F;
        f3 *= f2 * 0.94F + 0.06F;
        f4 *= f2 * 0.94F + 0.06F;
        f5 *= f2 * 0.91F + 0.09F;
        return Vec3.getTempVec3(f3, f4, f5);
    }
}
