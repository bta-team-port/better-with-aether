package bta.aether.block;

public class BlockAetherTallGrass extends BlockAetherFlower {
    public BlockAetherTallGrass(String key, int id) {
        super(key, id);
        this.setTicking(true);
        float f = 0.4f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 0.8f, 0.5f + f);
    }
}
