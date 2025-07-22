package teamport.aether.mixin;

import net.minecraft.client.gui.guidebook.GuidebookSections;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.guidebook.enchanter.GuidebookSectionEnchanter;
import teamport.aether.guidebook.freezer.GuidebookSectionFreezer;
import teamport.aether.guidebook.incubator.GuidebookIncubatorSection;

@Mixin(value = GuidebookSections.class, remap = false)
public abstract class GuidebookMixinInclude {

    @Unique
    private static boolean includedAetherSections = false;

    @Inject(method = "init", at=@At("TAIL"))
    private static void includeAetherSection(CallbackInfo ci){
        if(includedAetherSections){
            return;
        }
        includedAetherSections = true;
        GuidebookSections.register(new GuidebookSectionEnchanter("aether.guidebook.section.enchanter", new ItemStack(AetherBlocks.ENCHANTER_ACTIVE), 0x606060,   0x00A29C));
        GuidebookSections.register(new GuidebookSectionFreezer("aether.guidebook.section.freezer", new ItemStack(AetherBlocks.FREEZER_ACTIVE), 0x6E6E50, 0xbacce5));
        GuidebookSections.register(new GuidebookIncubatorSection("aether.guidebook.section.incubator", new ItemStack(AetherBlocks.INCUBATOR_ACTIVE), 0x9F8558, 0xECD13B));
    }

}
