package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.player.PlayerRemote;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobSkeleton;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.DisplayPos;
import teamport.aether.item.DartInterface;

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
    protected void renderSingle(@NonNull TessellatorGeneral tessellator, @Nullable Entity holder, @NonNull ItemStack itemStack, boolean items3d, byte lightIndex, int color, float partialTick, boolean mirrorX) {
        super.renderSingle(tessellator, holder, itemStack, items3d, lightIndex, color, partialTick, mirrorX);
        Item nextDart = null;
        if (holder instanceof Player player) {
            nextDart = getNextDart(player);
        } else if (holder instanceof MobSkeleton skeleton && skeleton.attackTime < 5) {
            nextDart = Items.AMMO_ARROW;
        }


        if (nextDart != null) {
            GLRenderer.pushFrame();
            if (mirrorX) {
                GLRenderer.modelM4f().rotateZ(((float) Math.PI / 2F));
            }

            GLRenderer.modelM4f().translate(-0.3125f, 0.3125f, 0.0625f);
            GLRenderer.modelM4f().rotateZ((float) Math.toRadians(90.0));
            this.renderCoordinate(tessellator, ItemModelDispatcher.getInstance().getDispatch(nextDart).getIcon(holder, nextDart.getDefaultStack()), lightIndex, color, items3d, !mirrorX);
            GLRenderer.popFrame();
        }

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
