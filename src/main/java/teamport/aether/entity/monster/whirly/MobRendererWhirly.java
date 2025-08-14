package teamport.aether.entity.monster.whirly;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.entity.particle.ParticleExplode;
import net.minecraft.client.entity.particle.ParticleSmoke;
import net.minecraft.client.render.ParticleEngine;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class MobRendererWhirly extends EntityRenderer<MobWhirly> {
    public final Random rand = new Random();

    public void RenderWhirly() {
    }

    public void render(Tessellator tessellator, MobWhirly entity, double d, double d1, double d2, float f, float f1) {
        ParticleEngine particleManager = ((Minecraft) FabricLoader.getInstance().getGameInstance()).particleEngine;

        double d3 = (float) entity.x;
        double d4 = (float) entity.y;
        double d5 = (float) entity.z;

        int i;
        double d6;
        double d7;
        double d8;
        float f2;
        if (!entity.evil) {
            for (i = 0; i < 2; ++i) {
                d6 = (float) entity.x + rand.nextFloat() * 0.25F;
                d7 = (float) entity.y + entity.bbHeight + 0.125F;
                d8 = (float) entity.z + rand.nextFloat() * 0.25F;
                f2 = rand.nextFloat() * 360.0F;
                ParticleExplode entityExplodeFx = new ParticleExplode(entity.world, -Math.sin(0.01745329F * f2) * 0.75, d7 - 0.25, Math.cos(0.01745329F * f2) * 0.75, d6, 0.125, d8);
                particleManager.add(entityExplodeFx);
                entity.fluffies.add(entityExplodeFx);
                entityExplodeFx.viewScale = 10.0;
                entityExplodeFx.noPhysics = true;
                entityExplodeFx.setRot(0.25F, 0.25F);
                entityExplodeFx.setPos(entity.x, entity.y, entity.z);
                entityExplodeFx.y = d7;
            }
        } else {
            for (i = 0; i < 3; ++i) {
                d6 = (float) entity.x + rand.nextFloat() * 0.25F;
                d7 = (float) entity.y + entity.bbHeight + 0.125F;
                d8 = (float) entity.z + rand.nextFloat() * 0.25F;
                f2 = rand.nextFloat() * 360.0F;
                ParticleSmoke entitySmokeFx = new ParticleSmoke(entity.world, -Math.sin(0.01745329F * f2) * 0.75, d7 - 0.25, Math.cos(0.01745329F * f2) * 0.75, d6, 0.125, d8, 3.5F);
                particleManager.add(entitySmokeFx);
                entity.fluffies.add(entitySmokeFx);
                entitySmokeFx.viewScale = 10.0;
                entitySmokeFx.noPhysics = true;
                entitySmokeFx.setRot(0.25F, 0.25F);
                entitySmokeFx.setPos(entity.x, entity.y, entity.z);
                entitySmokeFx.y = d7;
            }
        }

        if (!entity.fluffies.isEmpty()) {
            for (i = 0; i < entity.fluffies.size(); ++i) {
                Particle entityFx = entity.fluffies.get(i);
                if (entityFx.removed) {
                    entity.fluffies.remove(entityFx);
                } else {
                    d6 = (float) entityFx.x;
                    d7 = (float) entityFx.bb.minY;
                    d8 = (float) entityFx.z;
                    double d9 = entity.distanceTo(entityFx);
                    double d10 = d7 - d4;
                    entityFx.yd = 0.11500000208616257;
                    double d11 = Math.atan2(d3 - d6, d5 - d8) / 0.01745329424738884;
                    d11 += 160.0;
                    entityFx.xd = -Math.cos(0.01745329424738884 * d11) * (d9 * 2.5 - d10) * 0.10000000149011612;
                    entityFx.zd = Math.sin(0.01745329424738884 * d11) * (d9 * 2.5 - d10) * 0.10000000149011612;
                }
            }
        }
    }
}
