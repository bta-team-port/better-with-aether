package bta.aether.entity;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class EntityPoisonDart extends EntityGoldenDart {
    public EntityPoisonDart(World world) {
        super(world, 2);
        this.noPhysics = true;
    }

    public EntityPoisonDart(World world, double d, double d1, double d2) {
        super(world, d, d1, d2, 2);
        this.noPhysics = true;
    }

    public EntityPoisonDart(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
        super(world, entityliving, doesArrowBelongToPlayer, 2);
        this.noPhysics = true;
    }

    protected void init() {
        this.arrowGravity = 0.02F;
        this.arrowSpeed = 1.0F;
        this.arrowDamage = 2;
    }

    public void playerTouch(EntityPlayer player) {
        if (!this.world.isClientSide) {
            if (this.inGround && this.arrowShake <= 0 && player.inventory.addItemStackToInventory(new ItemStack(Item.ammoArrowGold, 1))) {
                this.world.playSoundAtEntity(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(this, Item.ammoArrowGold.id);
                this.remove();
            }

        }
    }
}
