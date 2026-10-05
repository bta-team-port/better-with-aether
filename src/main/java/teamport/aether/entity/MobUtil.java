package teamport.aether.entity;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.LevelListener;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.chunk.ChunkCache;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.pathing.base.Path;
import teamport.aether.entity.pathing.base.PathFinder;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherArmorMaterial;

public class MobUtil {

    private MobUtil() {
    }

    public static void knockback(
        Entity target, Entity attacker,
        float knockBackStrength, float lift
    ) {
        if (knockBackStrength < 0.001) {
            throw new RuntimeException("Cannot multiply speed by zero!");
        }
        double distX = attacker.x - target.x;
        double distZ = attacker.z - target.z;

        if (target instanceof Player player) {
            int count = PlayerUtil.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.OBSIDIAN);
            if (count >= 5) {
                return;
            }
            if (count >= 3) {
                distX /= 2;
                distZ /= 2;
            }
        }

        float horizontalDistance = Math.max(0.001F, MathHelper.sqrt(distX * distX + distZ * distZ));

        // half momentum, to slow the player down
        target.xd /= 2.0F; // velocity x
        target.yd /= 2.0F; // velocity y
        target.zd /= 2.0F; // velocity z

        // set velocity, apply knockback
        target.xd -= (distX / horizontalDistance * knockBackStrength);
        target.yd += lift;
        target.zd -= (distZ / horizontalDistance * knockBackStrength);

        target.hurtMarked = true;
    }


    public static boolean multiHit(Entity attacker, Entity victim, DamageInstance... instances) {
        if (instances == null) {
            return false;
        }
        if (instances.length < 2) {
            DamageInstance instance = instances[0];
            return victim.hurt(attacker, instance.getDamage(), instance.getType());
        }
        boolean cumulativeAccept = true;
        int cumulativeDamage = 0;
        for (DamageInstance instance : instances) {
            cumulativeDamage += instance.damage;
            cumulativeAccept = victim.hurt(attacker, cumulativeDamage, instance.getType());
        }
        return cumulativeAccept;
    }

    public static boolean killMob(Mob mob) {
        return MobUtil.killMob(mob, null);
    }

    public static boolean killMob(@NonNull Mob mob, Entity attack) {
        mob.setHealthRaw(0);
        mob.playDeathSound();
        mob.onDeath(attack);
        return true;
    }

    public static void convertMob(Entity mob, String mobId) {
        EntityDispatcher.EntityDispatcherEntry<?> entry = EntityDispatcher.getInstance().entryForId(mobId);
        if (entry == null) {
            return;
        }
        Class<? extends Entity> tempestClazz = entry.entityClass;
        Entity entity = EntityDispatcher.getInstance().createEntityInWorld(tempestClazz, mob.world);
        if (entity == null) {
            return;
        }
        entity.moveTo(mob.x, mob.y, mob.z, mob.yRot, mob.xRot);
        mob.world.entityJoinedWorld(entity);
        mob.remove();
    }

    public static Path getPath(@NotNull World world, @NotNull Entity target, @NotNull PathFinder pathFinder, float distance) {
        int x1 = MathHelper.floor(target.x);
        int y2 = MathHelper.floor(target.y);
        int z1 = MathHelper.floor(target.z);
        int radius = (int)(distance + 16.0F);
        int xMin = x1 - radius;
        int yMin = y2 - radius;
        int zMin = z1 - radius;
        int xMax = x1 + radius;
        int yMax = y2 + radius;
        int zMax = z1 + radius;
        WorldSource chunkcache = new ChunkCache(world, new TilePos(xMin, yMin, zMin),new TilePos(xMax, yMax, zMax), true);
        return pathFinder.findPath(chunkcache, target, distance);
    }

    // the slam does not use explosions because the slider isn't immune to explosions and the explosion actor isn't passed down to hurt.
    public static void doDestroyBlockEffect(@NotNull World world, double x, double y, double z) {
        Vector3dc vec = new Vector3d(x, y , z);
        int resolution = 16;
        for (int ix = 0; ix < resolution; ix++) {
            for (int iy = 0; iy < resolution; iy++) {
                for (int iz = 0; iz < resolution; iz++) {
                    if (ix != 0 && ix != resolution - 1 && iy != 0 && iy != resolution - 1 && iz != 0 && iz != resolution - 1) {
                        continue;
                    }
                    double dx = ix / (double)(resolution - 1) * 2.0 - 1.0;
                    double dy = iy / (double)(resolution - 1) * 2.0 - 1.0;
                    double dz = iz / (double)(resolution - 1) * 2.0 - 1.0;
                    double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    dx /= length;
                    dy /= length;
                    dz /= length;
                    float power = 4.0F * (0.7F + world.rand.nextFloat() * 0.6F);
                    double rayX = vec.x();
                    double rayY = vec.y();
                    double rayZ = vec.z();
                    for (float step = 0.3F; power > 0.0F; power -= step * 0.75F) {
                        TilePos tilePos = new TilePos(MathHelper.floor(rayX), MathHelper.floor(rayY), MathHelper.floor(rayZ));
                        Block<?> block = world.getBlockType(tilePos);
                        if (block != Blocks.AIR) {
                            power -= (block.getBlastResistance(null) + 0.3F) * step;
                        }
                        breakBlock(world, power, block, tilePos);
                        rayX += dx * step;
                        rayY += dy * step;
                        rayZ += dz * step;
                    }
                }
            }
        }
    }

    private static void breakBlock(@NotNull World world, float power, Block<?> block, TilePos tilePos) {
        if (power > 0.0F) {
            block.dropWithCause(world, EnumDropCause.EXPLOSION, tilePos, world.getBlockData(tilePos), world.getTileEntity(tilePos), null);
            world.playBlockEvent(tilePos, LevelListener.EVENT_BLOCK_BREAK, block.id());
            world.setBlockTypeDataNotify(tilePos, Blocks.AIR, 0);
        }
    }

    public static boolean isImmuneToFire(@NonNull MobWolf mobWolf) {
        ItemStack armor = mobWolf.getArmorItem();
        if (armor == null || !(armor.getItem() instanceof IArmorItem)) return false;
        ArmorMaterial armorMaterial = ((IArmorItem<?>) armor.getItem()).getArmorMaterial();
        return armorMaterial != null && armorMaterial.equals(AetherArmorMaterial.PHOENIX);
    }
}
