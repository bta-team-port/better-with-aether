package teamport.aether.mixin.fix;

import org.joml.Math;
import org.joml.Matrix4f;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.useless.dragonfly.data.entity.mojang.Bone;
import org.useless.dragonfly.data.entity.mojang.EntityGeometryMojangData;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.mojang.StaticEntityModelMojang;

import java.util.Map;

@Mixin(StaticEntityModelMojang.class)
public abstract class StaticEntityModelMojangMixin {

    @Shadow
    @Final
    public EntityGeometryMojangData data;

    @Shadow
    @Final
    @NonNull
    public Map<@NonNull String, @NonNull BoneTransform> transformMap;

    /**
     * @author luke
     * @reason move scale before rotating and translating so bones scale relative to their pivot
     */
    //TODO swap this overwrite later on
    @Overwrite
    protected boolean boneTransform(@NonNull Bone bone, Matrix4f dest) {
        boolean hidden = false;
        Bone parent = bone.parent != null ? this.data.bones.get(bone.parent) : null;
        if (parent != null) {
            hidden |= this.boneTransform(parent, dest);
        }

        BoneTransform transform = this.transformMap.get(bone.name);
        hidden |= !transform.visible;

        dest.translate((float) bone.pivot[0], (float) bone.pivot[1], (float) bone.pivot[2]);
        dest.translate((float) transform.posX, (float) transform.posY, (float) transform.posZ);

        dest.scale((float) transform.scaleX, (float) transform.scaleY, (float) transform.scaleZ);

        dest.rotateZ((float) (-transform.rotZ - Math.toRadians(bone.rotation[2])));
        dest.rotateY((float) (transform.rotY + Math.toRadians(bone.rotation[1])));
        dest.rotateX((float) (-transform.rotX - Math.toRadians(bone.rotation[0])));

        dest.translate((float) (-bone.pivot[0]), (float) (-bone.pivot[1]), (float) (-bone.pivot[2]));

        return hidden;
    }
}
