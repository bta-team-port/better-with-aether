package teamport.aether.entity.valkyrie;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.MobBoss;
import teamport.aether.items.AetherItems;

public class MobBossValkyrie extends MobBoss {

    public MobBossValkyrie(@Nullable World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "boss_valkyrie");
        this.setSize(0.8F, 2.0F);
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_HOLY.getDefaultStack(), 1));
    }

    public ItemStack getHeldItem() {
        return new ItemStack(AetherItems.TOOL_SWORD_HOLY, 1);
    }

}
