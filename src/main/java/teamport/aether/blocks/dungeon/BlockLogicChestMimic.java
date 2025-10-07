package teamport.aether.blocks.dungeon;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.AetherAchievements;
import teamport.aether.entity.monster.mimic.MimicEntry;
import teamport.aether.entity.monster.mimic.MimicRegistry;
import teamport.aether.entity.monster.mimic.MobMimic;
import teamport.aether.entity.tile.TileEntityMimic;
import teamport.aether.helper.ParticleHelper;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.ArrayList;
import java.util.List;

public class BlockLogicChestMimic extends BlockLogicRotatable{

    private double dx;
    private double dy;
    private double dz;
    public static final int MASK_VARIANT = 0b11111;

    public BlockLogicChestMimic(Block<?> block, Material material) {
        super(block, material);
        block.withEntity(TileEntityMimic::new);
    }

    @Override
    public void onBlockPlacedByMob(World world, int x, int y, int z, @NotNull Side side, Mob mob, double xPlaced, double yPlaced) {
        int metadata = world.getBlockMetadata(x, y, z);
        Direction direction = mob.getHorizontalPlacementDirection(side).getOpposite();
        metadata = getMetaWithDirection(metadata, direction);
        ItemStack stack = mob.getHeldItem();
        if (stack != null && stack.getItem() instanceof ItemBlock<?>) {
            metadata = metadata | stack.getMetadata();
            CompoundTag loot = stack.getData().getCompound("loot");
            TileEntityChest chest = new TileEntityMimic();
            chest.readFromNBT(loot);
            world.setTileEntity(x, y, z, chest);
        }
        world.setBlockMetadataWithNotify(x, y, z, metadata);
    }

    @Override
    public int getPlacedBlockMetadata(@Nullable Player player, ItemStack stack, World world, int x, int y, int z, Side side, double xPlaced, double yPlaced) {
        return stack.getMetadata();
    }

    public static int getMetaWithDirection(int meta, Direction direction) {
        if (direction == null) {
            return meta;
        }
        meta &= -4;
        meta |= direction.ordinal() & 3;
        return meta;
    }


    private @Nullable MobMimic summonMimic(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return summonMimic(world, tileEntity, x, y, z, metadata);
    }

    private @Nullable MobMimic summonMimic(World world, TileEntity tileEntity, int x, int y, int z, int metadata) {
        if (EnvironmentHelper.isClientWorld()) return null;
        List<ItemStack> chestInv = getAndClearInventory(tileEntity);
        MimicEntry variant = MimicRegistry.getMimicVariantByMimicChest(this.id(), metadata & 240);
        MobMimic mimic = new MobMimic(world);
        mimic.setPos(x + 0.5, y, z + 0.5);
        mimic.setLoot(chestInv);
        mimic.spawnInit();
        mimic.setSkinVariant(variant.getMimicVariant());
        mimic.setBlockData(variant.getMimicChestId(), variant.getMimicChestMetadata());
        if(tileEntity instanceof TileEntityMimic){
            mimic.setNickname(((TileEntityMimic) tileEntity).getNickName());
            mimic.setChatColor(((TileEntityMimic) tileEntity).getChatColor());
        }
        world.entityJoinedWorld(mimic);
        return mimic;
    }

    @Override
    public ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                if (tileEntity == null) {
                    return null;
                }
                ItemStack result = new ItemStack(this.block, 1, meta & (MASK_VARIANT << 3));
                CompoundTag data = result.getData();
                CompoundTag mimicData = new CompoundTag();
                tileEntity.writeToNBT(mimicData);
                data.putCompound("loot", mimicData);
                result.setData(data);
                return new ItemStack[]{result};
            default:
                MobMimic mimic = summonMimic(world, tileEntity, x, y, z, meta);
                world.playSoundEffect(mimic, SoundCategory.ENTITY_SOUNDS, x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);
                world.setBlockWithNotify(x, y, z, 0);
                if (!EnvironmentHelper.isServerEnvironment()) ParticleHelper.spawnParticle(world,
                        "explode",
                        x + 0.5, y + 1, z + 0.5,
                        0.0, 0.0, 0.0,
                        0
                );

