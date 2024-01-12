package bta.aether.world.generate.loot;

import com.b100.utils.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.commands.GiveCommand;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

class Loot {

    // the actual item
    public Item item;

    // how rare it is, for example, 99 equals a one hundred in one chance of finding it.
    public int rarity;

    // the max and minimum stack sizes for a given item.
    public int maxQuantity;
    public int minQuantity;


    // the range of damage values a given item can spawn with.
    public int maxMetadata;
    public int minMetadata;

    public Loot(Item item, int rarity, int minMetadata, int maxMetadata, int minQuantity, int maxQuantity) {
        this.item = item;
        this.rarity = rarity;

        this.maxMetadata = maxMetadata;
        this.minMetadata = minMetadata;
        this.maxQuantity = maxQuantity;
        this.minQuantity = minQuantity;
    }

}

public class LootTable {

    public HashMap<Integer, Loot> lootTable = new HashMap<>();
    private final Random random = new Random();

    public LootTable(String path){
        String jsonString = StringUtils.readInputString(DataLoader.class.getResourceAsStream(path));
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        for (JsonElement element : jsonArray) {
            if (!element.isJsonNull()) {
                JsonObject lootTableJson = element.getAsJsonObject();
                Item item = null;
                if (!lootTableJson.get("key").getAsString().equals("null")) {
                    item = GiveCommand.getItem(lootTableJson.get("key").getAsString());
                }


                lootTable.put(lootTable.size(), new Loot(
                        item,
                        lootTableJson.get("rarity").getAsInt(),
                        lootTableJson.getAsJsonObject("meta").get("min").getAsInt(),
                        lootTableJson.getAsJsonObject("meta").get("max").getAsInt(),
                        lootTableJson.getAsJsonObject("amount").get("min").getAsInt(),
                        lootTableJson.getAsJsonObject("amount").get("max").getAsInt()
                ));
            }
        }
    }

    public ItemStack[] generateLoot(int quantity){
        ItemStack[] result = new ItemStack[quantity];
        for (int slot = 0; slot < quantity; slot++) {
            result[slot] = getLootItem(lootTable.get(random.nextInt(lootTable.size())));
        }
        return result;
    }

    public ItemStack getLootItem(Loot loot) {
        int quantity;
        int metadata;

        if ( loot.rarity != 0) {

            if (random.nextInt(loot.rarity) == 0) {

                if (loot.maxQuantity - loot.minQuantity > 0)
                    quantity = random.nextInt(loot.maxQuantity - loot.minQuantity) + loot.minQuantity;
                else quantity = loot.minQuantity;

                if (loot.maxMetadata - loot.minMetadata > 0)
                    metadata = random.nextInt( loot.maxMetadata - loot.minMetadata) + loot.minMetadata;
                else metadata = loot.minMetadata;

            } else return null;

        } else {

            // if rarity equals to zero.
            if (loot.maxQuantity - loot.minQuantity > 0)
                quantity = random.nextInt(loot.maxQuantity - loot.minQuantity) + loot.minQuantity;
            else quantity = loot.minQuantity;

            if (loot.maxMetadata - loot.minMetadata > 0)
                metadata = random.nextInt( loot.maxMetadata - loot.minMetadata) + loot.minMetadata;
            else metadata = loot.minMetadata;

        }

        return  new ItemStack(loot.item, quantity, metadata);
    }
}
