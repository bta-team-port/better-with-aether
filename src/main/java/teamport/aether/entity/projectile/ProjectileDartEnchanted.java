package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
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

    public void setHeading(double newMotionX, double newMotionY, double newMotionZ, float speed, float randomness) {
        float velocity = MathHelper.sqrt(newMotionX * newMotionX + newMotionY * newMotionY + newMotionZ * newMotionZ);
        newMotionX /= velocity;
        newMotionY /= velocity;
        newMotionZ /= velocity;
        newMotionX += (this.random.nextGaussian() * 0.0075 * (double)randomness) / 4;
        newMotionY += (this.random.nextGaussian() * 0.0075 * (double)randomness) / 4;
        newMotionZ += (this.random.nextGaussian() * 0.0075 * (double)randomness) / 4;
        newMotionX *= speed;
        newMotionY *= speed;
        newMotionZ *= speed;
        this.xd = newMotionX;
        this.yd = newMotionY;
        this.zd = newMotionZ;
        float f3 = MathHelper.sqrt(newMotionX * newMotionX + newMotionZ * newMotionZ);
        this.yRotO = this.yRot = (float)(Math.atan2(newMotionX, newMotionZ) * 180.0 / Math.PI);
        this.xRotO = this.xRot = (float)(Math.atan2(newMotionY, f3) * 180.0 / Math.PI);
        this.ticksInGround = 0;
    }

    public void initProjectile() {
        super.initProjectile();
        this.defaultGravity = 0.005F;
        this.defaultProjectileSpeed = 1.0F;
        this.damage = 6;
    }

    public void playerTouch(Player player) {
        if (this.dartBelongsToPlayer()) {
            super.playerTouch(player);
        }
    }
}
