package teamport.aether.entity.monster.valkyrie;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.items.AetherItems;

public class MobValkyrie extends MobMonster implements Enemy {
    public boolean isSwinging;
    public boolean boss;
    public boolean duel;
    public int teleTimer;
    public int angerLevel;
    public int timeLeft;
    public int chatTime;

    public MobValkyrie(@Nullable World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "valkyrie");
        this.setSize(0.8F, 2.0F);
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.MEDAL_VICTORY.getDefaultStack(), 1));
        this.moveSpeed = 0.5F;
        this.timeLeft = 1200;
        this.attackStrength = 7;
        this.scoreValue = 5000;
    }

    public void causeFallDamage(float distance) {
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
            this.teleFail();
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
            this.teleTimer = this.random.nextInt(40);
        }

    }

    public boolean isAirySpace(int x, int y, int z) {
        int p = this.world.getBlockId(x, y, z);
        return p == 0;
    }

    public boolean interact(@NotNull Player player) {
        this.lookAt(player, 180.0F, 180.0F);
                ItemStack itemstack = player.inventory.getCurrentItem();
                if (itemstack != null && itemstack.itemID == AetherItems.MEDAL_VICTORY.id && itemstack.stackSize >= 0) {
                    if (itemstack.stackSize >= 10) {
                        player.sendMessage("Umm... that's a nice pile of medallions you have there...");
                    } else if (itemstack.stackSize >= 5) {
                        player.sendMessage("That's pretty impressive, but you won't defeat me.");
                    } else {
                        player.sendMessage("You think you're a tough guy, eh? Well, bring it on!");
                    }
                } else {
                    int pokey = this.random.nextInt(3);
                    if (pokey == 2) {
                        player.sendMessage("What's that? You want to fight? Aww, what a cute little human.");
                    } else if (pokey == 1) {
                        player.sendMessage("You're not thinking of fighting a big, strong valkyrie are you?");
                    } else {
                        player.sendMessage("I don't think you should bother me, you could get really hurt.");
                    }
                }
        return false;
    }

    public void updateAI() {
        super.updateAI();
        ++this.teleTimer;
        if (this.teleTimer >= 450) {
            if (this.target != null) {
                if (this.onGround) {
                    this.teleport(this.x, this.y, this.z, 12 + this.random.nextInt(12));
                } else {
                    this.teleport(this.x, this.y, this.z, 12 + this.random.nextInt(12));
                }
            }
        } else if (this.teleTimer >= 446 || !(this.y <= 0.0) && !(this.y <= this.yo - 16.0)) {
            if (this.teleTimer % 5 == 0 && this.target != null && !this.canEntityBeSeen(this.target)) {
                this.teleTimer += 100;
            }
        } else {
            this.teleTimer = 446;
        }

        if (this.onGround && this.teleTimer % 10 == 0) {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
        }

        if (this.target != null && this.target.removed) {
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

    public void teleFail() {
        this.teleTimer -= this.random.nextInt(40) + 40;
        if (this.y <= 0.0) {
            this.teleTimer = 446;
        }

    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("Anger", (short) this.angerLevel);
        tag.putShort("TeleTimer", (short) this.teleTimer);
        tag.putShort("TimeLeft", (short) this.timeLeft);
        tag.putBoolean("Duel", this.duel);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.angerLevel = tag.getShort("Anger");
        this.teleTimer = tag.getShort("TeleTimer");
        this.timeLeft = tag.getShort("TimeLeft");
        this.duel = tag.getBoolean("Duel");
    }

    public Entity findPlayerToAttack() {
        return this.world.getDifficulty().canHostileMobsSpawn() && this.duel && this.angerLevel > 0 ? super.findPlayerToAttack() : null;
    }

    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (attacker instanceof Player && this.world.getDifficulty().canHostileMobsSpawn()) {
            int pokey;
                if (this.target == null) {
                    this.chatTime = 0;
                    pokey = this.random.nextInt(3);
                    if (pokey == 2) {
                        ((Player) attacker).sendMessage("I'm not going easy on you!");
                    } else if (pokey == 1) {
                        ((Player) attacker).sendMessage("You're gonna regret that!");
                    } else {
                        ((Player) attacker).sendMessage("Now you're in for it!");
                    }
                } else {
                    this.teleTimer -= 10;
                }

                this.setTarget(target);
                boolean flag = super.hurt(target, i, DamageType.COMBAT);
                if (flag && this.getHealth() <= 0) {
                    pokey = this.random.nextInt(3);
                    this.dead = true;
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
            this.maxFireTicks = 0;
            return false;
        }
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.75F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            this.swingArm();
            entity.hurt(this, this.attackStrength, DamageType.COMBAT);
            if (this.target != null && entity == this.target && entity instanceof Mob) {
                Mob e1 = (Mob) entity;
                if (e1.getHealth() <= 0) {
                    this.target = null;
                    this.angerLevel = 0;
                    int pokey = this.random.nextInt(3);
                    this.chatTime = 0;
                    if (pokey == 2) {
                        ((Player) entity).sendMessage("You want a medallion? Try being less pathetic.");
                    } else if (pokey == 1 && e1 instanceof Player) {
                        Player ep = (Player) e1;
                        String s = ep.getDisplayName();
                        ((Player) entity).sendMessage("Maybe some day, " + s + "... maybe some day.");
                    } else {
                        ((Player) entity).sendMessage("Humans aren't nearly as cute when they're dead.");
                    }
                }
            }
        }
    }

    public int getMaxHealth() {
        return 50;
    }

    public ItemStack getHeldItem() {
        return new ItemStack(AetherItems.TOOL_SWORD_VALKYRIE, 1);
    }

}
