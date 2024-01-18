package bta.aether.item;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityBossSlider;
import bta.aether.util.StructureLoader;
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
        StructureLoader structure = new StructureLoader();
        structure.loadStructure("/assets/aether/structures/test.json");
        entityplayer.addChatMessage(String.valueOf(structure.symbols));

        structure.generate(world, blockX, blockY, blockZ);
        return true;
    }
}
