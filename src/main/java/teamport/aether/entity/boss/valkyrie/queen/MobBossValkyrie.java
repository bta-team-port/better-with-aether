package teamport.aether.entity.boss.valkyrie.queen;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.AetherAchievements;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.entity.boss.MobBoss;
import teamport.aether.entity.projectile.ProjectileElementLightning;
import teamport.aether.items.AetherItems;

public class MobBossValkyrie extends MobBoss implements EnemyBoss {
    public boolean isSwinging;
    public boolean duel;
    public int teleportTimer;
    public int chatTime;
    public float sinage;
    public int attackStrength;
    public boolean attacked;


    public MobBossValkyrie(@Nullable World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_valkyrie");
        this.setSize(0.8F, 2.0F);
        this.scoreValue = 50000;
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_HOLY.getDefaultStack(), 1));
        this.moveSpeed = 0.5F;
        this.attackStrength = 10;
        this.footSize = 1.5f;
        this.chatColor = (byte)(TextFormatting.GRAY.id & 255);
    }

    public void jump() {
        this.yd = 0.72;
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
        if (!this.world.getDifficulty().canHostileMobsSpawn() && this.target != null) {
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
            this.sinage += 0.75F;
        } else {
            this.sinage += 0.15F;
        }

        if (this.sinage > 6.283186F) {
            this.sinage -= 6.283186F;
        }
    }

    public void updateAI() {
        super.updateAI();
        ++this.teleportTimer;

        if (this.duel && this.attacked) {
            this.target = world.getClosestPlayerToEntity(this, 16);
        }

        if (this.duel && this.target != null) {
            if (this.teleportTimer >= 125) {
                this.teleport(this.target.x, this.target.y, this.target.z, 8);
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
            this.duel = false;
        }

        if (this.chatTime > 0) {
            --this.chatTime;
        }
    }

    public boolean interact(@NotNull Player entityplayer) {
        if (this.chatTime > 0 || (this.duel && this.target == entityplayer)) {
            return false;
        }

        this.lookAt(entityplayer, 180.0F, 180.0F);
        world.playSoundAtEntity(null, this, "aether:mob.valkyrie.talk", 1.0f, 0.75F);

        if (!this.world.getDifficulty().canHostileMobsSpawn()) {
            entityplayer.sendMessage("I have no time for pathetic humans like you.");
            world.playSoundAtEntity(null, this, "aether:mob.valkyrie.laugh", 1.0f, 0.75F);
            this.chatTime = 60;
            return true;
        }

        if (this.duel) {
            entityplayer.sendMessage("If you wish to challenge me, strike at any time.");
            this.chatTime = 60;
            return true;
        }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack != null && itemstack.itemID == AetherItems.MEDAL_VICTORY.id && itemstack.stackSize >= 10) {
            itemstack.stackSize -= 10;
            if (itemstack.stackSize <= 0) {
                entityplayer.destroyCurrentEquippedItem();
            }
            entityplayer.sendMessage("Very well, attack me when you wish to begin.");
            this.duel = true;
        } else {
            entityplayer.sendMessage("Show me 10 victory medals, and I will fight you.");
        }
        this.chatTime = 60;
        return true;
    }

    public void causeFallDamage(float distance) {
    }

    public void spawnInit() {
        this.teleportTimer = this.random.nextInt(125);
    }

    public Entity findPlayerToAttack() {
        return this.world.getDifficulty().canHostileMobsSpawn() && this.duel && this.target != null ? super.getTarget() : null;
    }

    public void onDeath(Entity entityKilledBy) {
        this.world.players.stream()
                .filter(player -> player.distanceTo(this) < 32)
                .forEach(p -> {
                    this.dead = true;
                    p.triggerAchievement(AetherAchievements.SILVER);
                    this.world.playSoundEffect(p, SoundCategory.WORLD_SOUNDS, p.x, p.y, p.z, "aether:achievement.silver", 0.5f, 1.0f);
                    p.sendMessage("You are truly... a mighty warrior...");
                });
        super.onDeath(entityKilledBy);
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
            world.playSoundAtEntity(null, this, "mob.ghast.fireball", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
            this.world.spawnParticle("explode", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
            this.world.spawnParticle("smoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
            this.world.spawnParticle("largesmoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
            this.setPos(newX + 0.5, newY, newZ + 0.5);
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
        int p = this.world.getBlockId(x, y, z);
        return p == 0 || Blocks.blocksList[p] == null || Blocks.blocksList[p].getCollisionBoundingBoxFromPool(this.world, x, y, z) == null;
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
            this.teleportTimer = 100;
        }
    }

    @Override
    public boolean canSpawnHere() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.bb.minY);
        int k = MathHelper.floor(this.z);
        return this.world.getFullBlockLightValue(i, j, k) > 8 && this.world.getIsAnySolidGround(this.bb) && this.world.getCollidingSolidBlockBoundingBoxes(this, this.bb).isEmpty() && !this.world.getIsAnyLiquid(this.bb);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("teleportTimer", (short) this.teleportTimer);
        tag.putBoolean("duel", this.duel);
        tag.putBoolean("attacked", this.attacked);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.teleportTimer = tag.getShort("teleportTimer");
        this.duel = tag.getBoolean("duel");
        this.attacked = tag.getBoolean("attacked");
    }

    public boolean canFight() {
        return isAlive() && duel;
    }

    @Override
    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (!duel) {
            return false;
        }

        Player player = (Player) attacker;
        if (!this.world.getDifficulty().canHostileMobsSpawn() && attacker instanceof Player) {
            if (this.chatTime <= 0) {
                player.sendMessage("Sorry, I don't fight with weaklings.");
                world.playSoundAtEntity(null, this, "aether:mob.valkyrie.laugh", 1.0f, 0.75F);
                this.chatTime = 60;
            }
            return false;
        }

        if (!this.duel && attacker instanceof Player) {
            if (this.chatTime <= 0) {
                String message = this.random.nextInt(2) == 0 ? "Try defeating some weaker valkyries first." : "Collect 10 medallions before trying that.";
                world.playSoundAtEntity(null, this, "aether:mob.valkyrie.talk", 1.0f, 0.75F);
                player.sendMessage(message);
                this.chatTime = 60;
            }
            return false;
        }

        if (this.target == null && this.chatTime <= 0 && attacker instanceof Player) {
            player.sendMessage("This will be your final battle!");
            this.attacked = true;
            this.chatTime = 60;
        } else {
            this.teleportTimer += 60;
        }

        this.target = attacker;
        return super.hurt(attacker, i, type);
    }



    @Override
    public void attackEntity(@NotNull Entity entity, float distance) {
        if (this.getHealth() < this.getMaxHealth() / 2) {
            if (distance > 5.0F) {
                double d = entity.x - this.x;
                double d1 = entity.z - this.z;
                if (this.attackTime == 0) {
                    if (!this.world.isClientSide) {
                        ProjectileElementLightning elementLightning = new ProjectileElementLightning(this.world, this);
                        elementLightning.setHeading(world.rand.nextDouble(), this.getLookAngle().y + 5, world.rand.nextDouble(), 0.5f, 0.0f);
                        this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() + this.random.nextFloat()) * 1.2F + 1.0F);
                        this.world.entityJoinedWorld(elementLightning);
                    }
                    this.attackTime = 50;
                }
                this.yRot = (float) (Math.atan2(d1, d) * 180.0 / Math.PI) - 90.0F;
                this.hasAttacked = true;
            }
        }

        if (this.attackTime <= 0 && distance < 2.75F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            this.swingArm();
            entity.hurt(this, this.attackStrength, DamageType.COMBAT);
            if (this.target != null && entity == this.target && entity instanceof Player) {
                Player target = (Player) entity;
                if (target.getHealth() <= 0 && this.chatTime <= 0) {
                    this.target = null;
                    this.chatTime = 60;
                    ((Player) entity).sendMessage("As expected of a human.");
                    world.playSoundAtEntity(null, this, "aether:mob.valkyrie.laugh", 1.0f, 0.75F);
                    this.heal(400);
                    this.duel = false;
                }
            }
        }
    }

    public String getLivingSound() {
        return null;
    }

    public String getHurtSound() {
        return "aether:mob.valkyrie.hurt";
    }

    public String getDeathSound() {
        return "aether:mob.valkyrie.death";
    }

    public void playHurtSound() {
        this.world.playSoundAtEntity(null, this, this.getHurtSound(), 0.75f, 0.75F);
    }

    public void playDeathSound() {
        this.world.playSoundAtEntity(null, this, this.getDeathSound(), 1.0f, 0.75F);
    }

    public int getMaxHealth() {
        return 400;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public ItemStack getHeldItem() {
        return new ItemStack(AetherItems.TOOL_SWORD_HOLY, 1);
    }
}
