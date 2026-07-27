package teamport.aether.item.accessory.pendant;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.joml.Vector3d;
import teamport.aether.entity.player.PlayerUtil;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

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
        Vector3d playerPos = new Vector3d(player.x, player.y - player.bbHeight, player.z);
        Vector3d playerNextPos = new Vector3d(player.x + player.xd, player.y - player.bbHeight + player.yd - 1, player.z + player.zd);
        HitResult hits = world.checkBlockCollisionBetweenPoints(playerPos, playerNextPos, true);
        if (!(hits instanceof HitResult.Tile)) return;
        int x = MathHelper.ceil(hits.location.x());
        int y = MathHelper.ceil(hits.location.y());
        int z = MathHelper.ceil(hits.location.z());
        int proc = 0;
        for (int radius = -1; radius <= 1; radius++) {
            for (int depth = -1; depth <= 1; depth++) {
                int xPos = x + radius;
                int zPos = z + depth;
                Material material = world.getBlockMaterial(xPos, y, zPos);
                if (material == Materials.WATER) {
                    proc++;
                    world.setBlockWithNotify(xPos, y, zPos, Blocks.ICE.id());
                } else if (material == Materials.LAVA) {
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
        if (PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_1_SLOT) == stack && stack.stackSize <= 0) {
            PlayerUtil.clearArmorOrAccessoryItem(player, TRINKET_1_SLOT);
            return;
        }
        if (PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_2_SLOT) == stack && stack.stackSize <= 0) {
            PlayerUtil.clearArmorOrAccessoryItem(player, TRINKET_2_SLOT);
        }
    }
}
