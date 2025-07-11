package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;

import java.util.Random;

public class BlockLogicLeavesSkyroot extends BlockLogicLeavesBase {

    public BlockLogicLeavesSkyroot(Block<?> block, Material material, Block<?> sapling) {
        super(block, material, sapling);
    }

    public void animationTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(20) == 0) {
            world.spawnParticle(AetherMod.MOD_ID + "$fallingleaf", x, (double) y - 0.10000000149011612, z, 0.0, 0.0, 0.0, 0);
        }
    }

}
