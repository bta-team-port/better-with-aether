package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.achievements.AetherAchievements;

public class ItemAmbrosium extends ItemFood {
    private final int ticksPerHeal;

    public ItemAmbrosium(String name, String namespaceId, int id, int healAmount, int ticksPerHeal, boolean favouriteWolfMeat, int maxStackSize) {
        super(name, namespaceId, id, healAmount, ticksPerHeal, favouriteWolfMeat, maxStackSize);
        this.ticksPerHeal = ticksPerHeal;
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        if (entityplayer.getHealth() < entityplayer.getMaxHealth() && entityplayer.getHealth() + entityplayer.getTotalHealingRemaining() < entityplayer.getMaxHealth() && itemstack.consumeItem(entityplayer)) {
            entityplayer.eatFood(this);
            entityplayer.triggerAchievement(AetherAchievements.AMBROSIUM);
            world.playSoundAtEntity(entityplayer, entityplayer, this.ticksPerHeal >= 10 ? "random.bite_extended" : "random.bite", 0.5F + (itemRand.nextFloat() - itemRand.nextFloat()) * 0.1F, 1.1F + (itemRand.nextFloat() - itemRand.nextFloat()) * 0.1F);
        }

        return itemstack;
    }

}
