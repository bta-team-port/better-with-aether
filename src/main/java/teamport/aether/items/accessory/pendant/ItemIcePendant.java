package teamport.aether.items.accessory.pendant;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

public class ItemIcePendant extends ItemPendant {
    public ItemIcePendant(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name, ArmorMaterial.IRON);
    }

    // TODO need to account for player velocity, up to |v| = 10, anymore seem to be overkill
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

        boolean blockExists = false;
        int x = MathHelper.floor(player.x);
        int y = MathHelper.floor(player.y);
        int z = MathHelper.floor(player.z);

        for (int radius = -1; radius <= 1; radius++) {
            for (int depth = -1; depth <= 1; depth++) {
                int xPos = x + radius;
                int zPos = z + depth;

                if (player.isWalking && player.xd > 0.1 || player.zd > 0.1 || player.xd < -0.1 || player.zd < -0.1) {
                    if (world.getBlockMaterial(xPos, y - 2, zPos) == Material.water) {
                        blockExists = true;
                        world.setBlockWithNotify(xPos, y - 2, zPos, Blocks.ICE.id());
                    } else if (world.getBlockMaterial(xPos, y - 2, zPos) == Material.lava) {
                        blockExists = true;
                        world.setBlockWithNotify(xPos, y - 2, zPos, Blocks.OBSIDIAN.id());
                    }
                }
            }
        }

        if (blockExists) stack.damageItem(1, player);

        if (player.inventory.armorInventory[TRINKET_1_SLOT] == stack) {
            if (stack.stackSize <= 0) player.inventory.armorInventory[TRINKET_1_SLOT] = null;
        }

        if (player.inventory.armorInventory[TRINKET_2_SLOT] == stack) {
            if (stack.stackSize <= 0) player.inventory.armorInventory[TRINKET_2_SLOT] = null;
        }
    }
}
