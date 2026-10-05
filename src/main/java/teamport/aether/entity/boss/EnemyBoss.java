package teamport.aether.entity.boss;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.MaterialLiquid;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import teamport.aether.block.dungeon.BlockLogicChestLocked;
import teamport.aether.block.dungeon.BlockLogicDungeonDoor;
import teamport.aether.block.dungeon.BlockLogicLocked;
import teamport.aether.world.feature.util.WorldFeatureBlock;
import teamport.aether.world.feature.util.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.List;

public interface EnemyBoss {

    void setDungeonID(int id);

    String getTranslatedBossTitle();

    String getBossTitleKey();

    String getBossName();

    byte getBossColor();

    void returnToHome();

    void setReturnPoint(WorldFeaturePoint coord);

    boolean canFight();

    void setTrophy(ItemStack itemStack);

    @SuppressWarnings("unused")
    ItemStack getTrophy();


    static boolean cannotBreakBlock(Block<?> block) {
        return block.getLogic() instanceof BlockLogicLocked ||
            block.getLogic() instanceof BlockLogicDungeonDoor ||
            block.getLogic() instanceof BlockLogicChestLocked ||
            block.getMaterial() instanceof MaterialLiquid ||
            block.getHardness() <= 0;
    }

    static List<WorldFeatureBlock> blockCollidingWithAABB(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        List<WorldFeatureBlock> blockInAABB = new ArrayList<>();
        TilePos tilePos = new TilePos(0, 0, 0);
        for (tilePos.x = minX; tilePos.x <= maxX; ++tilePos.x) {
            for (tilePos.z = minZ; tilePos.z <= maxZ; ++tilePos.z) {
                for (tilePos.y = minY; tilePos.y <= maxY; ++tilePos.y) {
                    Block<?> block = world.getBlockType(tilePos);
                    if (EnemyBoss.cannotBreakBlock(block) || block == Blocks.AIR) {
                        continue;
                    }
                    blockInAABB.add(WorldFeatureBlock.wfb(WorldFeaturePoint.wfp(tilePos), block.id()));
                }
            }
        }
        return blockInAABB;
    }


}
