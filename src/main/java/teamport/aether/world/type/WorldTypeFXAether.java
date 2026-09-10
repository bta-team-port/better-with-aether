package teamport.aether.world.type;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.render.worldtype.WorldTypeFX;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.type.WorldType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;

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
    public float @Nullable [] getSunriseColor(float timeOfDay, float partialTick) {
        float[] sunriseCol = new float[4];
        float sunriseMinimum = 0.4F;
        float sunrisePresence = MathHelper.cos(timeOfDay * (float) Math.PI * 2.0F);
        float middle = 0.0F;
        if (sunrisePresence >= middle - sunriseMinimum && sunrisePresence <= middle + sunriseMinimum) {
            float c = (sunrisePresence - middle) / sunriseMinimum * 0.5F + 0.5F;
            float a = 1.0F - (1.0F - MathHelper.sin(c * (float) Math.PI)) * 0.99F;
            a *= a;
            sunriseCol[0] = c * 0.3F + 0.1F;
            sunriseCol[1] = c * c * 0.7F + 0.2F;
            sunriseCol[2] = c * c * 0.7F + 0.2F;
            sunriseCol[3] = a;
            return sunriseCol;
        } else {
            return null;
        }
    }

    @Override
    public @NonNull Vector3fc getFogColor(@NonNull World world, double x, double y, double z, float celestialAngle, float partialTick) {
        float dayProgress = MathHelper.cos((float) (celestialAngle * Math.PI * 2.0F)) * 2.0F + 0.5F;
        dayProgress = MathHelper.clamp(dayProgress, 0.0F, 1.0F);
        float r;
        float g;
        float b;
        if (Colorizers.fog.isEnabled()) {
            int color = Colorizers.fog.getColor(world, new TilePos(x, y, z));
            r = (float) (color >> 16 & 255) / 255.0F;
            g = (float) (color >> 8 & 255) / 255.0F;
            b = (float) (color & 255) / 255.0F;
        } else {
            int baseColor = 0x8080a0;
            r = (baseColor >> 16 & 255) / 255.0F;
            g = (baseColor >> 8 & 255) / 255.0F;
            b = (baseColor & 255) / 255.0F;
        }

        r *= dayProgress * 0.94F + 0.06F;
        g *= dayProgress * 0.94F + 0.06F;
        b *= dayProgress * 0.91F + 0.09F;
        return new Vector3f(r, g, b);
    }
}
