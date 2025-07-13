package teamport.aether.entity.mimic;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlocks;

public class MobMimic extends MobMonster{
    public MobMimic(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "mimic");
        this.attackStrength = 5;
        this.target = world.getClosestPlayer(this.x, this.y, this.z, 64);
        this.mobDrops.add(new WeightedRandomLootObject(AetherBlocks.CHEST_PLANKS_SKYROOT.getDefaultStack(), 1, 1));

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
