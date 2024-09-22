package bta.aether.world;

import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManagerGeneric;

public class WorldTypeAetherDefault
    extends WorldTypeAether
{
    public WorldTypeAetherDefault(String languageKey) {
        super(
            languageKey,
            Weather.overworldClear,
            new WindManagerGeneric(),
            SeasonConfig.builder()
                .withSingleSeason(Seasons.NULL)
                .build()
        );
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getMaxY() {
        return 255;
    }

    @Override
    public Vec3d getFogColor(World world, double d, double e, double f, float g, float h) {
        int i = 8421536;
        float f2 = MathHelper.cos((float) (f * 3.141593F * 2.0F)) * 2.0F + 0.5F;
        if(f2 < 0.0F)
        {
            f2 = 0.0F;
        }
        if(f2 > 1.0F)
        {
            f2 = 1.0F;
        }
        float f3 = (float)(i >> 16 & 255) / 255F;
        float f4 = (float)(i >> 8 & 255) / 255F;
        float f5 = (float)(i & 255) / 255F;
        f3 *= f2 * 0.94F + 0.06F;
        f4 *= f2 * 0.94F + 0.06F;
        f5 *= f2 * 0.91F + 0.09F;
        return Vec3d.createVector(f3, f4, f5);
    }
}
