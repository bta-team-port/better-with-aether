package teamport.aether.entity.monster.mimic;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.Global;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.SkinVariantList;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.dungeon.BlockLogicChestMimic;
import teamport.aether.block.dungeon.BlockLogicPaintedChestMimic;
import teamport.aether.block.entity.TileEntityMimic;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.helper.client.MimicClientHelper;
import teamport.aether.helper.unboxed.IntPair;
import teamport.aether.item.item_tool.ItemToolAxeAether;
import teamport.aether.item.item_tool.ItemToolPickaxeAether;
import teamport.aether.world.feature.util.WorldFeatureComponent;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.*;

import static net.minecraft.core.net.command.TextFormatting.RED;
import static net.minecraft.core.net.command.TextFormatting.RESET;
import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.AetherMod.TRANSLATOR;
import static teamport.aether.entity.monster.mimic.MimicRegistry.DEFAULT;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

@SuppressWarnings("java:S110")
public class MobMimic extends MobMonsterAether implements Enemy, AetherDeathMessage {
    private int mimicTime;
    int mimicChestID = AetherBlocks.CHEST_MIMIC_SKYROOT.id();
    int mimicChestMetadata = 0;

    public MobMimic(World world) {
        super(world);
        this.setSize(1.0F, 1.8F);
        this.attackStrength = 5;
        this.scoreValue = 2000;
        this.mimicTime = 60 * Global.TICKS_PER_SECOND;
        this.setTextureIdentifier("aether", "mimic");
        this.setSkinVariant(this.getSkinVariant());

    }

    @Override
    public void spawnInit() {
        MimicEntry randomEntry = MimicRegistry.getRandomEntry(this.random);
        this.entityData.set(3, randomEntry.getMimicVariant());
        this.setBlockData(randomEntry.mimicChestID, randomEntry.mimicChestMetadata);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(3, DEFAULT.getMimicVariant(), Integer.class);
    }

    public final void setVariant(int index) {
        MimicEntry mimicEntry = MimicRegistry.getMimicVariantByID(index);
        this.entityData.set(3, mimicEntry.getMimicVariant());
        this.setBlockData(mimicEntry.mimicChestID, mimicEntry.mimicChestMetadata);
    }

    @Override
    public @NonNull String getDefaultEntityTexture() {
        return String.format("/assets/%s/textures/entity/%s/%s/0.png", this.textureIdentifier.namespace(), DEFAULT.getPathName(), this.textureIdentifier.value());
    }

    @Override
    public @NonNull String getEntityTexture() {
        MimicEntry entry = MimicRegistry.getMimicVariantByID(this.entityData.getInt(3));
        String basePath = String.format("/assets/%s/textures/entity/%s/%s/", this.textureIdentifier.namespace(), this.textureIdentifier.value(), entry.getPathName());
        return basePath + this.getTextureReference() + ".png";
    }

    @Override
    public String getTextureReference() {
        MimicEntry entry = MimicRegistry.getMimicVariantByID(this.entityData.getInt(3));
        SkinVariantList variantList = Global.accessor.getSkinVariantList();
        String basePath = String.format("/assets/%s/textures/entity/%s/%s/", this.textureIdentifier.namespace(), this.textureIdentifier.value(), entry.getPathName());
        return variantList.getSkinReference(basePath + "variants.json", "0", this.getSkinVariant());
    }

    @Override
    public boolean cycleVariant() {
        return !EnvironmentHelper.isMultiplayerServer() && MimicClientHelper.cycleVariant(this);
    }

    public int getMimicVariant() {
        return this.entityData.getInt(3);
    }

    public String getMimicTextureBasePath() {
        MimicEntry entry = MimicRegistry.getMimicVariantByID(this.getMimicVariant());
        return String.format("/assets/%s/textures/entity/%s/%s/", this.textureIdentifier.namespace(), this.textureIdentifier.value(), entry.getPathName());
    }


    public void setBlockData(int mimicChestID, int mimicChestMetadata) {
        this.mimicChestID = mimicChestID;
        this.mimicChestMetadata = mimicChestMetadata;
    }

