package bta.aether.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelQuadruped;

@Environment(EnvType.CLIENT)
public class ModelSheepuff3 extends ModelQuadruped {
    public ModelSheepuff3() {
        super(12, 0.0F);
        this.head = new Cube(0, 0);
        this.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
        this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
        this.body = new Cube(28, 8);
        this.body.addBox(-4.0F, -8.0F, -7.0F, 8, 16, 6, 3.75F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        float f = 0.5F;
        this.leg1 = new Cube(0, 16);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
        this.leg2 = new Cube(0, 16);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
        this.leg3 = new Cube(0, 16);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
        this.leg4 = new Cube(0, 16);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
    }
}
