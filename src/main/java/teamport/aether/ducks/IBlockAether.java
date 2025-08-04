package teamport.aether.ducks;

import net.minecraft.core.block.Block;

public interface IBlockAether {
    void better_with_aether$setEmissionOverride(int emission);
    int better_with_aether$getEmissionOverride();
    static IBlockAether of(Block<?> block) {
        return (IBlockAether)(Object)(block);
    }
}
