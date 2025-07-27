package teamport.aether.entity.phyg;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;

public class MobRendererPhyg extends MobRenderer<MobPhyg> {
    public MobRendererPhyg(ModelBase modelbase, float f) {
        super(modelbase, f);
        this.setArmorModel(modelbase);
    }

    public boolean renderSaddledPig(MobPhyg entity, int i, float f) {
        this.bindTexture("/assets/aether/textures/entity/phyg/saddle.png");
        return i == 0 && entity != null && entity.getSaddled();
    }

    public float limbSway(MobPhyg pig, float partialTick) {
        float wingBend = -((float)Math.acos(pig.wingFold));
        float x = 32.0F * pig.wingFold / 4.0F;
        float y = -32.0F * (float)Math.sqrt(1.0F - pig.wingFold * pig.wingFold) / 4.0F;
        float z = 0.0F;
        float x2 = x * (float)Math.cos(pig.wingAngle) - y * (float)Math.sin(pig.wingAngle);
        float y2 = x * (float)Math.sin(pig.wingAngle) + y * (float)Math.cos(pig.wingAngle);
        ModelPhyg.leftWingInner.setRotationPoint(4.0F + x2, y2 + 12.0F, z);
        ModelPhyg.rightWingInner.setRotationPoint(-4.0F - x2, y2 + 12.0F, z);
        x *= 3.0F;
        x2 = x * (float)Math.cos(pig.wingAngle) - y * (float)Math.sin(pig.wingAngle);
        y2 = x * (float)Math.sin(pig.wingAngle) + y * (float)Math.cos(pig.wingAngle);

        ModelPhyg.leftWingOuter.setRotationPoint(4.0F + x2, y2 + 12.0F, z);
        ModelPhyg.rightWingOuter.setRotationPoint(-4.0F - x2, y2 + 12.0F, z);
        ModelPhyg.leftWingInner.zRot = pig.wingAngle + wingBend + 1.5707964F;
        ModelPhyg.leftWingOuter.zRot = pig.wingAngle - wingBend + 1.5707964F;
        ModelPhyg.rightWingInner.zRot = -(pig.wingAngle + wingBend - 1.5707964F);
        ModelPhyg.rightWingOuter.zRot = -(pig.wingAngle - wingBend + 1.5707964F);
        return wingBend;
    }

    public boolean prepareArmor(MobPhyg entity, int renderPass, float partialTick) {
        return this.renderSaddledPig(entity, renderPass, partialTick);
    }
}
