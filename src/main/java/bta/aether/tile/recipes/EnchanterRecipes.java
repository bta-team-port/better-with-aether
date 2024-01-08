package bta.aether.tile.recipes;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemRecord;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.Pair;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Deprecated
public class EnchanterRecipes {
    protected static final HashMap<ItemStack, Pair<ItemStack,Integer>> recipeList = new HashMap<>();

    public static HashMap<ItemStack, Pair<ItemStack,Integer>> getRecipeList() {
        return recipeList;
    }

    public static void addRecipe(ItemStack input, ItemStack output, int time) {
        recipeList.put(input,Pair.of(output,time));
    }

    public static ItemStack getResult(ItemStack input) {
        for (Map.Entry<ItemStack, Pair<ItemStack, Integer>> entry : recipeList.entrySet()) {
            ItemStack K = entry.getKey();
            ItemStack V = entry.getValue().getLeft();
            if (K.itemID == input.itemID) {
                return V.copy();
            }
        }
        return null;
    }

    public static int getTime(ItemStack input){
        for (Map.Entry<ItemStack, Pair<ItemStack, Integer>> entry : recipeList.entrySet()) {
            ItemStack K = entry.getKey();
            int V = entry.getValue().getRight();
            if (K.itemID == input.itemID) {
                return V;
            }
        }
        return 0;
    }

