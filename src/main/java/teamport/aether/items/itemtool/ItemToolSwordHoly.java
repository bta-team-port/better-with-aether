package teamport.aether.items.itemtool;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.*;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import teamport.aether.AetherMod;
import teamport.aether.helper.ParticleHelper;

import java.util.Random;

public class ItemToolSwordHoly extends ItemToolSword {

    public ItemToolSwordHoly(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof MobZombie || target instanceof MobZombiePig || target instanceof MobZombieArmored || target instanceof MobGhast || target instanceof MobSnowman || target instanceof MobSkeleton || target instanceof MobGiant) {
            Random random = new Random();
            double dx = target.x + (random.nextDouble() * 0.5) - 0.25;
            double dy = target.y + 0.5 + (random.nextDouble() * 0.5) - 0.25;
            double dz = target.z + (random.nextDouble() * 0.5) - 0.25;
            double motionX = (random.nextDouble() * 0.1) - 0.05;
            double motionY = (random.nextDouble() * 0.1) - 0.05;
            double motionZ = (random.nextDouble() * 0.1) - 0.05;
            ParticleHelper.spawnParticle(target.world, "blueflame", dx, dy, dz, motionX, motionY, motionZ, 0);
            ParticleHelper.spawnParticle(target.world, "blueflame", dx, dy, dz, -motionX, motionY, motionZ, 0);
            ParticleHelper.spawnParticle(target.world, "blueflame", target.x, target.y + 0.5, target.z, 0.0, 0.0, 0.0, 0);
            target.hurt(attacker, 20, AetherMod.HOLY);
        }

        target.hurt(attacker, getDamageVsEntity(target, itemstack), AetherMod.HOLY);
        itemstack.damageItem(1, attacker);
        return true;
    }
}
