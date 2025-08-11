package teamport.aether.entity.monster.valkyrie;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.DoubleTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.items.AetherItems;

public class MobValkyrie extends MobMonster implements Enemy {
    public boolean isSwinging;
    public int teleportTimer;
    public int angerLevel;
    public int timeLeft;
    public int chatTime;
    public double safeX;
    public double safeY;
    public double safeZ;
    public float sinage;

    public MobValkyrie(@Nullable World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "valkyrie");
        this.setSize(0.8F, 2.0F);
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.MEDAL_VICTORY.getDefaultStack(), 1));
        this.moveSpeed = 0.5F;
        this.timeLeft = 1200;
        this.attackStrength = 7;
        this.scoreValue = 5000;
        this.teleportTimer = this.random.nextInt(250);
        this.timeLeft = 1200;
        this.safeX = this.x;
        this.safeY = this.y;
        this.safeZ = this.z;
    }

    public MobValkyrie(World world, double x, double y, double z, boolean flag) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "valkyrie");
        this.setSize(0.8F, 2.0F);
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.MEDAL_VICTORY.getDefaultStack(), 1));
        this.moveSpeed = 0.5F;
        this.timeLeft = 1200;
        this.attackStrength = 7;
        this.scoreValue = 5000;
        this.teleportTimer = this.random.nextInt(250);
        this.timeLeft = 1200;
        this.safeX = this.x;
        this.safeY = this.y;
        this.safeZ = this.z;
    }

    public void causeFallDamage(float distance) {
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

        if (!this.onGround && !this.canClimb() && Math.abs(this.yd - this.yo) > 0.07 && Math.abs(this.yd - this.yo) < 0.09) {
            this.yd += 0.054999999701976776;
            if (this.yd < -0.2750000059604645) {
                this.yd = -0.2750000059604645;
            }
        }

        this.moveSpeed = this.target == null ? 0.5F : 1.0F;
        if (!this.world.getDifficulty().canHostileMobsSpawn() && (this.target != null || this.angerLevel > 0)) {
            this.angerLevel = 0;
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


            if (this.timeLeft <= 0) {
                this.dead = true;
                this.animateHurt();
            }
        }

    public void teleport(double x, double y, double z, int rad) {
        int a = this.random.nextInt(rad + 1);
        int b = this.random.nextInt(rad / 2);
        int c = rad - a;
        a *= this.random.nextInt(2) * 2 - 1;
        b *= this.random.nextInt(2) * 2 - 1;
        c *= this.random.nextInt(2) * 2 - 1;
        x += a;
        y += b;
        z += c;
        int newX = (int) Math.floor(x - 0.5);
        int newY = (int) Math.floor(y - 0.5);
        int newZ = (int) Math.floor(z - 0.5);
        boolean flag = false;

        for (int q = 0; q < 32 && !flag; ++q) {
            int i = newX + (this.random.nextInt(rad / 2) - this.random.nextInt(rad / 2));
            int j = newY + (this.random.nextInt(rad / 2) - this.random.nextInt(rad / 2));
            int k = newZ + (this.random.nextInt(rad / 2) - this.random.nextInt(rad / 2));
            if (j <= 124 && j >= 5 && this.isAirySpace(i, j, k) && this.isAirySpace(i, j + 1, k) && !this.isAirySpace(i, j - 1, k)) {
                newX = i;
                newY = j;
                newZ = k;
                flag = true;
            }
        }

        if (!flag) {
            this.teleportFailed();
        } else {
            this.animateHurt();
            this.setPos((double) newX + 0.5, (double) newY + 0.5, (double) newZ + 0.5);
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
            this.animateHurt();
            this.teleportTimer = this.random.nextInt(40);
        }
    }

    public boolean isAirySpace(int x, int y, int z) {
        int p = this.world.getBlockId(x, y, z);
        return p == 0 || Blocks.blocksList[p] == null || Blocks.blocksList[p].getCollisionBoundingBoxFromPool(this.world, x, y, z) == null;
    }

    @Override
    public boolean canDespawn() {
        return true;
    }

    @Override
    public boolean interact(@NotNull Player entityplayer) {
        this.lookAt(entityplayer, 180.0F, 180.0F);
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (this.angerLevel > 1) return false;

        if (this.timeLeft >= 1200) {
            if (itemstack != null && itemstack.itemID == AetherItems.MEDAL_VICTORY.id && itemstack.stackSize >= 0) {
                if (itemstack.stackSize >= 10) {
                    entityplayer.sendMessage("Umm... that's a nice pile of medallions you have there...");
                } else if (itemstack.stackSize >= 5) {
                    entityplayer.sendMessage("That's pretty impressive, but you won't defeat me.");
                } else {
                    entityplayer.sendMessage("You think you're a tough guy, eh? Well, bring it on!");
                }
            } else {
                int pokey = this.random.nextInt(3);
                if (pokey == 2) {
                    entityplayer.sendMessage("What's that? You want to fight? Aww, what a cute little human.");
                } else if (pokey == 1) {
                    entityplayer.sendMessage("You're not thinking of fighting a big, strong Valkyrie are you?");
                } else {
                    entityplayer.sendMessage("I don't think you should bother me, you could get really hurt.");
                }
            }
        }
        return false;
    }

    @Override
    public void updateAI() {
        super.updateAI();
        ++this.teleportTimer;
        if (this.teleportTimer >= 450) {
            if (this.target != null) {
                this.teleport(this.target.x, this.target.y, this.target.z, 7);
            } else if (this.onGround) {
                this.teleport(this.x, this.y, this.z, 12 + this.random.nextInt(12));
            } else {
                this.teleport(this.safeX, this.safeY, this.safeZ, 6);
            }
        } else if (this.teleportTimer >= 446 || !(this.y <= 0.0) && !(this.y <= this.safeY - 16.0)) {
            if (this.teleportTimer % 5 == 0 && this.target != null && !this.canEntityBeSeen(this.target)) {
                this.teleportTimer += 100;
            }
        } else {
            this.teleportTimer = 446;
        }

        if (this.onGround && this.teleportTimer % 10 == 0) {
            this.safeX = this.x;
            this.safeY = this.y;
            this.safeZ = this.z;
        }

        if (this.target != null && !this.target.isAlive()) {
            this.target = null;
            this.angerLevel = 0;
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
            this.teleportTimer = 446;
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
        tag.putShort("Anger", (short) this.angerLevel);
        tag.putShort("teleportTimer", (short) this.teleportTimer);
        tag.putShort("TimeLeft", (short) this.timeLeft);
        ListTag safePos = new ListTag();
        safePos.addTag(new DoubleTag(this.safeX));
        safePos.addTag(new DoubleTag(this.safeY));
        safePos.addTag(new DoubleTag(this.safeZ));
        tag.put("SafePos", safePos);
        tag.put("SafePos", this.newDoubleList(new double[]{this.safeX, this.safeY, this.safeZ}));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.angerLevel = tag.getShort("Anger");
        this.teleportTimer = tag.getShort("teleportTimer");
        this.timeLeft = tag.getShort("TimeLeft");
        ListTag safePos = tag.getList("SafePos");
        this.safeX = ((DoubleTag) safePos.tagAt(0)).getValue();
        this.safeY = ((DoubleTag) safePos.tagAt(1)).getValue();
        this.safeZ = ((DoubleTag) safePos.tagAt(2)).getValue();
    }

    @Override
    public Entity findPlayerToAttack() {
        return this.world.getDifficulty().canHostileMobsSpawn() && this.angerLevel > 0 ? super.getTarget() : null;
    }

    @Override
    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (attacker instanceof Player && this.world.getDifficulty().canHostileMobsSpawn()) {
            int pokey = this.random.nextInt(3);
            if (this.target == null) {
                this.chatTime = 0;
                if (pokey == 2) {
                    ((Player) attacker).sendMessage("I'm not going easy on you!");
                } else if (pokey == 1) {
                    ((Player) attacker).sendMessage("You're gonna regret that!");
                } else {
                    ((Player) attacker).sendMessage("Now you're in for it!");
                }
            } else {
                this.teleportTimer -= 10;
            }

            this.becomeAngryAt(attacker);
            boolean flag = super.hurt(attacker, i, type);
            if (flag && this.getHealth() <= 0) {
                this.dead = true;
                this.chatTime = 0;
                if (pokey == 2) {
                    ((Player) attacker).sendMessage("Alright, alright! You win!");
                } else if (pokey == 1) {
                    ((Player) attacker).sendMessage("Okay, I give up! Geez!");
                } else {
                    ((Player) attacker).sendMessage("Oww! Fine, here's your medal...");
                }
                this.animateHurt();
            }
            return flag;
        } else {
            this.teleport(this.x, this.y, this.z, 8);
            this.remainingFireTicks = 0;
            return false;
        }
    }

    @Override
    public void attackEntity(@NotNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.75F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            this.swingArm();
            entity.hurt(this, this.attackStrength, DamageType.COMBAT);
            if (this.target != null && entity == this.target && entity instanceof Player) {
                Player e1 = (Player) entity;
                if (e1.getHealth() <= 0) {
                    this.target = null;
                    this.angerLevel = 0;
                    int pokey = this.random.nextInt(3);
                    this.chatTime = 0;
                    if (pokey == 2) {
                        ((Player) entity).sendMessage("You want a medallion? Try being less pathetic.");
                    } else if (pokey == 1 && e1 instanceof Player) {
                        String s = e1.getDisplayName();
                        ((Player) entity).sendMessage("Maybe some day, " + s + "... maybe some day.");
                    } else {
                        ((Player) entity).sendMessage("Humans aren't nearly as cute when they're dead.");
                    }
                }
            }
        }
    }

    public void becomeAngryAt(Entity entity) {
        this.target = entity;
        this.angerLevel = 200 + this.random.nextInt(200);
    }

    public int getMaxHealth() {
        return 50;
    }

    public ItemStack getHeldItem() {
        return new ItemStack(AetherItems.TOOL_SWORD_VALKYRIE, 1);
    }
    
}