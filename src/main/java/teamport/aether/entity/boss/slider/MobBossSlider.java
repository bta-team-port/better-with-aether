package teamport.aether.entity.boss.slider;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.MaterialLiquid;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.dungeon.BlockLogicDungeonDoor;
import teamport.aether.blocks.dungeon.BlockLogicLocked;
import teamport.aether.blocks.dungeon.BlockLogicTrapped;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.boss.MobBoss;
import teamport.aether.helper.MessageMaker;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.items.itemtool.ItemToolPickaxeAether;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.core.Global.TICKS_PER_SECOND;
import static teamport.aether.world.feature.util.map.DungeonMap.runWithDungeon;

public class MobBossSlider extends MobBoss {
    public float deformX;
    public int deformY;
    public int deformZ;

    public static final float angerThreshold = 0.50F;
    public static final float baseDamage = 10F;
    public static final int maxAttackCoolDown = 50;
    public static final int minAttackCoolDown = 10;

    // blocks per second.
    public static final float baseSpeed = 15;
    public static float speed = baseSpeed;
    public float blocksToMove = 0;

    @NonNull
    public Direction moveDirection = Direction.NONE;

    public int attackCoolDown = 0;
    public boolean allowedToMove;

    public final ArrayList<Player> creativeAttackersList = new ArrayList<>();
    public Entity target;


    private State currentState = State.ASLEEP;

    public void setState(State state) {
        this.currentState = state;
    }

    public enum State {
        AWAKE(MobBossSlider::stateAwake),
        SLAM(MobBossSlider::stateSlam),
        ASLEEP(MobBossSlider::stateAsleep);

        public final Consumer<MobBossSlider> consumer;

        State(Consumer<MobBossSlider> consumer) {
            this.consumer = consumer;
        }

        public Consumer<MobBossSlider> getConsumer() {
            return this.consumer;
        }
    }

