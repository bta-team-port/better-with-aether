package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import teamport.aether.items.AetherItems;

@Environment(EnvType.CLIENT)
public class ItemModelShooter extends ItemModelStandard {
    public ItemModelShooter(Item item, String namespace) {
        super(item, namespace);
    }

    public void renderItem(Tessellator tessellator, ItemRenderer renderer, ItemStack itemstack, Entity entity, float brightness, boolean handheldTransform) {
        super.renderItem(tessellator, renderer, itemstack, entity, brightness, handheldTransform);
        Item nextDart = null;
        if (entity instanceof Player) {
            Player entityplayer = (Player)entity;
            nextDart = this.getNextDart(entityplayer);
        }

        if (nextDart != null) {
            GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-1.2F, 0.3F, 0.0625F);
            ItemModelDispatcher.getInstance().getDispatch(nextDart).renderItem(tessellator, renderer, itemstack, entity, brightness, false);
        }

    }

    public void renderItemIntoGui(Tessellator tessellator, Font font, TextureManager textureManager, ItemStack itemStack, int x, int y, float brightness, float alpha) {
        Minecraft mc = Minecraft.getMinecraft();
        Item nextDart = this.getNextDart(mc.thePlayer);
        if (itemStack == mc.thePlayer.getHeldItem() && nextDart != null) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2884);
            ItemModelStandard dartModel = (ItemModelStandard)ItemModelDispatcher.getInstance().getDispatch(nextDart);
            IconCoordinate textureIndex = dartModel.getIcon(mc.thePlayer, nextDart.getDefaultStack());
            GL11.glDisable(2896);
            textureIndex.parentAtlas.bind();
            if (this.useColor) {
                int color = this.getColor(itemStack);
                float r = (float)(color >> 16 & 255) / 255.0F;
                float g = (float)(color >> 8 & 255) / 255.0F;
                float b = (float)(color & 255) / 255.0F;
                GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);
            } else {
                GL11.glColor4f(brightness, brightness, brightness, alpha);
            }

            this.renderTexturedQuad(tessellator, x, y, textureIndex, true, false);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
        }

        super.renderItemIntoGui(tessellator, font, textureManager, itemStack, x, y, brightness, alpha);
    }

    public Item getNextDart(Player player) {
        Item nextDart = null;
        if (player.hasItem(AetherItems.AMMO_DART_ENCHANTED)) {
            nextDart = AetherItems.AMMO_DART_ENCHANTED;
        } else if (player.hasItem(AetherItems.AMMO_DART_POISON)) {
            nextDart = AetherItems.AMMO_DART_POISON;
        } else if (player.hasItem(AetherItems.AMMO_DART_GOLDEN)) {
            nextDart = AetherItems.AMMO_DART_GOLDEN;
        }

        return nextDart;
    }

    public int getDartId(Player player) {
        return player.getEntityData().getInt(18);
    }

    public void heldTransformThirdPerson(ItemRenderer renderer, Entity entity, ItemStack itemStack) {
        GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
        GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.625F, -0.625F, 0.625F);
        GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
    }
}
