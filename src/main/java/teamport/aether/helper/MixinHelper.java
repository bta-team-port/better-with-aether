package teamport.aether.helper;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

import static teamport.aether.AetherGlobals.LOGGER;

public class MixinHelper {

    private MixinHelper() {}

    public static final int ANIMATION_LENGTH = 30;

    public static boolean isBrokenAABB(@NonNull AABBdc aabb) {
        double diffX = Math.abs(aabb.maxX() - aabb.minX());
        double diffY = Math.abs(aabb.maxY() - aabb.minY());
        double diffZ = Math.abs(aabb.maxZ() - aabb.minZ());

        return diffX > 1_000_000
            || diffY > 1_000_000
            || diffZ > 1_000_000
            || Double.isNaN(diffX)
            || Double.isNaN(diffY)
            || Double.isNaN(diffZ);
    }

    public static <T> List<T> preventStupidShit(World world, Class<T> ofClass, Entity entity, AABBdc aabb, Operation<List<T>> original) {
        if (MixinHelper.isBrokenAABB(aabb)) {
            if (entity != null) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("{} is moving too fast. Entity at {} {} {} with speed {}! Please send this to a developer!",
                        Entity.getNameFromEntity(entity, true),
                        entity.x, entity.y, entity.z,
                        Math.sqrt(entity.xd * entity.xd + entity.yd * entity.yd + entity.zd * entity.zd)
                    );
                }
                Block<?> block = world.getBlockType(new TilePos((int) Math.round(entity.x), (int) Math.round(entity.y - 1), (int) Math.round(entity.z)));
                String name = block.getLanguageKey(0);
                LOGGER.error("Currently standing on: {} at ", name);
                LOGGER.error("Please send this log to a BWA developer!");
                Thread.dumpStack();
                entity.absMoveTo(0, 255, 0, 0f, 0f);
                entity.xo = 0;
                entity.yo = 0;
                entity.zo = 0;
            } else {
                LOGGER.error("Something is moving too fast! Please send this to a developer!");
                Thread.dumpStack();
            }
            return new ArrayList<>();
        }
        return entity != null ? original.call(entity, aabb) : original.call(ofClass, aabb);
    }

    public static int mixColor(int colorA, int colorB, float ratio) {
        int alphaA = Color.alphaFromInt(colorA);
        int redA = Color.redFromInt(colorA);
        int blueA = Color.blueFromInt(colorA);
        int greenA = Color.greenFromInt(colorA);
        int alphaB = Color.alphaFromInt(colorB);
        int redB = Color.redFromInt(colorB);
        int blueB = Color.blueFromInt(colorB);
        int greenB = Color.greenFromInt(colorB);

        int alphaRes = (int) (alphaA * ratio + alphaB * (1 - ratio));
        int redRes = (int) (redA * ratio + redB * (1 - ratio));
        int blueRes = (int) (blueA * ratio + blueB * (1 - ratio));
        int greenRes = (int) (greenA * ratio + greenB * (1 - ratio));

        return Color.intToIntARGB(alphaRes, redRes, blueRes, greenRes);
    }

}
