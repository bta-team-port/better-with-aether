package teamport.aether.entity.monster.swet;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherItems;

import java.util.ArrayList;
import java.util.List;

public class MobSwetGold extends MobSwet implements Enemy {

    public MobSwetGold(World world) {
        super(world);
        this.heightOffset = 0.0F;
        this.scoreValue = 400;
        this.setPos(this.x, this.y, this.z);
        this.jumpDelay = 15;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "swet_gold");
        this.moveSpeed = 3.0F;
        this.mobDrops.add(new WeightedRandomLootObject(Blocks.GLOWSTONE.getDefaultStack(), 0));
    }

    @Override
    public List<WeightedRandomLootObject> getMobDrops() {
        List<WeightedRandomLootObject> drops = new ArrayList<>();
        drops.add(new WeightedRandomLootObject(Blocks.GLOWSTONE.getDefaultStack(), 1, 2));
        return drops;
    }

    @Override
    public int getMaxHealth() {
        return 26;
    }

    @Override
    public void jump() {
        if (this.passenger != null) {
            this.yd = 1.8;
        } else {
            this.yd = 0.8;
        }
    }

    @Override
    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    @Override
    public int getLightmapCoord(float partialTick) {
        return this.world == null ? super.getLightmapCoord(partialTick) : this.world.getLightmapCoord(15, 15);
    }

    @Override
    public void doTickEffect() {
        if (random.nextInt(2) == 0) {
            ParticleMaker.spawnParticle(world, "arrowtrail", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
            ParticleMaker.spawnParticle(world, "arrowtrail", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
        }
    }

    @Override
    public Item getBounceParticle() {
        return AetherItems.FOOD_GUMMY_GOLD;
    }

    @Override
    public void attackEntity(@NonNull Entity entity, float distance) {
        if (this.isAlive() && !this.isFriendly() && this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY && getHealth() > 0 && !dead) {
            this.attackTime = 200;
            if (this.world != null)
                this.world.playSoundAtEntity(null, this, "mob.slimeattack", 0.5F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            entity.hurt(this, 3, DamageType.COMBAT);
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (this.isAlive() && !this.isFriendly() && this.canEntityBeSeen(player) && (double) this.distanceTo(player) < 2.0F && player.hurt(this, 3, DamageType.COMBAT) && getHealth() > 0 && !dead && grabDelay == 0) {
            player.startRiding(this);
            grabDelay = 50;
        }
    }

}
