package teamport.aether.entity.monster.tempest;

import net.minecraft.client.entity.particle.Particle;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.accessory.AetherInvisibility;

public class MobTempest extends MobMonsterAether implements Enemy, AetherDeathMessage {
    private int creeperCountdown;
    private final int maxLifetime;

    public MobTempest(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "tempest");
        this.maxLifetime = this.random.nextInt(1024) + 1024;
        this.scoreValue = 400;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getHealth() > 0) {
            ParticleMaker.spawnWhirlyParticles(world, this, 12, "tempest");
            ParticleMaker.spawnParticleFar(world, "lightning", this.x, this.y + world.rand.nextDouble(), this.z,
                world.rand.nextDouble() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), world.rand.nextDouble() * 0.2F, world.rand.nextDouble() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0);
        }
    }

    @Override
    public void attackEntity(@NonNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, entity.x, entity.y, entity.z, "aether:zap", 0.5F, (1.3F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
            entity.hurt(this, this.attackStrength, AetherMod.LIGHTNING);
        }
    }

    @Override
    public void updateAI() {
        super.updateAI();

        if (this.isInWaterOrRain() || (this.entityAge >= this.maxLifetime && !this.hadNicknameSet)) {
            for (int l = 0; l < 16; ++l) {
                double angle = Math.toRadians(l * 45.0);
                ParticleMaker.spawnParticle(world, "largesmoke", x, y, z, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
            }
            world.playSoundAtEntity(null, this, "random.whoose.out", 0.3F, 1.0F / (random.nextFloat() * -0.2F - 0.4F));
            this.remove();
        }

        if (this.target != null) {
            ++this.creeperCountdown;
        }

        if (this.creeperCountdown >= 256 && this.target != null) {
            MobCreeper creeper = new MobCreeper(this.world);
            creeper.setPos(this.x, this.y + 0.75, this.z);
            creeper.xd = (this.random.nextFloat() - this.random.nextFloat()) * 0.125;
            creeper.zd = (this.random.nextFloat() - this.random.nextFloat()) * 0.125;
            this.world.entityJoinedWorld(creeper);
            this.creeperCountdown = 0;
        }
    }

    protected Entity findPlayerToAttack() {
        if (this.world == null) return null;
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
        if (entityplayer instanceof AetherInvisibility) {
            AetherInvisibility invPlayer = (AetherInvisibility) entityplayer;
            if (invPlayer.aether$isInvisible()) {
                entityplayer = this.world.getClosestPlayerToEntity(this, 2.0);
            }
        }
        return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
    }

    @SuppressWarnings("java:S131")
    @Override
    public boolean collidesWith(Entity entity) {
        float launchSpeed = 0.75F;
        double distanceTo = entity.distanceTo(x, y, z);

        if (this.world != null && !(entity instanceof MobCreeper) && !(entity instanceof MobTempest) && !(entity instanceof Particle)) {
            switch (Direction.values()[this.world.rand.nextInt(Direction.values().length)]) {
                case NORTH:
                    entity.push(0, launchSpeed / 4, -launchSpeed / distanceTo);
                    break;

                case SOUTH:
                    entity.push(0, launchSpeed / 4, launchSpeed / distanceTo);
                    break;

                case EAST:
                    entity.push(launchSpeed / distanceTo, launchSpeed / 4, 0);
                    break;

                case WEST:
                    entity.push(-launchSpeed / distanceTo, launchSpeed / 4, 0);
                    break;
            }
            return false;
        }
        return true;
    }

    @Override
    public String getLivingSound() {
        return "random.whoose.out";
    }

    @Override
    public String getHurtSound() {
        return "random.whoose.out";
    }

    @Override
    public String getDeathSound() {
        return "random.whoose.out";
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public boolean makeStepSound() {
        return false;
    }

    @Override
    public boolean canClimb() {
        return false;
    }
}
