package teamport.aether.entity.animal.aerwhale;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelAerwhale extends ModelBase {
    public Cube body;
    public Cube body2;
    public Cube body3;
    public Cube fin1;
    public Cube fin2;
    public Cube fin3;
    public Cube fin4;

    public ModelAerwhale() {
        this.body2 = new Cube(0, 0);
        this.body2.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5);
        this.body3 = new Cube(0, 10);
        this.body3.addBox(-1.5F, -1.5F, 2.5F, 3, 3, 4);
        this.fin1 = new Cube(0, 17);
        this.fin1.addBox(-7.5F, -0.5F, 2.49F, 8, 1, 4);
        this.fin2 = new Cube(0, 17);
        this.fin2.addBox(-0.5F, -0.5F, 2.49F, 8, 1, 4);
        this.fin3 = new Cube(0, 22);
        this.fin3.addBox(-7.5F, 1.5F, -6.5F, 4, 1, 2);
        this.fin4 = new Cube(0, 22);
        this.fin4.addBox(3.5F, 1.5F, -6.5F, 4, 1, 2);
        this.body = new Cube(20, 0);
        this.body.addBox(-3.5F, -3.5F, -12.5F, 7, 6, 10);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.body.render(scale);
        this.body2.render(scale);
        this.body3.render(scale);
        this.fin1.render(scale);
        this.fin2.render(scale);
        this.fin3.render(scale);
        this.fin4.render(scale);
    }
}