                Player player = world.getClosestPlayer(x, y, z, 16);
                if (player != null) {
                    moveToSafe(world, mimic, x, y, z, player.xRot - 180, player.xRot - 180);
                    player.triggerAchievement(AetherAchievements.ITS_A_TRAP);
                } else mimic.absMoveTo(x + 0.5, y, z + 0.5, mimic.yRot, mimic.xRot);
        }

        return null;
    }

    /// To prevent player from removing them in peaceful
    @Override
    public float blockStrength(World world, int x, int y, int z, Side side, Player player) {
        if (world.getDifficulty().canHostileMobsSpawn()) {
            return super.blockStrength(world, x, y, z, side, player);
        }
        return -1;
    }

    @Override
    public void onBlockRemoved(World world, int x, int y, int z, int data) {
    }

    private List<ItemStack> getAndClearInventory(TileEntity tileEntity) {
        if (!(tileEntity instanceof TileEntityMimic)) {
            return null;
        }
        Container inv = (Container) tileEntity;
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            stacks.add(inv.getItem(i));
            inv.setItem(i, null);
        }
        return stacks;
    }

    @Override
    public void onActivatorInteract(World world, int x, int y, int z, TileEntityActivator activator, Direction direction) {
        MobMimic mimic = summonMimic(world, x, y, z);
        moveToSafe(world, mimic, x, y, z, 0, 0);

        world.setBlockWithNotify((int) Math.round(dx), (int) Math.round(dy), (int) Math.round(dz), 0);
        world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, dx, dy, dz, "random.door_open", 1.0f, 0.5f);

        if (!EnvironmentHelper.isServerEnvironment()) ParticleHelper.spawnParticle(world,
                "explode",
                dx, dy, dz,
                0.0, 0.0, 0.0,
                0
        );
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        if (player.gamemode == Gamemode.creative) {
            ItemStack stack = player.getHeldItem();
            if (stack == null || !stack.getItem().hasTag(ItemTags.PREVENT_CREATIVE_MINING)) {
                player.displayChestScreen(BlockLogicChest.getInventory(world, x, y, z), x, y, z);
                return true;
            }
        }
        if (world.getDifficulty().canHostileMobsSpawn()) {
            if (!EnvironmentHelper.isClientWorld()) {
                MobMimic mimic = summonMimic(world, x, y, z);
                moveToSafe(world, mimic, x, y, z, player.xRot - 180, player.xRot - 180);
            }
            player.triggerAchievement(AetherAchievements.ITS_A_TRAP);
            world.setBlockWithNotify(x, y, z, 0);
            world.playSoundEffect(player, SoundCategory.ENTITY_SOUNDS, x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);
            if (!EnvironmentHelper.isServerEnvironment()) ParticleHelper.spawnParticle(world,
                    "explode",
                    dx, dy, dz,
                    0.0, 0.0, 0.0,
                    0
            );
            return true;
        }
        player.displayChestScreen(BlockLogicChest.getInventory(world, x, y, z), x, y, z);
        return true;
    }

    // not sure if this is the correct place for these functions
    private void moveToSafe(World world, Mob mob, int x, int y, int z, float yRot, float xRot) {
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

    @Override
    public String getLanguageKey(int meta) {
        return super.getLanguageKey(meta);
    }


    /// ############################ thing that have to go ###########################################

    public static int getVariantFromMeta(int meta) {
        return (meta >> 3) & MASK_VARIANT;
    }

    public static int setVariantToMeta(int meta, int variant) {
        return (meta & ~(MASK_VARIANT << 3)) | ((variant & MASK_VARIANT) << 3);
    }

}
