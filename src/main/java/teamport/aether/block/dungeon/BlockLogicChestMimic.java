package teamport.aether.block.dungeon;

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
import net.minecraft.core.item.ItemLabel;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.block.entity.TileEntityMimic;
import teamport.aether.entity.monster.mimic.MimicEntry;
import teamport.aether.entity.monster.mimic.MimicRegistry;
import teamport.aether.entity.monster.mimic.MobMimic;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BlockLogicChestMimic extends BlockLogicRotatable {

    private double dx;
    private double dy;
    private double dz;
    public static final int COLOR_MASK = 0b11110000;

    public BlockLogicChestMimic(Block<?> block, Material material) {
        super(block, material);
        block.withEntity(TileEntityMimic::new);
    }

    @Override
    public void onBlockPlacedByMob(World world, int x, int y, int z, @NonNull Side side, Mob mob, double xPlaced, double yPlaced) {
        int metadata = world.getBlockMetadata(x, y, z);
        Direction direction = mob.getHorizontalPlacementDirection(side).getOpposite();
        metadata = getMetaWithDirection(metadata, direction);
        ItemStack stack = mob.getHeldItem();
        if (stack != null && stack.getItem() instanceof ItemBlock<?>) {
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
        return (meta & COLOR_MASK) | (direction.ordinal() & 3);
    }

    @SuppressWarnings("java:S128")
    @Override
    public @Nullable ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        if (tileEntity == null) {
            tileEntity = world.getTileEntity(x, y, z);
        }
        switch (dropCause) {
            case EXPLOSION:
            case PISTON_CRUSH:
                return dropAsDefeatedMimic(world, x, y, z, meta, tileEntity);
            case SILK_TOUCH:
            case PICK_BLOCK:
                return dropAsBlock(meta, tileEntity);
            case WORLD:
            case PROPER_TOOL:
            case IMPROPER_TOOL:
                if (!world.getDifficulty().canHostileMobsSpawn()) {
                    return dropAsDefeatedMimic(world, x, y, z, meta, tileEntity);
                }
            default:
                triggerMimic(world, x, y, z, meta, tileEntity);
        }
        return null;
    }

    @SuppressWarnings("java:S2259")
    @Override
    public void onActivatorInteract(World world, int x, int y, int z, TileEntityActivator activator, Direction direction) {
        MobMimic mimic = summonMimic(world, x, y, z);
        moveToSafe(world, mimic, x, y, z, 0, 0);

        world.setBlockWithNotify((int) Math.round(dx), (int) Math.round(dy), (int) Math.round(dz), 0);
        world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, dx, dy, dz, "random.door_open", 1.0f, 0.5f);

        ParticleMaker.spawnParticle(world,
            "explode",
            dx, dy, dz,
            0.0, 0.0, 0.0,
            0
        );
    }

    @SuppressWarnings({"java:S3516", "java:S2259"})
    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        ItemStack held = player.getHeldItem();
        if (held != null && held.getItem() instanceof ItemLabel) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityMimic) {
                ((TileEntityMimic) tileEntity).setCustomName(held.getCustomName(), (byte) Math.max(held.getCustomColor(), 0));
                return true;
            }
        }
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
            if (!EnvironmentHelper.isServerEnvironment()) ParticleMaker.spawnParticle(world,
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

    private @NonNull ItemStack[] dropAsDefeatedMimic(World world, int x, int y, int z, int meta, TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityMimic) {
            ((TileEntityMimic) tileEntity).dropContentForced(world, x, y, z);
        }
        MimicEntry variant = MimicRegistry.getMimicVariantByMimicChest(this.id(), meta & COLOR_MASK);
        return new ItemStack[]{new ItemStack(variant.getChestID(), 1, variant.getChestMetadata())};
    }

    private @Nullable MobMimic summonMimic(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return summonMimic(world, tileEntity, x, y, z, metadata);
    }

    @SuppressWarnings("java:S2259")
    private void triggerMimic(World world, int x, int y, int z, int meta, TileEntity tileEntity) {
        MobMimic mimic = summonMimic(world, tileEntity, x, y, z, meta);
        world.playSoundEffect(mimic, SoundCategory.ENTITY_SOUNDS, x + 0.5, y + 0.5, z + 0.5, "random.door_open", 1.0f, 0.5f);
        world.setBlockWithNotify(x, y, z, 0);
        if (!EnvironmentHelper.isServerEnvironment()) ParticleMaker.spawnParticle(world,
            "explode",
            x + 0.5, y + 1.0, z + 0.5,
            0.0, 0.0, 0.0,
            0
        );

        Player player = world.getClosestPlayer(x, y, z, 16);
        if (player != null) {
            moveToSafe(world, mimic, x, y, z, player.xRot - 180, player.xRot - 180);
            player.triggerAchievement(AetherAchievements.ITS_A_TRAP);
        } else {
            Objects.requireNonNull(mimic).absMoveTo(x + 0.5, y, z + 0.5, mimic.yRot, mimic.xRot);
        }
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
        mimic.setBlockData(variant.getMimicChestID(), variant.getMimicChestMetadata());
        if (tileEntity instanceof TileEntityMimic) {
            mimic.setNickname(((TileEntityMimic) tileEntity).getNickName());
            mimic.setChatColor(((TileEntityMimic) tileEntity).getChatColor());
        }
        world.entityJoinedWorld(mimic);
        return mimic;
    }

    private @NonNull ItemStack[] dropAsBlock(int meta, TileEntity tileEntity) {
        ItemStack result = new ItemStack(this.block, 1, meta & COLOR_MASK);
        CompoundTag data = result.getData();
        CompoundTag mimicData = new CompoundTag();
        if (tileEntity != null) {
            if (tileEntity instanceof TileEntityMimic) {
                TileEntityMimic mimicEntity = (TileEntityMimic) tileEntity;
                result.setCustomColor(mimicEntity.getChatColor());
                result.setCustomName(mimicEntity.getNickName());
            }
            tileEntity.writeToNBT(mimicData);
        }
        data.putCompound("loot", mimicData);
        result.setData(data);
        return new ItemStack[]{result};
    }

    private List<ItemStack> getAndClearInventory(TileEntity tileEntity) {
        if (!(tileEntity instanceof TileEntityMimic)) {
            return Collections.emptyList();
        }
        Container inv = (Container) tileEntity;
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            stacks.add(inv.getItem(i));
            inv.setItem(i, null);
        }
        return stacks;
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
            this.dy = y + 1.0;
            this.dz = z;
            return;
        }
        mob.moveTo(x + 0.5F, y + 1.0, z + 0.5F, yRot, xRot);
        this.dx = x + 0.5F;
        this.dy = y + 1.0;
        this.dz = z + 0.5F;
    }

    private boolean isSafe(World world, int x, int y, int z) {
        return !world.isBlockNormalCube(x, y, z) && !world.isBlockNormalCube(x, y + 1, z);
    }

}
