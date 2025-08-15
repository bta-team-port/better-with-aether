package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.AetherAchievements;
import teamport.aether.entity.monster.mimic.MobMimic;

import java.util.ArrayList;
import java.util.List;

public class BlockLogicChestMimic extends BlockLogicChest {

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
                List<ItemStack> chestInv = getAndClearInventory(world, x, y, z);
                mimic.setLoot(chestInv);
                world.playSoundEffect(mimic, SoundCategory.ENTITY_SOUNDS,x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);
                world.setBlockWithNotify(x, y, z, 0);
                mimic.spawnInit();
                Player player = world.getClosestPlayer(x, y, z, 16);
                if (player != null) {
                    mimic.absMoveTo(x + 0.5, y, z + 0.5, (player.yRot) - 180F, -player.xRot);
                    player.triggerAchievement(AetherAchievements.ITS_A_TRAP);
                }
                else mimic.absMoveTo(x + 0.5, y, z + 0.5, mimic.yRot, mimic.xRot);
                world.spawnParticle("explode", x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0, 0);
                world.entityJoinedWorld(mimic);
        }
        return null;
    }

    private List<ItemStack> getAndClearInventory(World world, int x, int y, int z) {
        Container inv = getInventory(world, x, y, z);
        if (inv == null) {
            return null;
        }
        List<ItemStack> stacks = new ArrayList<>();
        for(int i = 0; i < inv.getContainerSize(); i++){
            stacks.add(inv.getItem(i));
            inv.setItem(i, null);
        }
        return stacks;
    }

    public void onActivatorInteract(World world, int x, int y, int z, TileEntityActivator activator, Direction direction) {
        world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS,x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);
        List<ItemStack> chestInv = getAndClearInventory(world, x, y, z);
        world.setBlockWithNotify(x, y, z, 0);
        MobMimic mimic = new MobMimic(world);
        mimic.setLoot(chestInv);
        mimic.spawnInit();
        mimic.absMoveTo(x + 0.5, y, z + 0.5, (mimic.yRot) - 180.0f, -(mimic.xRot));
        world.spawnParticle("explode", x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0, 0);
        world.entityJoinedWorld(mimic);
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        List<ItemStack> chestInv = getAndClearInventory(world, x, y, z);
        world.setBlockWithNotify(x, y, z, 0);
        if (!world.isClientSide) {
            MobMimic mimic = new MobMimic(world);
            mimic.setLoot(chestInv);
            mimic.spawnInit();
            mimic.absMoveTo(x + 0.5, y, z + 0.5, (player.yRot) - 180.0f, -(player.xRot));
            world.entityJoinedWorld(mimic);
        }
        world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);

        world.spawnParticle("explode", x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0, 0);

        player.triggerAchievement(AetherAchievements.ITS_A_TRAP);

        return true;
    }

}
