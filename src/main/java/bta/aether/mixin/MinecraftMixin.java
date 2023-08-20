package bta.aether.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(
        value = Minecraft.class,
        remap = false
)
public class MinecraftMixin {

    @Shadow private static Minecraft theMinecraft;

    /**
     * @author MartinSVK12
     * @reason this should not be necessary, see <a href="https://discord.com/channels/866992171319558144/1136731864837673140">this</a>
     */
    @Overwrite
    public static Minecraft getMinecraft(Class<?> caller) {
        return theMinecraft;
    }
}
