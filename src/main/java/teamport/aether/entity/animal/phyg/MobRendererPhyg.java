package teamport.aether.entity.animal.phyg;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;

@Environment(EnvType.CLIENT)
public class MobRendererPhyg extends MobRenderer<MobPhyg> {
    public MobRendererPhyg(ModelBase modelbase, float shadowSize) {
        super(modelbase, shadowSize);
        this.setArmorModel(modelbase);
    }

    private boolean renderSaddledPig(MobPhyg entity, int i) {
        this.bindTexture("/assets/aether/textures/entity/phyg/saddle.png");
        return i == 0 && entity != null && entity.getSaddled();
    }

    @Override
    public float limbSway(MobPhyg pig, float partialTick) {
        if (!(this.mainModel instanceof ModelPhyg)) return super.limbSway(pig, partialTick);
        ModelPhyg model = (ModelPhyg) this.mainModel;
        float wingFold = MathHelper.lerp(pig.getWingFoldO(), pig.getWingFold(), partialTick);
        float wingAngle = MathHelper.lerp(pig.getWingAngleO(), pig.getWingAngle(), partialTick);

        float wingBend = -((float) Math.acos(wingFold));
        float x = 32.0F * wingFold / 4.0F;
        float y = -32.0F * (float) Math.sqrt(1.0F - wingFold * wingFold) / 4.0F;
        float z = 0.0F;
        float x2 = x * (float) Math.cos(wingAngle) - y * (float) Math.sin(wingAngle);
        float y2 = x * (float) Math.sin(wingAngle) + y * (float) Math.cos(wingAngle);
        model.getLeftWingInner().setRotationPoint(4.0F + x2, y2 + 12.0F, z);
        model.getRightWingInner().setRotationPoint(-4.0F - x2, y2 + 12.0F, z);
        x *= 3.0F;
        x2 = x * (float) Math.cos(wingAngle) - y * (float) Math.sin(wingAngle);
        y2 = x * (float) Math.sin(wingAngle) + y * (float) Math.cos(wingAngle);

        model.getLeftWingOuter().setRotationPoint(4.0F + x2, y2 + 12.0F, z);
        model.getRightWingOuter().setRotationPoint(-4.0F - x2, y2 + 12.0F, z);
        model.getLeftWingInner().zRot = wingAngle + wingBend + 1.5707964F;
        model.getLeftWingOuter().zRot = wingAngle - wingBend + 1.5707964F;
        model.getRightWingInner().zRot = -(wingAngle + wingBend - 1.5707964F);
        model.getRightWingOuter().zRot = -(wingAngle - wingBend + 1.5707964F);
        return wingBend;
    }

    @Override
    public boolean prepareArmor(MobPhyg entity, int renderPass, float partialTick) {
        return this.renderSaddledPig(entity, renderPass);
    }
}
