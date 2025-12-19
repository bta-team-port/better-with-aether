package teamport.aether.entity.animal.whirly;

import net.minecraft.client.entity.particle.Particle;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.Creature;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.MobUtil;
import teamport.aether.entity.animal.MobAetherAnimal;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherItems;

public class MobWhirly extends MobAetherAnimal implements Creature {
    private int lootTimer;
    private final int maxLifetime;
    private static final WeightedRandomBag<WeightedRandomLootObject> LOOT_BAG = new WeightedRandomBag<>();

    public MobWhirly(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "whirly");
        this.maxLifetime = this.random.nextInt(1024) + 1024;
        this.moveSpeed = 0.35F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getHealth() > 0) {
            ParticleMaker.spawnWhirlyParticles(world, this, 4, "whirly");
        }
    }

    @Override
    public void updateAI() {
        super.updateAI();

        if (this.isInWaterOrRain() || (this.entityAge >= this.maxLifetime && !this.hadNicknameSet)) {
            for (int l = 0; l < 16; ++l) {
                double angle = Math.toRadians(l * 45.0);
                ParticleMaker.spawnParticle(world, "snowshovel", x, y, z, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
            }
            world.playSoundAtEntity(null, this, "random.whoose.out", 0.6F, 1.0F / (random.nextFloat() * 0.2F + 0.4F));
            this.remove();
        }

        if (world.getClosestPlayer(x, y, z, 16) != null) {
            ++this.lootTimer;
        }

        if (this.lootTimer >= 256) {
            WeightedRandomLootObject lootObject = LOOT_BAG.getRandom();
            if (lootObject != null) {
                ItemStack stack = lootObject.getItemStack();
                if (stack != null) {
                    this.dropItem(stack.copy(), 0.0F);
                }
            }
            this.lootTimer = 0;
        }

    }

    static {
        LOOT_BAG.addEntry(new WeightedRandomLootObject(new ItemStack(AetherBlocks.BLOCK_GRAVITITE), 1, 1), 1);
        LOOT_BAG.addEntry(new WeightedRandomLootObject(new ItemStack(AetherItems.ZANITE),           1, 2), 4);
        LOOT_BAG.addEntry(new WeightedRandomLootObject(new ItemStack(AetherItems.AMBROSIUM),        1, 8), 5);

        LOOT_BAG.addEntry(new WeightedRandomLootObject(new ItemStack(AetherItems.PETAL_AECHOR),     1, 4), 9);
        LOOT_BAG.addEntry(new WeightedRandomLootObject(new ItemStack(AetherItems.STICK_SKYROOT),    1, 8), 12);
        LOOT_BAG.addEntry(new WeightedRandomLootObject(new ItemStack(AetherItems.AMBER),            1, 4), 14);

        LOOT_BAG.addEntry(new WeightedRandomLootObject(new ItemStack(AetherBlocks.DIRT_AETHER),     1, 16), 15);
        LOOT_BAG.addEntry(new WeightedRandomLootObject(new ItemStack(AetherBlocks.ICESTONE),        1, 8), 11);
        LOOT_BAG.addEntry(new WeightedRandomLootObject(new ItemStack(AetherBlocks.LOG_SKYROOT),     1, 4), 17);
        LOOT_BAG.addEntry(new WeightedRandomLootObject(new ItemStack(AetherBlocks.QUICKSOIL),       1, 8), 21);
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

        if (this.world != null && !(entity instanceof MobWhirly) && !(entity instanceof Particle)) {
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
    public boolean hurt(Entity entity, int damage, DamageType type) {
        if (entity == null && type == null && damage == 100) {
            return MobUtil.killMob(this);
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
