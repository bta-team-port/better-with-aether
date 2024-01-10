package bta.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;

public class BlockQuicksoil extends Block {
    public BlockQuicksoil(String key, int id, Material material) {
        super(key, id, material);
        this.movementScale = 1.1f;
    }
}
