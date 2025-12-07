package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import teamport.aether.achievements.AetherAchievements;

public class BlockLogicDungeon extends BlockLogic {
    public BlockLogicDungeon(Block<?> block, Material material) {
        super(block, material);
    }

    @Override
    public boolean collidesWithEntity(Entity entity, World world, int x, int y, int z) {
        if (entity instanceof Player) {
            ((Player) entity).triggerAchievement(AetherAchievements.WEVE_GOT_HOSTILES);
        }

        return super.collidesWithEntity(entity, world, x, y, z);
    }
}
