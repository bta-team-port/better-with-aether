package teamport.aether.items;

import net.minecraft.core.item.IDispensable;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.entity.projectile.ProjectileDart;

import java.util.Random;

public class ItemDart extends Item implements IDispensable {
    private final int dartType;

    public ItemDart(String translationKey, String namespaceId, int id, int dartType) {
        super(translationKey, namespaceId, id);
        this.dartType = dartType;
    }

    @Override
    public void onDispensed(ItemStack itemStack, World world, double x, double y, double z, int xOffset, int yOffset, int zOffset, Random random) {
        ProjectileDart dart = new ProjectileDart(world, x, y, z, this.dartType);
        dart.setHeading(xOffset, yOffset + 0.1, zOffset, 1.1F, 3.0f);
        dart.setDoesDartBelongToPlayer(true);
        world.entityJoinedWorld(dart);
    }
}
