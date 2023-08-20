package bta.aether.block;

import bta.aether.Aether;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class BlockOreAmbrosium extends Block {
    public BlockOreAmbrosium(String key, int id, Material stone) {
        super(key, id, Material.stone);
    }

        public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
            switch (dropCause) {
                case SILK_TOUCH:
                case PICK_BLOCK:
                    return new ItemStack[]{new ItemStack(this)};
                case PROPER_TOOL:
                    return new ItemStack[]{new ItemStack(Aether.ambrosium)};
                default:
                    return null;
            }
        }
    }
