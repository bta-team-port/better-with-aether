package teamport.aether.item.accessory.trinket;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.joml.primitives.AABBd;
import teamport.aether.item.AetherRepulsion;
import teamport.aether.item.accessory.IAccessoryEffects;
import teamport.aether.entity.player.PlayerUtil;

import java.util.List;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

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

        ItemStack trinketOne = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_1_SLOT);
        if (armorSlot == TRINKET_2_SLOT && trinketOne != null && trinketOne.getItem() instanceof ItemRepulsionShield) {
            return;
        }

        double velocity = MathHelper.sqrt(player.xd * player.xd + player.zd * player.zd);
        if (player.isSneaking() || (player.onGround && velocity <= 0.075D)) {
            ((AetherRepulsion) player).aether$setRepulsion(true);
            List<Projectile> projectiles = world.getEntitiesWithinAABB(Projectile.class, new AABBd(player.bb.minX - 1.25D, player.bb.minY - 1.25D, player.bb.minZ - 1.25D, player.bb.maxX + 1.25D, player.bb.maxY + 1.25D, player.bb.maxZ + 1.25D));
            if (!projectiles.isEmpty()) {
                for (Projectile projectile : projectiles) {
                    if (projectile.owner != player) {
                        projectile.xd = -projectile.xd;
                        projectile.zd = -projectile.zd;
                    }
                }
            }
        } else {
            ((AetherRepulsion) player).aether$setRepulsion(false);
        }
    }
    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        ((AetherRepulsion) player).aether$setRepulsion(false);
    }
}
