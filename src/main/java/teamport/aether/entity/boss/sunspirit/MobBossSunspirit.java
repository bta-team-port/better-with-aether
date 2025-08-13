package teamport.aether.entity.boss.sunspirit;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.AetherAchievements;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.entity.boss.MobBoss;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.projectile.ProjectileElementFire;
import teamport.aether.entity.projectile.ProjectileElementIce;

public class MobBossSunspirit extends MobBoss implements EnemyBoss {
    public int timesShot = 0;
    public int wideness;
    public int orgX;
    public int orgY;
    public int orgZ;
    public int motionTimer;
    public int entCount;
    public int flameCount;
    public int ballCount;
    public int chatLog;
    public int chatTime;
    public int hurtness;
    public int direction;
    public double rotary;
    public double speedness;
    public Entity target;
    public boolean gotTarget;
    public boolean isBoss;
    public static final float jimz = 57.295773F;

    public MobBossSunspirit(@Nullable World world) {
        super(world);
        this.setSize(2.25F, 2.5F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_sunspirit");
        this.fireImmune = true;
        this.maxHurtTime = 40;
        this.scoreValue = 100000;
    }

    protected void causeFallDamage(float distance) {
    }

    public void addVelocity(double d, double d1, double d2) {
    }

    public void knockBack(Entity entity, int damage, double xd, double yd) {
    }

    public void tick() {
        super.tick();
        if (this.getHealth() > 0) {
            double a = this.random.nextFloat() - 0.5F;
            double b = this.random.nextFloat();
            double c = this.random.nextFloat() - 0.5F;
            double d = this.x + a * b;
            double e = this.bb.minY + b - 0.5;
            double f = this.z + c * b;
            this.world.spawnParticle("flame", d, e, f, 0.0, -0.07500000298023224, 0.0, 0);
            ++this.entCount;
            if (this.entCount >= 3) {
                this.entCount = 0;
            }
        }

        if (this.chatTime > 0) {
            --this.chatTime;
        }

    }

    public boolean chatWithMe(Player player) {
        if (this.chatTime <= 0) {
            if (this.chatLog == 0) {
                player.sendMessage("§eYou are certainly a brave soul to have entered this chamber.");
                this.chatLog = 1;
                this.chatTime = 60;
            } else if (this.chatLog == 1) {
                player.sendMessage("§eBegone human, you serve no purpose here.");
                this.chatLog = 2;
                this.chatTime = 60;
            } else if (this.chatLog == 2) {
                player.sendMessage("§eYour presence annoys me. Do you not fear my burning aura?");
                this.chatLog = 3;
                this.chatTime = 60;
            } else if (this.chatLog == 3) {
                player.sendMessage("§eI have nothing to offer you, fool. Leave me at peace.");
                this.chatLog = 4;
                this.chatTime = 60;
            } else if (this.chatLog == 4) {
                player.sendMessage("§ePerhaps you are ignorant. Do you wish to know who I am?");
                this.chatLog = 5;
                this.chatTime = 60;
            } else if (this.chatLog == 5) {
                player.sendMessage("§eI am a sun spirit, embodiment of Aether's eternal daylight.");
                player.sendMessage("§eAs long as I am alive, the sun will never set on this world.");
                this.chatLog = 6;
                this.chatTime = 60;
            } else if (this.chatLog == 6) {
                player.sendMessage("§eMy body burns with the anger of a thousand beasts.");
                player.sendMessage("§eNo man, hero, or villain can harm me. You are no exception.");
                this.chatLog = 7;
                this.chatTime = 60;
            } else if (this.chatLog == 7) {
                player.sendMessage("§eYou wish to challenge the might of the sun? You are mad.");
                player.sendMessage("§eDo not further insult me or you will feel my wrath.");
                this.chatLog = 8;
                this.chatTime = 60;
            } else if (this.chatLog == 8) {
                player.sendMessage("§eThis is your final warning. Leave now, or prepare to burn.");
                this.chatLog = 9;
                this.chatTime = 60;
            } else {
                if (this.chatLog == 9) {
                    player.sendMessage("§1As you wish, your death will be slow and agonizing.");
                    this.chatLog = 10;
                    return true;
                }

                if (this.chatLog == 10 && this.target == null) {
                    player.sendMessage("§eDid your previous death not satisfy your curiosity, human?");
                    this.chatLog = 9;
                    this.chatTime = 60;
                }
            }
        }
        return false;
    }

    public boolean interact(@NotNull Player player) {
        if (this.chatWithMe(player)) {
            this.rotary = 57.295772552490234 * Math.atan2(this.x - player.x, this.z - player.z);
            this.target = player;
            this.gotTarget = true;
            return true;
        } else {
            return false;
        }
    }

    public void onDeath(Entity entityKilledBy) {
        this.world.players.stream()
            .filter(player -> player.distanceTo(this) < 32)
            .forEach( p -> {
                p.sendMessage("§3Such bitter cold... is this the feeling... of pain?");
                p.triggerAchievement(AetherAchievements.GOLD);
                this.world.playSoundEffect(p, SoundCategory.WORLD_SOUNDS, p.x, p.y, p.z, "aether:achievement.gold", 0.5f, 1.0f);
            });

        super.onDeath(entityKilledBy);
    }

    public int getMaxHealth() {
        return 1000;
    }

    public void moveEntityWithHeading(float moveStrafing, float moveForward) {
        if (this.isInWater()) {
            this.moveRelative(moveStrafing, moveForward, 0.02F);
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.8;
            this.yd *= 0.8;
            this.zd *= 0.8;
        } else if (this.isInLava()) {
            this.moveRelative(moveStrafing, moveForward, 0.02F);
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.5;
            this.yd *= 0.5;
            this.zd *= 0.5;
        } else {
            float f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.5460001F;
                int i = this.world.getBlockId(MathHelper.floor(this.x), MathHelper.floor(this.bb.minY) - 1, MathHelper.floor(this.z));
                if (i > 0) {
                    f2 = Blocks.blocksList[i].friction * 0.91F;
                }
            }

            float f3 = 0.1627714F / (f2 * f2 * f2);
            this.moveRelative(moveStrafing, moveForward, this.onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.5460001F;
                int j = this.world.getBlockId(MathHelper.floor(this.x), MathHelper.floor(this.bb.minY) - 1, MathHelper.floor(this.z));
                if (j > 0) {
                    f2 = Blocks.blocksList[j].friction * 0.91F;
                }
            }

            this.move(this.xd, this.yd, this.zd);
            this.xd *= f2;
            this.yd *= f2;
            this.zd *= f2;
        }

        this.walkAnimSpeedO = this.walkAnimSpeed;
        double d = this.x - this.xo;
        double d1 = this.z - this.zo;
        float f4 = MathHelper.sqrt(d * d + d1 * d1) * 4.0F;
        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        this.walkAnimSpeed += (f4 - this.walkAnimSpeed) * 0.4F;
        this.walkAnimPos += this.walkAnimSpeed;
    }

