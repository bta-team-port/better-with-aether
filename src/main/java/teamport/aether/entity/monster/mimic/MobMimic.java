package teamport.aether.entity.monster.mimic;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.ITranslatableDeathMessage;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.itemtool.ItemToolAxeAether;

import java.util.List;

public class MobMimic extends MobMonster implements Enemy, ITranslatableDeathMessage {
    public MobMimic(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "mimic");
        this.attackStrength = 5;
        this.scoreValue = 2000;
        this.mobDrops.add(new WeightedRandomLootObject(AetherBlocks.CHEST_PLANKS_SKYROOT.getDefaultStack(), 0, 1));

    }

    public Entity findPlayerToAttack() {
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 64.0);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
    }

    protected void attackEntity(@NotNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.5F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            entity.hurt(this, this.attackStrength, DamageType.COMBAT);
        }

    }

    public boolean hurt(Entity attacker, int damage, DamageType type) {

        if (attacker instanceof Player) {
            ItemStack item = ((Player)attacker).inventory.getCurrentItem();

            if (item != null && (item.getItem() instanceof ItemToolAxe || item.getItem() instanceof ItemToolAxeAether)) {
                return super.hurt(attacker, damage * 2, type);
            }
        }
        return super.hurt(attacker, damage, type);
    }

    public String getHurtSound() {
        return "step.wood";
    }

    public String getDeathSound() {
        return "random.door_open";
    }

    public float getSoundVolume() {
        return 0.6F;
    }

    public int getMaxHealth() {
        return 80;
    }

    public void setLoot(List<ItemStack> loot){
        if(loot == null) return;
        for(ItemStack itemStack : loot){
            this.mobDrops.add(new WeightedRandomLootObject(itemStack, 0, 1));
        }
    }
}
