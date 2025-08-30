package teamport.aether.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

@Mixin(value = TomlConfigHandler.class, remap = false)
public interface ConfigAccessor {

    @Accessor("config")
    Toml getConfig();

}
