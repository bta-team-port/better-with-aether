package bta.aether.world.generate;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityAetherBossBase;
import bta.aether.tile.TileEntityChestLocked;
import bta.aether.util.LootTable;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkCoordinate;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public abstract class WorldFeatureAetherDungeonBase extends WorldFeature {

    public static ItemStack makeTreasureChest(LootTable lootTable, int quantity, Item itemKey, Boolean isLocked, World world, int x, int y, int z){
        return makeTreasureChest(lootTable, quantity, itemKey, null, isLocked, world, x, y, z);
    }
    public static ItemStack makeTreasureChest(LootTable lootTable, int quantity, Item itemKey, String password, Boolean isLocked, World world, int x, int y, int z){
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
            ((TileEntityChestLocked)chest).setPasswordHashed(password);

            ItemStack key = new ItemStack(itemKey);
            key.getData().putString("password", password);
            return key;
        }

        return null;
    }

    public static EntityAetherBossBase placeBoss(World world, int x, int y, int z, Class<? extends EntityAetherBossBase> aetherBossClass) {
        EntityAetherBossBase boss;
        try {
            boss = aetherBossClass.getConstructor(World.class).newInstance(world);
        } catch (Exception exception) {
            Aether.LOGGER.error("SOMETHING WENT WRONG");
            Aether.LOGGER.error(String.valueOf(exception));
            return null;
        }

        boss.spawnInit();
        boss.moveTo(x, y, z, 0.0F, 0.0F);
        world.entityJoinedWorld(boss);

        return boss;
    }

    public ItemStack makeTreasureChest(LootTable lootTable, int quantity, World world, int x, int y, int z){
        return makeTreasureChest(lootTable, quantity, null, null, false, world, x, y, z);
    }
    public int[] drawLine(World world, int id, int meta, Direction direction, int length, int startX, int startY, int startZ, boolean withNotify){
        for (int i = 0; i < length - 1; i++) {
            setBlock(world,startX, startY, startZ, id, meta, withNotify);
            startX += direction.getOffsetX();
            startY += direction.getOffsetY();
            startZ += direction.getOffsetZ();
        }
        setBlock(world,startX, startY, startZ, id, meta, withNotify);
        return new int[]{startX, startY, startZ};
    }
    public int[] drawPlane(World world, int id, int meta, Direction direction1, int length1, Direction direction2, int length2, int startX, int startY, int startZ, boolean withNotify){
        int blockX = startX;
        int blockY = startY;
        int blockZ = startZ;
        for (int i = 0; i < length2; i++) {
            blockX = startX + direction2.getOffsetX() * i;
            blockY = startY + direction2.getOffsetY() * i;
            blockZ = startZ + direction2.getOffsetZ() * i;
            for (int j = 0; j < length1; j++) {
                setBlock(world,blockX, blockY, blockZ, id, meta, withNotify);
                blockX += direction1.getOffsetX();
                blockY += direction1.getOffsetY();
                blockZ += direction1.getOffsetZ();
            }
        }
        return new int[]{blockX, blockY, blockZ};
    }
    public int[] drawVolume(World world, int id, int meta, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3, int startX, int startY, int startZ, boolean withNotify){
        int blockX = startX;
        int blockY = startY;
        int blockZ = startZ;
        for (int i = 0; i < length3; i++) {
            int x3 = startX + direction3.getOffsetX() * i;
            int y3 = startY + direction3.getOffsetY() * i;
            int z3 = startZ + direction3.getOffsetZ() * i;
            for (int j = 0; j < length2; j++) {
                blockX = x3 + direction2.getOffsetX() * j;
                blockY = y3 + direction2.getOffsetY() * j;
                blockZ = z3 + direction2.getOffsetZ() * j;
                for (int k = 0; k < length1; k++) {
                    setBlock(world,blockX, blockY, blockZ, id, meta, withNotify);
                    blockX += direction1.getOffsetX();
                    blockY += direction1.getOffsetY();
                    blockZ += direction1.getOffsetZ();
                }
            }
        }
        return new int[]{blockX, blockY, blockZ};
    }
    public int[] drawShell(World world, int id, int meta, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3, int startX, int startY, int startZ, boolean withNotify){
        drawPlane(world, id, meta, direction1, length1, direction2, length2, startX, startY, startZ, withNotify);
        drawPlane(world, id, meta, direction1, length1, direction2, length2, startX + direction3.getOffsetX() * (length3 - 1), startY + direction3.getOffsetY() * (length3 - 1), startZ + direction3.getOffsetZ() * (length3 - 1), withNotify);

        drawPlane(world, id, meta, direction1, length1, direction3, length3, startX, startY, startZ, withNotify);
        drawPlane(world, id, meta, direction1, length1, direction3, length3, startX + direction2.getOffsetX() * (length2 - 1), startY + direction2.getOffsetY() * (length2 - 1), startZ + direction2.getOffsetZ() * (length2 - 1), withNotify);

        drawPlane(world, id, meta, direction2, length2, direction3, length3, startX, startY, startZ, withNotify);
        return drawPlane(world, id, meta, direction2, length2, direction3, length3, startX + direction1.getOffsetX() * (length1 - 1), startY + direction1.getOffsetY() * (length1 - 1), startZ + direction1.getOffsetZ() * (length1 - 1), withNotify);
    }
    protected void setBlock(World world, int x, int y, int z, int id, int meta, boolean withNotify){
        if (withNotify){
            world.setBlockAndMetadataWithNotify(x, y, z, id, meta);
        } else {
            world.setBlockAndMetadata(x, y, z, id, meta);
        }
    }
}
