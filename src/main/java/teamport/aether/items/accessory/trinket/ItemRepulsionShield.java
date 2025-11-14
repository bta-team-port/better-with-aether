package teamport.aether.items.accessory.trinket;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import teamport.aether.items.AetherRepulsion;
import teamport.aether.items.accessory.AetherInvisibility;
import teamport.aether.items.accessory.IAccessoryEffects;

import java.util.List;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

public class ItemRepulsionShield extends ItemShield implements IAccessoryEffects {

    public ItemRepulsionShield(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;

        int armorSlot = slotId - player.inventory.mainInventory.length;
        if (armorSlot != TRINKET_1_SLOT && armorSlot != TRINKET_2_SLOT) {
            return;
        }

        ItemStack[] armor = player.inventory.armorInventory;
        if (armorSlot == TRINKET_2_SLOT && armor[TRINKET_1_SLOT] != null && armor[TRINKET_1_SLOT].getItem() instanceof ItemRepulsionShield) {
            return;
        }

        double velocity = MathHelper.sqrt(player.xd * player.xd + player.zd * player.zd);
        if (player.isSneaking() || (player.onGround && velocity <= 0.075D)) {
            ((AetherRepulsion) player).aether$setRepulsion(true);
            List<Projectile> projectiles = world.getEntitiesWithinAABB(Projectile.class, player.bb.grow(1.25D, 1.25D, 1.25D));
            if (!projectiles.isEmpty()) {
                for (Projectile projectile : projectiles) {
                    if (projectile.owner != player) {
                        projectile.xd = -projectile.xd;
                        projectile.zd = -projectile.zd;
                    }
                }
            }
        } else {((AetherRepulsion) player).aether$setRepulsion(false);}
    }
    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        ((AetherRepulsion) player).aether$setRepulsion(false);
    }
}
