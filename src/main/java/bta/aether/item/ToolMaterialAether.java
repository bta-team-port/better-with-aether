package bta.aether.item;

import net.minecraft.core.item.material.ToolMaterial;

public class ToolMaterialAether extends ToolMaterial {
    private int dropMultipier = 1;
    public int getDropMultipier(){
        return dropMultipier;
    }
    public ToolMaterialAether setDropMultipier(int multipier) {
        this.dropMultipier = multipier;
        return this;
    }
}
