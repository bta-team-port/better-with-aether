package bta.aether.mixin;

import net.minecraft.core.block.BlockLanternFirefly;
import net.minecraft.core.entity.animal.EntityFireflyCluster;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockLanternFirefly.class)
public interface FireflyJankMixin {
    @Mutable
    @Shadow
    @Accessor("color")
    public void setColor(EntityFireflyCluster.FireflyColor color);

}