    public boolean canClimb() {
        return false;
    }

    public Entity findPlayerToAttack() {
        Player player = this.world.getClosestPlayerToEntity(this, 16.0);
        if (player != null && canEntityBeSeen(player) && player.getGamemode().consumeBlocks() && gotTarget) {
            ((AetherBossList) player).aether$TryAddBossList(this);
            return player;
        }
        return null;
    }

    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    public int getLightmapCoord(float partialTick) {
        return this.world.getLightmapCoord(15, 15);
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
        if (gotTarget) {
            int totalShots = 4;
            if (distance < 10.0F) {
                double d = entity.x - this.x;
                double d1 = entity.z - this.z;
                if (this.attackTime == 0) {
                    if (!this.world.isClientSide) {
                        if (this.timesShot < totalShots) {
                            ProjectileElementFire elementFire = new ProjectileElementFire(this.world, this);
                            elementFire.setHeading(world.rand.nextDouble(), this.getLookAngle().y, world.rand.nextDouble(), 2.0f, 0.0F);
                            this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                            this.world.entityJoinedWorld(elementFire);
                            this.timesShot++;
                        } else {
                            ProjectileElementIce elementIce = new ProjectileElementIce(this.world, this);
                            elementIce.setHeading(this.getLookAngle().x, this.getLookAngle().y, this.getLookAngle().z, 0.5f, 50.0F);
                            this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 2.0F);
                            this.world.entityJoinedWorld(elementIce);
                            this.timesShot = 0;
                        }
                    }
                    this.attackTime = 50;
                }
                this.yRot = (float) (Math.atan2(d1, d) * 180.0 / Math.PI) - 90.0F;
                this.hasAttacked = true;
            }
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.timesShot = tag.getInteger("timesShot");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("timesShot", this.timesShot);
    }

    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker instanceof ProjectileElementIce) {
            super.hurt(attacker, 100, type);
            MobFireMinion minion = new MobFireMinion(this.world);
            if (this.getHealth() > this.getMaxHealth() / 2) {
                this.world.entityJoinedWorld(minion);
            } else {
                this.world.entityJoinedWorld(minion);
                this.world.entityJoinedWorld(minion);
                this.world.entityJoinedWorld(minion);
            }
            return true;
        }
        return false;
    }

    public String getHurtSound() {
        return "aether:mob.sunspirit.hurt";
    }

    public String getDeathSound() {
        return "aether:mob.sunspirit.death";
    }


    public String getEntityTexture() {
        if (this.hurtTime > 0) {
            return "/assets/aether/textures/entity/boss_sunspirit/sunspirit_hurt.png";
        }
        return "/assets/aether/textures/entity/boss_sunspirit/sunspirit.png";
    }


    public @NotNull String getDefaultEntityTexture() {
        if (this.hurtTime > 0) {
            return "/assets/aether/textures/entity/boss_sunspirit/sunspirit_hurt.png";
        }
        return "/assets/aether/textures/entity/boss_sunspirit/sunspirit.png";
    }

}
