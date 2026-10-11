package teamport.aether.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.dispatch.Dispatcher;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ColorHelper extends Dispatcher<Integer, ColorHelper.ColorFrame> {


    protected static final int[] cc = new int[]{
        0x702963, // purple
        0xffcf40, // yellow
        0x6495ED, // blue
    };
    protected static final int FRAME_SIZE = 30;
    private static final ColorFrame DEFAULT = ColorFrame.frame(49, new int[]{0xFF0000, 0xFFA500, 0xffff00, 0x00ff00, 0x91ffff, 0x0000FF, 0x8c32ff});
    private static final ColorHelper instance = new ColorHelper();


    private ColorHelper() {
        this.reload();
    }

    public static ColorHelper getInstance() {
        return instance;
    }

    public void reload() {
        this.dispatches.clear();
        this.addDispatch(
            (TextFormatting.RED + "Finger of Death").toLowerCase(Locale.ROOT).hashCode(),
            ColorFrame.frame(100, new int[]{0x750000, 0x8e0000, 0xa80000, 0xc10000, 0xdb0000})
        );
        this.addDispatch(
            (TextFormatting.LIGHT_BLUE + "Cold Shoulder").toLowerCase(Locale.ROOT).hashCode(),
            ColorFrame.frame(100, new int[]{0xe3fdff, 0xd1f7ff, 0xc0f7ff, 0x94f7ff, 0x49e8ff})
        );
        this.addDispatch(
            (TextFormatting.YELLOW + "The Sun").toLowerCase(Locale.ROOT).hashCode(),
            ColorFrame.frame(50, new int[]{0xfeb204, 0xff8503, 0xd53600})
        );

    }

    @Override
    protected ColorFrame getDefault() {
        return DEFAULT;
    }

    public record ColorFrame(int frameSize, int[] colors){

        public static ColorFrame frame(int ... colors){
            return new ColorFrame(colors.length * 10, colors);
        }

        public static ColorFrame frame(int frameSize, int[] colors){
            return new ColorFrame(frameSize, colors);
        }
    }
    public record ColorF(float r, float g, float b) {}

    public static ColorFrame getPartileColor(@NotNull ItemStack itemStack) {
        String name = itemStack.getCustomName();
        if(name != null){
            TextFormatting formatting = TextFormatting.get(itemStack.getCustomColor());
            int key = (formatting + name).toLowerCase(Locale.ROOT).hashCode();
            return ColorHelper.getInstance().getDispatch(key);
        }
        return ColorHelper.getInstance().getDefault();
    }

    public static ColorF colorLerp(int hash){
        return ColorHelper.colorLerp(ColorHelper.getInstance().getDispatch(hash));
    }

    public static ColorF colorLerp(ColorFrame frame){
        return ColorHelper.colorLerp(frame.frameSize, frame.colors());
    }

    public static ColorF colorLerp(int frameSize, int[] colors){
        final int tickCount =  Minecraft.getMinecraft().ticksRan;
        int segmentLength = frameSize / colors.length;
        int currentSegment = (tickCount / segmentLength) % colors.length;
        int nextSegment = (currentSegment + 1) % colors.length;
        float segmentProgress = (tickCount % segmentLength) / (float) segmentLength;
        return setColors(segmentProgress, colors[currentSegment], colors[nextSegment]);
    }

    private static ColorF setColors(float segmentProgress, int startingColor, int endingColor) {
        float r = (startingColor >> 16 & 255) / 255.0F;
        float g = (startingColor >> 8 & 255) / 255.0F;
        float b = (startingColor & 255) / 255.0F;
        float nr = (endingColor >> 16 & 255) / 255.0F;
        float ng = (endingColor >> 8 & 255) / 255.0F;
        float nb = (endingColor & 255) / 255.0F;

        return new ColorF(
            MathHelper.lerp(r, nr, segmentProgress),
            MathHelper.lerp(g, ng, segmentProgress),
            MathHelper.lerp(b, nb, segmentProgress)
        );
    }
}
