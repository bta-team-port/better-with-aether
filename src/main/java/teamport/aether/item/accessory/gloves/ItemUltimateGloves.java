package teamport.aether.item.accessory.gloves;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.item.AetherItems;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryWearing;
import teamport.aether.item.accessory.SlotAccessory;

public class ItemUltimateGloves extends ItemGloves implements ItemGloveTool {
    private final Tag<Block<?>>[] tagEffectiveAgainst = new Tag[]{
        BlockTags.MINEABLE_BY_SWORD,
        BlockTags.MINEABLE_BY_PICKAXE,
        BlockTags.MINEABLE_BY_AXE,
        BlockTags.MINEABLE_BY_SHOVEL,
        AetherBlockTags.MINEABLE_BY_AETHER_SWORD,
        AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE,
        AetherBlockTags.MINEABLE_BY_AETHER_AXE,
        AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL
    };
    private final int damageVsEntity;
    protected ToolMaterial toolMaterial;

    public ItemUltimateGloves(String translationKey, String namespaceId, int id, ArmorMaterial toolMaterial, HumanAccessoryShape humanAccessoryShape) {
        super(translationKey, namespaceId, id, toolMaterial, humanAccessoryShape);
        this.setMaxDamage(350);
        this.damageVsEntity = 5;
        this.toolMaterial = ToolMaterial.diamond;
    }

    @Override
    public boolean canHarvestBlockEQ(Mob mob, ItemStack itemStack, Block<?> block) {
        return this.hasTag(block);
    }

    @Override
    public float getStrVsBlockEQ(ItemStack itemstack, Block<?> block) {
        return this.hasTag(block) ? this.toolMaterial.getEfficiency(false) : 1.0F;
    }

    @SuppressWarnings("java:S3740")
    private boolean hasTag(Block<?> block) {
        for (Tag<Block<?>> tag : this.tagEffectiveAgainst) {
            if (block.hasTag(tag)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack getBrokenItem() {
        return AetherItems.UNCHARGED_ULT_GLOVES.getDefaultStack();
    }


    @Override
    public int getDamageVsEntity(@NotNull ItemStack selfStack, @NotNull Entity entity) {
        return this.damageVsEntity;
    }

    @Override
    public boolean hitEntity(@NotNull ItemStack gloves, @NotNull Mob target, @NotNull Mob attacker) {
        super.hitEntity(gloves, target, attacker);
        if (gloves.stackSize <= 0 && attacker instanceof IAccessoryWearing<?> accessoryWielder) {
            accessoryWielder.better_with_aether$setAccessoryInSlot(SlotAccessory.GLOVES_SLOT, this.getBrokenItem());
        }
        return true;
    }

    /// MP: ServerPlayerController 	:check:
    /// SP: PlayerControllerSP 		:check:
    @Override
    public boolean onBlockDestroyedEQ(World world, ItemStack itemstack, Block<?> block, TilePosc tilePosc, Side side, Mob mob) {
        if (!(mob instanceof Player player)) {
            return false;
        }
        if (block == null || (block.getHardness() <= 0.0F && !this.isSilkTouch())) {
            return true;
        }
        itemstack.damageItem(1, player);
        if (itemstack.stackSize <= 0 && player instanceof IAccessoryWearing<?> accessoryWielder) {
            accessoryWielder.better_with_aether$setAccessoryInSlot(SlotAccessory.GLOVES_SLOT, this.getBrokenItem());
        }
        return true;
    }

    @Override
    public String getTextureName() {
        return "ult";
    }
}
