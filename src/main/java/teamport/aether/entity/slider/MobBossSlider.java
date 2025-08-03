package teamport.aether.entity.slider;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.AetherBossList;
import teamport.aether.entity.EnemyBoss;
import teamport.aether.entity.MobBoss;
import teamport.aether.items.itemtool.ItemToolPickaxeAether;
import teamport.aether.world.AetherDimension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MobBossSlider extends MobBoss implements EnemyBoss {
    public float deformX;
    public int deformY;
    public int deformZ = 1;

    public static final float angerThreshold = 0.50F;
    public static final float baseDamage = 10F;
    public static final int maxAttackCoolDown = 60;
    public static final float baseSpeed = 1.375F;

    public float momentumX, momentumY, momentumZ = 0F;
    public int attackCoolDown = 0;
    public boolean allowedToMove;

    private final ArrayList<Player> creativeAttackersList = new ArrayList<>();
    public Entity target;

    private State currentState = State.ASLEEP;
    enum State {
        AWAKE(MobBossSlider::stateAwake),
        SLAM(MobBossSlider::stateSlam),
        ASLEEP(MobBossSlider::stateASleep);

        private final Consumer<MobBossSlider> consumer;

        State(Consumer<MobBossSlider> consumer) {this.consumer = consumer;}
        public Consumer<MobBossSlider> getConsumer() {return this.consumer;}
    }

    public MobBossSlider(World world) {
        super(world);
        this.yRot = 0.0f;
        this.xRot = 0.0F;

        this.setSize(2.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_slider");
    }

    @Override
    public void tick() {
        super.baseTick();

        int blocksBroken = 0;
        if (Math.abs(momentumX) > 1.0F || Math.abs(momentumZ) > 1.0F) {
            for (int x = -2; x <= 1; x++) {
            for (int z = -2; z <= 1; z++) {
            for (int y = (momentumY < -0.1) ? -2 : -1; y <= 2; y++) {
                if (doBlockSmash(world, (int) (this.x + x), (int) (this.y + y), (int) (this.z + z))) {
                    this.momentumX *= 0.85F;
                    this.momentumY *= 0.85F;
                    this.momentumZ *= 0.85F;

                    blocksBroken++;
                    if (blocksBroken >= 9) {
                        if (this.momentumY <= 0) this.momentumY = 0.4125F;
                        this.allowedToMove = false;
                        this.attackCoolDown = maxAttackCoolDown;
                        return;
                    }
                }
            }}}
        }

        this.momentumX *= 0.75F;
        this.momentumY *= 0.75F;
        this.momentumZ *= 0.75F;
        move(this.momentumX, this.momentumY, this.momentumZ);

        this.attackCoolDown--;
        if (attackCoolDown <= 0) allowedToMove = true;
        this.currentState.getConsumer().accept(this);
    }

    protected void stateASleep() { /* ZZZ... */}

    protected void stateAwake() {
        assert world != null;

        if (world.players
            .stream()
            .noneMatch(entityPlayer -> distanceToSqr(entityPlayer) < AetherDimension.bossDetectionRangeSQR)
        )   {
            this.currentState = State.ASLEEP;
            returnToHome();
            return;
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
            if (this.distanceToSqr(target) > AetherDimension.bossDetectionRangeSQR) target = null;
        }

        if (allowedToMove && target != null && (Math.abs(this.momentumX) <= 0.05F && Math.abs(this.momentumY) <= 0.05F && Math.abs(this.momentumZ) <= 0.05F)) {
            this.speed = baseSpeed * getSpeedModifier(target);
            this.attackCoolDown = maxAttackCoolDown * this.getHealth()/this.getMaxHealth();

            if (this.distanceToSqr(target) <= 25 && this.getHealth() < (this.getMaxHealth() * 0.50F) && random.nextInt(6) == 0) {
                chargeDirection(Direction.UP);
                this.attackCoolDown = (int) (maxAttackCoolDown * 0.50F);
                this.currentState = State.SLAM;
                return;
            }

            Direction moveDirection = calculateDirection(target);
            chargeDirection(moveDirection);
        }
    }

    protected double slamY = 0;

    protected void stateSlam() {
        assert world != null;

        if (allowedToMove) this.momentumY -= baseSpeed;

        if (this.slamY == this.y && allowedToMove) {
            final int slamRadius = 5;
            final float launchSpeed = 0.75F;

            final AABB boundingBox = AABB.getTemporaryBB(this.x - slamRadius, this.y, this.z  - slamRadius, this.x + slamRadius, this.y + slamRadius, this.z + slamRadius);
            List<Entity> list = world.getEntitiesWithinAABB(Entity.class, boundingBox);
            for (Entity entity : list) {
                entity.hurt(this, (int) ((baseDamage * 0.50F) * getAngerModifier()), DamageType.FALL);
                entity.hurt(this, (int) ((baseDamage * 0.75F) * getAngerModifier()), DamageType.GENERIC);

                switch (calculateDirection(entity)) {
                    case NORTH:
                        entity.push(0, launchSpeed /2, -launchSpeed);
                        break;

                    case SOUTH:
                        entity.push(0, launchSpeed /2, launchSpeed);
                        break;

                    case EAST:
                        entity.push(launchSpeed, launchSpeed /2, 0);
                        break;

                    case WEST:
                        entity.push(-launchSpeed, launchSpeed /2, 0);
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

            if (this.momentumY <= 0) this.momentumY = 0.4125F;
            this.currentState = State.AWAKE;
            this.attackCoolDown = maxAttackCoolDown;
        }

        this.slamY = this.y;
    }

    public boolean doBlockSmash(World world, int x, int y, int z) {
        Block<?> block = world.getBlock(x, y, z);

        if (block == null) { return  false; }

        //if (block != null && !(block instanceof BlockDungeon) && !(block.blockMaterial instanceof LiquidMaterial)) {
            block.dropBlockWithCause(world, EnumDropCause.EXPLOSION, x, y, z, world.getBlockMetadata(x, y,z), world.getTileEntity(x, y, z), null);
            doExplosionEffect(world, x, y, z);
            world.setBlockWithNotify(x, y, z, 0);

            return true;
        //}

        //return false;
    }

    public void doExplosionEffect(World world, double x, double y, double z){
        for (int particle = 0; particle < 16; particle++) {
            double XParticle = x + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double YParticle = y + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double ZParticle = z + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);

            world.spawnParticle("explode", XParticle, YParticle, ZParticle, 0,0,0,0);
        }

        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "random.explode", 1.5F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
    }

    protected Player findPlayerToAttack() {
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
        double deltaX =  this.x - entity.x;
        double deltaY =  this.y - entity.y;
        double deltaZ =  this.z - entity.z;

        if (Math.abs(deltaY) >= entity.bbHeight * 1.25F) {
            return deltaY < 0 ? Direction.UP : Direction.DOWN;
        }

        if (Math.abs(deltaX) > Math.abs(deltaZ)) {
            return deltaX < 0 ? Direction.EAST : Direction.WEST;
        } else {
            return deltaZ < 0 ? Direction.SOUTH : Direction.NORTH;
        }
    }

    protected void chargeDirection(Direction direction) {
        switch (direction) {
            case UP:
                this.allowedToMove = false;
                this.momentumY += baseSpeed * getSpeedModifier(target);
                break;

            case DOWN:
                this.allowedToMove = false;
                this.momentumY -= baseSpeed * getSpeedModifier(target);
                break;

            case NORTH:
                this.allowedToMove = false;
                this.momentumZ -= speed;
                break;

            case SOUTH:
                this.allowedToMove = false;
                this.momentumZ += speed;
                break;

            case EAST:
                this.allowedToMove = false;
                this.momentumX += speed;
                break;

            case WEST:
                this.allowedToMove = false;
                this.momentumX -= speed;
                break;
        }
    }

    @Override
    public boolean isOnFire() { return false; }

    @Override
    public void fireHurt() {}

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if(this.isAwake() && type == DamageType.BLAST) return super.hurt(attacker, damage/4, type);

        if (attacker instanceof Player) {
            ItemStack item = ((Player)attacker).inventory.getCurrentItem();

            if (item != null && (item.getItem() instanceof ItemToolPickaxe || item.getItem() instanceof ItemToolPickaxeAether)) {
                tryAwake();
                if (!((Player)attacker).gamemode.areMobsHostile()) creativeAttackersList.add((Player) attacker);
                return super.hurt(attacker, (int) item.getStrVsBlock(AetherBlocks.COBBLE_HOLYSTONE), type);
            }

            if (!this.isAwake()) {
                String message = "<"+((Player)attacker).getDisplayName()+"> "+ I18n.getInstance().translateKey("aether.entity.boss_slider.hit_fail");
                ((Player)attacker).sendTranslatedChatMessage(message);
            }
        }
        return false;
    }

    @Override
    public boolean canFight() {
        return isAlive() && isAwake();
    }

    @Override
    protected boolean isMovementBlocked() {
        return super.isMovementBlocked() || !isAwake();
    }

    public float getSpeedModifier(Entity target){
        double distance = this.distanceTo(target);
        return distance > 3 ? getAngerModifier() : (float) distance * 1.75F;
    }

    public float getAngerModifier() {
        return 1.0F + ( (float) (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth() );
    }

    public boolean isAngry() {return ((float) this.getHealth() / this.getMaxHealth()) < angerThreshold;}
    public boolean isAwake() {return this.currentState != State.ASLEEP;}
    public boolean doingSlam() {return this.currentState == State.SLAM;}

    public void tryAwake() {
        if (this.currentState != State.SLAM) {
            this.currentState = State.AWAKE;
        }

    }

    public int getMaxHealth() {
        return 500;
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

    public @NotNull String getDefaultEntityTexture() {
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

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        try { currentState = State.valueOf(tag.getString("state")); }
        catch (IllegalArgumentException e) { currentState = State.ASLEEP; }

        attackCoolDown = tag.getInteger("attackCoolDown");
        allowedToMove = tag.getBoolean("allowedToMove");
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        tag.putString("state", currentState.toString());
        tag.putInt("attackCoolDown", attackCoolDown);
        tag.putBoolean("allowedToMove", allowedToMove);
        super.addAdditionalSaveData(tag);
    }
}
