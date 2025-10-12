package teamport.aether.entity.animal.phow;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;

@Environment(EnvType.CLIENT)
public class MobRendererPhow extends MobRenderer<MobPhow> {
    public MobRendererPhow(ModelBase modelbase, float shadowSize) {
        super(modelbase, shadowSize);
        this.setArmorModel(modelbase);
    }

    public float limbSway(MobPhow cow, float partialTick) {
        float wingFold = MathHelper.lerp(cow.wingFoldO, cow.wingFold, partialTick);
        float wingAngle = MathHelper.lerp(cow.wingAngleO, cow.wingAngle, partialTick);

        float wingBend = -((float) Math.acos(wingFold));
        float x = 32.0F * wingFold / 4.0F;
        float y = -32.0F * (float) Math.sqrt(1.0F - wingFold * wingFold) / 4.0F;
        float z = 0.0F;
        float x2 = x * (float) Math.cos(wingAngle) - y * (float) Math.sin(wingAngle);
        float y2 = x * (float) Math.sin(wingAngle) + y * (float) Math.cos(wingAngle);
        ModelPhow.leftWingInner.setRotationPoint(4.0F + x2, y2 + 6.0F, z);
        ModelPhow.rightWingInner.setRotationPoint(-4.0F - x2, y2 + 6.0F, z);
        x *= 3.0F;
        x2 = x * (float) Math.cos(wingAngle) - y * (float) Math.sin(wingAngle);
        y2 = x * (float) Math.sin(wingAngle) + y * (float) Math.cos(wingAngle);

        ModelPhow.leftWingOuter.setRotationPoint(4.0F + x2, y2 + 6.0F, z);
        ModelPhow.rightWingOuter.setRotationPoint(-4.0F - x2, y2 + 6.0F, z);
        ModelPhow.leftWingInner.zRot = wingAngle + wingBend + 1.5707964F;
        ModelPhow.leftWingOuter.zRot = wingAngle - wingBend + 1.5707964F;
        ModelPhow.rightWingInner.zRot = -(wingAngle + wingBend - 1.5707964F);
        ModelPhow.rightWingOuter.zRot = -(wingAngle - wingBend + 1.5707964F);
        return wingBend;
    }

    public boolean renderSaddledPhow(MobPhow entity, int i, float f) {
        this.bindTexture("/assets/aether/textures/entity/phow/phow_saddle.png");
        return i == 0 && entity != null && entity.getSaddled();
    }

    public boolean prepareArmor(MobPhow entity, int renderPass, float partialTick) {
        return this.renderSaddledPhow(entity, renderPass, partialTick);
    }
}
