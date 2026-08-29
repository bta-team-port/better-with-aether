package teamport.aether.world.feature.chest;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.dungeon.BlockLogicChestLocked;
import teamport.aether.helper.AetherMathHelper;

import java.util.List;
import java.util.Random;

import static teamport.aether.world.feature.util.WorldFeatureComponent.LootGenerator;
import static teamport.aether.world.feature.util.WorldFeatureComponent.getOrCreateChestInventory;
import static teamport.aether.world.feature.util.WorldFeatureComponent.placeItemInChest;

public class WorldFeatureAetherTreasureChest implements WorldFeatureInterface {
    private final int chestMetadata;
    private final Block<?> chest;
    private final LootGenerator lootGenerator;
    private final WeightedRandomBag<WeightedRandomLootObject> lootRare;
    protected int guaranteedRare;


    public WorldFeatureAetherTreasureChest(Block<?> chest, int chestMetadata, LootGenerator lootGenerator, WeightedRandomBag<WeightedRandomLootObject> lootRare) {
        this.chest = chest;
        this.lootGenerator = lootGenerator;
        this.lootRare = lootRare;
        this.chestMetadata = chestMetadata;
    }

    public boolean place(@NonNull World world, @NotNull Random random, @NotNull TilePosc pos) {
        Block<?> block = world.getBlockType(pos);
        Container inventory = getOrCreateChestInventory(world, pos);
        if (inventory != null && block.getLogic() instanceof BlockLogicChestLocked) {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                inventory.setItem(i, null);
            }
        }
        if (block != chest) {
            world.setBlockTypeDataNotify(pos, chest, chestMetadata);
        }
        this.setTreasure(world, random, pos);
        return true;
    }

    public void setTreasure(World world, Random random, TilePosc tilePosc) {
        Container inventory = getOrCreateChestInventory(world, tilePosc);
        if (inventory == null) return;
        int quantity = AetherMathHelper.invertedExponentialCapped(random, 1, 9);
        List<ItemStack> normalLoot = lootGenerator.generate(random);
        for (ItemStack stack : normalLoot) {
            placeItemInChest(random, stack, inventory);
        }
        for (int i = 0; i < guaranteedRare + quantity; i++) {
            placeItemInChest(random, lootRare.getRandom().getItemStack(random), inventory);
        }
    }
}
