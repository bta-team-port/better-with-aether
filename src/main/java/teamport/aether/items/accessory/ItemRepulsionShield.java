package teamport.aether.items.accessory;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import teamport.aether.items.accessory.trinket.ItemShield;

import java.util.List;

import static teamport.aether.items.accessory.SlotAccessory.*;

public class ItemRepulsionShield extends ItemShield {
    private int coolDown = 0;

    public ItemRepulsionShield(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slotId, boolean flag) {
        // First, we get the player and their held stack.
        // If the held stack IS EQUAL to the shield, we follow the first part of the method.
        // Otherwise, if the shield is in the TRINKET slots, go to the second part of the method.
        Player player = (Player) entity;
        if (player.getHeldItem() == stack) {

            // Now we check if the item cooldown is greater than zero.
            // If so, lower cooldown and return.
            if (coolDown > 0) {
                coolDown--;
                return;
            }

            // Now we get a list of projectiles around the player.
            // If it's empty, we return. Otherwise, we get the individual projectiles.
            List<Projectile> projectiles = world.getEntitiesWithinAABB(Projectile.class, player.bb.grow(1.25D, 1.25D, 1.25D));
            if (projectiles.isEmpty()) return;

            // For the individual ones we first check if it's the player's arrow.
            // If so, continue. (aka ignore it!) Afterward, we reverse the projectile's
            // xd and zd values, set the cooldown, and break the loop.
            for (Projectile projectile : projectiles) {
                if (projectile.owner == player) continue;

                projectile.xd = -projectile.xd;
                projectile.zd = -projectile.zd;
                coolDown = 20;
                break;
            }
        } else {
            if (slotId < player.inventory.mainInventory.length || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT)
                return;

            // Checks for player movement - First is if the player is moving,
            // second is if the player is jumping/falling. If either is too
            // high, we return.
            double velocity = MathHelper.sqrt(player.xd * player.xd + player.zd * player.zd);
            double yVelocity = Math.abs(player.yd);

            if (velocity > 0.001D || yVelocity > 0.1D) return;

            // Now we do the same as above, just without a cooldown.
            List<Projectile> projectiles = world.getEntitiesWithinAABB(Projectile.class, player.bb.grow(1.25D, 1.25D, 1.25D));
            if (projectiles.isEmpty()) return;
            for (Projectile projectile : projectiles) {
                if (projectile.owner == player) continue;

                projectile.xd = -projectile.xd;
                projectile.zd = -projectile.zd;
                break;
            }
        }
    }
}
