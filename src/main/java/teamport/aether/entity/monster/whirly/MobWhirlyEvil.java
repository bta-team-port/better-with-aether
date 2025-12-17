package teamport.aether.entity.monster.whirly;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.accessory.AetherInvisibility;

public class MobWhirlyEvil extends MobMonsterAether implements Enemy, AetherDeathMessage {

    private int entcount;
    private final float angle;

    public MobWhirlyEvil(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "whirly_evil");
        this.moveSpeed = 0.6F;
        this.angle = this.random.nextFloat() * 360.0F;
        this.speed = this.random.nextFloat() * 0.025F + 0.025F;
        this.entityAge = (this.random.nextInt(256) + 256);
    }

    @Override
    public void tick() {
        super.tick();
        ParticleMaker.spawnWhirlyParticles(world, this, 4, "whirlyevil");
    }


    @Override
    public void updateAI() {
        Player player = this.world.getClosestPlayerToEntity(this, 16.0);
        if (player != null && player.onGround && this.canEntityBeSeen(player)) {
            this.target = player;
        }

        if (this.target == null) {
            this.xd = Math.cos(0.01745329F * this.angle) * this.speed;
            this.zd = -Math.sin(0.01745329F * this.angle) * this.speed;
        } else {
            super.updateAI();
        }

        if (this.entityAge-- <= 0 || this.isInWaterOrRain()) {
            this.remove();
        }

        if (this.target != null) {
            ++this.entcount;
        }

        if (this.entcount >= 256 && this.target != null) {
            MobCreeper creeper = new MobCreeper(this.world);
            creeper.setPos(this.x, this.y + 0.75, this.z);
            creeper.xd = (this.random.nextFloat() - this.random.nextFloat()) * 0.125;
            creeper.zd = (this.random.nextFloat() - this.random.nextFloat()) * 0.125;
            this.world.entityJoinedWorld(creeper);
            this.entcount = 0;
        }

        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.y);
        int z = MathHelper.floor(this.z);
        if (this.world != null && this.world.getBlockId(x, y + 1, z) != 0) {
            this.entityAge -= 50;
        }
    }

    @Override
    protected void attackEntity(@NonNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {

            this.attackTime = 20;
            entity.hurt(this, this.attackStrength, DamageType.COMBAT);
        }
    }

    public Entity getPlayer() {
        if (this.world == null) return null;
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
        if (entityplayer instanceof AetherInvisibility) {
            AetherInvisibility invPlayer = (AetherInvisibility) entityplayer;
            if (invPlayer.aether$isInvisible()) {
                entityplayer = this.world.getClosestPlayerToEntity(this, 2.0);
            }
        }
        return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
    }

    @SuppressWarnings("java:S131")
    @Override
    public boolean collidesWith(Entity entity) {
        float launchSpeed = 0.75F;
        double distanceTo = entity.distanceTo(x, y, z);

        if (this.world != null && !(entity instanceof MobCreeper) && !(entity instanceof MobWhirlyEvil)) {
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
        }
        return false;
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
