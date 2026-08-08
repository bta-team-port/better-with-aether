package teamport.aether.item.item_tool;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.*;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.util.helper.DamageType;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherHasCustomDamageType;

public class ItemToolSwordHoly extends ItemToolSword implements AetherHasCustomDamageType {
    public ItemToolSwordHoly(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(@NonNull ItemStack itemstack, @NonNull Mob target, @NonNull Mob attacker) {
        boolean hitEntity = super.hitEntity(itemstack, target, attacker);
        if (target.hurtTime == 10 && (undeadKills(target))) {
            ParticleMaker.spawnHolySwordParticles(target);
        }
        return hitEntity;
    }

    private static boolean undeadKills(Entity target) {
        return target instanceof MobZombie
            || target instanceof MobGhast
            || target instanceof MobSnowman
            || target instanceof MobGiant
            || target instanceof MobSkeleton;
    }

    @Override
    public int getDamageVsEntity(@NonNull ItemStack itemstack, @NonNull Entity entity) {
        int damage = super.getDamageVsEntity(itemstack, entity);
        if (undeadKills(entity)) {
            damage = damage * 2;
        }
        return damage;
    }

    @Override
    public DamageType getDamageType() {
        return AetherMod.HOLY;
    }
}
