package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;

@Environment(EnvType.CLIENT)
public class ItemModelLance extends ItemModelStandard {
    public ItemModelLance(Item item, String namespace) {
        super(item, namespace);
    }

    @Override
    public void heldTransformThirdPerson(ItemRenderer renderer, Entity entity, ItemStack itemStack) {
        GL11.glScalef(1.25F, 1.25F, 1.25F);
        if (entity instanceof MobValkyrie) {
            GL11.glTranslatef(0.05F, 0.55F, -0.45F);
        } else {
            GL11.glTranslatef(0.025F, 0.50F, -0.45F);
        }
        GL11.glScalef(0.625F, -0.625F, 0.625F);
        GL11.glRotatef(-35.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(40.0F, 0.0F, 1.0F, 0.0F);
    }
}
