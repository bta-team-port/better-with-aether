package teamport.aether.entity.boss.sunspirit;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.material.Material;
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
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.projectile.ProjectileElementFire;
import teamport.aether.entity.projectile.ProjectileElementIce;

public class MobBossSunspirit extends MobBossFlying implements EnemyBoss {
    public int timesShot = 0;
    public int chatLog;
    public int chatTime;
    public int direction;
    public double rotary;
    public Entity target;
    public boolean gotTarget;
    public boolean hasAttacked;
    public int wideness;
    public double speedness;
    public int motionTimer;

    public MobBossSunspirit(@Nullable World world) {
        super(world);
        this.setSize(2.25F, 2.5F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_sunspirit");
        this.fireImmune = true;
        this.maxHurtTime = 40;
        this.scoreValue = 100000;
        this.wideness = 10;
        this.speedness = 0.5 - (double) this.getHealth() / 70.0 * 0.2;
    }

    public void lerpMotion(double xd, double yd, double zd) {
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
            this.evaporateWater();
        }

        if (this.chatTime > 0) {
            --this.chatTime;
        }
    }

//    public void updateAI() {
//        super.updateAI();
//        if (this.gotTarget && this.target == null) {
//            this.target = this.findPlayerToAttack();
//            this.gotTarget = false;
//        }
//
//        if (this.target == null) {
//            this.setPos((double) this.returnPoint.x + 0.5, (double) this.returnPoint.y, (double) this.returnPoint.z + 0.5);
//        } else {
//            this.yBodyRot = this.yRot;
//            this.setPos(this.x, (double) this.returnPoint.y, this.z);
//            this.yd = 0.0;
//            boolean pool = false;
//            if (this.xd > 0.0 && (int) Math.floor(this.x) > this.returnPoint.x + this.wideness) {
//                this.rotary = 360.0 - this.rotary;
//                pool = true;
//            } else if (this.xd < 0.0 && (int) Math.floor(this.x) < this.returnPoint.x - this.wideness) {
//                this.rotary = 360.0 - this.rotary;
//                pool = true;
//            }
//
//            if (this.zd > 0.0 && (int) Math.floor(this.z) > this.returnPoint.z + this.wideness) {
//                this.rotary = 180.0 - this.rotary;
//                pool = true;
//            } else if (this.zd < 0.0 && (int) Math.floor(this.z) < this.returnPoint.z - this.wideness) {
//                this.rotary = 180.0 - this.rotary;
//                pool = true;
//            }
//
//            if (this.rotary > 360.0) {
//                this.rotary -= 360.0;
//            } else if (this.rotary < 0.0) {
//                this.rotary += 360.0;
//            }
//
//            if (this.target != null) {
//                this.lookAt(this.target, 20.0F, 20.0F);
//            }
//
//            double crazy = this.rotary / 57.295772552490234;
//            this.xd = Math.sin(crazy) * this.speedness;
//            this.zd = Math.cos(crazy) * this.speedness;
//            ++this.motionTimer;
//            if (this.motionTimer >= 20 || pool) {
//                this.motionTimer = 0;
//                if (this.random.nextInt(3) == 0) {
//                    this.rotary += (double) (this.random.nextFloat() - this.random.nextFloat()) * 60.0;
//                }
//            }
//
//            if (this.target != null) {
//                this.attackEntity(this.target, 32);
//            }
//
//            if (this.target != null && !this.target.isAlive()) {
//                this.setPos((double) this.returnPoint.x + 0.5, (double) this.returnPoint.y, (double) this.returnPoint.z + 0.5);
//                this.xd = 0.0;
//                this.yd = 0.0;
//                this.zd = 0.0;
//                this.target = null;
//                ((Player) target).sendMessage("§eSuch is the fate of a being who opposes the might of the sun.");
//                this.gotTarget = false;
//            }
//
//        }
//    }


