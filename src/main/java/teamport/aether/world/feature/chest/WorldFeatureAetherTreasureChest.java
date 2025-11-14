package teamport.aether.world.feature.chest;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.dungeon.BlockLogicChestLocked;
import teamport.aether.helper.AetherMathHelper;

import java.util.List;
import java.util.Random;

import static teamport.aether.world.feature.util.WorldFeatureComponent.LootGenerator;
import static teamport.aether.world.feature.util.WorldFeatureComponent.placeItemInChest;

public class WorldFeatureAetherTreasureChest extends WorldFeature {
    private final int chestMetadata;
    private final int chestID;
    private final LootGenerator lootGenerator;
    private final WeightedRandomBag<WeightedRandomLootObject> lootRare;
    protected int guaranteedRare;

    public WorldFeatureAetherTreasureChest(int chestID, int chestMetadata, LootGenerator lootGenerator, WeightedRandomBag<WeightedRandomLootObject> lootRare) {
        this.chestID = chestID;
        this.lootGenerator = lootGenerator;
        this.lootRare = lootRare;
        this.chestMetadata = chestMetadata;
    }

    @Override
    public boolean place(World world, Random random, int ix, int iy, int iz) {
        Container inventory = BlockLogicChest.getInventory(world, ix, iy, iz);
        Block<?> block = world.getBlock(ix, iy, iz);
        if (block != null && inventory != null && block.getLogic() instanceof BlockLogicChestLocked) {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                inventory.setItem(i, null);
            }
        }
        if (block == null || block.id() != chestID) {
            world.setBlockAndMetadataWithNotify(ix, iy, iz, chestID, chestMetadata);
        }
        this.setTreasure(world, random, ix, iy, iz);
        return true;
    }

    public void setTreasure(World world, Random random, int ix, int iy, int iz) {
        Container inventory = BlockLogicChest.getInventory(world, ix, iy, iz);
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
