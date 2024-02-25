package bta.aether.entity;

import net.minecraft.client.entity.fx.EntityFlameFX;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.TextureHelper;

import static bta.aether.Aether.MOD_ID;

public class EntityFlameAmbrosiumFX extends EntityFlameFX {
    public EntityFlameAmbrosiumFX(World world, double d, double d1, double d2, double d3, double d4, double d5, Type type) {
        super(world, d, d1, d2, d3, d4, d5, type);
        this.particleTextureIndex = TextureHelper.getOrCreateParticleTextureIndex(MOD_ID, "flameambrosium.png");
    }
}
