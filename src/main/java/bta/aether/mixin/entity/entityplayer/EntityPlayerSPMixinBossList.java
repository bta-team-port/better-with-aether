package bta.aether.mixin.entity.entityplayer;

import bta.aether.entity.EntityAetherBossBase;
import bta.aether.entity.EntityBossSlider;
import bta.aether.entity.IPlayerBossList;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixinBossList extends EntityPlayer implements IPlayerBossList {

    @Unique
     List<EntityAetherBossBase> aether$bossList = new ArrayList<>();

    public EntityPlayerSPMixinBossList(World world) {
        super(world);
    }

    @Override
    public List<EntityAetherBossBase> aether$getBossList() {
        List<EntityAetherBossBase> _bosses = new ArrayList<>(aether$bossList);
        for (EntityAetherBossBase bossBase : aether$bossList) {
            if (!bossBase.isAlive() || (bossBase instanceof EntityBossSlider && !((EntityBossSlider) bossBase).awake)) {
                _bosses.remove(bossBase);
            }
        }
        aether$bossList = _bosses;
        return aether$bossList;
    }

    @Override
    public void attackTargetEntityWithCurrentItem(Entity entity) {
        if (entity instanceof EntityAetherBossBase && !aether$bossList.contains(entity)) {
            aether$bossList.add((EntityAetherBossBase) entity);
        }
        super.attackTargetEntityWithCurrentItem(entity);
    }

    @Override
    public void onDeath(Entity entity) {
        aether$bossList.clear();
        super.onDeath(entity);
    }


}