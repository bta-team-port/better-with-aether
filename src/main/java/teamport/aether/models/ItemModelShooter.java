package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerRemote;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.DisplayPos;
import teamport.aether.item.DartInterface;

@Environment(EnvType.CLIENT)
public class ItemModelShooter extends ItemModelStandard {
    public ItemModelShooter(@NonNull Item item, boolean defaultTextureLookup) {
        super(item, defaultTextureLookup);
        this.setDisplayPos(DisplayPos.THIRD_PERSON_RIGHT_HAND, new DisplayPos(
            -0.0625f, -0.125f, 0.15625f, -80.0f, 260.0f, -40.0f, 0.9f, 0.9f, 0.9f
        ));
        this.setDisplayPos(DisplayPos.THIRD_PERSON_LEFT_HAND, new DisplayPos(
            -0.0625f, -0.125f, 0.15625f, -80.0f, -280.0f, 40.0f, 0.9f, 0.9f, 0.9f
        ));
    }

    @Override
    public void render(@NonNull TessellatorGeneral tessellator, @Nullable Entity holder, @NonNull ItemStack itemStack, @NonNull String displayPosId, boolean items3d, int clusterSize, byte lightIndex, float partialTick, boolean leftHanded) {
        Player player = holder instanceof Player p ? p : Minecraft.getMinecraft().thePlayer;

        boolean isHeld = items3d || (player != null && player.getHeldItem() == itemStack);
        Item nextDart = (isHeld && player != null) ? getNextDart(player) : null;

        if (!items3d) {
            if (nextDart != null) {
                renderDart(tessellator, holder, nextDart, lightIndex, false, false);
            }
            GLRenderer.pushFrame();
            GLRenderer.modelM4f().translate(0.0f, 0.0f, 0.001f);
            super.render(tessellator, holder, itemStack, displayPosId, false, clusterSize, lightIndex, partialTick, leftHanded);
            GLRenderer.popFrame();
        } else {
            super.render(tessellator, holder, itemStack, displayPosId, true, clusterSize, lightIndex, partialTick, leftHanded);
            if (nextDart != null) {
                boolean isLeft = leftHanded || displayPosId.contains("lefthand");
                renderDart(tessellator, holder, nextDart, lightIndex, true, isLeft);
            }
        }
    }

    private void renderDart(@NonNull TessellatorGeneral tessellator, @Nullable Entity holder, @NonNull Item nextDart, byte lightIndex, boolean items3d, boolean isLeftHanded) {
        GLRenderer.pushFrame();

        if (items3d) {
            GLRenderer.modelM4f().rotateZ((float) (Math.PI / 2.0F));
            float zOffset = isLeftHanded ? 0.0625f : -0.0625f;
            GLRenderer.modelM4f().translate(0.3125f, 0.3125f, zOffset);
        }

        this.renderCoordinate(tessellator, ItemModelDispatcher.getInstance().getDispatch(nextDart).getIcon(holder, nextDart.getDefaultStack()), lightIndex, -1, items3d, false);

        GLRenderer.popFrame();
    }

    public Item getNextDart(Player player) {
        DartInterface dartPlayer = (DartInterface) player;
        if (player instanceof PlayerRemote) {
            int id = dartPlayer.better_with_aether$getDartId();
            return id >= 0 && id < Item.itemsList.length ? Item.itemsList[id] : null;
        } else {
            return dartPlayer.better_with_aether$getNextDart();
        }
    }
}
