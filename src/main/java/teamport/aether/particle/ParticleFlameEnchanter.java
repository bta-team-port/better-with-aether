package teamport.aether.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;

import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class ParticleFlameEnchanter extends ParticleFlameAmbrosium {
    public ParticleFlameEnchanter(World world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z, xd, yd, zd);
        this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/flameenchanter");
    }
}
