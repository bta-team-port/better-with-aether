package teamport.aether.entity.monster.aechorplant;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererAechorPlant extends MobRenderer<MobAechorPlant> {

    public MobRendererAechorPlant(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobAechorPlant entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model = this.getModel("main");
        model.resetBones();

        float sinage = (float) Math.sin(entity.getSinage());
        float sinage2;
        if (entity.hurtTime > 0) {
            sinage *= 0.45F;
            sinage -= 0.125F;
            sinage2 = 1.75F + (float) Math.sin(entity.getSinage() + 2.0F) * 1.5F;
        } else if (entity.hasTarget()) {
            sinage *= 0.25F;
            sinage2 = 1.75F + (float) Math.sin(entity.getSinage() + 2.0F) * 1.5F;
        } else {
            sinage *= 0.125F;
            sinage2 = 1.75F;
        }

        BoneTransform body = model.getTransform("body");
        BoneTransform stem = model.getTransform("stem");
        body.rotX = 0.0F;
        body.rotY = getHeadPitch(entity, partialTick) / 57.29578F;
        float boff = sinage2;
        stem.rotY = body.rotY;
        stem.posY = boff * 0.5F;

        String[] petals = {
            "petalLarge1", "petalLarge2", "petalLarge3", "petalLarge4", "petalLarge5",
            "petalSmall1", "petalSmall2", "petalSmall3", "petalSmall4", "petalSmall5"
        };

        for (String bone : petals) {
            BoneTransform t = model.getTransform(bone);
            t.rotX += sinage;
        }

        String[] thorns = {
            "thorn1", "thorn2", "thorn3", "thorn4", "thorn5", "thorn6", "thorn7", "thorn8"
        };

        for (String bone : thorns) {
            BoneTransform t = model.getTransform(bone);
            t.rotX += sinage;
        }

        return model;
    }
}
