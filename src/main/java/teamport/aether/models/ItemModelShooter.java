package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerRemote;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.useless.dragonfly.DisplayPos;
import teamport.aether.item.DartInterface;
import teamport.aether.item.ItemDart;

@Environment(EnvType.CLIENT)
public class ItemModelShooter extends ItemModelStandard {
    public ItemModelShooter(Item item, boolean handheld) {
        super(item, handheld);
        this.setDisplayPos(DisplayPos.THIRD_PERSON_RIGHT_HAND, new DisplayPos(
            -0.0625f, -0.125f, 0.15625f, -80.0f, 260.0f, -40.0f, 0.9f, 0.9f, 0.9f
        ));
        this.setDisplayPos(DisplayPos.THIRD_PERSON_LEFT_HAND, new DisplayPos(
            -0.0625f, -0.125f, 0.15625f, -80.0f, -280.0f, 40.0f, 0.9f, 0.9f, 0.9f
        ));
    }

    @Override
    public void render(TessellatorGeneral tessellator, Entity entity, ItemStack itemStack,
                       String displayPosition, boolean render3d, int renderCount, byte lightmap, float brightness, boolean leftHand) {
        Player player = entity instanceof Player ? (Player) entity : null;
        if (player == null) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc != null) player = mc.thePlayer;
        }

        Item nextDart = player != null ? getNextDart(player) : null;
        ItemModel dartModel = nextDart != null
            ? ItemModelDispatcher.getInstance().getDispatch(nextDart.getDefaultStack())
            : null;

        boolean isGui = DisplayPos.GUI.equals(displayPosition);

        if (isGui && dartModel != null) {
            dartModel.render(tessellator, entity, nextDart.getDefaultStack(),
                DisplayPos.NONE, false, 1, lightmap, brightness, leftHand);
        }

        super.render(tessellator, entity, itemStack, displayPosition, render3d, renderCount, lightmap, brightness, leftHand);

        // i hate models
        if (!isGui && dartModel != null) {
            GLRenderer.pushFrame();
            GLRenderer.modelM4f()
                .translate(-0.3125f, 0.3125f, 0.0625f)
                .rotateZ((float) Math.toRadians(90.0));
            dartModel.render(tessellator, entity, nextDart.getDefaultStack(),
                DisplayPos.NONE, true, 1, lightmap, brightness, leftHand);
            GLRenderer.popFrame();
        }
    }

    public Item getNextDart(Player player) {
        DartInterface dartPlayer = (DartInterface) player;
        if (player instanceof PlayerRemote) {
            int id = dartPlayer.better_with_aether$getDartId();
            if (id < 0 || id > Item.highestItemId) return null;
            Item item = Item.getItem(id);
            return item instanceof ItemDart ? item : null;
        } else {
            return dartPlayer.better_with_aether$getNextDart();
        }
    }
}
