package teamport.aether.entity.monster.mimic;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicChestLocked;
import teamport.aether.blocks.BlockLogicChestMimic;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.items.itemtool.ItemToolAxeAether;
import teamport.aether.items.itemtool.ItemToolPickaxeAether;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class MobMimic extends MobMonsterAether implements Enemy, AetherDeathMessage {

    public MobMimic(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "mimic");
        this.attackStrength = 5;
        this.scoreValue = 2000;
        MimicVariant variant = MimicVariant.fromId(this.getSkinVariant());
        this.setSkinVariant(variant.getId());
    }

    @Override
    public void dropDeathItems() {
        MimicVariant variant = MimicVariant.fromId(this.getSkinVariant());
        this.dropItem(new ItemStack(variant.getItemID(), 1, variant.getItemMetadata()), 0);
        for (WeightedRandomLootObject lootObject : mobDrops) {
            ItemStack stack = lootObject.getDefinedItemStack();
            if (stack != null) {
                this.dropItem(stack, 0);
            }
        }
    }

    public Entity findPlayerToAttack() {
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 64.0);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            entity.hurt(this, this.attackStrength, DamageType.COMBAT);
        }

    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        MimicVariant variant = MimicVariant.fromId(this.getSkinVariant());
        String material = variant.getMaterial();

        if (attacker instanceof Player) {
            ItemStack item = ((Player) attacker).inventory.getCurrentItem();
            if (item != null) {
                if (material.equals("wood") && (item.getItem() instanceof ItemToolAxe || item.getItem() instanceof ItemToolAxeAether)) {
                    return super.hurt(attacker, damage * 2, type);
                } else if (material.equals("stone") && (item.getItem() instanceof ItemToolPickaxe || item.getItem() instanceof ItemToolPickaxeAether)) {
                    return super.hurt(attacker, damage * 2, type);
                }
            }
        }
        return super.hurt(attacker, damage, type);
    }

    public String getHurtSound() {
        MimicVariant variant = MimicVariant.fromId(this.getSkinVariant());
        String material = variant.getMaterial();

        if (material.equals("stone")) {
            return "step.stone";
        }
        return "step.wood";
    }

    public String getDeathSound() {
        MimicVariant variant = MimicVariant.fromId(this.getSkinVariant());
        String material = variant.getMaterial();

        if (material.equals("stone")) {
            return "step.stone";
        }
        return "random.door_open";
    }

    public float getSoundVolume() {
        return 0.6F;
    }

    public int getMaxHealth() {
        return 80;
    }

    public void setLoot(List<ItemStack> loot) {
        if (loot == null || loot.isEmpty()) return;
        for (ItemStack itemStack : loot) {
            mobDrops.add(new WeightedRandomLootObject(itemStack));
        }
    }

    @Override
    public void remove() {
        if (this.isAlive()) {
            place();
        }
        super.remove();
    }

    private void place() {
        WorldFeaturePoint point = wfp((int) Math.round(this.x), (int) Math.round(this.y), (int) Math.round(this.z));
        Direction[] check = new Direction[]{NONE, NORTH, EAST, SOUTH, WEST, UP, DOWN};
        for (Direction dir : check) {
            WorldFeaturePoint dPoint = point.copy().moveInDirection(dir);
            if (this.isSafe(world, dPoint)) {
                this.placeChest(dPoint);
                this.populateChest(dPoint);
                return;
            }
        }
    }

    private boolean isSafe(@Nullable World world, WorldFeaturePoint point) {
        Block<?> block = world.getBlock(point.x, point.y, point.z);
        int blockID = block == null ? 0 : block.id();
        Material blockMaterial = blockID == 0 ? Material.air : block.getMaterial();
        return blockID == 0 || blockMaterial.isLiquid();
    }

    private void placeChest(WorldFeaturePoint point) {
        MimicVariant variant = getTarget(world, point);
        world.setBlockAndMetadataWithNotify(point.x, point.y, point.z, AetherBlocks.CHEST_MIMIC.id(), BlockLogicChestMimic.setVariantToMeta(0,variant));
        BlockLogicChestMimic.setDefaultDirection(world, point.x, point.y, point.z);
    }

    private MimicVariant getTarget(World world, WorldFeaturePoint point){
        for (int ix = -5; ix < 5; ix++) {
            for (int iy = -2; iy < 2; iy++) {
                for (int iz = -5; iz < 5; iz++) {

                    Block<?> block = world.getBlock(point.x + ix, point.y + iy, point.z + iz);
                    int blockID = block == null ? 0 : block.id();
                    Material blockMaterial = blockID == 0 ? Material.air : block.getMaterial();
                    BlockLogic blockLogic = block == null ? null : block.getLogic();

                    if (block == null) {
                        continue;
                    }

                    if (blockLogic instanceof BlockLogicChestLocked && blockMaterial.isStone()) {
                        if (blockID == AetherBlocks.CHEST_DUNGEON_BRONZE.id() || blockID == AetherBlocks.CHEST_DUNGEON_BRONZE_LOCKED.id()) {
                            return MimicVariant.DUNGEON_BRONZE;
                        }
                        if (blockID == AetherBlocks.CHEST_DUNGEON_SILVER.id() || blockID == AetherBlocks.CHEST_DUNGEON_SILVER_LOCKED.id()) {
                            return MimicVariant.DUNGEON_SILVER;
                        }
                        if (blockID == AetherBlocks.CHEST_DUNGEON_GOLD.id() || blockID == AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED.id()) {
                            return MimicVariant.DUNGEON_GOLD;
                        }
                    }

                    if(blockLogic instanceof BlockLogicChestMimic){
                        int meta = world.getBlockMetadata(point.x + ix, point.y +iy, point.z +iz);
                        return MimicVariant.fromId(BlockLogicChestMimic.getVariantFromMeta(meta));
                    }

                    if(blockMaterial != Material.wood) {
                        continue;
                    }
                    if(blockLogic instanceof BlockLogicChestPainted && blockID == Blocks.CHEST_PLANKS_OAK_PAINTED.id()){
                        int meta = world.getBlockMetadata(point.x + ix, point.y +iy, point.z +iz);
                        return MimicVariant.fromId(BlockLogicChestMimic.getColorIDFromMeta(meta) + 2);
                    }
                    // TODO for future chest code
//                    if(blockLogic instanceof BlockLogicChestPainted && blockID == AetherBlocks.SKYROOT_PLANKS_OAK_PAINTED.id()){
//                        int meta = world.getBlockMetadata(point.x + ix, point.y +iy, point.z +iz);
//                        return varaintFromMetaData(meta);
//                    }
                    if(blockID == Blocks.CHEST_PLANKS_OAK.id()){
                        return MimicVariant.OAK;
                    }
                }
            }
        }
        return MimicVariant.SKYROOT;
    }

    private void populateChest(WorldFeaturePoint point) {
        Container inventory = BlockLogicChest.getInventory(world, point.x, point.y, point.z);
        if (inventory == null) {
            return;
        }
        List<WeightedRandomLootObject> listLootObj = this.getMobDrops();
        for (WeightedRandomLootObject lootObj : listLootObj) {
            WorldFeatureComponent.placeItemInChest(random, lootObj.getDefinedItemStack(), inventory);
        }
    }
}
