package teamport.aether.mixin.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.models.DynamicTextureDungeonCompass;

@Mixin(value = TextureManager.class, remap = false)
public abstract class DynamicTexturesMixin {
    @Shadow
    @Final
    public Minecraft mc;
    @Shadow
    protected abstract void addDynamicTexture(DynamicTexture texture);
    @Inject(method = "initDynamicTextures", at = @At(value = "TAIL"))
    public void initDynamicTextures(CallbackInfo ci) {
        this.addDynamicTexture(new DynamicTextureDungeonCompass(this.mc, TextureRegistry.getTexture("aether:item/tool_dungeon_compass")));
    }
}
