package bta.aether.block;

import bta.aether.entity.EntityAetherBossBase;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import java.util.Random;

public class BlockDungeon extends Block {

    private final int replacementID;

    public BlockDungeon(String key, int id, Material material, int replacementID) {
        super(key, id, material);
        this.setTicking(true);
        this.replacementID = replacementID;
    }

    @Override
    public int tickRate() {
        return 1200;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        this.tryToTurn(world, x, y, z);
        super.updateTick(world, x, y, z, rand);
    }

    private double getDistanceFrom(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    private void tryToTurn(World world, int x, int y, int z){
        if (world.loadedEntityList.stream().noneMatch(entity -> entity instanceof EntityAetherBossBase && getDistanceFrom(x, y, z, entity.x, entity.y, entity.z) < 300)) {
            world.setBlockWithNotify(x, y, z, this.replacementID);
            for (int x1 = -3; x1 < 3; x1++ ) {
                for (int y1 = -3; y1 < 2; y1++) {
                    for (int z1 = -3; z1 < 3; z1++) {
                        world.scheduleBlockUpdate(x + x1, y + y1, z + z1, this.id, 1);
                    }
                }
            }

        }
    }
}
