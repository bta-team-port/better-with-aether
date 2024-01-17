package bta.aether.item;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityBossSlider;
import bta.aether.world.AetherDimension;
import bta.aether.util.LootTable;
import bta.aether.world.generate.WorldFeatureAetherDungeonBase;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class ItemDevStick extends Item {
    public ItemDevStick(int id) {
        super(id);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        int dungeon = AetherDimension.registerDungeonToMap(blockX, (blockY), blockZ);

        int size = 5;
        for (int x = -size; x < size; x++) {
            for (int y = 0; y < size*2; y++) {
                for (int z = -size; z < size; z++) {
                    if (world.rand.nextInt(10) == 0) {
                        world.setBlockWithNotify(x + blockX, y + blockY, z + blockZ, AetherBlocks.stoneCarvedLightLocked.id);
                        continue;
                    }
                    world.setBlockWithNotify(x + blockX, y + blockY, z + blockZ, AetherBlocks.stoneCarvedLocked.id);
                }
            }
        }

        size = 4;
        for (int x = -size; x < size; x++) {
            for (int y = 1; y < size*2 + 1; y++) {
                for (int z = -size; z < size; z++) {
                    world.setBlockWithNotify(x + blockX, y + blockY, z + blockZ, 0);
                }
            }
        }

        blockX--;
        blockZ--;

        for (int x = -1; x <= 2; x++) {
            for (int z = -1; z <= 2; z++) {
                for (int y = -1; y <= 1; y++) {
                    world.setBlockWithNotify(blockX + x, blockY + y, blockZ + z, AetherBlocks.stoneCarvedLocked.id);
                }
            }
        }


        world.setBlockWithNotify(blockX, blockY, blockZ, 0);
        world.setBlockWithNotify(blockX, blockY, blockZ + 1, 0);
        world.setBlockWithNotify(blockX + 1, blockY, blockZ, 0);
        world.setBlockWithNotify(blockX + 1, blockY, blockZ + 1, 0);

        EntityBossSlider boss = (EntityBossSlider) WorldFeatureAetherDungeonBase.placeBoss(world, blockX + 1, blockY + 2, blockZ + 1, EntityBossSlider.class);
        System.out.println(boss.getClass());
        boss.setKeychain(WorldFeatureAetherDungeonBase.makeTreasureChest(new LootTable("/assets/aether/lootTables/bronze-rare.json"), 16, true, world, blockX, blockY, blockZ));
        boss.setToDungeon(dungeon);

        boss.pedestalX = blockX;
        boss.pedestalY = blockY + 1;
        boss.pedestalZ = blockZ;

        world.setBlockWithNotify(blockX + 1, blockY + 2, blockZ + 1, AetherBlocks.torchAmbrosium.id);
        return true;
    }
}
