package teamport.aether.compat.commandly;

import redart15.commandly.api.CommandlyPlugin;
import redart15.commandly.veincapitator.OreGroups;
import redart15.commandly.veincapitator.PickAxeRegister;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;
import teamport.aether.item.item_tool.AetherToolMaterial;

public class AetherCommandlyPlugin implements CommandlyPlugin {

    @Override
    public void registerOreGroups(OreGroups registry) {
        OreGroups.OreGroupBuilder.register("ambrosium")
            .addOre(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE);

        OreGroups.OreGroupBuilder.register("zanite")
            .addOre(AetherBlocks.ORE_ZANITE_HOLYSTONE);

        OreGroups.OreGroupBuilder.register("gravitite")
            .addOre(AetherBlocks.ORE_GRAVITITE_HOLYSTONE);
    }

    @Override
    public void registerPickaxe(PickAxeRegister register) {
        PickAxeRegister.register(AetherItems.TOOL_PICKAXE_SKYROOT, AetherToolMaterial.skyroot);
        PickAxeRegister.register(AetherItems.TOOL_PICKAXE_HOLYSTONE, AetherToolMaterial.holystone);
        PickAxeRegister.register(AetherItems.TOOL_PICKAXE_ZANITE, AetherToolMaterial.zanite);
        PickAxeRegister.register(AetherItems.TOOL_PICKAXE_GRAVITITE, AetherToolMaterial.gravitite);
        PickAxeRegister.register(AetherItems.TOOL_PICKAXE_VALKYRIE, AetherToolMaterial.valkyrie);
    }
}
