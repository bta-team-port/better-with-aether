package bta.aether.entity;

import bta.aether.AetherBlockTags;
import bta.aether.block.AetherBlocks;
import bta.aether.world.AetherDimension;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

import java.util.List;

public class EntityWhirlwind extends EntityMonster {
    public EntityWhirlwind(World world) {
        super(world);
        this.setSize(2.5F, 2.5F);
        this.speed = 0.15F;
    }

    private int rotation = 0;
    public boolean isHostile = false;

    @Override
    public void spawnInit() {
        if (this.random.nextInt(10) == 0) this.isHostile = true;
        super.spawnInit();
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.world.loadedEntityList.stream().anyMatch(entity -> Math.pow(entity.x - x, 2) + Math.pow(entity.y - y, 2) + Math.pow(entity.z - z, 2) < 3600 && entity instanceof EntityWhirlwind)) {
            return false;
        }

        int x = MathHelper.floor_double(this.x);
        int y = MathHelper.floor_double(this.bb.minY);
        int z = MathHelper.floor_double(this.z);

        if (world.getBlock(x, y-1, z) == null) return false;
        return this.world.getBlock(x, y - 1, z).hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN);
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        isHostile = !isHostile;
        return super.interact(entityplayer);
    }

    @Override
    public void tick() {
        int radius = 8;
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, AABB.getBoundingBox(this.x - radius, this.y, this.z  - radius, this.x + radius, this.y + radius, this.z + radius));
        for (Entity entity : list) {
            if (entity instanceof EntityPlayer && !isHostile) this.remove();
        }

        super.tick();
        drawCone();
    }

    @Override
    protected void attackEntity(Entity entity, float distance) {
    }


    public void drawCone() {
        double coneHeight = 2.5;
        double coneTopRadius = 2.0;
        double coneBottomRadius = 0.01;

        for (int l = 0; l < 360; ++l) {
            double ratio = (double) l / 360.0;
            double currentRadius = coneBottomRadius + ratio * (coneTopRadius - coneBottomRadius);

            double angle = Math.toRadians(-(l + rotation));
            double x = this.x + 0.5 + currentRadius * Math.cos(angle);
            double y = this.y + 1 + (l * coneHeight / 360.0);
            double z = this.z + 0.5 + currentRadius * Math.sin(angle);
            if (world.rand.nextBoolean()) continue;

            world.spawnParticle(particle(), x + ((double) world.rand.nextInt(10) / 20), y + ((double) world.rand.nextInt(10) / 20), z + ((double) world.rand.nextInt(10) / 20), 0.0, 0.03, 0.0);
        }

        rotation += 32;
    }

    protected String particle(){
        if (isHostile) {
            return world.rand.nextInt(150) == 0 ? "flame" : "smoke";
        } else {
            return "explode";
        }
    }

    public boolean collidesWith(Entity entity) {
        if (isHostile) {
            float launchSpeed = 1.25F;

            switch (Direction.values()[world.rand.nextInt(Direction.values().length)]) {
                case NORTH:
                    entity.push(0, launchSpeed / 3, -launchSpeed);
                    break;

                case SOUTH:
                    entity.push(0, launchSpeed / 3, launchSpeed);
                    break;

                case EAST:
                    entity.push(launchSpeed, launchSpeed / 3, 0);
                    break;

                case WEST:
                    entity.push(-launchSpeed, launchSpeed / 3, 0);
                    break;
            }
        }

        return super.collidesWith(entity);
    }

    @Override
    protected Entity findPlayerToAttack() {
        if (isHostile) {

            int radius = 32;
            List<Entity> list = world.getEntitiesWithinAABB(Entity.class, AABB.getBoundingBox(this.x - radius, this.y, this.z - radius, this.x + radius, this.y + radius, this.z + radius));

            for (int attempt = 0; attempt < 32; attempt++) {
                Entity target = list.get(world.rand.nextInt(list.size()));
                if (!(target instanceof EntityWhirlwind) && (target instanceof EntityLiving)) {
                    if (target instanceof EntityPlayer && !(((EntityPlayer) target).gamemode.areMobsHostile()))
                        continue;
                    return target;
                }
            }
        }

        return null;
    }
}