    static {
        addRecipe(new ItemStack(AetherBlocks.oreGravititeHolystone,1),new ItemStack(AetherBlocks.gravititeEnchanted),1000);
        addRecipe(new ItemStack(AetherItems.dartGolden,1),new ItemStack(AetherItems.dartEnchanted),500);
        addRecipe(new ItemStack(AetherItems.dartPoison,1),new ItemStack(AetherItems.dartEnchanted),500);
        addRecipe(new ItemStack(AetherItems.bucketSkyrootPoison,1),new ItemStack(AetherItems.bucketSkyrootRemedy),1000);
        addRecipe(new ItemStack(AetherBlocks.quicksoil, 1),new ItemStack(AetherBlocks.glassQuicksoil),500);
        addRecipe(new ItemStack(AetherBlocks.holystone,1),new ItemStack(AetherItems.healingstone),1000);
        addRecipe(new ItemStack(AetherItems.dartshooter,1),new ItemStack(AetherItems.dartEnchanted),2000);
        addRecipe(new ItemStack(AetherItems.dartshooterPoison,1),new ItemStack(AetherItems.dartEnchanted),2000);

        addRecipe(new ItemStack(AetherItems.toolSwordSkyroot,1),new ItemStack(AetherItems.toolSwordSkyroot),500);
        addRecipe(new ItemStack(AetherItems.toolPickaxeSkyroot,1),new ItemStack(AetherItems.toolPickaxeSkyroot),500);
        addRecipe(new ItemStack(AetherItems.toolShovelSkyroot,1),new ItemStack(AetherItems.toolShovelSkyroot),500);
        addRecipe(new ItemStack(AetherItems.toolAxeSkyroot,1),new ItemStack(AetherItems.toolAxeSkyroot),500);

        addRecipe(new ItemStack(AetherItems.toolSwordHolystone,1),new ItemStack(AetherItems.toolSwordHolystone),1000);
        addRecipe(new ItemStack(AetherItems.toolPickaxeHolystone,1),new ItemStack(AetherItems.toolPickaxeHolystone),1000);
        addRecipe(new ItemStack(AetherItems.toolShovelHolystone,1),new ItemStack(AetherItems.toolShovelHolystone),1000);
        addRecipe(new ItemStack(AetherItems.toolAxeHolystone,1),new ItemStack(AetherItems.toolAxeHolystone),1000);

        addRecipe(new ItemStack(AetherItems.toolSwordZanite,1),new ItemStack(AetherItems.toolSwordZanite),2000);
        addRecipe(new ItemStack(AetherItems.toolPickaxeZanite,1),new ItemStack(AetherItems.toolPickaxeZanite),2000);
        addRecipe(new ItemStack(AetherItems.toolShovelZanite,1),new ItemStack(AetherItems.toolShovelZanite),2000);
        addRecipe(new ItemStack(AetherItems.toolAxeZanite,1),new ItemStack(AetherItems.toolAxeZanite),2000);

        addRecipe(new ItemStack(AetherItems.toolSwordGravitite,1),new ItemStack(AetherItems.toolSwordGravitite),6000);
        addRecipe(new ItemStack(AetherItems.toolPickaxeGravitite,1),new ItemStack(AetherItems.toolPickaxeGravitite),6000);
        addRecipe(new ItemStack(AetherItems.toolShovelGravitite,1),new ItemStack(AetherItems.toolShovelGravitite),6000);
        addRecipe(new ItemStack(AetherItems.toolAxeGravitite,1),new ItemStack(AetherItems.toolAxeGravitite),6000);

        addRecipe(new ItemStack(Item.toolSwordWood,1),new ItemStack(Item.toolSwordWood),500);
        addRecipe(new ItemStack(Item.toolPickaxeWood,1),new ItemStack(Item.toolPickaxeWood),500);
        addRecipe(new ItemStack(Item.toolShovelWood,1),new ItemStack(Item.toolShovelWood),500);
        addRecipe(new ItemStack(Item.toolAxeWood,1),new ItemStack(Item.toolAxeWood),500);
        addRecipe(new ItemStack(Item.toolHoeWood,1),new ItemStack(Item.toolHoeWood),500);

        addRecipe(new ItemStack(Item.toolSwordStone,1),new ItemStack(Item.toolSwordStone),1000);
        addRecipe(new ItemStack(Item.toolPickaxeStone,1),new ItemStack(Item.toolPickaxeStone),1000);
        addRecipe(new ItemStack(Item.toolShovelStone,1),new ItemStack(Item.toolShovelStone),1000);
        addRecipe(new ItemStack(Item.toolAxeStone,1),new ItemStack(Item.toolAxeStone),1000);
        addRecipe(new ItemStack(Item.toolHoeStone,1),new ItemStack(Item.toolHoeStone),1000);

        addRecipe(new ItemStack(Item.toolSwordIron,1),new ItemStack(Item.toolSwordIron),2000);
        addRecipe(new ItemStack(Item.toolPickaxeIron,1),new ItemStack(Item.toolPickaxeIron),2000);
        addRecipe(new ItemStack(Item.toolShovelIron,1),new ItemStack(Item.toolShovelIron),2000);
        addRecipe(new ItemStack(Item.toolAxeIron,1),new ItemStack(Item.toolAxeIron),2000);
        addRecipe(new ItemStack(Item.toolHoeIron,1),new ItemStack(Item.toolHoeIron),2000);

        addRecipe(new ItemStack(Item.toolSwordGold,1),new ItemStack(Item.toolSwordGold),1000);
        addRecipe(new ItemStack(Item.toolPickaxeGold,1),new ItemStack(Item.toolPickaxeGold),1000);
        addRecipe(new ItemStack(Item.toolShovelGold,1),new ItemStack(Item.toolShovelGold),1000);
        addRecipe(new ItemStack(Item.toolAxeGold,1),new ItemStack(Item.toolAxeGold),1000);
        addRecipe(new ItemStack(Item.toolHoeGold,1),new ItemStack(Item.toolHoeGold),1000);

        addRecipe(new ItemStack(Item.toolSwordDiamond,1),new ItemStack(Item.toolSwordDiamond),6000);
        addRecipe(new ItemStack(Item.toolPickaxeDiamond,1),new ItemStack(Item.toolPickaxeDiamond),6000);
        addRecipe(new ItemStack(Item.toolShovelDiamond,1),new ItemStack(Item.toolShovelDiamond),6000);
        addRecipe(new ItemStack(Item.toolAxeDiamond,1),new ItemStack(Item.toolAxeDiamond),6000);
        addRecipe(new ItemStack(Item.toolHoeDiamond,1),new ItemStack(Item.toolHoeDiamond),6000);

        addRecipe(new ItemStack(Item.toolSwordSteel,1),new ItemStack(Item.toolSwordSteel),16000);
        addRecipe(new ItemStack(Item.toolPickaxeSteel,1),new ItemStack(Item.toolPickaxeSteel),16000);
        addRecipe(new ItemStack(Item.toolShovelSteel,1),new ItemStack(Item.toolShovelSteel),16000);
        addRecipe(new ItemStack(Item.toolAxeSteel,1),new ItemStack(Item.toolAxeSteel),16000);
        addRecipe(new ItemStack(Item.toolHoeSteel,1),new ItemStack(Item.toolHoeSteel),16000);

        addRecipe(new ItemStack(Item.toolBow,1),new ItemStack(Item.toolBow),500);
        addRecipe(new ItemStack(Item.toolFishingrod,1),new ItemStack(Item.toolFishingrod),500);
        addRecipe(new ItemStack(Item.toolShears,1),new ItemStack(Item.toolShears),2000);
        addRecipe(new ItemStack(Item.toolFirestriker,1),new ItemStack(Item.toolFirestriker),2000);
        addRecipe(new ItemStack(Item.toolShearsSteel,1),new ItemStack(Item.toolShearsSteel),16000);
        addRecipe(new ItemStack(Item.toolFirestrikerSteel,1),new ItemStack(Item.toolFirestrikerSteel),16000);

        addRecipe(new ItemStack(Item.armorHelmetLeather,1),new ItemStack(Item.armorHelmetLeather),500);
        addRecipe(new ItemStack(Item.armorChestplateLeather,1),new ItemStack(Item.armorHelmetLeather),500);
        addRecipe(new ItemStack(Item.armorLeggingsLeather,1),new ItemStack(Item.armorHelmetLeather),500);
        addRecipe(new ItemStack(Item.armorBootsLeather,1),new ItemStack(Item.armorHelmetLeather),500);

        addRecipe(new ItemStack(Item.armorHelmetChainmail,1),new ItemStack(Item.armorHelmetChainmail),3000);
        addRecipe(new ItemStack(Item.armorChestplateChainmail,1),new ItemStack(Item.armorChestplateChainmail),3000);
        addRecipe(new ItemStack(Item.armorLeggingsChainmail,1),new ItemStack(Item.armorLeggingsChainmail),3000);
        addRecipe(new ItemStack(Item.armorBootsChainmail,1),new ItemStack(Item.armorBootsChainmail),3000);

        addRecipe(new ItemStack(Item.armorHelmetIron,1),new ItemStack(Item.armorHelmetIron),2000);
        addRecipe(new ItemStack(Item.armorChestplateIron,1),new ItemStack(Item.armorChestplateIron),2000);
        addRecipe(new ItemStack(Item.armorLeggingsIron,1),new ItemStack(Item.armorLeggingsIron),2000);
        addRecipe(new ItemStack(Item.armorBootsIron,1),new ItemStack(Item.armorBootsIron),2000);

        addRecipe(new ItemStack(Item.armorHelmetGold,1),new ItemStack(Item.armorHelmetGold),6000);
        addRecipe(new ItemStack(Item.armorChestplateGold,1),new ItemStack(Item.armorChestplateGold),6000);
        addRecipe(new ItemStack(Item.armorLeggingsGold,1),new ItemStack(Item.armorLeggingsGold),6000);
        addRecipe(new ItemStack(Item.armorBootsGold,1),new ItemStack(Item.armorBootsGold),6000);

        addRecipe(new ItemStack(Item.armorHelmetDiamond,1),new ItemStack(Item.armorHelmetDiamond),6000);
        addRecipe(new ItemStack(Item.armorChestplateDiamond,1),new ItemStack(Item.armorChestplateDiamond),6000);
        addRecipe(new ItemStack(Item.armorLeggingsDiamond,1),new ItemStack(Item.armorLeggingsDiamond),6000);
        addRecipe(new ItemStack(Item.armorBootsDiamond,1),new ItemStack(Item.armorBootsDiamond),6000);

        addRecipe(new ItemStack(Item.armorHelmetSteel,1),new ItemStack(Item.armorHelmetSteel),16000);
        addRecipe(new ItemStack(Item.armorChestplateSteel,1),new ItemStack(Item.armorChestplateSteel),16000);
        addRecipe(new ItemStack(Item.armorLeggingsSteel,1),new ItemStack(Item.armorLeggingsSteel),16000);
        addRecipe(new ItemStack(Item.armorBootsSteel,1),new ItemStack(Item.armorBootsSteel),16000);

        addRecipe(new ItemStack(AetherItems.armorHelmetZanite,1),new ItemStack(AetherItems.armorHelmetZanite),2000);
        addRecipe(new ItemStack(AetherItems.armorChestplateZanite,1),new ItemStack(AetherItems.armorChestplateZanite),2000);
        addRecipe(new ItemStack(AetherItems.armorLeggingsZanite,1),new ItemStack(AetherItems.armorLeggingsZanite),2000);
        addRecipe(new ItemStack(AetherItems.armorBootsZanite,1),new ItemStack(AetherItems.armorBootsZanite),2000);

        addRecipe(new ItemStack(AetherItems.armorHelmetGravitite,1),new ItemStack(AetherItems.armorHelmetGravitite),6000);
        addRecipe(new ItemStack(AetherItems.armorChestplateGravitite,1),new ItemStack(AetherItems.armorChestplateGravitite),6000);
        addRecipe(new ItemStack(AetherItems.armorLeggingsGravitite,1),new ItemStack(AetherItems.armorLeggingsGravitite),6000);
        addRecipe(new ItemStack(AetherItems.armorBootsGravitite,1),new ItemStack(AetherItems.armorBootsGravitite),6000);

        addRecipe(new ItemStack(AetherItems.armorBootsGravitite,1),new ItemStack(AetherItems.armorBootsGravitite),6000);



        addRecipe(new ItemStack(Item.record13, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordBlocks, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordCat, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordChirp, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordDog, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordFar, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordMall, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordMellohi, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordStal, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordStrad, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordWait, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordWard, 1),new ItemStack(AetherItems.recordBlue),1000);
        addRecipe(new ItemStack(Item.recordDog, 1),new ItemStack(AetherItems.recordBlue),1000);

    }
}
