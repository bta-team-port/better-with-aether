package teamport.aether.entity.monster.valkyrie;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.IItemHolding;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.MobUtil;
import teamport.aether.entity.player.MessageMaker;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherItems;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class MobValkyrie extends MobPathfinder implements Enemy, AetherDeathMessage, IItemHolding {
    private static final int ATTACK_STRENGTH = 7;
    private boolean isSwinging;
    private int teleportTimer;
    private int chatTime;
    protected float wingSpeed;


    public MobValkyrie(World world) {
        super(world);
        this.setTextureIdentifier("aether", "valkyrie");
        this.setSize(0.8F, 1.9F);
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.MEDAL_VICTORY.getDefaultStack(), 1));
        this.moveSpeed = 0.5F;
        this.scoreValue = 5000;
        this.footSize = 1.5f;
        this.canBreatheUnderwater();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void jump() {
        this.yd = 0.72;
    }

    @Override
    public void causeFallDamage(float distance) {
    }

    @Override
    public void spawnInit() {
        this.teleportTimer = this.random.nextInt(250);
    }

    @Override
    public void tick() {
        this.yo = this.yd;
        super.tick();
        if (!this.onGround && this.target != null && this.yo >= 0.0 && this.yd < 0.0 && this.distanceTo(this.target) <= 16.0F && this.canEntityBeSeen(this.target)) {
            double a = this.target.x - this.x;
            double b = this.target.z - this.z;
            double angle = Math.atan2(a, b);
            this.xd = Math.sin(angle) * 0.25;
            this.zd = Math.cos(angle) * 0.25;
        }

        if (!this.onGround && this.yd < 0.0) {
            this.yd += 0.054999999701976776;
            if (this.yd < -0.2750000059604645) {
                this.yd = -0.2750000059604645;
            }
        }

        this.moveSpeed = this.target == null ? 0.5F : 1.0F;
        if (this.world != null && !this.world.getDifficulty().canHostileMobsSpawn() && this.target != null) {
            this.target = null;
        }

        if (this.isSwinging) {
            this.prevSwingProgress += 0.15F;
            this.swingProgress += 0.15F;
            if (this.prevSwingProgress > 1.0F || this.swingProgress > 1.0F) {
                this.isSwinging = false;
                this.prevSwingProgress = 0.0F;
                this.swingProgress = 0.0F;
            }
        }

        if (!this.onGround) {
            this.wingSpeed += 0.75F;
        } else {
            this.wingSpeed += 0.15F;
        }

        if (this.wingSpeed > 6.283186F) {
            this.wingSpeed -= 6.283186F;
        }
    }

    public void teleport(double x, double y, double z, int rad) {
        int a = this.random.nextInt(rad + 1) * (this.random.nextInt(2) * 2 - 1);
        int b = this.random.nextInt(rad / 2) * (this.random.nextInt(2) * 2 - 1);
        int c = (rad - Math.abs(a)) * (this.random.nextInt(2) * 2 - 1);
        x += a;
        y += b;
        z += c;

        int newX = (int) Math.floor(x);
        int newY = (int) Math.floor(y);
        int newZ = (int) Math.floor(z);
        boolean flag = false;

        int dungeonXMin = (int) (this.x - 10);
        int dungeonXMax = (int) (this.x + 10);
        int dungeonZMin = (int) (this.z - 10);
        int dungeonZMax = (int) (this.z + 10);

        for (int q = 0; q < 128 && !flag; ++q) {
            int i = newX + (this.random.nextInt(6) - this.random.nextInt(6));
            int j = (int) this.y;
            int k = newZ + (this.random.nextInt(6) - this.random.nextInt(6));

            if (j >= 0 && j <= 255 && this.isAirySpace(i, j, k) && this.isAirySpace(i, j + 1, k) && !this.isAirySpace(i, j - 1, k) && i >= dungeonXMin && i <= dungeonXMax && k >= dungeonZMin && k <= dungeonZMax) {
                newX = i;
                newY = j;
                newZ = k;
                flag = true;
            }
        }

        if (!flag) {
            this.teleportFailed();
        } else {
            if (!EnvironmentHelper.isServerEnvironment()) {
                ParticleMaker.spawnParticle(this.world, "explode", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
                ParticleMaker.spawnParticle(this.world, "smoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
                ParticleMaker.spawnParticle(this.world, "largesmoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
            }
            this.setPos(newX + 0.5, newY, newZ + 0.5);
            if (this.world != null) world.playSoundAtEntity(null, this, "mob.ghast.fireball", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
            this.xd = 0.0;
            this.yd = 0.0;
            this.zd = 0.0;
            this.moveForward = 0.0F;
            this.moveStrafing = 0.0F;
            this.isJumping = false;
            this.xRot = 0.0F;
            this.yRot = 0.0F;
            this.setPathToEntity(null);
            this.yBodyRot = this.random.nextFloat() * 360.0F;
            this.teleportTimer = this.random.nextInt(40);
        }
    }

    public boolean isAirySpace(int x, int y, int z) {
        if (this.world == null) return true;
        int p = this.world.getBlockId(x, y, z);
        Block<?> block = world.getBlock(x, y, z);
        Block<?> blockTwo = Blocks.blocksList[p];

        return p == 0 || blockTwo == null || blockTwo.getCollisionBoundingBoxFromPool(this.world, x, y, z) == null || block != null && block.getMaterial() == Materials.WATER;
    }

    @Override
    public boolean interact(@NonNull Player entityplayer) {
        if (this.chatTime > 0 || this.target != null) {
            return false;
        }

        this.lookAt(entityplayer, 180.0F, 180.0F);
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        this.chatTime = 60;
        if (itemstack != null && itemstack.itemID == AetherItems.MEDAL_VICTORY.id && itemstack.stackSize >= 0) {
            StringBuilder formatString = new StringBuilder("valkyrie.show_medal.");
            if (itemstack.stackSize >= 10) {
                formatString.append(1);
            } else if (itemstack.stackSize >= 5) {
                formatString.append(2);
            } else {
                formatString.append(3);
            }
            String message = AetherMod.TRANSLATOR.translateKey(formatString.toString());
            MessageMaker.sendMessage(entityplayer, message);
        } else {
            int pokey = this.random.nextInt(3) + 1;
            String formatString = String.format("%s.%d", "valkyrie.interact", pokey);
            String message = AetherMod.TRANSLATOR.translateKey(formatString);
            MessageMaker.sendMessage(entityplayer, message);
        }
        if (this.world != null) world.playSoundAtEntity(null, this, "aether:mob.valkyrie.talk", 1.0f, 1.0f);
        return true;
    }

    @Override
    public void updateAI() {
        super.updateAI();
        ++this.teleportTimer;
        if (this.target != null) {
            if (this.teleportTimer >= 250) {
                this.teleport(this.target.x, this.target.y, this.target.z, 4);
                this.remainingFireTicks = 0;
            } else if (this.teleportTimer % 5 == 0 && !this.canEntityBeSeen(this.target)) {
                this.teleportTimer += 50;
            }
        } else {
            this.teleportTimer = this.random.nextInt(40);
        }

        if (this.onGround && this.teleportTimer % 10 == 0) {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
        }

        if (this.target != null && !this.target.isAlive()) {
            this.target = null;
        }

        if (this.chatTime > 0) {
            --this.chatTime;
        }
    }


    public void swingArm() {
        if (!this.isSwinging) {
            this.isSwinging = true;
            this.prevSwingProgress = 0.0F;
            this.swingProgress = 0.0F;
        }
    }

    public void teleportFailed() {
        this.teleportTimer -= this.random.nextInt(40) + 40;
        if (this.y <= 0.0) {
            this.teleportTimer = 200;
        }
    }

    @Override
    public boolean canSpawnHere() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.bb.minY);
        int k = MathHelper.floor(this.z);
        return this.world != null && this.world.getFullBlockLightValue(i, j, k) > 8 && this.world.getIsAnySolidGround(this.bb) && this.world.getCollidingSolidBlockBoundingBoxes(this, this.bb).isEmpty() && !this.world.getIsAnyLiquid(this.bb);
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("teleportTimer", (short) this.teleportTimer);
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.teleportTimer = tag.getShort("teleportTimer");
    }

    @Override
    public Entity findPlayerToAttack() {
        return this.world != null && this.world.getDifficulty().canHostileMobsSpawn() ? super.getTarget() : null;
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker == null && type == null && damage == 100) {
            return MobUtil.killMob(this);
        }

        if (type == AetherMod.HOLY) {
            super.hurt(attacker, damage / 2, type);
        }

        if (attacker instanceof Player && this.world != null && this.world.getDifficulty().canHostileMobsSpawn()) {
            int pokey = this.random.nextInt(3) + 1;
            if (this.target == null && this.chatTime <= 0) {
                String formatString = String.format("%s.%d", "valkyrie.duel_start", pokey);
                String message = AetherMod.TRANSLATOR.translateKey(formatString);
                MessageMaker.sendMessage((Player) attacker, message);
                this.chatTime = 60;
                world.playSoundAtEntity(null, this, "aether:mob.valkyrie.talk", 1.0f, 1.0f);
            } else {
                this.teleportTimer += 25;
            }

            this.target = attacker;
            boolean flag = super.hurt(attacker, damage, type);
            if (flag && this.getHealth() <= 0) {
                this.dead = true;
                String formatString = String.format("%s.%d", "valkyrie.submit", pokey);
                String message = AetherMod.TRANSLATOR.translateKey(formatString);
                MessageMaker.sendMessage((Player) attacker, message);
                this.animateHurt();
            }
            return flag;
        } else {
            this.teleport(this.x, this.y, this.z, 4);
            this.remainingFireTicks = 0;
            if (this.world != null) world.playSoundAtEntity(null, this, "aether:mob.valkyrie.laugh", 1.0f, 0.75F);
            return false;
        }
    }

    @Override
    public void attackEntity(@NonNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.75F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            this.swingArm();
            entity.hurt(this, ATTACK_STRENGTH, AetherMod.HOLY);
            if (this.target != null && entity == this.target && entity instanceof Player) {
                Player player = (Player) entity;
                if (player.getHealth() <= 0) {
                    int pokey = this.random.nextInt(3) + 1;
                    String formatString = String.format("%s.%d", "valkyrie.attacked", pokey);
                    String message = AetherMod.TRANSLATOR.translateKey(formatString);
                    MessageMaker.sendMessage(player, message);
                    if (this.world != null) world.playSoundAtEntity(null, this, "aether:mob.valkyrie.laugh", 1.0f, 1.0f);
                    this.target = null;
                    this.chatTime = 0;
                }
            }
        }
    }

    @Override
    public String getLivingSound() {
        return null;
    }

    @Override
    public String getHurtSound() {
        return "aether:mob.valkyrie.hurt";
    }

    @Override
    public String getDeathSound() {
        return "aether:mob.valkyrie.death";
    }

    @Override
    public int getMaxHealth() {
        return 50;
    }

    @Override
    public ItemStack getHeldItem() {
        return new ItemStack(AetherItems.TOOL_SWORD_VALKYRIE, 1);
    }

    @Override
    public void setHeldItem(ItemStack itemStack) {
    }

    @Override
    public boolean isLeftHanded() {
        return false;
    }

}
