package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.monster.MobHuman;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.mimic.MobMimic;

public class BlockLogicChestMimic extends BlockLogicRotatable {
    public BlockLogicChestMimic(Block<?> block) {
        super(block, Material.wood);
    }

    public ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {

        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                return new ItemStack[]{new ItemStack(this)};
            default:
                MobMimic mimic = new MobMimic(world);
                world.playSoundEffect(mimic, SoundCategory.ENTITY_SOUNDS,x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);
                world.setBlockWithNotify(x, y, z, 0);
                mimic.spawnInit();
                Player player = world.getClosestPlayer(x, y, z, 16);
                if (player != null) {
                    mimic.absMoveTo(x + 0.5, y, z + 0.5, (player.yRot) - 180F, -player.xRot);
                }
                else mimic.absMoveTo(x + 0.5, y, z + 0.5, mimic.yRot, mimic.xRot);
                world.spawnParticle("explode", x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0, 0);
                world.entityJoinedWorld(mimic);
        }
        return null;
    }

    public void onActivatorInteract(World world, int x, int y, int z, TileEntityActivator activator, Direction direction) {
        world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS,x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);
        world.setBlockWithNotify(x, y, z, 0);
        MobMimic mimic = new MobMimic(world);
        mimic.spawnInit();
        mimic.absMoveTo(x + 0.5, y, z + 0.5, (mimic.yRot) - 180.0f, -(mimic.xRot));
        world.spawnParticle("explode", x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0, 0);
        world.entityJoinedWorld(mimic);
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        world.setBlockWithNotify(x, y, z, 0);

        if (!world.isClientSide) {
            MobMimic mimic = new MobMimic(world);
            mimic.spawnInit();
            mimic.absMoveTo(x + 0.5, y, z + 0.5, (player.yRot) - 180.0f, -(player.xRot));
            world.entityJoinedWorld(mimic);
        }
        world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);

        world.spawnParticle("explode", x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0, 0);

        return true;
    }

}
