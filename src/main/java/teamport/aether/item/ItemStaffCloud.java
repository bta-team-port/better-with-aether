package teamport.aether.item;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.joml.Vector3dc;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.projectile.ProjectileWindball;

public class ItemStaffCloud extends Item {
    public ItemStaffCloud(String translationKey, String namespaceId, int id) {
        super(translationKey, namespaceId, id);
        this.setMaxStackSize(1);
        this.setMaxDamage(192);
    }

    @Override
    public ItemStack onUse(@NonNull ItemStack itemstack, @NonNull World world, @NonNull Player entityplayer) {
        world.playSoundAtEntity(entityplayer, entityplayer, "aether:mob.zephyr.shoot", 0.3F, 1.0F / (itemRand.nextFloat() * -0.2F - 0.4F));
        if (!world.isClientSide) {
            Vector3dc look = entityplayer.getViewVector(1.0F);
            double lookX = look.x();
            double lookY = look.y();
            double lookZ = look.z();

            double perpX = -lookZ;
            double perpZ = lookX;

            double length = Math.sqrt(perpX * perpX + perpZ * perpZ);
            if (length > 0) {
                perpX = (perpX / length);
                perpZ = (perpZ / length);
            } else {
                perpX = 1;
                perpZ = 0;
            }

            ProjectileWindball windballLeft = new ProjectileWindball(world);
            windballLeft.setPos(entityplayer.x + perpX, entityplayer.y, entityplayer.z + perpZ);
            windballLeft.setHeading(lookX, lookY, lookZ, 1.0f, 0.0f);
            world.entityJoinedWorld(windballLeft);

            ProjectileWindball windballRight = new ProjectileWindball(world);
            windballRight.setPos(entityplayer.x - perpX, entityplayer.y, entityplayer.z - perpZ);
            windballRight.setHeading(lookX, lookY, lookZ, 1.0f, 0.0f);
            world.entityJoinedWorld(windballRight);

            itemstack.damageItem(1, entityplayer);
        }
        return itemstack;
    }
}
