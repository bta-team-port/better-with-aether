package teamport.aether.entity;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.entity.projectile.ProjectileDart;
import teamport.aether.items.AetherItems;

public class ProjectileDartEnchanted extends ProjectileDart {
    public ProjectileDartEnchanted(World world) {
        super(world, 2);
        this.noPhysics = true;
        this.stack = new ItemStack(AetherItems.AMMO_DART_ENCHANTED);
    }

    public ProjectileDartEnchanted(World world, double x, double y, double z) {
        super(world, x, y, z, 2);
        this.noPhysics = true;
        this.stack = new ItemStack(AetherItems.AMMO_DART_ENCHANTED);
    }

    public ProjectileDartEnchanted(World world, Mob owner, boolean doesDartBelongToPlayer) {
        super(world, owner, doesDartBelongToPlayer, 2);
        this.noPhysics = true;
        this.stack = new ItemStack(AetherItems.AMMO_DART_ENCHANTED);
    }

    public void initProjectile() {
        super.initProjectile();
        this.defaultGravity = 0.02F;
        this.defaultProjectileSpeed = 1.0F;
        this.damage = 2;
    }

    public void playerTouch(Player player) {
        if (this.dartBelongsToPlayer()) {
            super.playerTouch(player);
        }

    }
}
