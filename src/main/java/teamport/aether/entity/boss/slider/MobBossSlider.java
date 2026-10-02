package teamport.aether.entity.boss.slider;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.ICollidable;
import net.minecraft.core.entity.Mob;
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
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.dungeon.BlockLogicTrapped;
import teamport.aether.entity.MobUtil;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.entity.boss.MobBoss;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.pathing.base.BoundingBoxSize;
import teamport.aether.entity.pathing.base.Node;
import teamport.aether.entity.pathing.base.Path;
import teamport.aether.entity.pathing.boss.SliderPathFinder;
import teamport.aether.entity.player.MessageMaker;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.item_tool.ItemToolPickaxeAether;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.util.WorldFeatureBlock;
import teamport.aether.world.feature.util.map.DungeonMap;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.core.Global.TICKS_PER_SECOND;
import static teamport.aether.entity.DamageInstance.inst;

public class MobBossSlider extends MobBoss implements ICollidable {
    public static final Block<?> SLIDER_BLOCK = AetherBlocks.COBBLE_HOLYSTONE;
    private State currentState = State.ASLEEP;

    /// movement
    @NonNull
    private Direction moveDirection = Direction.NONE;
    public static final float BASE_SPEED = 15;
    private double blocksToMove = 0;
    private boolean allowedToMove;
    /// attack
    private int attackCoolDown = 0;
    public static final float ANGER_THRESHOLD = 0.50F;
    public static final float BASE_DAMAGE = 10F;
    public static final int MAX_ATTACK_COOL_DOWN = 50;
    public static final int MIN_ATTACK_COOL_DOWN = 10;
    public static final int MAX_MOVE_DISTANCE = 25;
    /// wakeup timer
    public static final int WAKEUP_TIMER = 12;
    public int wakeUpTimer = 0;
    /// slam
    private double slamY = -1;
    private boolean slamGoingDown = false;
    /// deformation when hit
    private float deformX;
    private int deformY;
    private int deformZ;
    /// sync data defaults
    static final int DATA_STATE = 17;
    static final int DATA_ALLOW_MOVEMENT = 18;
    static final int DATA_MOVEMENT_DIRECTION = 19;
    static final int DATA_MOVEMENT_AMOUNT = 20;
    /// target list
    private final List<Player> creativeAttackersList = new ArrayList<>();
    /// sentry spawn
    int sentryCoolDown = 0;
    int currentPlayerCount = 0;
    public static final int SENTRY_TIMER = 15 * TICKS_PER_SECOND;

