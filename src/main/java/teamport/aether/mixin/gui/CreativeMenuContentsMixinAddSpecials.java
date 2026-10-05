package teamport.aether.mixin.gui;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.player.inventory.CreativeMenuContents;
import net.minecraft.core.util.helper.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;

import java.util.List;

@Mixin(value = CreativeMenuContents.class, priority = 1100)
public abstract class CreativeMenuContentsMixinAddSpecials {

    @Shadow
    private static @NotNull IPainted painted(@NotNull Block<?> block) {
        throw new AssertionError();
    }

    @Shadow
    @Final
    private static DyeColor[] RAINBOW_ORDER;

    @WrapMethod(method = "populate")
    private static void addMisc(List<ItemStack> list, Operation<Void> original) {
        original.call(list);
        for (int i = 0; i < list.size(); i++) {
            ItemStack stack = list.get(i);

            // fuck these blanks
            if (stack == null) {
                list.remove(i);
                i--;
                continue;
            }

            if(stack.itemID == Blocks.FLOWER_ORANGE.id()){
                list.add(++i, new ItemStack(AetherBlocks.FLOWER_PURPLE, 1, 0));
                list.add(++i, new ItemStack(AetherBlocks.FLOWER_WHITE, 1, 0));
            }
            if (stack.itemID  == Items.SIGN_PAINTED.id) {
                Item item = stack.getItem();
                int metadata = stack.getMetadata();
                DyeColor color = getColor(item, metadata);
                list.add(++i, addPaintedWoodBlock(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED, color));
                list.add(++i, addPaintedWoodBlock(AetherBlocks.BUTTON_PLANKS_SKYROOT_PAINTED, color));
                list.add(++i, addPaintedWoodBlock(AetherBlocks.PLANKS_SKYROOT_PAINTED, color));
                list.add(++i, addPaintedWoodBlock(AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED, color));
                list.add(++i, addPaintedWoodBlock(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED, color));
                list.add(++i, addPaintedWoodBlock(AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED, color));
                list.add(++i, addPaintedWoodBlock(AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED, color));
                list.add(++i, addPaintedWoodBlock(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED, color));
                list.add(++i, addPaintedWoodBlock(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED, color));
                list.add(++i, new ItemStack(AetherItems.DOOR_SKYROOT_PAINTED, 1, color.itemMeta));
                list.add(++i, new ItemStack(AetherItems.SIGN_SKYROOT_PAINTED, 1, color.itemMeta));

            }
            if(stack.itemID  == Items.BUCKET_STEEL.id && ItemBucket.getState(stack).equals(ItemBucket.STATE_ICECREAM)){
                list.add(++i, new ItemStack(AetherItems.BUCKET_SKYROOT));
                list.add(++i, new ItemStack(AetherItems.BUCKET_SKYROOT_WATER));
                list.add(++i, new ItemStack(AetherItems.BUCKET_SKYROOT_MILK));
                list.add(++i, new ItemStack(AetherItems.BUCKET_SKYROOT_ICECREAM));
                list.add(++i, new ItemStack(AetherItems.BUCKET_SKYROOT_POISON));
                list.add(++i, new ItemStack(AetherItems.BUCKET_SKYROOT_REMEDY));
            }
            if(stack.itemID  == AetherBlocks.PILLAR_CAPSTONE.id()){
                list.add(++i, new ItemStack(AetherBlocks.CHEST_MIMIC_OAK));
                list.add(++i, new ItemStack(AetherBlocks.CHEST_MIMIC_SKYROOT));
                for (DyeColor color : RAINBOW_ORDER) {
                    list.add(++i, addPaintedWoodBlock(AetherBlocks.CHEST_MIMIC_OAK_PAINTED, color));
                    list.add(++i, addPaintedWoodBlock(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED, color));
                }
                list.add(++i, new ItemStack(AetherBlocks.CHEST_MIMIC_BRONZE));
                list.add(++i, new ItemStack(AetherBlocks.CHEST_MIMIC_SILVER));
                list.add(++i, new ItemStack(AetherBlocks.CHEST_MIMIC_GOLD));
            }
            if(stack.itemID == AetherItems.ARMOR_WOLF_NEPTUNE.id){
                list.add(++i, new ItemStack(AetherItems.ARMOR_SHIELD_REPULSION));

                list.add(++i, new ItemStack(AetherItems.ARMOR_TALISMAN_BUBBLE));
                list.add(++i, new ItemStack(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD));
                list.add(++i, new ItemStack(AetherItems.ARMOR_TALISMAN_REGEN));

                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_AGILITY));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_INVISIBILITY));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_SWET));

                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_RED));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_ORANGE));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_YELLOW));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_LIME));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_GREEN));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_CYAN));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_LIGHTBLUE));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_BLUE));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_PURPLE));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_MAGENTA));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_PINK));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_BROWN));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_WHITE));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_SILVER));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_GRAY));
                list.add(++i, new ItemStack(AetherItems.ARMOR_CAPE_BLACK));

                list.add(++i, new ItemStack(AetherItems.PARACHUTE_CLOUD));
                list.add(++i, new ItemStack(AetherItems.PARACHUTE_CLOUD_GOLD));
            }
        }
    }

    @Unique
    private static @NotNull DyeColor getColor(Item item, int metadata) {
        if (item instanceof ItemBlock<?>) {
            return DyeColor.colorFromBlockMeta(metadata);
        }
        return DyeColor.colorFromItemMeta(metadata);
    }

    @Unique
    private static ItemStack addPaintedWoodBlock(Block<?> block, DyeColor color) {
        return new ItemStack(block, 1, painted(block).toMetadata(color));
    }

}
