package teamport.aether.entity.monster.aechorplant;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class ModelAechorPlant extends ModelBase {
    private static final int PETALS = 10;
    private static final int THORNS = 4;
    private static final int STAMENS = 3;

    private float sinage;
    private float sinage2;
    private float size;
    private final Cube[] petal;
    private final Cube[] leaf;
    private final Cube[] stamen;
    private final Cube[] stamen2;
    private final Cube[] thorn;
    private final Cube stem;
    private final Cube head;
    private final float pie;

    public ModelAechorPlant() {
        this(0.0F);
    }

    public ModelAechorPlant(float f) {
        this(f, 0.0F);
    }

    public ModelAechorPlant(float f, float f1) {
        this.pie = 6.283186F;
        this.size = 1.0F;
        this.petal = new Cube[PETALS];
        this.leaf = new Cube[PETALS];

        int i;
        for (i = 0; i < PETALS; ++i) {
            this.petal[i] = new Cube(0, 0);
            if (i % 2 == 0) {
                this.petal[i] = new Cube(29, 3);
                this.petal[i].addBox(-4.0F, -1.0F, -12.0F, 8, 0, 9, f);
                this.petal[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
            } else {
                this.petal[i].addBox(-4.0F, -1.0F, -13.0F, 8, 0, 10, f);
                this.petal[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
            }

            this.leaf[i] = new Cube(38, 13);
            this.leaf[i].addBox(-2.0F, -1.0F, -9.5F, 4, 1, 8, f);
            this.leaf[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        }

        this.stamen = new Cube[STAMENS];
        this.stamen2 = new Cube[STAMENS];

        for (i = 0; i < STAMENS; ++i) {
            this.stamen[i] = new Cube(36, 13);
            this.stamen[i].addBox(0.0F, -9.0F, -1.5F, 1, 6, 0, f);
            this.stamen[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        }

        for (i = 0; i < STAMENS; ++i) {
            this.stamen2[i] = new Cube(32, 15);
            this.stamen2[i].addBox(0.0F, -8.1F, -2.0F, 1, 0, 1, f);
            this.stamen2[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        }

        this.head = new Cube(0, 12);
        this.head.addBox(-3.0F, -3.0F, -3.0F, 6, 2, 6, f + 0.75F);
        this.head.setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        this.stem = new Cube(24, 13);
        this.stem.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, f);
        this.stem.setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        this.thorn = new Cube[THORNS];

        for (i = 0; i < THORNS; ++i) {
            this.thorn[i] = new Cube(32, 13);
            this.thorn[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        }

        this.thorn[0].addBox(-1.75F, 1.25F, -1.0F, 1, 0, 1, f);
        this.thorn[1].addBox(-1.0F, 2.25F, 0.75F, 1, 0, 1, f);
        this.thorn[2].addBox(0.75F, 1.25F, 0.0F, 1, 0, 1, f);
        this.thorn[3].addBox(0.0F, 2.25F, -1.75F, 1, 0, 1, f);
    }

    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f4);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.2F, 0.0F);
        GL11.glScalef(this.size, this.size, this.size);

        int i;
        for (i = 0; i < PETALS; ++i) {
            this.petal[i].render(f5);
            this.leaf[i].render(f5);
        }

        for (i = 0; i < STAMENS; ++i) {
            this.stamen[i].render(f5);
            this.stamen2[i].render(f5);
        }

        this.head.render(f5);
        this.stem.render(f5);

        for (i = 0; i < THORNS; ++i) {
            this.thorn[i].render(f5);
        }

        GL11.glPopMatrix();
    }

    private void setAngles(float f4) {
        this.head.xRot = 0.0F;
        this.head.yRot = f4 / 57.29578F;
        float boff = this.sinage2;
        this.stem.yRot = this.head.yRot;
        this.stem.y = boff * 0.5F;

        int i;
        for (i = 0; i < THORNS; ++i) {
            this.thorn[i].yRot = this.head.yRot;
            this.thorn[i].y = boff * 0.5F;
        }

        Cube var10000;
        for (i = 0; i < PETALS; ++i) {
            this.petal[i].xRot = i % 2 == 0 ? -0.25F : -0.4125F;
            var10000 = this.petal[i];
            var10000.xRot += this.sinage;
            this.petal[i].yRot = this.head.yRot;
            var10000 = this.petal[i];
            var10000.yRot += this.pie / PETALS * i;
            this.leaf[i].xRot = i % 2 == 0 ? 0.1F : 0.2F;
            var10000 = this.leaf[i];
            var10000.xRot += this.sinage * 0.75F;
            this.leaf[i].yRot = this.head.yRot + this.pie / PETALS / 2.0F;
            var10000 = this.leaf[i];
            var10000.yRot += this.pie / PETALS * i;
            this.petal[i].y = boff;
            this.leaf[i].y = boff;
        }

        for (i = 0; i < STAMENS; ++i) {
            this.stamen[i].xRot = 0.2F + i / 15.0F;
            this.stamen[i].yRot = this.head.yRot + 0.1F;
            var10000 = this.stamen[i];
            var10000.yRot += this.pie / STAMENS * i;
            var10000 = this.stamen[i];
            var10000.xRot += this.sinage * 0.4F;
            this.stamen2[i].xRot = 0.2F + i / 15.0F;
            this.stamen2[i].yRot = this.head.yRot + 0.1F;
            var10000 = this.stamen2[i];
            var10000.yRot += this.pie / STAMENS * i;
            var10000 = this.stamen2[i];
            var10000.xRot += this.sinage * 0.4F;
            this.stamen[i].y = boff + this.sinage * 2.0F;
            this.stamen2[i].y = boff + this.sinage * 2.0F;
        }

        this.head.y = boff + this.sinage * 2.0F;
    }
    public void setSinage(float sinage) {
        this.sinage = sinage;
    }
    public void setSinage2(float sinage2) {
        this.sinage2 = sinage2;
    }
    public void setSize(float size) {
        this.size = size;
    }
}
