package teamport.aether.items.itemtool;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import teamport.aether.AetherMod;
import teamport.aether.helper.ParticleHelper;

import java.util.Random;

public class ItemToolSwordLightning extends ItemToolSword {
    public int weaponDamage;

    public ItemToolSwordLightning(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
        this.weaponDamage = 1;
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.isAlive()) {
            for (int particle = 0; particle < 16; particle++) {
                Random random = new Random();
                double dx = target.x + (random.nextDouble() * 0.5) - 0.25;
                double dy = target.y + 1.0 + (random.nextDouble());
                double dz = target.z + (random.nextDouble() * 0.5) - 0.25;
                double motionX = (random.nextDouble() * 0.1) - 0.05;
                double motionY = (random.nextDouble() * 0.1) - 0.05;
                double motionZ = (random.nextDouble() * 0.1) - 0.05;
                ParticleHelper.spawnParticle(target.world, "lightning", dx, dy, dz, motionX, motionY, motionZ, 0);
                ParticleHelper.spawnParticle(target.world, "lightning", dx, dy, dz, -motionX, motionY, motionZ, 0);
                ParticleHelper.spawnParticle(target.world, "lightning", dx, dy, dz, motionX, motionY, -motionZ, 0);
                ParticleHelper.spawnParticle(target.world, "lightning", dx, dy, dz, -motionX, motionY, -motionZ, 0);
            }
            target.hurt(attacker, 10, AetherMod.LIGHTNING);
        }

        if (target.hurtTime == 10) {
            target.hurt(attacker, 10, AetherMod.LIGHTNING);
        }
        itemstack.damageItem(1, attacker);
        return true;
    }
}
