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
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherItems;

@Environment(EnvType.CLIENT)
public class ItemModelBowPhoenix extends ItemModelStandard {
    public ItemModelBowPhoenix(@NonNull Item item, boolean defaultTextureLookup) {
        super(item, defaultTextureLookup);
    }

    @Override
    protected void renderSingle(@NonNull TessellatorGeneral tessellator, @Nullable Entity holder, @NonNull ItemStack itemStack, boolean items3d, byte lightIndex, int color, float partialTick, boolean mirrorX) {
        super.renderSingle(tessellator, holder, itemStack, items3d, lightIndex, color, partialTick, mirrorX);
        Item nextArrow = null;
        if (holder instanceof Player player) {
            nextArrow = this.getNextArrow(player);
        } else if (holder instanceof MobSkeleton skeleton && skeleton.attackTime < 5) {
            nextArrow = AetherItems.AMMO_ARROW_FLAMING;
        }

        if (nextArrow != null) {
            GLRenderer.pushFrame();
            if (mirrorX) {
                GLRenderer.modelM4f().rotateZ(((float) Math.PI / 2F));
            }

            GLRenderer.modelM4f().translate(1.0E-4F, 1.0E-4F, 0.0F);
            GLRenderer.modelM4f().scale(1.0F, 1.0F, 0.999F);
            this.renderCoordinate(tessellator, ItemModelDispatcher.getInstance().getDispatch(nextArrow).getIcon(holder, nextArrow.getDefaultStack()), lightIndex, color, items3d, !mirrorX);
            GLRenderer.popFrame();
        }

    }

    public Item getNextArrow(Player player) {
        if (player instanceof PlayerRemote) {
            int id = player.getArrowId();
            if (id < 0 || id > Item.highestItemId) return null;
            Item arrow = Item.getItem(id);
            return arrow == Items.AMMO_ARROW || arrow == Items.AMMO_ARROW_GOLD || arrow == Items.AMMO_ARROW_FLAMING
                ? AetherItems.AMMO_ARROW_FLAMING : null;
        } else {
            if (PlayerUtil.getActiveQuiver(player) != null
                || player.hasItem(Items.AMMO_ARROW) || player.hasItem(Items.AMMO_ARROW_GOLD) || player.hasItem(Items.AMMO_ARROW_FLAMING)) {
                return AetherItems.AMMO_ARROW_FLAMING;
            }
            return null;
        }
    }
}
