package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Mob;
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
import teamport.aether.tile.TileEntityMimic;

import java.util.ArrayList;
import java.util.List;

import static teamport.aether.AetherMod.BWAILA;

public class BlockLogicChestMimic extends BlockLogicRotatable {
    private double dx;
    private double dy;
    private double dz;

    public BlockLogicChestMimic(Block<?> block) {
        super(block, Material.wood);
        block.withEntity(TileEntityMimic::new);
    }

    @Override
    public String getLanguageKey(int meta) {
        if (BWAILA) {
            // hides the mimic name and description
            return AetherBlocks.CHEST_PLANKS_SKYROOT.getKey();
        }
        return super.getLanguageKey(meta);
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
                world.playSoundEffect(mimic, SoundCategory.ENTITY_SOUNDS, x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);
                world.setBlockWithNotify(x, y, z, 0);
                mimic.spawnInit();
                Player player = world.getClosestPlayer(x, y, z, 16);
                if (player != null) {
                    moveToSafe(world, mimic, x, y, z, player.xRot - 180, player.xRot - 180);
                    player.triggerAchievement(AetherAchievements.ITS_A_TRAP);
                } else {
                    mimic.absMoveTo(x + 0.5, y, z + 0.5, mimic.yRot, mimic.xRot);
                }
                world.spawnParticle("explode", x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0, 0);
                world.entityJoinedWorld(mimic);
        }
        return null;
    }

    private List<ItemStack> getAndClearInventory(World world, int x, int y, int z) {
        Container inv = (Container) world.getTileEntity(x, y, z);
        if (inv == null) {
            return null;
        }
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            stacks.add(inv.getItem(i));
            inv.setItem(i, null);
        }
        return stacks;
    }

    public void onActivatorInteract(World world, int x, int y, int z, TileEntityActivator activator, Direction direction) {
        List<ItemStack> chestInv = getAndClearInventory(world, x, y, z);
        MobMimic mimic = new MobMimic(world);
        mimic.setLoot(chestInv);
        mimic.spawnInit();
        moveToSafe(world, mimic, x, y, z, 0, 0);
        world.entityJoinedWorld(mimic);
        world.setBlockWithNotify((int)Math.round(dx), (int)Math.round(dy), (int)Math.round(dz), 0);
        world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, dx, dy, dz, "random.door_open", 1.0f, 0.5f);
        world.spawnParticle("explode", dx, dy, dz, 0.0, 0.0, 0.0, 0);
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        List<ItemStack> chestInv = getAndClearInventory(world, x, y, z);
        world.setBlockWithNotify(x, y, z, 0);
        if (!world.isClientSide) {
            player.triggerAchievement(AetherAchievements.ITS_A_TRAP);
            MobMimic mimic = new MobMimic(world);
            mimic.setLoot(chestInv);
            mimic.spawnInit();
            moveToSafe(world, mimic, x, y, z, player.xRot - 180, player.xRot - 180);
            world.entityJoinedWorld(mimic);
        }
        if (player.tickCount % 10 == 0 && !BWAILA) {
            player.sendMessage("Thank you dark souls.");
        }
        world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);
        world.spawnParticle("explode", x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0, 0);
        player.triggerAchievement(AetherAchievements.ITS_A_TRAP);
        return true;
    }

    // not sure if this is the correct place for these functions
    public void moveToSafe(World world, Mob mob, int x, int y, int z, float yRot, float xRot) {
        if (this.isSafe(world, x - 1, y, z)) {
            mob.moveTo(x - 0.5F, y, z + 0.5F, yRot, xRot);
            this.dx = x - 0.5F;
            this.dy = y;
            this.dz = z + 0.5F;
            return;
        }
        if (this.isSafe(world, x + 1, y, z)) {
            mob.moveTo(x + 1.5F, y, z + 0.5F, yRot, xRot);
            this.dx = x + 1.5F;
            this.dy = y;
            this.dz = z + 0.5F;
            return;
        }
        if (this.isSafe(world, x, y, z - 1)) {
            mob.moveTo(x + 0.5F, y, z - 0.5F, yRot, xRot);
            this.dx = x + 0.5F;
            this.dy = y;
            this.dz = z - 0.5F;
            return;
        }
        if (this.isSafe(world, x, y, z + 1)) {
            mob.moveTo(x + 0.5F, y, z + 1.5F, yRot, xRot);
            this.dx = x + 0.5F;
            this.dy = y + 1;
            this.dz = z;
            return;
        }
        mob.moveTo(x + 0.5F, y + 1, z + 0.5F, yRot, xRot);
        this.dx = x + 0.5F;
        this.dy = y + 1;
        this.dz = z + 0.5F;
    }

    private boolean isSafe(World world, int x, int y, int z) {
        return !world.isBlockNormalCube(x, y, z) && !world.isBlockNormalCube(x, y + 1, z);
    }

}
