package teamport.aether.item.accessory.pendant;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.player.PlayerUtil;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

public class ItemIcePendant extends ItemPendant {

    public ItemIcePendant(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, ArmorMaterial.CHAINMAIL, name);
    }

    @Override
    public void tickAccessory(@NonNull ItemStack stack, @NonNull World world, @NonNull Player player, int slotId, boolean flag) {
        if (player.isInWater() || player.isSneaking()) {
            return;
        }

        freezeBlocks(stack, world, player, slotId);
    }

    public void freezeBlocks(@NonNull ItemStack stack, @NonNull World world, @NonNull Entity entity, int slotId) {
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

        int playerX = MathHelper.floor(player.x);
        int playerY = MathHelper.floor(player.y - 0.5);
        int playerZ = MathHelper.floor(player.z);

        int proc = 0;
        int radius = pendantCount;

        for (int xOffset = -radius; xOffset <= radius; xOffset++) {
            for (int zOffset = -radius; zOffset <= radius; zOffset++) {
                int xPos = playerX + xOffset;
                int zPos = playerZ + zOffset;

                Material material = world.getBlockMaterial(xPos, playerY, zPos);
                if (material == Materials.WATER) {
                    proc++;
                    world.setBlockWithNotify(xPos, playerY, zPos, Blocks.ICE.id());
                } else if (material == Materials.LAVA) {
                    proc++;
                    world.setBlockWithNotify(xPos, playerY, zPos, Blocks.OBSIDIAN.id());
                }
            }
        }
        if (proc > 0) {
            damagePendant(stack, player);
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
