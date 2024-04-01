package bta.aether.mixin.entity.entityplayer;

import bta.aether.entity.EntityBossBase;
import bta.aether.entity.EntityBossSlider;
import bta.aether.entity.IAetherBoss;
import bta.aether.entity.IPlayerBossList;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixinBossList extends EntityPlayer implements IPlayerBossList {

    @Unique
    List<EntityLiving> aether$bossList = new ArrayList<>();

    public EntityPlayerSPMixinBossList(World world) {
        super(world);
    }

    @Override
    public List<EntityLiving> aether$getBossList() {
        List<EntityLiving> _bosses = new ArrayList<>(aether$bossList);
        for (EntityLiving bossBase : aether$bossList) {
            if (!bossBase.isAlive() || (bossBase instanceof EntityBossSlider && !((EntityBossSlider) bossBase).isAwake())) {
                _bosses.remove(bossBase);
            }
        }
        aether$bossList = _bosses;
        return aether$bossList;
    }

    @Override
    public void attackTargetEntityWithCurrentItem(Entity entity) {
        if (entity instanceof IAetherBoss && !aether$bossList.contains(entity)) {
            aether$bossList.add((EntityBossBase) entity);
        }
        super.attackTargetEntityWithCurrentItem(entity);
    }

    @Override
    public void onDeath(Entity entity) {
        aether$bossList.clear();
        super.onDeath(entity);
    }


}