    ///  pathing 2.0
    Path path = null;
    SliderPathFinder pathFinder;


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
        this.pathFinder = new SliderPathFinder(this, this::getBlockPathWeight);
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
        tag.putInt("sentryCooldown", this.sentryCoolDown);
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
        this.sentryCoolDown = tag.getInteger("sentryCooldown");
        super.readAdditionalSaveData(tag);
    }

    private void updateEntityData() {
        if (EnvironmentHelper.isMultiplayerServer()) {
            entityData.set(DATA_STATE, currentState.ordinal());
            entityData.set(DATA_ALLOW_MOVEMENT, allowedToMove ? 1 : 0);
            entityData.set(DATA_MOVEMENT_DIRECTION, moveDirection.ordinal());
            entityData.set(DATA_MOVEMENT_AMOUNT, Float.floatToIntBits((float) blocksToMove));
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
    @Override
    public int getAmbientSoundInterval() {
        return 40 * TICKS_PER_SECOND;
    }

    @Override
    public String getLivingSound() {
        return "ambient.cave.cave";
    }

    @Override
    public void playLivingSound() {
        if (this.currentState != State.ASLEEP) return;
        this.world.playSoundAtEntity(null, this, this.getLivingSound(), 1.0F, 1.0f);
    }

    @Override
    public String getHurtSound() {
        return "step.stone";
    }

    @Override
    public String getDeathSound() {
        return "aether:mob.slider.death";
    }

    private void playCollidingSound() {
        this.world.playSoundAtEntity(null, this, "aether:mob.slider.collide", 1.60F + random.nextFloat(), .45F + random.nextFloat());
    }

    // Hurt
    @Override
    public int getMaxHealth() {
        return 500;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public void fireHurt() {
        // immune to fire
    }

    @Override
    public void lavaHurt() {
        // immune to lava
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    @SuppressWarnings("java:S6541")
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (!this.world.getDifficulty().canHostileMobsSpawn()) {
            return false;
        }
        if (this.isAwake() && type == DamageType.BLAST) {
            return super.hurt(attacker, damage / 4, type);
        }
        if (!(attacker instanceof Player player)) {
            return false;
        }
        ItemStack item = player.inventory.getCurrentItem();
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
        return super.hurt(attacker, (int) item.getStrVsBlock(SLIDER_BLOCK), type);
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
        this.moveDirection = Direction.NONE;
        this.allowedToMove = false;
        this.blocksToMove = 0;
        this.sentryCoolDown = 0;
        this.attackCoolDown = MAX_ATTACK_COOL_DOWN;
        super.onDeath(entityKilledBy);
    }

    // Texture
    @Override
    public @NonNull String getEntityTexture() {
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

    @Override
    public @NonNull String getDefaultEntityTexture() {
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
            ParticleMaker.spawnParticle(this.world, "block", posX, posY, posZ, 0, 0, 0, SLIDER_BLOCK.id());
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

    public static void doExplosionEffect(World world, double x, double y, double z) {
        for (int particle = 0; particle < 16; particle++) {
            double xParticle = x + 0.5 + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            double yParticle = y + 0.5 + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            double zParticle = z + 0.5 + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            ParticleMaker.spawnParticle(world, "explode", xParticle, yParticle, zParticle, 0, 0, 0, 0);
        }
        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "random.explode", 0.5F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
    }

    private void spawnDeathParticles() {
        double px = this.bb.minX + this.random.nextDouble() * this.bbWidth;
        double py = this.bb.minY + this.random.nextDouble() * this.bbHeight;
        double pz = this.bb.minZ + this.random.nextDouble() * this.bbWidth;
        Vector3d vec = new Vector3d(px, py, pz);
        vec.sub(this.x, this.y + this.bbHeight / 2.0D, this.z);
        if(vec.length() > 0.0D){
            vec.normalize();
            vec.mul(0.05D);
        }
        ParticleMaker.spawnParticle(this.world, "block", px, py, pz, vec.x(), vec.y(), vec.z(), AetherBlocks.COBBLE_HOLYSTONE.id());
    }

    // AABB
    @Override
    public @Nullable AABBdc getCollisionAABB() {
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

    @Override
    public boolean showBoundingBoxOnHover() {
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
    public boolean collidesWith(Entity entity) {
        if (0.25F >= blocksToMove) {
            return super.collidesWith(entity);
        }
        if (entity instanceof Mob) {
            if (entity instanceof Player player && player.gamemode.hasInvulnerablePlayer()) {
                return super.collidesWith(entity);
            }
            MobUtil.multiHit(this, entity,
                inst((int) Math.floor(BASE_DAMAGE * getAngerModifier()), DamageType.FALL),
                inst((int) Math.floor((BASE_DAMAGE * 0.50F) * getAngerModifier()), DamageType.COMBAT)
            );
        }
        MobBossSlider.doExplosionEffect(entity.world, entity.x, entity.y, entity.z);
        this.playCollidingSound();
        return super.collidesWith(entity);
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

    // State
    public void setState(State state) {
        this.currentState = state;
    }

    protected void stateAsleep() {/* ZZZ... */}

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

    public boolean isAwake() {
        return this.currentState != State.ASLEEP;
    }

    public boolean isAngry() {
        return ((float) this.getHealth() / this.getMaxHealth()) < ANGER_THRESHOLD;
    }

    @Override
    public boolean canFight() {
        return isAlive() && this.isAwake();
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
        if (this.distanceToSqr(this.target) <= MAX_MOVE_DISTANCE && progress < .60F && this.random.nextInt(6) == 0) {
            this.moveDirection = Direction.UP;
            this.blocksToMove = 45;
            this.speed = BASE_SPEED * 2;
            this.attackCoolDown = (int) Math.floor(MathHelper.lerp(MIN_ATTACK_COOL_DOWN, MAX_ATTACK_COOL_DOWN, 0.5));
            this.setState(State.SLAM);
            this.slamGoingDown = false;
            return;
        }
        this.getNext();
        this.world.playSoundAtEntity(null, this, "aether:mob.slider.move", 1.60F + this.random.nextFloat(), .45F + this.random.nextFloat());
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

    private void getNext() {
        this.path = MobUtil.getPath(this.world, this.target, this.pathFinder, MAX_MOVE_DISTANCE);
        if (this.path == null) {
            this.getNextOld();
            return;
        }
        Node wp = this.path.next();
        double dx = wp.x() - this.bb.minX;
        double dy = wp.y() - this.bb.minY;
        double dz = wp.z() - this.bb.minZ;
        this.setDirectionAndMovementAmount(dx, dy, dz);
        this.blocksToMove = Math.min(MAX_MOVE_DISTANCE, this.blocksToMove + (this.sameAxis() ? 3 : 0));
    }

    private void getNextOld() {
        assert this.target != null;
        this.moveDirection = this.calculateDirection(new Vector3d(this.target.x, this.target.y, this.target.z));
        int moveAmount = switch (this.moveDirection) {
            case EAST, WEST -> (int) Math.abs(this.x - this.target.x);
            case DOWN, UP -> (int) Math.abs(this.y - this.target.y);
            case NORTH, SOUTH -> (int) Math.abs(this.z - this.target.z);
            default -> 0;
        };
        this.blocksToMove = Math.min(MAX_MOVE_DISTANCE, moveAmount + (this.sameAxis() ? 3 : 0));
    }

    private void setDirectionAndMovementAmount(double dx, double dy, double dz) {
        if (dx != 0) {
            this.moveDirection = dx > 0 ? Direction.EAST : Direction.WEST;
            this.blocksToMove = Math.abs(dx);
            return;
        }
        if (dy != 0) {
            this.moveDirection = dy > 0 ? Direction.UP : Direction.DOWN;
            this.blocksToMove = Math.abs(dy);
            return;
        }
        if (dz != 0) {
            this.moveDirection = dz > 0 ? Direction.SOUTH : Direction.NORTH;
            this.blocksToMove = Math.abs(dz);
            return;
        }
        this.moveDirection = Direction.NONE;
        this.blocksToMove = 0;
    }

    public float getAngerModifier() {
        return 1.0F + ((float) (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth());
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean doingSlam() {
        return this.currentState == State.SLAM;
    }

    @SuppressWarnings("java:S131")
    protected void stateSlam() {
        if (this.allowedToMove && !this.slamGoingDown) {
            this.slamGoingDown = true;
            // intellij is being dumb here, but this is so slamY re-initializes every move.
            // it'd be better to call prologue functions when changing state. Oh, well.
            this.slamY = -1;
            this.moveDirection = Direction.DOWN;
            this.blocksToMove = 45;
        } else if (this.allowedToMove && this.slamY == this.y) {
            final int slamRadius = 5;
            final float launchSpeed = 0.75F;
            // this might be a bit to much for now
//            MobUtil.doDestroyBlockEffect(this.world, this.x, this.y, this.z);
            final AABBdc boundingBox = new AABBd(this.x - slamRadius, this.y, this.z - slamRadius, this.x + slamRadius, this.y + slamRadius, this.z + slamRadius);
            List<Entity> list = this.world.getEntitiesWithinAABB(Entity.class, boundingBox);
            for (Entity entity : list) {
                MobUtil.multiHit(this, entity,
                    inst((int) Math.floor((BASE_DAMAGE * 0.50F) * getAngerModifier()), DamageType.FALL),
                    inst((int) Math.floor((BASE_DAMAGE * 0.75F) * getAngerModifier()), DamageType.COMBAT)
                );
                switch (calculateDirection(new Vector3d(entity.x, entity.y, entity.z))) {
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


    @Override
    @SuppressWarnings("java:S6541")
    public void tick() {
        super.baseTick();
        if (!this.noAI) {
            if (!this.world.getDifficulty().canHostileMobsSpawn()) {
                if (this.isAwake()) {
                    this.setState(State.ASLEEP);
                    this.returnToOriginalState();
                }
                return;
            }

            this.lerpSlider();
            this.breakBlockAheadOfAABB();
            this.moveSlider();
            this.collideWithEntity();
            this.updateO();
            this.spawnSentries();
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
            if (this.deathTime > 0) {
                for (int i = 0; i < 32; i++) {
                    this.spawnDeathParticles();
                }
            }
        }
        if (this.deformX > 0.01F) {
            this.deformX *= 0.8F;
        }
    }


    // MOVEMENT
    @Override
    public boolean isMovementBlocked() {
        return super.isMovementBlocked() || !isAwake();
    }

    private void lerpSlider() {
        if (this.newPosRotationIncrements > 0) {
            double lerpXD = this.x + (this.newPosX - this.x) / this.newPosRotationIncrements;
            double lerpYD = this.y + (this.newPosY - this.y) / this.newPosRotationIncrements;
            double lerpZD = this.z + (this.newPosZ - this.z) / this.newPosRotationIncrements;
            double lerpYRot = this.newRotationYaw - this.yRot;
            double lerpXRot = this.newRotationPitch - this.xRot;
            for (; lerpYRot < -180.0F; lerpYRot += 360.0F) ;
            for (; lerpYRot >= 180.0F; lerpYRot -= 360.0F) ;
            this.yRot = (float) (this.yRot + lerpYRot / this.newPosRotationIncrements);
            this.xRot = (float) (this.xRot + lerpXRot / this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPos(lerpXD, lerpYD, lerpZD);
            this.setRot(this.yRot, this.xRot);
        }
    }

    private void breakBlockAheadOfAABB() {
        if (this.blocksToMove <= 0 || this.getHealth() <= 0) {
            return;
        }
        final double EPS = 1e-6;

        int minX = MathHelper.floor(this.bb.minX() + EPS);
        int maxX = MathHelper.floor(this.bb.maxX() - EPS);
        int minY = MathHelper.floor(this.bb.minY() + EPS);
        int maxY = MathHelper.floor(this.bb.maxY() - EPS);
        int minZ = MathHelper.floor(this.bb.minZ() + EPS);
        int maxZ = MathHelper.floor(this.bb.maxZ() - EPS);
        int dx = this.moveDirection.offsetX();
        int dy = this.moveDirection.offsetY();
        int dz = this.moveDirection.offsetZ();
        minX += Math.min(dx, 0);
        maxX += Math.max(dx, 0);
        minY += Math.min(dy, 0);
        maxY += Math.max(dy, 0);
        minZ += Math.min(dz, 0);
        maxZ += Math.max(dz, 0);
        List<WorldFeatureBlock> blockInAABB = EnemyBoss.blockCollidingWithAABB(this.world, minX, minY, minZ, maxX, maxY, maxZ);
        TilePos tilePos = new TilePos(0, 0, 0);
        for (WorldFeatureBlock wfb : blockInAABB) {
            Block<?> block = Blocks.getBlock(wfb.getBlockId());
            tilePos.set(wfb.getX(), wfb.getY(), wfb.getZ());
            block.dropWithCause(world, EnumDropCause.EXPLOSION, tilePos, world.getBlockData(tilePos), world.getTileEntity(tilePos), null);
            world.playBlockEvent(tilePos, LevelListener.EVENT_BLOCK_BREAK, block.id());
            world.setBlockTypeDataNotify(tilePos, Blocks.AIR, 0);
            this.blocksToMove -= 0.5F * Math.min(block.getHardness() / 3f, 1);
        }
    }

    private void moveSlider() {
        if (!this.isAwake() || this.wakeUpTimer > 0 || this.moveDirection == Direction.NONE) {
            this.blocksToMove = 0;
            return;
        }
        double moveAmount = this.speed / TICKS_PER_SECOND;
        if (moveAmount >= this.blocksToMove) {
            moveAmount = this.blocksToMove;
        }
        double dx = moveAmount * this.moveDirection.offsetX();
        double dy = moveAmount * this.moveDirection.offsetY();
        double dz = moveAmount * this.moveDirection.offsetZ();
        this.move(dx, dy, dz);
        this.blocksToMove -= moveAmount;
    }

    public boolean sameAxis() {
        if (this.target == null) {
            return false;
        }
        final AABBdc asThis = this.bb;
        final AABBdc asTarget = this.target.bb;
        final boolean axisY = asThis.minY() < asTarget.maxY() && asThis.maxY() > asTarget.minY();
        final boolean axisX = asThis.minX() < asTarget.maxX() && asThis.maxX() > asTarget.minX();
        final boolean axisZ = asThis.minZ() < asTarget.maxZ() && asThis.maxZ() > asTarget.minZ();
        return switch (this.moveDirection.axis()) {
            case Y -> axisX && axisZ;
            case X -> axisY && axisZ;
            case Z -> axisX && axisY;
            default -> false;
        };
    }

    private void updateO() {
        if (blocksToMove <= 0.05F) {
            this.yo = this.y;
            this.xo = this.x;
            this.zo = this.z;
        }
    }

    /// this following functions is the single most annoying solution in this class.
    /// If you know better than me, please replace it with something decent. -Khep
    /// After a small change it looks fine to me -Redart15
    public Direction calculateDirection(@NonNull Vector3dc vec) {
        double deltaX = this.x - vec.x();
        double deltaZ = this.z - vec.z();
        double deltaY = this.y - vec.y();
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

    private double getBlockPathWeight(WorldSource world, Vector3dc pos, BoundingBoxSize size) {
        AABBd aabb = new AABBd();
        double maxX = pos.x() + size.width();
        double maxY = pos.y() + size.height();
        double maxZ = pos.z() + size.length();
        aabb.setMin(pos.x(), pos.y(), pos.z()).setMax(maxX, maxY, maxZ);
        int minX = (int) Math.floor(aabb.minX());
        int maxX1 = (int) Math.floor(aabb.maxX() + 1.0D);
        int minY = (int) Math.floor(aabb.minY());
        int maxY1 = (int) Math.floor(aabb.maxY() + 1.0D);
        int minZ = (int) Math.floor(aabb.minZ());
        int maxZ1 = (int) Math.floor(aabb.maxZ() + 1.0D);
        TilePos tilePos = new TilePos(0, 0, 0);
        for (tilePos.x = minX; tilePos.x <= maxX1; ++tilePos.x) {
            for (tilePos.z = minZ; tilePos.z <= maxZ1; ++tilePos.z) {
                for (tilePos.y = minY; tilePos.y <= maxY1; ++tilePos.y) {
                    Block<?> block = world.getBlockType(tilePos);
                    if (EnemyBoss.cannotBreakBlock(block) && block != Blocks.AIR) {
                        return Double.POSITIVE_INFINITY;
                    }
                }
            }
        }
        return 1.0;
    }


    private void spawnSentries() {
        if (this.currentState != State.AWAKE) {
            return;
        }
        sentryCoolDown++;
        if (this.sentryCoolDown % (5 * TICKS_PER_SECOND) == 0) {
            // sampling player count
            List<Player> playerList = this.world.getPlayersWithinRange(this.x, this.y, this.z, 24.24871130596428);
            this.currentPlayerCount = playerList.isEmpty() ? 0 : playerList.size();
        }
        if (sentryCoolDown <= SENTRY_TIMER) {
            return;
        }
        List<Player> playerList = this.world.getPlayersWithinRange(this.x, this.y, this.z, 24.24871130596428);
        int count = Math.abs(playerList.size() - this.currentPlayerCount) <= 1 ? playerList.size() : this.currentPlayerCount;
        this.sentryCoolDown = Math.min(2, count - 1) * 5 * TICKS_PER_SECOND;
        List<MobSentry> mobSentries = this.world.getEntitiesWithinRadius(MobSentry.class, this.x, this.y, this.z, MAX_MOVE_DISTANCE);
        if (mobSentries.size() > 8) {
            return;
        }
        for (int i = 0; i < 5; i++) {
            MobSentry sentry = EntityDispatcher.getInstance().createEntityInWorld(MobSentry.class, this.world);
            if (sentry == null) {
                continue;
            }
            sentry.spawnInit();
            int tries = 16;
            while (tries-- > 0) {
                final double angleRad = Math.toRadians(world.rand.nextInt(360));
                double spawnX = x + 6 * Math.cos(angleRad);
                double spawnZ = z + 6 * Math.sin(angleRad);
                double spawnY = y + 0.5;
                sentry.moveTo(spawnX, spawnY, spawnZ, 0.0f, 0.0f);
                if (world.getCubes(sentry, sentry.bb).isEmpty()) {
                    this.world.entityJoinedWorld(sentry);
                    BlockLogicTrapped.spawnParticles(world, spawnX, spawnY + 0.25, spawnZ);
                    break;
                }
            }
        }

    }
}
