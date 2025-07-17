package teamport.aether.entity.mimic;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlocks;

public class MobMimic extends MobMonster {
    public MobMimic(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "mimic");
        this.attackStrength = 5;
        this.mobDrops.add(new WeightedRandomLootObject(AetherBlocks.CHEST_PLANKS_SKYROOT.getDefaultStack(), 0, 1));

    }

    protected Entity findPlayerToAttack() {
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 64.0);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
    }

    public String getHurtSound() {
        return "step.wood";
    }

    public String getDeathSound() {
        return "random.door_open";
    }

    public float getSoundVolume() {
        return 0.6F;
    }

    public int getMaxHealth() {
        return 10;
    }
}
