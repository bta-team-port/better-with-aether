package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.achievements.AetherAchievements;

public class BlockLogicDungeon extends BlockLogic {
    public BlockLogicDungeon(Block<?> block, Material material) {
        super(block, material);
    }

    @Override
    public boolean collidesWithEntity(@NonNull Entity entity, @NonNull World world, @NonNull TilePosc tilePos) {
        if (entity instanceof Player player) {
            player.triggerAchievement(AetherAchievements.WEVE_GOT_HOSTILES);
        }

        return super.collidesWithEntity(entity, world, tilePos);
    }
}
