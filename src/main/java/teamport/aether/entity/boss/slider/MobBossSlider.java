package teamport.aether.entity.boss.slider;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.MaterialLiquid;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.ICollidable;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.LevelListener;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.Nullable;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.dungeon.BlockLogicChestLocked;
import teamport.aether.block.dungeon.BlockLogicDungeonDoor;
import teamport.aether.block.dungeon.BlockLogicLocked;
import teamport.aether.block.dungeon.BlockLogicTrapped;
import teamport.aether.entity.MobUtil;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.boss.MobBoss;
import teamport.aether.entity.pathing.base.BoundingBoxSize;
import teamport.aether.entity.player.MessageMaker;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.item_tool.ItemToolPickaxeAether;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.util.map.DungeonMap;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.core.Global.TICKS_PER_SECOND;
import static teamport.aether.entity.DamageInstance.inst;

public class MobBossSlider extends MobBoss implements ICollidable {
    private State currentState = State.ASLEEP;

    /// movement
    @NonNull
    private Direction moveDirection = Direction.NONE;
    public static final float BASE_SPEED = 15;
    private float blocksToMove = 0;
    private boolean allowedToMove;
    /// attack
    private int attackCoolDown = 0;
    public static final float ANGER_THRESHOLD = 0.50F;
    public static final float BASE_DAMAGE = 10F;
    public static final int MAX_ATTACK_COOL_DOWN = 50;
    public static final int MIN_ATTACK_COOL_DOWN = 10;
    /// wakeup timer
    public static final int WAKEUP_TIMER = 20;
    public int wakeUpTimer = 0;
    /// slam
    private double slamY = -1;
    private boolean slamGoingDown = false;
    /// deformation when hit
    private float deformX;
    private int deformY;
    private int deformZ;
    ///  sync data defaults
    static final int DATA_STATE = 17;
    static final int DATA_ALLOW_MOVEMENT = 18;
    static final int DATA_MOVEMENT_DIRECTION = 19;
    static final int DATA_MOVEMENT_AMOUNT = 20;
    /// target list
    private final List<Player> creativeAttackersList = new ArrayList<>();


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
        this.speed = BASE_SPEED;
        this.scoreValue = 10000;
        this.setSize(2F, 2F);
        this.heightOffset = 0.0F;
        this.setBounds();
        this.setTextureIdentifier("aether", "boss_slider");
        this.chatColor = (byte) (TextFormatting.BROWN.id & 255);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_STATE, State.ASLEEP.ordinal(), Integer.class);
        this.entityData.define(DATA_ALLOW_MOVEMENT, 0, Integer.class);
        this.entityData.define(DATA_MOVEMENT_DIRECTION, Direction.NONE.ordinal(), Integer.class);
        this.entityData.define(DATA_MOVEMENT_AMOUNT, 0, Integer.class);
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        tag.putString("state", this.currentState.toString());
        tag.putInt("attackCoolDown", this.attackCoolDown);
        tag.putBoolean("allowedToMove", this.allowedToMove);
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        try {
            this.currentState = State.valueOf(tag.getString("state"));
        } catch (IllegalArgumentException e) {
            this.setState(State.ASLEEP);
            returnToOriginalState();
        }
        this.attackCoolDown = tag.getInteger("attackCoolDown");
        this.allowedToMove = tag.getBoolean("allowedToMove");
        super.readAdditionalSaveData(tag);
    }

    private void updateEntityData() {
        if (EnvironmentHelper.isMultiplayerServer()) {
            entityData.set(DATA_STATE, currentState.ordinal());
            entityData.set(DATA_ALLOW_MOVEMENT, allowedToMove ? 1 : 0);
            entityData.set(DATA_MOVEMENT_DIRECTION, moveDirection.ordinal());
            entityData.set(DATA_MOVEMENT_AMOUNT, Float.floatToIntBits(blocksToMove));
            return;
        }
        if (EnvironmentHelper.isMultiplayerClient()) {
            currentState = State.values()[entityData.getInt(DATA_STATE)];
            allowedToMove = entityData.getInt(DATA_ALLOW_MOVEMENT) > 0;
            moveDirection = Direction.values()[entityData.getInt(DATA_MOVEMENT_DIRECTION)];
            blocksToMove = Float.intBitsToFloat(entityData.getInt(DATA_MOVEMENT_AMOUNT));
        }
    }


    // Sound

    @Override public int getAmbientSoundInterval() {
        return 40 * TICKS_PER_SECOND;
    }
    @Override public String getLivingSound() {
        return "ambient.cave.cave";
    }
    @Override public void playLivingSound() {
        if (this.currentState != State.ASLEEP) return;
        this.world.playSoundAtEntity(null, this, this.getLivingSound(), 1.0F, 1.0f);
    }
    @Override public String getHurtSound() {
        return "step.stone";
    }
    @Override public String getDeathSound() {
        return "aether:mob.slider.death";
    }

    private void playCollidingSound() {
        this.world.playSoundAtEntity(null, this, "aether:mob.slider.collide", 1.60F + random.nextFloat(), .45F + random.nextFloat());
    }
    // Hurt
    @Override public int getMaxHealth() {
        return 500;
    }
    @Override public boolean isOnFire() {
        return false;
    }
    @Override public void fireHurt() {
        // immune to fire
    }
    @Override public void lavaHurt() {
        // immune to lava
    }
    @Override public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    @SuppressWarnings("java:S6541") public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (!this.world.getDifficulty().canHostileMobsSpawn()) {
            return false;
        }
        if (this.isAwake() && type == DamageType.BLAST) {
            return super.hurt(attacker, damage / 4, type);
        }
        if (!(attacker instanceof Player player)) {
            return false;
        }
        ItemStack item = ((Player) attacker).inventory.getCurrentItem();
        if (item == null || (!(item.getItem() instanceof ItemToolPickaxe) && !(item.getItem() instanceof ItemToolPickaxeAether))) {
            if (!this.isAwake()) {
                String message = "<" + player.getDisplayName() + "> " + I18n.getInstance().translateKey("boss_slider.hit_fail");
                MessageMaker.sendMessage(player, message);
            }
            return false;
        }
        this.tryAwake();
        if (!(player.gamemode.hasHostileMobs())) {
            this.creativeAttackersList.add(player);
        }
        this.target = attacker;
        ((AetherBossList) attacker).aether$TryAddBossList(this);
        this.performDeformation(attacker);
        this.createDamageParticle(damage);
        return super.hurt(attacker, (int) item.getStrVsBlock(AetherBlocks.COBBLE_HOLYSTONE), type);
    }
    // Texture

    @Override public @NonNull String getEntityTexture() {
        if (this.isAwake() && !this.doingSlam()) {
            if (this.isAngry()) {
                return "/assets/aether/textures/entity/boss_slider/slider_awake_red.png";
            }
            return "/assets/aether/textures/entity/boss_slider/slider_awake.png";
        }
        if (this.isAngry()) {
            return "/assets/aether/textures/entity/boss_slider/slider_sleep_red.png";
        }
        return "/assets/aether/textures/entity/boss_slider/slider_sleep.png";
    }
    @Override public @NonNull String getDefaultEntityTexture() {
        return "/assets/aether/textures/entity/boss_slider/slider_awake.png";
    }
    // Particles

    private void createDamageParticle(int damage) {
        for (int i = 0; i < (Math.min(10, damage + this.random.nextInt(2)) * 32) / 10; i++) {
            // it really doesn't matter if they are inverted somewhere... the slider is square.
            float faceX = 2 * this.random.nextFloat();
            float faceY = 2 * this.random.nextFloat();

            float posX;
            float posY;
            float posZ;
            Direction dir = Direction.all[this.random.nextInt(Direction.all.length)];
            switch (dir) {
                case WEST:
                    posX = (float) (this.x - 1);
                    posY = (float) (this.y + faceY);
                    posZ = (float) (this.z - 1 + faceX);
                    break;
                case EAST:
                    posX = (float) (this.x + 1);
                    posY = (float) (this.y + faceY);
                    posZ = (float) (this.z - 1 + faceX);
                    break;
                case SOUTH:
                    posX = (float) (this.x - 1 + faceX);
                    posY = (float) (this.y + faceY);
                    posZ = (float) (this.z + 1);
                    break;
                case NORTH:
                    posX = (float) (this.x - 1 + faceX);
                    posY = (float) (this.y + faceY);
                    posZ = (float) (this.z - 1);
                    break;
                case DOWN:
                    posX = (float) (this.x - 1 + faceX);
                    posY = (float) (this.y);
                    posZ = (float) (this.z - 1 + faceY);
                    break;
                case UP:
                default:
                    posX = (float) (this.x - 1 + faceX);
                    posY = (float) (this.y + 2);
                    posZ = (float) (this.z - 1 + faceY);
                    break;
            }
            ParticleMaker.spawnParticle(this.world, "block", posX, posY, posZ, 0, 0, 0, AetherBlocks.COBBLE_HOLYSTONE.id());
        }
    }
    private void createSlamParticle(int slamRadius) {
        for (int particle = 0; particle < 16; particle++) {
            double explosionX = this.x - slamRadius + this.world.rand.nextInt(slamRadius * 2);
            double explosionY = this.y - slamRadius + this.world.rand.nextInt(slamRadius * 2);
            double explosionZ = this.z - slamRadius + this.world.rand.nextInt(slamRadius * 2);
            doExplosionEffect(this.world, explosionX, explosionY, explosionZ);
        }
    }
    // AABB
    @Override public @Nullable AABBdc getCollisionAABB() {
        return this.bb;
    }
    public float getDeformX() {
        return this.deformX;
    }
    public int getDeformY() {
        return this.deformY;
    }
    public int getDeformZ() {
        return this.deformZ;
    }
    @Override public boolean showBoundingBoxOnHover() {
        return !this.isAwake() && this.getHealth() > 0;
    }
    private void performDeformation(@NonNull Entity attacker) {
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
        this.deformX = 0.7F - this.getHealth() / 875.0F;
    }



    @Override
    public void onDeath(Entity entityKilledBy) {
        this.world.players.stream()
            .filter(player -> player.distanceTo(this) < 32)
            .forEach(p -> p.triggerAchievement(AetherAchievements.BRONZE));
        this.world.playSoundAtEntity(null, this, "aether:achievement.bronze", 0.5f, 1.0f);
        if (!EnvironmentHelper.isMultiplayerServer()) {
            MobBoss.stop();
        }
        super.onDeath(entityKilledBy);
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public boolean isAngry() {
        return ((float) this.getHealth() / this.getMaxHealth()) < ANGER_THRESHOLD;
    }

    public boolean isAwake() {
        return this.currentState != State.ASLEEP;
    }

    @Override public boolean canFight() {
        return isAlive() && this.isAwake();
    }

    public void tryAwake() {
        if (!this.world.getDifficulty().canHostileMobsSpawn() || this.isAwake()) {
            return;
        }
        this.setState(State.AWAKE);
        DungeonMap.runWithDungeon(dungeonID, d -> d.lock(this.world));
        this.world.playSoundAtEntity(null, this, "aether:mob.slider.awaken", 1F, 1F);
        if (!EnvironmentHelper.isMultiplayerServer()) {
            MobBoss.play("aether:aether_music_boss.sliderboss", this.x, this.y, this.z);
        }
        this.wakeUpTimer = WAKEUP_TIMER;
    }

    protected void stateAwake() {
        if (this.world.getClosestPlayerToEntity(this, AetherDimension.BOSS_DETECTION_RADIUS) == null) {
            this.setState(State.ASLEEP);
            this.returnToOriginalState();
            return;
        }
        this.setTarget();
        if (!this.allowedToMove || this.target == null || this.blocksToMove > 0.05F) {
            return;
        }
        float progress = (float) Math.max((float) this.getHealth() / this.getMaxHealth(), .32);
        this.attackCoolDown = (int) Math.floor(MathHelper.lerp(MIN_ATTACK_COOL_DOWN, MAX_ATTACK_COOL_DOWN, progress));
        this.allowedToMove = false;
        if (this.distanceToSqr(this.target) <= 25 && progress < .60F && this.random.nextInt(6) == 0) {
            this.moveDirection = Direction.UP;
            this.blocksToMove = 45;
            this.speed = BASE_SPEED * 2;
            this.attackCoolDown = (int) Math.floor(MathHelper.lerp(MIN_ATTACK_COOL_DOWN, MAX_ATTACK_COOL_DOWN, 0.5));
            this.setState(State.SLAM);
            this.slamGoingDown = false;
            return;
        }
        int moveAmount;
        this.moveDirection = this.calculateDirection(this.target);
        moveAmount = this.getMoveAmount();
        this.blocksToMove = Math.min(25, moveAmount + (this.sameAxis() ? 3 : 0));
        this.world.playSoundAtEntity(null, this, "aether:mob.slider.move", 1.60F + this.random.nextFloat(), .45F + this.random.nextFloat());
    }

    private int getMoveAmount() {
        if(this.target == null){
            return 0;
        }
        return switch (this.moveDirection) {
            case EAST, WEST -> (int) Math.abs(this.x - this.target.x);
            case DOWN, UP -> (int) Math.abs(this.y - this.target.y);
            case NORTH, SOUTH -> (int) Math.abs(this.z - this.target.z);
            default -> 0;
        };
    }

    private void setTarget() {
        if (this.target != null && world.rand.nextInt(10) != 0) {
            if (this.distanceToSqr(this.target) <= AetherDimension.BOSS_DETECTION_RANGE_SQR) {
                return;
            }
            this.target = null;
        }
        this.target = findPlayerToAttack();
        if (this.creativeAttackersList.isEmpty()) {
            return;
        }
        this.target = this.creativeAttackersList.get(0);
        if (this.target == null) {
            return;
        }
        for (Player player : this.creativeAttackersList) {
            if (this.distanceToSqr(player) < this.distanceToSqr(this.target)) {
                this.target = player;
            }
        }
    }

    protected void stateAsleep() {/* ZZZ... */}

    @SuppressWarnings("java:S131")
    protected void stateSlam() {
        if (this.allowedToMove && !this.slamGoingDown) {
            this.slamGoingDown = true;
            // intellij is being dumb here, but this is so slamY re-initializes every move.
            // it'd be better to call prologue functions when changing state. Oh, well.
            this.slamY = -1;
            this.moveDirection = Direction.DOWN;
            this.blocksToMove = 999;
        } else if (this.allowedToMove && this.slamY == this.y) {
            final int slamRadius = 5;
            final float launchSpeed = 0.75F;
            final AABBdc boundingBox = new AABBd(this.x - slamRadius, this.y, this.z - slamRadius, this.x + slamRadius, this.y + slamRadius, this.z + slamRadius);
            List<Entity> list = this.world.getEntitiesWithinAABB(Entity.class, boundingBox);
            for (Entity entity : list) {
                MobUtil.multiHit(this, entity,
                    inst((int) Math.floor((BASE_DAMAGE * 0.50F) * getAngerModifier()), DamageType.FALL),
                    inst((int) Math.floor((BASE_DAMAGE * 0.75F) * getAngerModifier()), DamageType.COMBAT)
                );
                switch (calculateDirection(entity)) {
                    case NORTH -> entity.push(0, launchSpeed / 2, -launchSpeed);
                    case SOUTH -> entity.push(0, launchSpeed / 2, launchSpeed);
                    case EAST -> entity.push(launchSpeed, launchSpeed / 2, 0);
                    case WEST -> entity.push(-launchSpeed, launchSpeed / 2, 0);
                }
                MobBossSlider.doExplosionEffect(entity.world, entity.x, entity.y, entity.z);
            }
            this.createSlamParticle(slamRadius);
            this.blocksToMove = 0;
            this.moveDirection = Direction.NONE;
            this.setState(State.AWAKE);
            this.speed = BASE_SPEED;
            this.attackCoolDown = MAX_ATTACK_COOL_DOWN;
        }
        this.slamY = this.y;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean doingSlam() {
        return this.currentState == State.SLAM;
    }

    private void lerpSlider() {
        if (this.newPosRotationIncrements > 0) {
            double lerpXD = this.x + (this.newPosX - this.x) / this.newPosRotationIncrements;
            double lerpYD = this.y + (this.newPosY - this.y) / this.newPosRotationIncrements;
            double lerpZD = this.z + (this.newPosZ - this.z) / this.newPosRotationIncrements;
            double lerpYRot = this.newRotationYaw - this.yRot;
            double lerpXRot = this.newRotationPitch - this.xRot;
            for (;lerpYRot < -180.0F; lerpYRot += 360.0F);
            for (;lerpYRot >= 180.0F; lerpYRot -= 360.0F);
            this.yRot = (float) (this.yRot + lerpYRot / this.newPosRotationIncrements);
            this.xRot = (float) (this.xRot + lerpXRot / this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPos(lerpXD, lerpYD, lerpZD);
            this.setRot(this.yRot, this.xRot);
        }
    }

    @Override
    public boolean collidesWith(Entity entity) {
        if (0.25F >= blocksToMove) {
            return super.collidesWith(entity);
        }
        if (entity instanceof Player player) {
            if (!player.gamemode.hasInvulnerablePlayer()) {
                MobUtil.multiHit(this, entity,
                    inst((int) Math.floor(BASE_DAMAGE * getAngerModifier()), DamageType.FALL),
                    inst((int) Math.floor((BASE_DAMAGE * 0.50F) * getAngerModifier()), DamageType.COMBAT)
                );
            }
            return super.collidesWith(entity);
        }
        MobBossSlider.doExplosionEffect(entity.world, entity.x, entity.y, entity.z);
        this.playCollidingSound();
        return super.collidesWith(entity);
    }

    @Override
    public boolean isMovementBlocked() {
        return super.isMovementBlocked() || !isAwake();
    }

    public static void doExplosionEffect(World world, double x, double y, double z) {
        for (int particle = 0; particle < 16; particle++) {
            double xParticle = x + 0.5 + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            double yParticle = y + 0.5 + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            double zParticle = z + 0.5 + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            ParticleMaker.spawnParticle(world, "explode", xParticle, yParticle, zParticle, 0, 0, 0, 0);
        }
        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "random.explode", 0.5F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
    }

    public float getAngerModifier() {
        return 1.0F + ((float) (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth());
    }

    @Override
    @SuppressWarnings("java:S6541")
    public void tick() {
        super.baseTick();
        if (!this.world.getDifficulty().canHostileMobsSpawn()) {
            if (this.isAwake()) {
                this.setState(State.ASLEEP);
                this.returnToOriginalState();
            }
            return;
        }
        this.lerpSlider();
        int blocksBroken = this.getBlocksBroken();
        if (blocksBroken >= 9) {
            this.allowedToMove = false;
            this.attackCoolDown = MAX_ATTACK_COOL_DOWN;
            return;
        }
        this.moveSlider();
        this.collideWithEntity();
        if (blocksToMove <= 0.05F) {
            this.yo = this.y;
            this.xo = this.x;
            this.zo = this.z;
        }
        if (this.deformX > 0.01F) {
            this.deformX *= 0.8F;
        }
        if (!EnvironmentHelper.isMultiplayerClient()) {
            if (--attackCoolDown <= 0) {
                allowedToMove = true;
            }
            this.currentState.getConsumer().accept(this);
        }
        this.updateEntityData();
        if (this.isAwake()) {
            this.wakeUpTimer--;
        }
    }

    private void collideWithEntity() {
        // collide with entities
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, MathHelper.aabbGrow(this.bb, 0.2, 0.0F, 0.2, new AABBd()));
        if (list.isEmpty()) {
            return;
        }
        for (Entity entity : list) {
            if (entity.isPushable()) {
                entity.push(this);
            }
        }
    }

    @Override
    public Player findPlayerToAttack() {
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 32.0F);
        if (entityplayer == null) return null;
        if (!this.canEntityBeSeen(entityplayer) || !entityplayer.gamemode.hasHostileMobs()) {
            return null;
        }
        ((AetherBossList) entityplayer).aether$TryAddBossList(this);
        return entityplayer;
    }

    private void moveSlider() {
        if (!this.isAwake() || this.wakeUpTimer > 0 || this.moveDirection == Direction.NONE) {
            this.blocksToMove = 0;
            return;
        }
        float moveAmount = this.speed / TICKS_PER_SECOND;
        if (moveAmount >= this.blocksToMove) {
            moveAmount = this.blocksToMove;
        }
        float dx = moveAmount * this.moveDirection.offsetX();
        float dy = moveAmount * this.moveDirection.offsetY();
        float dz = moveAmount * this.moveDirection.offsetZ();
        this.move(dx, dy, dz);
        this.blocksToMove -= moveAmount;
    }

    private int getBlocksBroken() {
        int blocksBroken = 0;
        if (this.blocksToMove <= 0) {
            return 0;
        }
        final AABBdc aabb = this.bb;// not quite correct.
        int minX = MathHelper.floor(aabb.minX());
        int maxX = MathHelper.floor(aabb.maxX() + 1.0F);
        int minY = MathHelper.floor(aabb.minY());
        int maxY = MathHelper.floor(aabb.maxY() + 1.0F);
        int minZ = MathHelper.floor(aabb.minZ());
        int maxZ = MathHelper.floor(aabb.maxZ() + 1.0F);

        if (this.moveDirection.offsetX() > 0) {
            maxX += this.moveDirection.offsetX();
        } else {
            minX -= this.moveDirection.offsetX();
        }

        if (this.moveDirection.offsetY() > 0) {
            maxY += this.moveDirection.offsetY();
        } else{
            minY -= this.moveDirection.offsetY();
        }

        if (this.moveDirection.offsetZ() > 0) {
            maxZ += this.moveDirection.offsetZ();
        } else{
            minZ -= this.moveDirection.offsetZ();
        }

        TilePos tilePos = new TilePos(0, 0, 0);
        for (tilePos.x = minX; tilePos.x < maxX; ++tilePos.x) {
            for (tilePos.z = minZ; tilePos.z < maxZ; ++tilePos.z) {
                for (tilePos.y = minY; tilePos.y < maxY; ++tilePos.y) {
                    Block<?> block = this.world.getBlockType(tilePos);
                    if (this.canBreakBlock(block)) {
                        continue;
                    }
                    block.dropWithCause(world, EnumDropCause.EXPLOSION, tilePos, world.getBlockData(tilePos), world.getTileEntity(tilePos), null);
                    this.world.playBlockEvent(tilePos, LevelListener.EVENT_BLOCK_BREAK, block.id());
                    this.world.setBlockTypeDataNotify(tilePos, Blocks.AIR, 0);
                    this.blocksToMove -= 0.5F * Math.min(block.getHardness() / 3f, 1);
                    blocksBroken++;
                }

            }
        }
        return blocksBroken;
    }

    private boolean canBreakBlock(Block<?> block) {
        return block == Blocks.AIR ||
            block.getLogic() instanceof BlockLogicTrapped ||
            block.getLogic() instanceof BlockLogicLocked ||
            block.getLogic() instanceof BlockLogicDungeonDoor ||
            block.getLogic() instanceof BlockLogicChestLocked ||
            block.getMaterial() instanceof MaterialLiquid ||
            block.getHardness() <= 0;
    }


    /// this following functions is the single most annoying solution in this class.
    /// If you know better than me, please replace it with something decent. -Khep
    /// After a small change it looks fine to me -Redart15
    public Direction calculateDirection(@NonNull Entity entity) {
        double deltaX = this.x  - entity.x;
        double deltaZ = this.z  - entity.z;
        double deltaY = this.y  - entity.y;
        double absX = Math.abs(deltaX);
        double absZ = Math.abs(deltaZ);
        double absY = Math.abs(deltaY);
        if (absX > absZ && absX > absY) {
            return deltaX < 0 ? Direction.EAST : Direction.WEST;
        }
        if (absZ > absY) {
            return deltaZ < 0 ? Direction.SOUTH : Direction.NORTH;
        }
        return deltaY < 0 ? Direction.UP : Direction.DOWN;
    }

    public boolean sameAxis() {
        if(this.target == null){
            return false;
        }
        final AABBdc asThis = this.bb;
        final AABBdc asTarget = this.bb;
        final boolean axisX = asThis.minX() < asTarget.maxX() && asThis.maxX()  > asTarget.minX();
        final boolean axisZ = asThis.minZ() < asTarget.maxZ() && asThis.maxZ()  > asTarget.minZ();
        final boolean axisY = asThis.minY() < asTarget.maxY() && asThis.maxY()  > asTarget.minY();
        return switch (this.moveDirection.axis()) {
            case Y -> axisX && axisZ;
            case X -> axisY && axisZ;
            case Z -> axisX && axisY;
            default -> false;
        };
    }

    private double sliderWeight(WorldSource world, TilePosc pos, BoundingBoxSize size) {
        int minX = pos.x() - size.width() / 2;
        int minY = pos.y();
        int minZ = pos.z() - size.length() / 2;
        int maxX = minX + size.width();
        int maxY = minY + size.height();
        int maxZ = minZ + size.length();
        TilePos check = new TilePos(0, 0, 0);
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    check.x = x;
                    check.y = y;
                    check.z = z;
                    Block<?> block = world.getBlockType(check);
                    if (!canBreakBlock(block)) {
                        return Double.POSITIVE_INFINITY;
                    }
                }
            }
        }
        return 1.0;
    }
}