    public MobBossSlider(World world) {
        super(world);
        this.yRot = 0.0f;
        this.xRot = 0.0F;
        this.deformZ = 1;
        this.scoreValue = 10000;
        this.setSize(2F, 2F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_slider");
        this.chatColor = (byte) (TextFormatting.BROWN.id & 255);
        this.canBreatheUnderwater();
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 40 * TICKS_PER_SECOND;
    }

    public int getAttackCoolDown(float progress) {
        return (int) (minAttackCoolDown + (maxAttackCoolDown - minAttackCoolDown) * progress);
    }

    public void onDeath(Entity entityKilledBy) {
        this.world.players.stream()
                .filter(player -> player.distanceTo(this) < 32)
                .forEach(p -> {
                    p.triggerAchievement(AetherAchievements.BRONZE);
                    this.world.playSoundEffect(p, SoundCategory.WORLD_SOUNDS, p.x, p.y, p.z, "aether:achievement.bronze", 0.5f, 1.0f);
                });

        super.onDeath(entityKilledBy);
    }

    static final int DATA_STATE = 17;
    static final int DATA_ALLOW_MOVEMENT = 18;
    static final int DATA_MOVEMENT_DIRECTION = 19;
    static final int DATA_MOVEMENT_AMOUNT = 20;

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_STATE, State.ASLEEP.ordinal(), Integer.class);
        entityData.define(DATA_ALLOW_MOVEMENT, 0, Integer.class);
        entityData.define(DATA_MOVEMENT_DIRECTION, Direction.NONE.ordinal(), Integer.class);
        entityData.define(DATA_MOVEMENT_AMOUNT, 0, Integer.class);
    }

    @Override
    public void tick() {
        assert world != null;
        super.baseTick();

        if (this.newPosRotationIncrements > 0) {
            double lerpXD = this.x + (this.newPosX - this.x) / (double) this.newPosRotationIncrements;
            double lerpYD = this.y + (this.newPosY - this.y) / (double) this.newPosRotationIncrements;
            double lerpZD = this.z + (this.newPosZ - this.z) / (double) this.newPosRotationIncrements;

            double lerpYRot = this.newRotationYaw - (double) this.yRot;
            double lerpXRot = this.newRotationPitch - (double) this.xRot;

            while (lerpYRot < (double) -180.0F) {
                lerpYRot += 360.0F;
            }
            while (lerpYRot >= (double) 180.0F) {
                lerpYRot -= 360.0F;
            }

            this.yRot = (float) ((double) this.yRot + lerpYRot / (double) this.newPosRotationIncrements);
            this.xRot = (float) ((double) this.xRot + lerpXRot / (double) this.newPosRotationIncrements);

            --this.newPosRotationIncrements;
            this.setPos(lerpXD, lerpYD, lerpZD);
            this.setRot(this.yRot, this.xRot);
        }

        int blocksBroken = 0;
        if (blocksToMove > 0) {
            for (int x = -2; x <= 1; x++) {
                for (int z = -2; z <= 1; z++) {
                    for (int y = (moveDirection == Direction.DOWN && currentState != State.SLAM) ? -1 : 0; y <= 2; y++) {

                        int x1 = (int) (this.x + x);
                        int y1 = (int) (this.y + y);
                        int z1 = (int) (this.z + z);

                        Block<?> block = world.getBlock(x1, y1, z1);
                        if (block != null && breakBlock(world, x1, y1, z1)) {
                            doExplosionEffect(world, x1, y1, z1);
                            blocksToMove -= 0.5F * Math.min(block.getHardness() / 3f, 1);

                            blocksBroken++;
                            if (blocksBroken >= 9) {
                                this.allowedToMove = false;
                                this.attackCoolDown = maxAttackCoolDown;
                                return;
                            }
                        }
                    }
                }
            }
        }

        if (moveDirection != Direction.NONE) {
            float moveAmount = speed / TICKS_PER_SECOND;
            if (blocksToMove > moveAmount) {
                move(
                        moveAmount * moveDirection.getOffsetX(),
                        moveAmount * moveDirection.getOffsetY(),
                        moveAmount * moveDirection.getOffsetZ()
                );

                blocksToMove -= moveAmount;
            } else {
                move(
                        blocksToMove * moveDirection.getOffsetX(),
                        blocksToMove * moveDirection.getOffsetY(),
                        blocksToMove * moveDirection.getOffsetZ()
                );
                blocksToMove = 0;
            }
        } else {
            blocksToMove = 0;
        }

        if (blocksToMove <= 0.05F) {
            this.y = this.y % 1 < .50F ? Math.floor(this.y) : Math.ceil(this.y);

            this.yo = this.y;
            this.xo = this.x;
            this.zo = this.z;
        }

        if (this.deformX > 0.01F) {
            this.deformX *= 0.8F;
        }

        if (!EnvironmentHelper.isClientWorld()) {
            if (--attackCoolDown <= 0) allowedToMove = true;
            this.currentState.getConsumer().accept(this);
        }

        if (EnvironmentHelper.isServerEnvironment()) {
            entityData.set(DATA_STATE, currentState.ordinal());
            entityData.set(DATA_ALLOW_MOVEMENT, allowedToMove ? 1 : 0);
            entityData.set(DATA_MOVEMENT_DIRECTION, moveDirection.ordinal());
            entityData.set(DATA_MOVEMENT_AMOUNT, Float.floatToIntBits(blocksToMove));
        } else if (EnvironmentHelper.isClientWorld()) {
            currentState = State.values()[entityData.getInt(DATA_STATE)];
            allowedToMove = entityData.getInt(DATA_ALLOW_MOVEMENT) > 0;
            moveDirection = Direction.values()[entityData.getInt(DATA_MOVEMENT_DIRECTION)];
            blocksToMove = Float.intBitsToFloat(entityData.getInt(DATA_MOVEMENT_AMOUNT));
        }
    }

    protected void stateAsleep() { /* ZZZ... */}

    protected void stateAwake() {
        assert world != null;

        if (world.getClosestPlayerToEntity(this, AetherDimension.BOSS_DETECTION_RADIUS) == null) {
            this.currentState = State.ASLEEP;
            returnToOriginalState();
        }

        if (target == null || world.rand.nextInt(10) == 0) {
            this.target = findPlayerToAttack();

            if (!this.creativeAttackersList.isEmpty()) {
                target = creativeAttackersList.get(0);
                for (Player player : this.creativeAttackersList) {
                    if (this.distanceToSqr(player) < this.distanceToSqr(target)) target = player;
                }
            }

        } else {
            if (this.distanceToSqr(target) > AetherDimension.BOSS_DETECTION_RANGE_SQR) target = null;
        }

        if (allowedToMove && target != null && blocksToMove <= 0.05F) {
            float progress = (float) Math.max((float) this.getHealth() / this.getMaxHealth(), .32);

            this.attackCoolDown = getAttackCoolDown(progress);
            allowedToMove = false;

            if (this.distanceToSqr(target) <= 25 && progress < .60F && random.nextInt(6) == 0) {
                moveDirection = Direction.UP;
                blocksToMove = 45;

                speed = baseSpeed * 2;
                this.attackCoolDown = getAttackCoolDown(.50F);
                this.currentState = State.SLAM;
                slamGoingDown = false;
                return;
            }

            int moveAmount;
            moveDirection = calculateDirection(target);
            switch (moveDirection) {
                case EAST:
                case WEST:
                    moveAmount = (int) Math.abs(x - target.x);
                    break;

                case DOWN:
                case UP:
                    moveAmount = (int) Math.abs(y - target.y);
                    break;

                case NORTH:
                case SOUTH:
                    moveAmount = (int) Math.abs(z - target.z);
                    break;

                case NONE:
                default:
                    moveAmount = 0;
                    break;
            }

            blocksToMove = Math.min(25, Math.max(moveAmount + 1, 3));
            world.playSoundAtEntity(null, this, "aether:mob.slider.move", 1.60F + random.nextFloat(), .45F + random.nextFloat());
        }
    }

    public double slamY = -1;
    public boolean slamGoingDown = false;

    protected void stateSlam() {
        assert world != null;

        if (allowedToMove && !slamGoingDown) {
            slamGoingDown = true;
            // intellij is being dumb here, but this is so slamY re-initializes every move.
            // it'd be better to call prologue functions when changing state. Oh, well.
            this.slamY = -1;

            moveDirection = Direction.DOWN;
            blocksToMove = 999;

        } else if (allowedToMove && this.slamY == this.y) {
            final int slamRadius = 5;
            final float launchSpeed = 0.75F;

            final AABB boundingBox = AABB.getTemporaryBB(this.x - slamRadius, this.y, this.z - slamRadius, this.x + slamRadius, this.y + slamRadius, this.z + slamRadius);
            List<Entity> list = world.getEntitiesWithinAABB(Entity.class, boundingBox);
            for (Entity entity : list) {
                entity.hurt(this, (int) ((baseDamage * 0.50F) * getAngerModifier()), DamageType.FALL);
                entity.hurt(this, (int) ((baseDamage * 0.75F) * getAngerModifier()), DamageType.COMBAT);

                switch (calculateDirection(entity)) {
                    case NORTH:
                        entity.push(0, launchSpeed / 2, -launchSpeed);
                        break;

                    case SOUTH:
                        entity.push(0, launchSpeed / 2, launchSpeed);
                        break;

                    case EAST:
                        entity.push(launchSpeed, launchSpeed / 2, 0);
                        break;

                    case WEST:
                        entity.push(-launchSpeed, launchSpeed / 2, 0);
                        break;
                }

                doExplosionEffect(entity.world, entity.x, entity.y, entity.z);
            }

            for (int particle = 0; particle < 16; particle++) {
                double explosionX = this.x - slamRadius + world.rand.nextInt(slamRadius * 2);
                double explosionY = this.y - slamRadius + world.rand.nextInt(slamRadius * 2);
                double explosionZ = this.z - slamRadius + world.rand.nextInt(slamRadius * 2);

                doExplosionEffect(world, explosionX, explosionY, explosionZ);
            }

            blocksToMove = 0;
            moveDirection = Direction.NONE;

            currentState = State.AWAKE;
            speed = baseSpeed;

            attackCoolDown = maxAttackCoolDown;
        }

        this.slamY = this.y;
    }

    @Override
    public boolean collidesWith(Entity entity) {
        if (blocksToMove > 0.25F) {
            entity.hurt(this, (int) (baseDamage * getAngerModifier()), DamageType.FALL);
            entity.hurt(this, (int) ((baseDamage * .50F) * getAngerModifier()), DamageType.COMBAT);
            if (entity instanceof Player && ((Player) entity).gamemode.isPlayerInvulnerable()) {
                return super.collidesWith(entity);
            }
            doExplosionEffect(entity.world, entity.x, entity.y, entity.z);
            world.playSoundAtEntity(null, this, "aether:mob.slider.collide", 1.60F + random.nextFloat(), .45F + random.nextFloat());
        }

        return super.collidesWith(entity);
    }

    public boolean breakBlock(World world, int x, int y, int z) {
        Block<?> block = world.getBlock(x, y, z);

        if (block == null) {
            return false;
        }

        if (!(block.getLogic() instanceof BlockLogicTrapped || block.getLogic() instanceof BlockLogicLocked)
                && !(block.getLogic() instanceof BlockLogicDungeonDoor)
                && !(block.getMaterial() instanceof MaterialLiquid)
                && !(block.getHardness() < 0 || block.getHardness() >= 10)
                && !(block.getBlastResistance(this) < 0 || block.getBlastResistance(this) >= 10)
        ) {
            block.dropBlockWithCause(world, EnumDropCause.EXPLOSION, x, y, z, world.getBlockMetadata(x, y, z), world.getTileEntity(x, y, z), null);
            world.setBlockWithNotify(x, y, z, 0);
            return true;
        }

        return false;
    }

    public void doExplosionEffect(World world, double x, double y, double z) {
        for (int particle = 0; particle < 16; particle++) {
            double XParticle = x + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double YParticle = y + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double ZParticle = z + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);

            ParticleMaker.spawnParticle(world, "explode", XParticle, YParticle, ZParticle, 0, 0, 0, 0);
        }

        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "random.explode", 0.5F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
    }

    public Player findPlayerToAttack() {
        assert this.world != null;
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 32.0F);

        if (entityplayer == null) return null;

        if ((this.canEntityBeSeen(entityplayer) && entityplayer.gamemode.areMobsHostile())) {
            ((AetherBossList) entityplayer).aether$TryAddBossList(this);
            return entityplayer;
        }

        return null;
    }

    // this following functions is the single most annoying solution in this class.
    // If you know better than me, please replace it with something decent. -Khep
    public Direction calculateDirection(Entity entity) {
        double deltaX = this.x - entity.x;
        double deltaY = this.y - entity.y;
        double deltaZ = this.z - entity.z;

        if (Math.abs(deltaY) >= entity.bbHeight * 1.5) {
            return deltaY < 0 ? Direction.UP : Direction.DOWN;
        } else if (Math.abs(deltaX) > Math.abs(deltaZ)) {
            return deltaX < 0 ? Direction.EAST : Direction.WEST;
        } else {
            return deltaZ < 0 ? Direction.SOUTH : Direction.NORTH;
        }
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public void fireHurt() {
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker == null && type == null && damage == 100) {
            this.setHealthRaw(0);
            this.playDeathSound();
            this.onDeath(null);
            return true;
        }
        if (this.isAwake() && type == DamageType.BLAST) return super.hurt(attacker, damage / 4, type);

        if (attacker instanceof Player) {
            ItemStack item = ((Player) attacker).inventory.getCurrentItem();

            if (item != null && (item.getItem() instanceof ItemToolPickaxe || item.getItem() instanceof ItemToolPickaxeAether)) {
                tryAwake();
                if (!((Player) attacker).gamemode.areMobsHostile()) creativeAttackersList.add((Player) attacker);

                this.target = attacker;
                ((AetherBossList) attacker).aether$TryAddBossList(this);

                double a = Math.abs(this.x - attacker.x);
                double c = Math.abs(this.z - attacker.z);
                if (a > c) {
                    this.deformZ = 1;
                    this.deformY = 0;
                    if (this.x > attacker.x) {
                        this.deformZ = -1;
                    }
                } else {
                    this.deformY = 1;
                    this.deformZ = 0;
                    if (this.z > attacker.z) {
                        this.deformY = -1;
                    }
                }

                this.deformX = 0.7F - (float) this.getHealth() / 875.0F;

                for (int i = 0; i < (Math.min(10, damage + random.nextInt(2)) * 32) / 10; i++) {
                    // it really doesn't matter if they are inverted somewhere... the slider is square.
                    float faceX = 2 * random.nextFloat();
                    float faceY = 2 * random.nextFloat();

                    float posX, posY, posZ;
                    Direction dir = Direction.directions[(int) (random.nextFloat() * Direction.directions.length)];
                    switch (dir) {
                        case WEST:
                            posX = (float) (x - 1);
                            posY = (float) (y + faceY);
                            posZ = (float) (z - 1 + faceX);
                            break;

                        case EAST:
                            posX = (float) (x + 1);
                            posY = (float) (y + faceY);
                            posZ = (float) (z - 1 + faceX);
                            break;

                        case SOUTH:
                            posX = (float) (x - 1 + faceX);
                            posY = (float) (y + faceY);
                            posZ = (float) (z + 1);
                            break;

                        case NORTH:
                            posX = (float) (x - 1 + faceX);
                            posY = (float) (y + faceY);
                            posZ = (float) (z - 1);
                            break;

                        case DOWN:
                            posX = (float) (x - 1 + faceX);
                            posY = (float) (y);
                            posZ = (float) (z - 1 + faceY);
                            break;

                        default:
                        case UP:
                            posX = (float) (x - 1 + faceX);
                            posY = (float) (y + 2);
                            posZ = (float) (z - 1 + faceY);
                            break;
                    }

                    ParticleMaker.spawnParticle(world, "block", posX, posY, posZ, 0, 0, 0, AetherBlocks.COBBLE_HOLYSTONE.id());
                }

                return super.hurt(attacker, (int) item.getStrVsBlock(AetherBlocks.COBBLE_HOLYSTONE), type);
            }

            if (!this.isAwake()) {
                String message = "<" + ((Player) attacker).getDisplayName() + "> " + I18n.getInstance().translateKey("aether.entity.boss_slider.hit_fail");
                MessageMaker.sendMessage((Player) attacker, message);
            }
        }
        return false;
    }

    @Override
    public AABB getBb() {
        return this.bb.copy();
    }

    @Override
    public boolean showBoundingBoxOnHover() {
        return !isAwake() && getHealth() > 0;
    }

    @Override
    public boolean canFight() {
        return isAlive() && isAwake();
    }

    @Override
    public boolean isMovementBlocked() {
        return super.isMovementBlocked() || !isAwake();
    }

    public float getAngerModifier() {
        return 1.0F + ((float) (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth());
    }

    public boolean isAngry() {
        return ((float) this.getHealth() / this.getMaxHealth()) < angerThreshold;
    }

    public boolean isAwake() {
        return this.currentState != State.ASLEEP;
    }

    public boolean doingSlam() {
        return this.currentState == State.SLAM;
    }

    public void tryAwake() {
        if (currentState == State.ASLEEP) {
            this.currentState = State.AWAKE;
            runWithDungeon(dungeonID, d -> d.lock(this, world));
            world.playSoundAtEntity(null, this, "aether:mob.slider.awaken", 1F, 1F);
        }
    }

    public int getMaxHealth() {
        return 500;
    }

    public String getLivingSound() {
        return "ambient.cave.cave";
    }

    public void playLivingSound() {
        if (this.currentState == State.ASLEEP) {
            this.world.playSoundAtEntity(null, this, this.getLivingSound(), 1.0F, 1.0f);
        }
    }

    public String getHurtSound() {
        return "step.stone";
    }

    public String getDeathSound() {
        return "aether:mob.slider.death";
    }

    public String getEntityTexture() {
        if (isAwake() && !doingSlam()) {
            if (isAngry()) {
                return "/assets/aether/textures/entity/boss_slider/slider_awake_red.png";
            } else {
                return "/assets/aether/textures/entity/boss_slider/slider_awake.png";
            }
        } else {
            if (isAngry()) {
                return "/assets/aether/textures/entity/boss_slider/slider_sleep_red.png";
            } else {
                return "/assets/aether/textures/entity/boss_slider/slider_sleep.png";
            }
        }
    }

    public @NonNull String getDefaultEntityTexture() {
        return "/assets/aether/textures/entity/boss_slider/slider_awake.png";
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        try {
            currentState = State.valueOf(tag.getString("state"));
        } catch (IllegalArgumentException e) {
            currentState = State.ASLEEP;
            returnToOriginalState();
        }

        attackCoolDown = tag.getInteger("attackCoolDown");
        allowedToMove = tag.getBoolean("allowedToMove");
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        tag.putString("state", currentState.toString());
        tag.putInt("attackCoolDown", attackCoolDown);
        tag.putBoolean("allowedToMove", allowedToMove);
        super.addAdditionalSaveData(tag);
    }

}