    public boolean collidesWith(Entity entity) {
        entity.hurt(this, 20, DamageType.FIRE);
        entity.maxFireTicks = 300;
        entity.remainingFireTicks = 300;
        return false;
    }

    public void evaporateWater() {
        int x = MathHelper.floor(this.x);
        int z = MathHelper.floor(this.z);

        for (int i = 0; i < 8; ++i) {
            int b = (int) (this.yo - 2 + i);
            if (this.world.getBlockMaterial(x, b, z) == Material.water) {
                this.world.setBlock(x, b, z, 0);
                this.world.playSoundEffect(this, SoundCategory.ENTITY_SOUNDS, (float) x + 0.5F, (float) b + 0.5F, (float) z + 0.5F, "random.fizz", 0.5F, 2.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.8F);

                for (int l = 0; l < 8; ++l) {
                    this.world.spawnParticle("largesmoke", (double) x + Math.random(), (double) b + 0.75, (double) z + Math.random(), 0.0, 0.0, 0.0, 0);
                }
            }
        }
    }

    public boolean chatWithMe(Player player) {
        if (this.chatTime <= 0) {
            if (this.chatLog == 0) {
                player.sendMessage("§1You are certainly a brave soul to have entered this chamber.");
                this.chatLog = 1;
                this.chatTime = 60;
            } else if (this.chatLog == 1) {
                player.sendMessage("§1Begone human, you serve no purpose here.");
                this.chatLog = 2;
                this.chatTime = 60;
            } else if (this.chatLog == 2) {
                player.sendMessage("§1Your presence annoys me. Do you not fear my burning aura?");
                this.chatLog = 3;
                this.chatTime = 60;
            } else if (this.chatLog == 3) {
                player.sendMessage("§1I have nothing to offer you, fool. Leave me at peace.");
                this.chatLog = 4;
                this.chatTime = 60;
            } else if (this.chatLog == 4) {
                player.sendMessage("§1Perhaps you are ignorant. Do you wish to know who I am?");
                this.chatLog = 5;
                this.chatTime = 60;
            } else if (this.chatLog == 5) {
                player.sendMessage("§1I am a sun spirit, embodiment of Aether's eternal daylight.");
                player.sendMessage("§1As long as I am alive, the sun will never set on this world.");
                this.chatLog = 6;
                this.chatTime = 60;
            } else if (this.chatLog == 6) {
                player.sendMessage("§1My body burns with the anger of a thousand beasts.");
                player.sendMessage("§1No man, hero, or villain can harm me. You are no exception.");
                this.chatLog = 7;
                this.chatTime = 60;
            } else if (this.chatLog == 7) {
                player.sendMessage("§1You wish to challenge the might of the sun? You are mad.");
                player.sendMessage("§1Do not further insult me or you will feel my wrath.");
                this.chatLog = 8;
                this.chatTime = 60;
            } else if (this.chatLog == 8) {
                player.sendMessage("§1This is your final warning. Leave now, or prepare to burn.");
                this.chatLog = 9;
                this.chatTime = 60;
            } else {
                if (this.chatLog == 9) {
                    player.sendMessage("§eAs you wish, your death will be slow and agonizing.");
                    this.chatLog = 10;
                    return true;
                }

                if (this.chatLog == 10 && this.target == null) {
                    player.sendMessage("§1Did your previous death not satisfy your curiosity, human?");
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
                .forEach(p -> {
                    p.sendMessage("§3Such bitter cold... is this the feeling... of pain?");
                    p.triggerAchievement(AetherAchievements.GOLD);
                    this.world.playSoundEffect(p, SoundCategory.WORLD_SOUNDS, p.x, p.y, p.z, "aether:achievement.gold", 0.5f, 1.0f);
                });

        super.onDeath(entityKilledBy);
    }

    public int getMaxHealth() {
        return 50;
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
            this.hasAttacked = true;
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
            super.hurt(attacker, 5, type);
            ((Player) target).triggerAchievement(AetherAchievements.ICE_DEFLECT);
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
