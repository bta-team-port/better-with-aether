package teamport.aether.item.accessory.pendant;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.player.PlayerUtil;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

public class ItemIcePendant extends ItemPendant {

    public ItemIcePendant(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, ArmorMaterial.DIAMOND, name);
    }

    @Override
    public void tickAccessory(@NonNull ItemStack stack, @NonNull World world, @NonNull Player player, int slotId, boolean flag) {
        if (player.isInWater() || player.isSneaking()) {
            return;
        }
        this.freezeBlocks(stack, world, player, slotId);
    }

    public void freezeBlocks(@NonNull ItemStack stack, @NonNull World world, @NonNull Entity entity, int slotId) {
        class Cache{private static final @NotNull TilePos queryPos = new TilePos();}
        Player player = (Player) entity;
        int relativeSlot = slotId - player.inventory.mainInventory.length;

        int activeSlot = -1;
        int pendantCount = 0;

        for (int slot : new int[]{TRINKET_1_SLOT, TRINKET_2_SLOT}) {
            ItemStack equipped = PlayerUtil.getArmorOrAccessoryItem(player, slot);
            if (equipped != null && equipped.getItem() instanceof ItemIcePendant) {
                if (activeSlot == -1) {
                    activeSlot = slot;
                }
                pendantCount++;
            }
        }

        if (relativeSlot != activeSlot) {
            return;
        }

        Vector3d playerPos = new Vector3d(player.x, player.bb.minY + player.heightOffset - player.ySlideOffset, player.z);
        Vector3d playerNextPos = new Vector3d(player.x + player.xd, player.y + player.yd, player.z + player.zd);
        HitResult hits = world.checkBlockCollisionBetweenPoints(playerPos, playerNextPos, true);
        if (!(hits instanceof HitResult.Tile tile)) return;
        int proc = 0;
        int radius = pendantCount;
        for (int xOffset = -radius; xOffset <= radius; xOffset++) {
            for (int zOffset = -radius; zOffset <= radius; zOffset++) {
                Cache.queryPos.set(tile.tilePos.x() + xOffset, tile.tilePos.y(), tile.tilePos.z() + zOffset);
                Material material = world.getBlockMaterial(Cache.queryPos);
                if (material == Materials.WATER) {
                    proc++;
                    world.setBlockTypeNotify(Cache.queryPos, Blocks.ICE);
                } else if (material == Materials.LAVA) {
                    proc++;
                    world.setBlockTypeNotify(Cache.queryPos, Blocks.OBSIDIAN);
                }
            }
        }
        if (proc > 0) {
            this.damagePendant(stack, player);
        }
    }

    public void damagePendant(@NonNull ItemStack stack, Player player) {
        stack.damageItem(1, player);
        if (PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_1_SLOT) == stack && stack.stackSize <= 0) {
            PlayerUtil.clearArmorOrAccessoryItem(player, TRINKET_1_SLOT);
            return;
        }
        if (PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_2_SLOT) == stack && stack.stackSize <= 0) {
            PlayerUtil.clearArmorOrAccessoryItem(player, TRINKET_2_SLOT);
        }
    }
}
