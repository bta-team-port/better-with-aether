package teamport.aether.items.accessory.pendant;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

public class ItemIcePendant extends ItemPendant {

    public ItemIcePendant(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name, ArmorMaterial.IRON);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
            slotId < player.inventory.mainInventory.length
                || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT
                || player.isInWater()
                || player.isSneaking()
        ) {
            return;
        }
        Vec3 playerPos = Vec3.getPermanentVec3(player.x, player.y - player.bbHeight, player.z);
        Vec3 playerNextPos = Vec3.getPermanentVec3(player.x + player.xd, player.y + player.yd - player.bbHeight - 1, player.z + player.zd);
        HitResult hits = world.checkBlockCollisionBetweenPoints(playerPos, playerNextPos, true);
        if (hits == null || hits.hitType == HitResult.HitType.ENTITY) return;
        Material material = world.getBlockMaterial(hits.x, hits.y, hits.z);
        int x = MathHelper.ceil(hits.x);
        int y = MathHelper.ceil(hits.y);
        int z = MathHelper.ceil(hits.z);
        int proc = 0;
        for (int radius = -1; radius <= 1; radius++) {
            for (int depth = -1; depth <= 1; depth++) {
                int xPos = x + radius;
                int zPos = z + depth;
                if (material == Material.water) {
                    proc++;
                    world.setBlockWithNotify(xPos, y, zPos, Blocks.ICE.id());
                } else if (material == Material.lava) {
                    proc++;
                    world.setBlockWithNotify(xPos, y, zPos, Blocks.OBSIDIAN.id());
                }
            }
        }
        if (proc > 0) {
            damagePendant(stack, player);
        }
    }

    private void damagePendant(ItemStack stack, Player player) {
        stack.damageItem(1, player);
        if (player.inventory.armorInventory[TRINKET_1_SLOT] == stack) {
            if (stack.stackSize <= 0) {
                player.inventory.armorInventory[TRINKET_1_SLOT] = null;
                return;
            }
        }

        if (player.inventory.armorInventory[TRINKET_2_SLOT] == stack) {
            if (stack.stackSize <= 0) {
                player.inventory.armorInventory[TRINKET_2_SLOT] = null;
            }
        }
    }
}
