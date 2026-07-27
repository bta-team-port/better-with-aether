package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.modelviewer.ScreenModelViewer;
import net.minecraft.client.gui.modelviewer.categories.entries.entity.EntityEntry;
import net.minecraft.client.gui.modelviewer.elements.TextCycleElement;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import teamport.aether.entity.floating_block.EntityFloatingBlock;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class EntityEntryFloatingBlock extends EntityEntry<EntityFloatingBlock> {
    public EntityEntryFloatingBlock() {
    }

    public void onTick(EntityFloatingBlock entity) {
    }

    public List<ButtonElement> getEntryButtons(Minecraft mc, Screen parentScreen, final EntityFloatingBlock gravitite) {
        final TextCycleElement<Integer> blockIdCycle = new TextCycleElement<Integer>(parentScreen, mc.font, -120, 0, 120, 20, gravitite.getCarriedBlock().blockId) {
            public Integer cycleElement(Integer current, int offset) {
                return ScreenModelViewer.cycleBlockId(current, offset);
            }

            public Integer getElementFromString(String s) {
                try {
                    int id = Integer.parseInt(s);
                    if (id < 0 || id > Blocks.highestBlockId) return gravitite.getCarriedBlock().blockId;
                    Block<?> block = Blocks.getBlock(id);
                    if (block != null) {
                        return block.id();
                    }
                } catch (Exception ignored) { /* noop */ }

                return gravitite.getCarriedBlock().blockId;
            }

            public String getNameFromElement(Integer element) {
                return String.valueOf(element);
            }
        };
        blockIdCycle.textField.setPrefaceText("ID: ");
        blockIdCycle.textField.setPlaceholder("Block ID");
        blockIdCycle.setOnValueChanged(() -> gravitite.getCarriedBlock().blockId = blockIdCycle.getCurrentElement());
        List<ButtonElement> list = new ArrayList<>();
        list.add(blockIdCycle);
        return list;
    }

    public EntityFloatingBlock getEntityInstance(Minecraft mc, World world) {
        return new EntityFloatingBlock(world);
    }

    public void onOpen() {
    }

    public void onClose() {
    }
}
