package bta.aether.entity.renderer;

import bta.aether.entity.AetherEntities;
import bta.aether.entity.EntityMoa;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

public class MoaRenderer extends LivingRenderer<EntityMoa> {

    public MoaRenderer(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.setRenderPassModel(AetherEntities.modelMoa);
    }

    protected float getWingRotation(EntityMoa entity, float f) {
        float f1 = entity.field_756_e + (entity.field_752_b - entity.field_756_e) * f;
        float f2 = entity.field_757_d + (entity.destPos - entity.field_757_d) * f;
        return (MathHelper.sin(f1 * 0.225F) + 1.0F) * f2;
    }

    protected float ticksExisted(EntityMoa entity, float partialTick) {
        return this.getWingRotation(entity, partialTick);
    }

    @Override
    protected void preRenderCallback(EntityMoa entity, float f) {
        GL11.glScalef(1.0f, 1.0f, 1.0f);
    }

}
