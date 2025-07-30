package teamport.aether.lookup;

import net.minecraft.core.item.*;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.item.tool.ItemToolSword;

import java.util.HashSet;
import java.util.Set;

public class Repairable {
    public static final Repairable instance = new Repairable();
    public final Set<Class> registry = new HashSet<>();

    public Repairable() {
        this.init();
    }

    public void init(){
        this.register(ItemTool.class);
        this.register(ItemArmor.class);
        this.register(ItemFireStriker.class);
        this.register(ItemBow.class);
        this.register(ItemToolSword.class);
    }

    public void register(Class clazz) {
        this.registry.add(clazz);
    }

    public boolean isRepairable(ItemStack stack) {
        if(stack == null) return false;
        Item item = stack.getItem();
        return isRepairable(item);
    }

    public boolean isRepairable(Item stack) {
        return registry.stream().anyMatch(clazz -> clazz.isInstance(stack));
    }

}
