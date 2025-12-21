package teamport.aether.entity.boss.slider;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.client.Minecraft;
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
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
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
import teamport.aether.entity.player.MessageMaker;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.item_tool.ItemToolPickaxeAether;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.core.Global.TICKS_PER_SECOND;
import static teamport.aether.entity.DamageInstance.inst;
import static teamport.aether.world.feature.util.map.DungeonMap.runWithDungeon;

public class MobBossSlider extends MobBoss {
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
    public static final int WAKEUP_TIMER = 14;
    public int wakeUpTimer = 0;
    private double slamY = -1;
    private boolean slamGoingDown = false;
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
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_slider");
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
        if (this.currentState != State.ASLEEP || this.world == null) return;
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
        if (this.world != null) {
            this.world.playSoundAtEntity(null, this, "aether:mob.slider.collide", 1.60F + random.nextFloat(), .45F + random.nextFloat());
        }
    }

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
    public String getEntityTexture() {
        if (this.isAwake() && !this.doingSlam() && this.wakeUpTimer <= 0) {
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

    private void createDamageParticle(int damage) {
        for (int i = 0; i < (Math.min(10, damage + this.random.nextInt(2)) * 32) / 10; i++) {
            // it really doesn't matter if they are inverted somewhere... the slider is square.
            float faceX = 2 * this.random.nextFloat();
            float faceY = 2 * this.random.nextFloat();

            float posX;
            float posY;
            float posZ;
            Direction dir = Direction.directions[this.random.nextInt(Direction.directions.length)];
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

    @Override
    public AABB getBb() {
        return this.bb.copy();
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

    @Override
    public void onDeath(Entity entityKilledBy) {
        if (this.world == null) return;
        this.world.players.stream()
            .filter(player -> player.distanceTo(this) < 32)
            .forEach(p -> {
                p.triggerAchievement(AetherAchievements.BRONZE);
                this.world.playSoundEffect(p, SoundCategory.WORLD_SOUNDS, p.x, p.y, p.z, "aether:achievement.bronze", 0.5f, 1.0f);
            });

        if (!EnvironmentHelper.isServerEnvironment()) {
            Minecraft.getMinecraft().sndManager.stopMusic();
        }

        super.onDeath(entityKilledBy);
    }

    @SuppressWarnings("java:S6541")
    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker == null && type == null && damage == 100) {
            return MobUtil.killMob(this);
        }
        if (this.world != null && !this.world.getDifficulty().canHostileMobsSpawn()) {
            return false;
        }
        if (this.isAwake() && type == DamageType.BLAST) {
            return super.hurt(attacker, damage / 4, type);
        }
        if (!(attacker instanceof Player)) {
            return false;
        }
        ItemStack item = ((Player) attacker).inventory.getCurrentItem();
        if (item == null || (!(item.getItem() instanceof ItemToolPickaxe) && !(item.getItem() instanceof ItemToolPickaxeAether))) {
            if (!this.isAwake()) {
                String message = "<" + ((Player) attacker).getDisplayName() + "> " + I18n.getInstance().translateKey("boss_slider.hit_fail");
                MessageMaker.sendMessage((Player) attacker, message);
            }
            return false;
        }
        this.tryAwake();
        if (!((Player) attacker).gamemode.areMobsHostile()) {
            this.creativeAttackersList.add((Player) attacker);
        }
        this.target = attacker;
        ((AetherBossList) attacker).aether$TryAddBossList(this);
        this.performDeformation(attacker);
        this.createDamageParticle(damage);
        return super.hurt(attacker, (int) item.getStrVsBlock(AetherBlocks.COBBLE_HOLYSTONE), type);
    }

    private void performDeformation(Entity attacker) {
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
    public boolean canFight() {
        return isAlive() && this.isAwake();
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

    public void tryAwake() {
        if (this.world == null) {
            return;
        }
        if (!this.world.getDifficulty().canHostileMobsSpawn()) {
            return;
        }
        if (!this.isAwake()) {
            this.setState(State.AWAKE);
            runWithDungeon(dungeonID, d -> d.lock(this.world));
            this.world.playSoundAtEntity(null, this, "aether:mob.slider.awaken", 1F, 1F);

            if (!EnvironmentHelper.isServerEnvironment()) {
                Minecraft.getMinecraft().sndManager.stopMusic();
                Minecraft.getMinecraft().sndManager.playMusic("aether:aether_music_boss.sliderboss", (float) this.x, (float) this.y, (float) this.z, 1.0F, 1.0F);
            }

            this.wakeUpTimer = WAKEUP_TIMER;
        }
    }

    protected void stateAwake() {
        if (this.world == null) {
            return;
        }
        if (this.world.getClosestPlayerToEntity(this, AetherDimension.BOSS_DETECTION_RADIUS) == null) {
            this.setState(State.ASLEEP);
            returnToOriginalState();
        }
        if (this.target == null || world.rand.nextInt(10) == 0) {
            this.target = findPlayerToAttack();
            if (!this.creativeAttackersList.isEmpty()) {
                this.target = this.creativeAttackersList.get(0);
                for (Player player : this.creativeAttackersList) {
                    if (this.distanceToSqr(player) < this.distanceToSqr(this.target)) {
                        this.target = player;
                    }
                }
            }
        } else if (this.distanceToSqr(this.target) > AetherDimension.BOSS_DETECTION_RANGE_SQR) {
            this.target = null;
        }
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
            this.currentState = State.SLAM;
            this.slamGoingDown = false;
            return;
        }
        int moveAmount;
        this.moveDirection = calculateDirection(this.target);
        switch (this.moveDirection) {
            case EAST:
            case WEST:
                moveAmount = (int) Math.abs(this.x - this.target.x);
                break;
            case DOWN:
            case UP:
                moveAmount = (int) Math.abs(this.y - this.target.y);
                break;
            case NORTH:
            case SOUTH:
                moveAmount = (int) Math.abs(this.z - this.target.z);
                break;
            case NONE:
            default:
                moveAmount = 0;
                break;
        }
        this.blocksToMove = Math.min(25, Math.max(moveAmount + 1, 3));
        this.world.playSoundAtEntity(null, this, "aether:mob.slider.move", 1.60F + this.random.nextFloat(), .45F + this.random.nextFloat());
    }

    protected void stateAsleep() { /* ZZZ... */}

    @SuppressWarnings("java:S131")
    protected void stateSlam() {
        if (this.world == null) return;

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

            final AABB boundingBox = AABB.getTemporaryBB(this.x - slamRadius, this.y, this.z - slamRadius, this.x + slamRadius, this.y + slamRadius, this.z + slamRadius);
            List<Entity> list = this.world.getEntitiesWithinAABB(Entity.class, boundingBox);

            for (Entity entity : list) {
                MobUtil.multiHit(this, entity,
                    inst((int) Math.floor((BASE_DAMAGE * 0.50F) * getAngerModifier()), DamageType.FALL),
                    inst((int) Math.floor((BASE_DAMAGE * 0.75F) * getAngerModifier()), DamageType.COMBAT)
                );
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
            this.createSlamParticle(slamRadius);
            this.blocksToMove = 0;
            this.moveDirection = Direction.NONE;
            this.currentState = State.AWAKE;
            this.speed = BASE_SPEED;
            this.attackCoolDown = MAX_ATTACK_COOL_DOWN;
        }
        this.slamY = this.y;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean doingSlam() {
        return this.currentState == State.SLAM;
    }


    @SuppressWarnings("java:S6541")
    @Override
    public void tick() {
        super.baseTick();
        if (this.world == null) {
            return;
        }
        if (!this.world.getDifficulty().canHostileMobsSpawn()) {
            if (!this.isAwake()) {
                return;
            }
            this.setState(State.ASLEEP);
            this.returnToOriginalState();
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
        this.updateEntityData();
        if (this.isAwake()) {
            this.wakeUpTimer--;
        }
    }

    @Override
    public Player findPlayerToAttack() {
        if (this.world == null) return null;
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 32.0F);
        if (entityplayer == null) return null;
        if ((this.canEntityBeSeen(entityplayer) && entityplayer.gamemode.areMobsHostile())) {
            ((AetherBossList) entityplayer).aether$TryAddBossList(this);
            return entityplayer;
        }
        return null;
    }

    private void moveSlider() {
        if (!this.isAwake() || this.wakeUpTimer > 0) return;

        if (this.moveDirection == Direction.NONE) {
            this.blocksToMove = 0;
            return;
        }
        float moveAmount = this.speed / TICKS_PER_SECOND;
        if (this.blocksToMove > moveAmount) {
            move(
                moveAmount * this.moveDirection.getOffsetX(),
                moveAmount * this.moveDirection.getOffsetY(),
                moveAmount * this.moveDirection.getOffsetZ()
            );
            this.blocksToMove -= moveAmount;
        } else {
            move(
                this.blocksToMove * this.moveDirection.getOffsetX(),
                this.blocksToMove * this.moveDirection.getOffsetY(),
                this.blocksToMove * this.moveDirection.getOffsetZ()
            );
            this.blocksToMove = 0;
        }
        if (this.x == this.xo && this.y == this.yo && this.z == this.zo) {
            this.moveDirection = Direction.UP;
        }
    }

    private int getBlocksBroken() {
        if (this.world == null) {
            return 0;
        }
        int blocksBroken = 0;
        if (this.blocksToMove <= 0) {
            return blocksBroken;
        }
        int y = (this.moveDirection == Direction.DOWN && this.currentState != State.SLAM) ? -1 : 0;
        for (int x = -2; x <= 1; x++) {
            for (int z = -2; z <= 1; z++) {
                for (; y <= 2 && blocksBroken < 9; y++) {
                    int x1 = (int) (this.x + x);
                    int y1 = (int) (this.y + y);
                    int z1 = (int) (this.z + z);
                    Block<?> block = this.world.getBlock(x1, y1, z1);
                    if (block == null || !this.breakBlock(this.world, x1, y1, z1)) {
                        continue;
                    }
                    doExplosionEffect(this.world, x1, y1, z1);
                    this.blocksToMove -= 0.5F * Math.min(block.getHardness() / 3f, 1);
                    blocksBroken++;
                }
            }
        }
        return blocksBroken;
    }

    private void updateEntityData() {
        if (EnvironmentHelper.isServerEnvironment()) {
            entityData.set(DATA_STATE, currentState.ordinal());
            entityData.set(DATA_ALLOW_MOVEMENT, allowedToMove ? 1 : 0);
            entityData.set(DATA_MOVEMENT_DIRECTION, moveDirection.ordinal());
            entityData.set(DATA_MOVEMENT_AMOUNT, Float.floatToIntBits(blocksToMove));
            return;
        }
        if (EnvironmentHelper.isClientWorld()) {
            currentState = State.values()[entityData.getInt(DATA_STATE)];
            allowedToMove = entityData.getInt(DATA_ALLOW_MOVEMENT) > 0;
            moveDirection = Direction.values()[entityData.getInt(DATA_MOVEMENT_DIRECTION)];
            blocksToMove = Float.intBitsToFloat(entityData.getInt(DATA_MOVEMENT_AMOUNT));
        }
    }

    private void lerpSlider() {
        if (this.newPosRotationIncrements > 0) {
            double lerpXD = this.x + (this.newPosX - this.x) / this.newPosRotationIncrements;
            double lerpYD = this.y + (this.newPosY - this.y) / this.newPosRotationIncrements;
            double lerpZD = this.z + (this.newPosZ - this.z) / this.newPosRotationIncrements;

            double lerpYRot = this.newRotationYaw - this.yRot;
            double lerpXRot = this.newRotationPitch - this.xRot;

            while (lerpYRot < -180.0F) {
                lerpYRot += 360.0F;
            }
            while (lerpYRot >= 180.0F) {
                lerpYRot -= 360.0F;
            }

            this.yRot = (float) (this.yRot + lerpYRot / this.newPosRotationIncrements);
            this.xRot = (float) (this.xRot + lerpXRot / this.newPosRotationIncrements);

            --this.newPosRotationIncrements;
            this.setPos(lerpXD, lerpYD, lerpZD);
            this.setRot(this.yRot, this.xRot);
        }
    }


    /// this following functions is the single most annoying solution in this class.
    /// If you know better than me, please replace it with something decent. -Khep
    /// After a small change it looks fine to me -Redart15
    public Direction calculateDirection(Entity entity) {
        double deltaX = this.x - entity.x;
        double deltaZ = this.z - entity.z;
        double deltaY = this.y;
        if (entity instanceof Player) {
            deltaY -= PlayerUtil.getY((Player) entity);
        } else {
            deltaY -= entity.y;
        }
        if (Math.abs(deltaY) >= entity.bbHeight) {
            return deltaY < 0 ? Direction.UP : Direction.DOWN;
        } else if (Math.abs(deltaX) > Math.abs(deltaZ)) {
            return deltaX < 0 ? Direction.EAST : Direction.WEST;
        } else {
            return deltaZ < 0 ? Direction.SOUTH : Direction.NORTH;
        }
    }

    public boolean breakBlock(@NonNull World world, int x, int y, int z) {
        if (this.getHealth() <= 0) {
            return false;
        }
        Block<?> block = world.getBlock(x, y, z);
        if (block == null
            || block.getLogic() instanceof BlockLogicTrapped
            || block.getLogic() instanceof BlockLogicLocked
            || block.getLogic() instanceof BlockLogicDungeonDoor
            || block.getLogic() instanceof BlockLogicChestLocked
            || block.getMaterial() instanceof MaterialLiquid
            || block.getHardness() < 0) {
            return false;
        }
        block.dropBlockWithCause(world, EnumDropCause.EXPLOSION, x, y, z, world.getBlockMetadata(x, y, z), world.getTileEntity(x, y, z), null);
        world.setBlockWithNotify(x, y, z, 0);
        return true;
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

    @Override
    public boolean collidesWith(Entity entity) {
        if (0.25F >= blocksToMove) {
            return super.collidesWith(entity);
        }
        if (entity instanceof Player) {
            if (!((Player) entity).gamemode.isPlayerInvulnerable()) {
                MobUtil.multiHit(this, entity,
                    inst((int) Math.floor(BASE_DAMAGE * getAngerModifier()), DamageType.FALL),
                    inst((int) Math.floor((BASE_DAMAGE * 0.50F) * getAngerModifier()), DamageType.COMBAT)
                );
            }
            return super.collidesWith(entity);
        }
        doExplosionEffect(entity.world, entity.x, entity.y, entity.z);
        this.playCollidingSound();
        return super.collidesWith(entity);
    }

    @Override
    public boolean isMovementBlocked() {
        return super.isMovementBlocked() || !isAwake();
    }

    public float getAngerModifier() {
        return 1.0F + ((float) (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth());
    }

}
