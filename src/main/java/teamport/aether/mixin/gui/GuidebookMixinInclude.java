package teamport.aether.mixin.gui;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.guidebook.GuidebookSection;
import net.minecraft.client.gui.guidebook.GuidebookSections;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.gui.guidebook.enchanter.GuidebookSectionEnchanter;
import teamport.aether.gui.guidebook.freezer.GuidebookSectionFreezer;
import teamport.aether.gui.guidebook.incubator.GuidebookIncubatorSection;

@Mixin(value = GuidebookSections.class, remap = false)
public abstract class GuidebookMixinInclude {
    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/guidebook/GuidebookSections;register(Lnet/minecraft/client/gui/guidebook/GuidebookSection;)Lnet/minecraft/client/gui/guidebook/GuidebookSection;"))
    private static GuidebookSection includeAetherSection(GuidebookSection section, Operation<GuidebookSection> original) {
        GuidebookSections.register(new GuidebookSectionEnchanter("aether.guidebook.section.enchanter", new ItemStack(AetherBlocks.ENCHANTER_ACTIVE), 0x606060, 0x00A29C));
        GuidebookSections.register(new GuidebookSectionFreezer("aether.guidebook.section.freezer", new ItemStack(AetherBlocks.FREEZER_ACTIVE), 0x6E6E50, 0xbacce5));
        GuidebookSections.register(new GuidebookIncubatorSection("aether.guidebook.section.incubator", new ItemStack(AetherBlocks.INCUBATOR_ACTIVE), 0x9F8558, 0xECD13B));
        return original.call(section);
    }
}
