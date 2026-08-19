package teamport.aether.entity.monster.aechorplant;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererAechorPlant extends MobRenderer<MobAechorPlant> {

    public MobRendererAechorPlant(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobAechorPlant entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model = this.getModel("main");
        model.resetBones();

        float sinageAngle = MathHelper.lerp(entity.getSinageO(), entity.getSinage(), partialTick);

        float rawSin = MathHelper.sin(sinageAngle);
        float rawSin2 = MathHelper.sin(sinageAngle + 2.0F);

        float sinage;
        float sinage2;

        if (entity.hurtTime > 0) {
            sinage = rawSin * 0.45F - 0.125F;
            sinage2 = 1.75F + rawSin2 * 1.5F;
        } else if (entity.hasTarget()) {
            sinage = rawSin * 0.25F;
            sinage2 = 1.75F + rawSin2 * 1.5F;
        } else {
            sinage = rawSin * 0.125F;
            sinage2 = 1.75F;
        }

        BoneTransform body = model.getTransform("body");
        BoneTransform stem = model.getTransform("stem");

        body.rotX = 0.0F;
        body.rotY = getHeadPitch(entity, partialTick) * MathHelper.DEG_TO_RAD;

        stem.rotY = body.rotY;
        stem.posY = (sinage2 * 0.5F) - 1.0F;

        String[] petals = {
            "petalLarge1", "petalLarge2", "petalLarge3", "petalLarge4", "petalLarge5",
            "petalSmall1", "petalSmall2", "petalSmall3", "petalSmall4", "petalSmall5"
        };

        for (String bone : petals) {
            BoneTransform t = model.getTransform(bone);
            t.rotX += 0.15 - sinage;
        }

        String[] thorns = {
            "thorn1", "thorn2", "thorn3", "thorn4", "thorn5", "thorn6", "thorn7", "thorn8"
        };

        for (String bone : thorns) {
            BoneTransform t = model.getTransform(bone);
            t.rotX -= sinage;
        }

        String[] stalks = {
            "stalk1", "stalk2", "stalk3"
        };

        for (String bone : stalks) {
            BoneTransform t = model.getTransform(bone);
            t.rotX += sinage / 2;
            t.rotY += sinage / 2;
            t.rotZ += sinage / 2;
        }

        return model;
    }
}
