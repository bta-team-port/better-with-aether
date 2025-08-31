package teamport.aether.world.generate.feature;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.item.ItemStack;
import org.checkerframework.checker.units.qual.A;
import teamport.aether.helper.AetherMathHelper;
import teamport.aether.items.ItemStaffCloud;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootManager {
    WeightedRandomBag<WeightedRandomLootObject> JUNK;
    WeightedRandomBag<WeightedRandomLootObject> AMMO;
    WeightedRandomBag<WeightedRandomLootObject> GADGET;
    WeightedRandomBag<WeightedRandomLootObject> ARMOR;

    public LootManager(
            WeightedRandomBag<WeightedRandomLootObject> JUNK,
            WeightedRandomBag<WeightedRandomLootObject> AMMO,
            WeightedRandomBag<WeightedRandomLootObject> GADGET,
            WeightedRandomBag<WeightedRandomLootObject> ARMOR
    ){
        this.ARMOR = ARMOR;
        this.AMMO = AMMO;
        this.JUNK = JUNK;
        this.GADGET = GADGET;
    }

    public List<ItemStack> getLoot(Random random){
        List<ItemStack> loot = new ArrayList<>();
        int count = AetherMathHelper.invertedExponentialCapped(random, 0.5F, 2) + 1;
        for(int i = 0; i < count; i++) loot.add(ARMOR.getRandom(random).getItemStack());

        count = AetherMathHelper.invertedExponentialCapped(random, 0.5F, 2);
        for(int i = 0; i < count; i++) loot.add(GADGET.getRandom(random).getItemStack());

        count = random.nextInt(5 - 2 + 1) + 2;
        for(int i = 0; i < count; i++) loot.add(AMMO.getRandom(random).getItemStack());

        count = random.nextInt(10 - 8 + 1) + 8;
        for(int i = 0; i < count; i++) loot.add(JUNK.getRandom(random).getItemStack());

        return loot;
    }

}
