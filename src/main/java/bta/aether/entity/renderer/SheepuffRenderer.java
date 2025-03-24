package bta.aether.entity.renderer;

import bta.aether.entity.EntitySheepuff;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.animal.EntitySheep;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class SheepuffRenderer extends LivingRenderer<EntitySheepuff> {
    private ModelBase wool;
    private ModelBase puffed;
    public SheepuffRenderer(ModelBase modelbase, ModelBase modelbase1, ModelBase modelbase2, float f) {
        super(modelbase1, f);
        this.setRenderPassModel(modelbase);
        this.wool = modelbase;
        this.puffed = modelbase2;
    }

    protected boolean setWoolColorAndRender(EntitySheepuff entitysheep, int i, float f) {
        if (i == 0 && !entitysheep.getSheared()) {
            if (entitysheep.getPuffed()) {
                this.setRenderPassModel(this.puffed);
                this.loadTexture("/assets/aether/mobs/sheepuff/sheepuff_fur.png");
            } else {
                this.setRenderPassModel(this.wool);
                this.loadTexture("/assets/aether/mobs/sheepuff/sheepuff_fur.png");
            }

            float f1 = entitysheep.getBrightness(f);
            int j = entitysheep.getFleeceColor();
            GL11.glColor3f(f1 * EntitySheep.fleeceColorTable[j][0], f1 * EntitySheep.fleeceColorTable[j][1], f1 * EntitySheep.fleeceColorTable[j][2]);
            return true;
        } else {
            return false;
        }
    }

    protected boolean shouldRenderPass(EntitySheepuff entityliving, int i, float f) {
        return this.setWoolColorAndRender(entityliving, i, f);
    }
}