    public void setChatColor(byte chatColor) {
        this.chatColor = chatColor;
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("MimicChestID", mimicChestID);
        tag.putInt("MimicChestMetadata", mimicChestMetadata);
        tag.putInt("MimicType", this.entityData.getInt(3));

        ListTag lootTag = new ListTag();
        for (WeightedRandomLootObject lootObject : this.mobDrops) {
            ItemStack stack = lootObject.getDefinedItemStack();
            if (stack != null && stack.stackSize > 0) {
                CompoundTag itemTag = new CompoundTag();
                stack.writeToNBT(itemTag);
                lootTag.addTag(itemTag);
            }
        }
        tag.put("MimicLoot", lootTag);
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.mimicChestID = tag.getInteger("MimicChestID");
        this.mimicChestMetadata = tag.getInteger("MimicChestMetadata");
        this.entityData.set(3, tag.getInteger("MimicType"));

        this.mobDrops.clear();
        ListTag lootTag = tag.getList("MimicLoot");
        for (int i = 0; i < lootTag.tagCount(); i++) {
            CompoundTag itemTag = (CompoundTag) lootTag.tagAt(i);
            ItemStack stack = ItemStack.readItemStackFromNbt(itemTag);
            if (stack != null && stack.stackSize > 0) {
                this.mobDrops.add(new WeightedRandomLootObject(stack));
            }
        }
    }

    @Override
    public void dropDeathItems() {
        MimicEntry variant = MimicRegistry.getMimicVariantByID(this.entityData.getInt(3));
        this.dropItem(new ItemStack(variant.getChestID(), 1, variant.getChestMetadata()), 0);
        for (WeightedRandomLootObject lootObject : mobDrops) {
            ItemStack stack = lootObject.getDefinedItemStack();
            if (stack != null) {
                this.dropItem(stack, 0);
            }
        }
    }

    @Override
    public void updateAI() {
        super.updateAI();
        if (target == null && mimicTime-- == 0) {
            this.remove();
        }
    }

    @Override
    public Entity findPlayerToAttack() {
        Player player = PlayerUtil.getClosestNonInvisPlayerToEntity(this.world, this, 64);
        if (player == null || !this.canEntityBeSeen(player) || !player.getGamemode().hasHostileMobs()) {
            return null;
        }
        return player;
    }

