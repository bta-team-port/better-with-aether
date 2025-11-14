package teamport.aether.items.item_tool;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobPig;
import net.minecraft.core.entity.monster.MobZombiePig;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.helper.ParticleMaker;

import java.util.Random;

public class ItemToolSwordPig extends ItemToolSword {
    private final Random random = new Random();

    public ItemToolSwordPig(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (pigSwordKills(target)) {
            double dx = target.x + (random.nextDouble() * 0.5) - 0.25;
            double dy = target.y + 0.5 + (random.nextDouble() * 0.5) - 0.25;
            double dz = target.z + (random.nextDouble() * 0.5) - 0.25;
            double motionX = (random.nextDouble() * 0.1) - 0.05;
            double motionY = (random.nextDouble() * 0.1) - 0.05;
            double motionZ = (random.nextDouble() * 0.1) - 0.05;
            ParticleMaker.spawnParticle(target.world, "flame", dx, dy, dz, motionX, motionY, motionZ, 0);
            ParticleMaker.spawnParticle(target.world, "flame", dx, dy, dz, -motionX, motionY, motionZ, 0);
            ParticleMaker.spawnParticle(target.world, "largesmoke", target.x, target.y + 0.5, target.z, 0.0, 0.0, 0.0, 0);
            target.hurt(attacker, 100, DamageType.COMBAT);
            target.remove();
        }
        return super.hitEntity(itemstack, target, attacker);
    }

    private static boolean pigSwordKills(Entity target) {
        return target instanceof MobPig
            || target instanceof MobZombiePig
            || target instanceof MobPhyg
            || (target instanceof Player && "Tocinin".equals(((Player) target).username));
    }
}
