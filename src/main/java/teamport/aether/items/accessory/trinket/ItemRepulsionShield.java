package teamport.aether.items.accessory.trinket;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import java.util.List;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;

public class ItemRepulsionShield extends ItemShield {
    public int coolDown = 0;

    public ItemRepulsionShield(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slotId, boolean flag) {
        // First, we get the player and their held stack.
        Player player = (Player) entity;
        if (slotId < player.inventory.mainInventory.length || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT) {
            return;
        }

        // Checks for player movement - First is if the player is moving,
        // second is if the player is jumping/falling. If either is too
        // high, we return.

        double velocity = MathHelper.sqrt(player.xd * player.xd + player.zd * player.zd);
        if (!player.isSneaking() && (!player.onGround || velocity > 0.075D)) {
            return;
        }

        // Now we do the same as above, just without a cooldown.
        List<Projectile> projectiles = world.getEntitiesWithinAABB(Projectile.class, player.bb.grow(1.25D, 1.25D, 1.25D));
        if (projectiles.isEmpty()) {
            return;
        }

        for (Projectile projectile : projectiles) {
            if (projectile.owner == player) {
                continue;
            }
            projectile.xd = -projectile.xd;
            projectile.zd = -projectile.zd;
        }
    }
}
