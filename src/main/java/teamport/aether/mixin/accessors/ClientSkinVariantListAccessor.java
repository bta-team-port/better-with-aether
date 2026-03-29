package teamport.aether.mixin.accessors;

import net.minecraft.client.entity.ClientSkinVariantList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ClientSkinVariantList.class)
public interface ClientSkinVariantListAccessor {

	@Invoker(value = "getEntityVariants")
	ClientSkinVariantList.EntityVariants invokeGetEntityVariants(String jsonPath);
}
