package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.monster.MobHuman;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockLogicChestMimic extends BlockLogicRotatable {
    public BlockLogicChestMimic(Block<?> block) {
        super(block, Material.wood);
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS,x + 0.5, y, z + 0.5, "random.door_open", 1.0f, 1.0f);
        world.setBlockWithNotify(x, y, z, 0);
        MobHuman mimic = new MobHuman(world);
        mimic.spawnInit();
        mimic.moveTo(x + 0.5, y, z + 0.5, -(player.yRot), 0.0f);
        world.entityJoinedWorld(mimic);
        return true;
    }

}
