package teamport.aether.entity.monster.whirly;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.Creature;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.animal.MobAetherAnimal;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherItems;

public class MobWhirly extends MobAetherAnimal implements Creature {
    private int lootTimer;
    private final int maxLifetime;

    public MobWhirly(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "whirly");
        this.maxLifetime = this.random.nextInt(1024) + 1024;
    }

    @Override
    public void tick() {
        super.tick();
        ParticleMaker.spawnWhirlyParticles(world, this, 2, "whirly");
    }

    @Override
    public void updateAI() {
        super.updateAI();

        if (this.entityAge >= this.maxLifetime || this.isInWaterOrRain()) {
            this.remove();
        }

        if (world.getClosestPlayer(x, y, z, 16) != null) {
            ++this.lootTimer;
        }

        if (this.lootTimer >= 256) {
            int drop = this.loot();
            if (drop != 0) {
                this.dropItem(drop, 1);
            }
            this.lootTimer = 0;
        }

    }

    private int loot() {
        int i = this.random.nextInt(100) + 1;
        if (i == 100) return AetherBlocks.BLOCK_GRAVITITE.id();
        if (i >= 96) return AetherItems.ZANITE.id;
        if (i >= 91) return AetherItems.PETAL_AECHOR.id;
        if (i >= 82) return AetherItems.AMBROSIUM.id;
        if (i >= 75) return AetherBlocks.DIRT_AETHER.id();
        if (i >= 64) return AetherBlocks.ICESTONE.id();
        if (i >= 52) return AetherItems.STICK_SKYROOT.id;
        if (i >= 38) return AetherItems.AMBER.id;
        return i > 20 ? AetherBlocks.LOG_SKYROOT.id() : AetherBlocks.QUICKSOIL.id();
    }

    @Override
    public boolean canSpawnHere() {
        if (this.world == null) return false;
        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(this.z);

        Block<?> block = Blocks.blocksList[this.world.getBlockId(x, y - 1, z)];
        return block != null && block.hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN);
    }

    @SuppressWarnings("java:S131")
    @Override
    public boolean collidesWith(Entity entity) {
        float launchSpeed = 0.75F;
        double distanceTo = entity.distanceTo(x, y, z);

        if (this.world != null && !(entity instanceof MobWhirly)) {
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
    public boolean makeStepSound() {
        return false;
    }

    @Override
    public boolean canClimb() {
        return false;
    }

}
