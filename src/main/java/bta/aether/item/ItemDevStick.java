package bta.aether.item;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityAetherBossBase;
import bta.aether.entity.EntityDevBoss;
import bta.aether.entity.IAetherBoss;
import bta.aether.world.AetherDimension;
import bta.aether.world.LootTable;
import bta.aether.world.generate.WorldFeatureAetherDungeonBase;
import bta.aether.world.generate.feature.WorldFeatureTreeSkyroot;
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
                    world.setBlockWithNotify(x + blockX, y + blockY, z + blockZ, AetherBlocks.stoneCarvedLocked.id);
                }
            }
        }

        size = 4;
        for (int x = -size; x < size; x++) {
            for (int y =    1; y < size*2 + 1; y++) {
                for (int z = -size; z < size; z++) {
                    world.setBlockWithNotify(x + blockX, y + blockY, z + blockZ, 0);
                }
            }
        }


        EntityAetherBossBase boss = WorldFeatureAetherDungeonBase.placeBoss(world, blockX, blockY + 3, blockZ, EntityDevBoss.class);
        boss.setKeychain(WorldFeatureAetherDungeonBase.makeTreasureChest(new LootTable("/assets/aether/lootTables/bronze-rare.json"), 16, true, world, blockX, blockY + 1, blockZ));
        boss.setToDungeon(dungeon);
        world.setBlockWithNotify(blockX, blockY + 2, blockZ, AetherBlocks.torchAmbrosium.id);
        return true;
        }
}
