package bta.aether.item;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.EntityArmoredZombie;
import net.minecraft.core.entity.monster.EntityGhast;
import net.minecraft.core.entity.monster.EntityPigZombie;
import net.minecraft.core.entity.monster.EntitySkeleton;
import net.minecraft.core.entity.monster.EntitySnowman;
import net.minecraft.core.entity.monster.EntityZombie;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

import java.util.ArrayList;
import java.util.List;

public class ItemSwordHoly extends ItemToolSword {
    public static List<Class<? extends EntityLiving>> undeadList = new ArrayList<>();
    static {
        undeadList.add(EntityZombie.class);
        undeadList.add(EntityPigZombie.class);
        undeadList.add(EntityArmoredZombie.class);
        undeadList.add(EntitySkeleton.class);
        undeadList.add(EntityGhast.class);
        undeadList.add(EntitySnowman.class);
    }
    public ItemSwordHoly(String name, int id, ToolMaterial enumtoolmaterial) {
        super(name, id, enumtoolmaterial);
    }
    @Override
    public int getDamageVsEntity(Entity entity) {
        if (undeadList.contains(entity.getClass())){
            return 10;
        }
        return super.getDamageVsEntity(entity);
    }
}
