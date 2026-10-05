package teamport.aether.item;

import net.minecraft.core.item.IDispensable;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.projectile.ProjectileDart;

import java.util.Random;

public class ItemDart extends Item implements IDispensable {
    private final int dartType;

    public ItemDart(String translationKey, String namespaceId, int id, int dartType) {
        super(translationKey, namespaceId, id);
        this.dartType = dartType;
    }

    @Override
    public void onDispensed(@NonNull ItemStack itemStack, @NonNull World world, @NonNull Random random, @NonNull Direction direction, double x, double y, double z) {
        ProjectileDart dart = new ProjectileDart(world, x, y, z, this.dartType);
        dart.setHeading(direction.offsetX(), direction.offsetY() + 0.1, direction.offsetZ(), 1.1F, 3.0f);
        dart.setDoesDartBelongToPlayer(true);
        world.entityJoinedWorld(dart);
    }
}
