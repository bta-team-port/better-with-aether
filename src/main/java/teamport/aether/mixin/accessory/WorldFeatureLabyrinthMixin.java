package teamport.aether.mixin.accessory;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureLabyrinth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.item.AetherItems;

import java.util.Random;

@Mixin(value = WorldFeatureLabyrinth.class)
public abstract class WorldFeatureLabyrinthMixin {
    @Shadow
    public WeightedRandomBag<WeightedRandomLootObject> chestLoot;
    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/WeightedRandomBag;addEntry(Ljava/lang/Object;D)V", ordinal = 7, shift = At.Shift.AFTER))
    private void addCustomLoot(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        this.chestLoot.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_CHAIN.getDefaultStack()).setRandomMetadata(AetherItems.ARMOR_GLOVES_CHAIN.getMaxDamage() / 2, AetherItems.ARMOR_GLOVES_CHAIN.getMaxDamage()), 20.0);
        this.chestLoot.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_CHAIN.getDefaultStack()).setRandomMetadata(AetherItems.ARMOR_TALISMAN_CHAIN.getMaxDamage() / 2, AetherItems.ARMOR_TALISMAN_CHAIN.getMaxDamage()), 20.0);
    }
}
