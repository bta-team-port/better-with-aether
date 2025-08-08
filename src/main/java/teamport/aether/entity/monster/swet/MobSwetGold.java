package teamport.aether.entity.monster.swet;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;

public class MobSwetGold extends MobSwet implements Enemy {

    public MobSwetGold(World world) {
        super(world);
        this.heightOffset = 0.0F;
        this.scoreValue = 400;
        this.setSize(1.4F, 1.2F);
        this.setPos(this.x, this.y, this.z);
        this.jumpDelay = 20;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "swet_gold");
        this.moveSpeed = 3.0F;
        this.mobDrops.remove(new WeightedRandomLootObject(AetherBlocks.AERCLOUD_BLUE.getDefaultStack(), 1, 2));
        this.mobDrops.add(new WeightedRandomLootObject(Blocks.GLOWSTONE.getDefaultStack(), 1, 2));
    }

    public int getMaxHealth() {
        return 26;
    }

    public void jump() {
        this.yd = 0.8;
    }

    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    public int getLightmapCoord(float partialTick) {
        return this.world.getLightmapCoord(15, 15);
    }

    @Override
    public void tick() {

        if (random.nextInt(2) == 0) {
            this.world.spawnParticle("goldendust", this.x, this.y, this.z, world.rand.nextFloat(), world.rand.nextFloat(), world.rand.nextFloat(), 0);
        }

        this.oSquish = this.squish;
        boolean flag = this.onGround;
        super.tick();
        if (this.onGround && !flag) {
            int i = 2;

            for(int j = 0; j < i * 8; ++j) {
                float f = this.random.nextFloat() * 3.1415927F * 2.0F;
                double f1 = (double)this.random.nextFloat() * 0.5 + 0.5;
                double f2 = (double)(MathHelper.sin(f) * (float)i) * 0.5 * f1;
                double f3 = (double)(MathHelper.cos(f) * (float)i) * 0.5 * f1;
                this.world.spawnParticle("item", this.x + f2, this.bb.minY, this.z + f3, 0.0, 0.0, 0.0, AetherItems.FOOD_GUMMY_GOLD.id);
            }

            this.world.playSoundAtEntity(null, this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);

            this.squish = -0.5F;
        }

        if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
            this.remove();
        }

        this.squish *= 0.6F;
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
        if (!this.friendly) {
            if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY && getHealth() > 0 && !dead) {
                this.attackTime = 40;
                entity.hurt(this, 3, DamageType.COMBAT);
            }
        }
    }

    public void playerTouch(Player player) {
        int i = 3;
        if (!this.friendly) {
            if (this.canEntityBeSeen(player) && (double) this.distanceTo(player) < 0.6 * (double) i && player.hurt(this, i, DamageType.COMBAT)) {
                player.startRiding(this);
                this.splorch();
            }
        }
    }

}
