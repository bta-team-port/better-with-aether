package teamport.aether.items.itemtool;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.items.AetherHasCustomDamageType;

import java.util.Random;

public class ItemToolSwordFire extends ItemToolSword implements AetherHasCustomDamageType {
//    public int weaponDamage;

    public ItemToolSwordFire(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
//        this.weaponDamage = 1;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
//        if (target.hurtTime == 10) {
//            target.hurt(attacker, 10, DamageType.FIRE);
//        }
        if (target instanceof Mob && target.isAlive()) {
            for (int particle = 0; particle < 16; particle++) {
                Random random = new Random();
                double dx = target.x + (random.nextDouble() * 0.5) - 0.25;
                double dy = target.y + 1.0 + (random.nextDouble());
                double dz = target.z + (random.nextDouble() * 0.5) - 0.25;
                double motionX = (random.nextDouble() * 0.1) - 0.05;
                double motionY = (random.nextDouble() * 0.1) - 0.05;
                double motionZ = (random.nextDouble() * 0.1) - 0.05;
                ParticleHelper.spawnParticle(target.world, "flame", dx, dy, dz, motionX, motionY, motionZ, 0);
                ParticleHelper.spawnParticle(target.world, "flame", dx, dy, dz, -motionX, motionY, motionZ, 0);
                ParticleHelper.spawnParticle(target.world, "flame", dx, dy, dz, motionX, motionY, -motionZ, 0);
                ParticleHelper.spawnParticle(target.world, "flame", dx, dy, dz, -motionX, motionY, -motionZ, 0);
            }
            target.maxFireTicks = 600;
            target.remainingFireTicks = 600;
        }
        return super.hitEntity(itemstack, target, attacker);
    }

    @Override
    public boolean onUseItemOnBlock(ItemStack itemstack, Player player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        blockX += side.getOffsetX();
        blockY += side.getOffsetY();
        blockZ += side.getOffsetZ();
        int blockID = world.getBlockId(blockX, blockY, blockZ);
        if (blockID != 0) return false;
        if (!world.setBlockWithNotify(blockX, blockY, blockZ, Blocks.FIRE.id())) return false;
        world.playSoundEffect(player, SoundCategory.WORLD_SOUNDS, (double) blockX + (double) 0.5F, (double) blockY + (double) 0.5F, (double) blockZ + (double) 0.5F, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
        itemstack.damageItem(1, player);
        return true;
    }

    public int getDamageVsEntity(Entity entity, ItemStack is) {
        return this.weaponDamage;
    }

    @Override
    public DamageType getDamageTypes(){
        return DamageType.FIRE;
    }
}
