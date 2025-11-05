package teamport.aether.items.itemtool;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import teamport.aether.items.AetherItems;

public class ItemToolSwordVampire extends ItemToolSword {

    public ItemToolSwordVampire(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        boolean hitEntity = super.hitEntity(itemstack, target, attacker);
        if (target instanceof Mob && target.hurtTime == 10 && hitEntity) {
            if ((target instanceof Player) && ((Player) target).gamemode.isPlayerInvulnerable()) {
                return false;
            }
            if (attacker.getHealth() < attacker.getMaxHealth() && attacker.getHealth() + attacker.getTotalHealingRemaining() < attacker.getMaxHealth()) {
                attacker.heal(3);
                attacker.eatFood(AetherItems.FOOD_VAMPIRE_SWORD_HEALING);
            }
        }
        return hitEntity;
    }
}
