package teamport.aether.item.item_tool;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.*;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherHasCustomDamageType;

public class ItemToolSwordHoly extends ItemToolSword implements AetherHasCustomDamageType {
    public ItemToolSwordHoly(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        boolean hitEntity = super.hitEntity(itemstack, target, attacker);
        if (target.hurtTime == 10 && hitEntity
            && (target instanceof MobZombie
            || target instanceof MobGhast
            || target instanceof MobSnowman
            || target instanceof MobSkeleton
            || target instanceof MobGiant)
        ) {
            ParticleMaker.spawnHolySwordParticles(target);
        }
        return hitEntity;
    }

    @Override
    public int getDamageVsEntity(Entity entity, ItemStack itemstack) {
        int damage = super.getDamageVsEntity(entity, itemstack);
        if(entity instanceof MobZombie || entity instanceof MobGhast || entity instanceof MobSnowman || entity instanceof MobSkeleton || entity instanceof MobGiant){
            damage = damage * 2;
        }
        return damage;
    }

    @Override
    public DamageType getDamageType(){
        return AetherMod.HOLY;
    }
}
