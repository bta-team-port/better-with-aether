package teamport.aether.world.generate.feature.chests;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.BlockLogicChestLocked;
import teamport.aether.helper.AetherMathHelper;

import java.util.Random;

import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.placeItemInChest;

public class WorldFeatureAetherTreasureChest extends WorldFeature {
    public int chestID;
    public WeightedRandomBag<WeightedRandomLootObject> LOOT_NORMAL;
    public WeightedRandomBag<WeightedRandomLootObject> LOOT_RARE;
    public int guaranteedRare = 1;

    public WorldFeatureAetherTreasureChest(int chestID, WeightedRandomBag<WeightedRandomLootObject> LOOT_NORMAL, WeightedRandomBag<WeightedRandomLootObject> LOOT_RARE) {
        this.chestID = chestID;
        this.LOOT_NORMAL = LOOT_NORMAL;
        this.LOOT_RARE = LOOT_RARE;
    }

    @Override
    public boolean place(World world, Random random, int ix, int iy, int iz
    ) {
        Container inventory = BlockLogicChest.getInventory(world, ix, iy, iz);
        Block<?> block = world.getBlock(ix, iy, iz);
        if (block != null && inventory != null && block.getLogic() instanceof BlockLogicChestLocked) {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                inventory.setItem(i, null);
            }
        } else {
            world.setBlockAndMetadataWithNotify(ix, iy, iz, chestID, 4);
        }
        this.setTreasure(world, random, ix, iy, iz);
        return true;
    }

    public void setTreasure(
            World world, Random random,
            int x, int y, int z
    ) {
        Container inventory = BlockLogicChest.getInventory(world, x, y, z);
        if (inventory == null) return;
        int invSize = inventory.getContainerSize();
        int quantity = AetherMathHelper.invertedExponentialCapped(random, 1, 9);
        for (int i = 0; i < 10; i++) {
            placeItemInChest(random, LOOT_NORMAL, invSize, inventory);
        }
        for (int i = 0; i < guaranteedRare + quantity; i++) {
            int index = random.nextInt(invSize);
            for (int count = invSize; inventory.getItem(index) != null && count > 0; index++, count--) {
                if (index >= invSize) {
                    index = 0;
                }
            }
            inventory.setItem(index, LOOT_RARE.getRandom().getItemStack(random));
        }
    }
}