    @Override
    public void attackEntity(@NonNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;

            int attack = this.attackStrength;
            if (isWallace()) attack = (int) (attack * 1.5);

            entity.hurt(this, attack, DamageType.COMBAT);
        }
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (damage > 0 && this.isWallace()) damage = Math.max(1, damage / 3);
        return super.hurt(attacker, this.extraDamage(attacker, damage), type);
    }

    public int extraDamage(Entity attacker, int damage) {
        if (!(attacker instanceof Player)) {
            return damage;
        }
        ItemStack item = ((Player) attacker).inventory.getCurrentItem();
        Block<?> block = Blocks.getBlock(this.mimicChestID);
        if (item == null) {
            return damage;
        }
        int baseDamage = damage;
        if (block.getMaterial().isFlammable()) {
            baseDamage += damage;
        }
        if (block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE) && (item.getItem() instanceof ItemToolAxe || item.getItem() instanceof ItemToolAxeAether)
        ) {
            return baseDamage + damage;
        }
        if (block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE) && (item.getItem() instanceof ItemToolPickaxe || item.getItem() instanceof ItemToolPickaxeAether)) {
            return baseDamage + damage;
        }
        return baseDamage;
    }

    @Override
    public String getHurtSound() {
        Block<?> block = Blocks.getBlock(mimicChestID);
        return block.getSound().getStepSoundName();
    }

    @Override
    public String getDeathSound() {
        Block<?> block = Blocks.getBlock(mimicChestID);
        Material material = block.getMaterial();
        if (material == Materials.STONE) {
            return "step.stone";
        }
        return "random.door_open";
    }

    @Override
    public float getSoundVolume() {
        return 0.6F;
    }

    @Override
    public int getMaxHealth() {
        return 80;
    }

    public void setLoot(List<ItemStack> loot) {
        if (loot == null || loot.isEmpty()) return;
        this.mobDrops.clear();
        for (ItemStack itemStack : loot) {
            if (itemStack != null && itemStack.stackSize > 0) {
                mobDrops.add(new WeightedRandomLootObject(itemStack));
            }
        }
    }

    @Override
    public void remove() {
        if (this.isAlive()) {
            place();
        }
        super.remove();
    }

    @Override
    public String deathMessage(@NonNull Player player) {
        EntityDispatcher.EntityDispatcherEntry<?> entry = EntityDispatcher.getInstance().entryForClass(((Entity) this).getClass());
        String key = (entry == null ? "" : entry.nameKey) + ".death_message";
        String deathMessage = TRANSLATOR
            .translateKey(key)
            .replace("[PLAYER]", RESET + String.format("<%s>", player.getDisplayName()) + RESET + RED);
        return RED + deathMessage;
    }

    @Override
    public float getHeadHeight() {
        return this.bbHeight;
    }

    private void place() {
        if (EnvironmentHelper.isMultiplayerClient()) return;

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
        this.placeChest(point);
        this.populateChest(point);
    }

    private boolean isSafe(@Nullable World world, WorldFeaturePoint point) {
        if (world == null) return true;
        Block<?> block = world.getBlock(point.getX(), point.getY(), point.getZ());
        int blockID = block.id();
        Material blockMaterial = blockID == Blocks.AIR.id() ? Materials.AIR : block.getMaterial();
        return blockID == Blocks.AIR.id() || blockMaterial.isLiquid();
    }

    private void placeChest(WorldFeaturePoint point) {
        IntPair blockAndMeta = getTarget(world, point);
        world.setBlockAndMetadataWithNotify(point.getX(), point.getY(), point.getZ(), blockAndMeta.first(), blockAndMeta.second());
        BlockLogicChestMimic.setRandomDirections(world, this.random, point.getX(), point.getY(), point.getZ());
        WorldFeatureComponent.getOrCreateChestInventory(world, new TilePos(point.getX(), point.getY(), point.getZ()));
        TileEntity tileEntity = world.getTileEntity(point.getX(), point.getY(), point.getZ());
        if (tileEntity instanceof TileEntityMimic)
            ((TileEntityMimic) tileEntity).setCustomName(this.nickname, this.chatColor);
    }

    @SuppressWarnings("java:S3776")
    private IntPair getTarget(World world, WorldFeaturePoint point) {
        Map<WorldFeaturePoint, Integer> distance = new HashMap<>();
        Queue<WorldFeaturePoint> queue = new ArrayDeque<>();
        Direction[] check = new Direction[]{NORTH, EAST, SOUTH, WEST, UP, DOWN};
        queue.add(point);
        distance.put(point, 0);
        while (!queue.isEmpty()) {
            WorldFeaturePoint next = queue.poll();
            int cdist = distance.get(next);
            if (cdist >= 5) break;
            for (Direction direction : check) {
                WorldFeaturePoint to = new WorldFeaturePoint(next.getX() + direction.offsetX(), next.getY() + direction.offsetY(), next.getZ() + direction.offsetZ());
                if (distance.getOrDefault(to, -1) != -1) continue;
                distance.put(to, cdist + 1);
                Block<?> block = world.getBlock(to.getX(), to.getY(), to.getZ());
                int metadata = world.getBlockMetadata(to.getX(), to.getY(), to.getZ());
                BlockLogic blockLogic = block.getLogic();
                if (blockLogic instanceof BlockLogicChestMimic) {
                    return new IntPair(block.id(), metadata);
                }
                if (blockLogic instanceof BlockLogicChest) {
                    MimicEntry variant = MimicRegistry.getMimicVariantByChest(block.id(), metadata & 240);
                    return new IntPair(variant.getMimicChestID(), variant.getMimicChestMetadata());
                }
                queue.add(to);
            }
        }
        MimicEntry variant = MimicRegistry.getMimicVariantByID(this.getSkinVariant());
        return new IntPair(variant.getMimicChestID(), variant.getMimicChestMetadata());
    }

    private void populateChest(@NonNull WorldFeaturePoint point) {
        TileEntity tileEntity = world.getTileEntity(point.getX(), point.getY(), point.getZ());
        if (!(tileEntity instanceof Container inventory)) {
            return;
        }
        List<WeightedRandomLootObject> listLootObj = this.getMobDrops();
        for (WeightedRandomLootObject lootObj : listLootObj) {
            WorldFeatureComponent.placeItemInChest(random, lootObj.getDefinedItemStack(), inventory);
        }
    }

    public static void placeWallace(@NonNull World world, int x, int y, int z) {
        BlockLogicPaintedChestMimic blockLogic = AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.getLogic();
        TilePos pos = new TilePos(x, y, z);
        world.setBlockTypeRaw(pos, AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED);
        blockLogic.setColor(world, pos, DyeColor.PURPLE);
        ((TileEntityMimic) world.getTileEntity(pos)).setCustomName("Wallace", (byte) TextFormatting.PURPLE.id);
    }

    public boolean isWallace() {
        MimicEntry variantWallace = MimicRegistry.getMimicVariantByChest(
            AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(),
            AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.getLogic().toMetadata(DyeColor.PURPLE)
        );

        return "Wallace".equals(nickname)
            && variantWallace.getMimicVariant() == this.getSkinVariant();
    }

    @Override
    public boolean canSpawnHere() {
        return this.world.getDifficulty().canHostileMobsSpawn() && this.world.checkIfAABBIsClear(this.bb) && this.world.getCubes(this, this.bb).isEmpty();
    }
}
