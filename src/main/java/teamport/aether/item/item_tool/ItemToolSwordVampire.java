package teamport.aether.item.item_tool;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

public class ItemToolSwordVampire extends ItemToolSword {
    public ItemToolSwordVampire(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        boolean hitEntity = super.hitEntity(itemstack, target, attacker);
        if (target instanceof Mob && target.hurtTime == 10 && hitEntity) {
            if ((target instanceof Player) && ((Player) target).gamemode.hasInvulnerablePlayer()) {
                return false;
            }
            if (attacker.getHealth() < attacker.getMaxHealth() && attacker.getHealth() + attacker.getTotalHealingRemaining() < attacker.getMaxHealth()) {
                attacker.heal(2);
                attacker.eatFood(new ItemStack(Items.FOOD_FISH_RAW, 1));
            }
        }
        return hitEntity;
    }
}
