package bta.aether.world.generate;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityAetherBossBase;
import bta.aether.entity.EntityDevBoss;
import bta.aether.entity.tileEntity.TileEntityChestLocked;
import bta.aether.item.AetherItems;
import bta.aether.world.LootTable;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureAetherDungeonBase extends WorldFeature {
    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        return false;
    }

    public static ItemStack makeTreasureChest(LootTable lootTable, int quantity, Boolean isLocked, World world, int x, int y, int z){
        return makeTreasureChest(lootTable, quantity, null, isLocked, world, x, y, z);
    }

    public ItemStack makeTreasureChest(LootTable lootTable, int quantity, World world, int x, int y, int z){
        return makeTreasureChest(lootTable, quantity, null, false, world, x, y, z);
    }

    public static ItemStack makeTreasureChest(LootTable lootTable, int quantity, String password, Boolean isLocked, World world, int x, int y, int z){
        ItemStack[] items = lootTable.generateLoot(quantity);

        if (isLocked) {
            world.setBlock(x, y, z, AetherBlocks.dungeonChestLocked.id);
        } else {
            world.setBlockWithNotify(x, y, z, Block.chestPlanksOak.id);
        }

        TileEntityChest chest = (TileEntityChest) world.getBlockTileEntity(x, y, z);

        for (int item = 0; item < items.length;) {
            int slot = world.rand.nextInt(chest.getSizeInventory());
            if (items[item] == null || chest.getStackInSlot(slot) != null) { continue; }
            chest.setInventorySlotContents(slot, items[item]);
            item++;
        }

        if (isLocked) {
            if (password == null) {
                password = String.valueOf(world.rand.nextInt(100_000_000));
            }

            ((TileEntityChestLocked)chest).setLocked(true);
            ((TileEntityChestLocked)chest).setPassword(password);

            ItemStack key = new ItemStack(AetherItems.keyBronze);
            key.getData().putString("password", password);
            return key;
        }

        return null;
    }

    public static EntityAetherBossBase placeBoss(World world, int x, int y, int z, Class<? extends EntityAetherBossBase> aetherBossClass) {
        EntityDevBoss boss;
        try {
            boss = (EntityDevBoss) aetherBossClass.getConstructor(World.class).newInstance(world);
        } catch (Exception exception) {
            Aether.LOGGER.error(String.valueOf(exception));
            return null;
        }

        boss.spawnInit();
        boss.moveTo(x, y, z, 0.0F, 0.0F);
        world.entityJoinedWorld(boss);

        return boss;
    }



}
