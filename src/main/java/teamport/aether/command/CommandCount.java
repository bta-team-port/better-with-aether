package teamport.aether.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import com.mojang.brigadier.builder.ArgumentBuilderRequired;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.arguments.ArgumentTypeVector3d;
import net.minecraft.core.net.command.helpers.DoubleCoordinates;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

import static com.mojang.brigadier.builder.ArgumentBuilderLiteral.literal;
import static teamport.aether.AetherMod.TRANSLATOR;

public class CommandCount implements CommandManager.CommandRegistry {

    /* @a, all players
     *  @e, all entity
     *  @p, nearest player
     *  @r, random
     *  @s, self
     * */

    @SuppressWarnings("unchecked")
    @Override
    public void register(@NonNull CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher
            .register((ArgumentBuilderLiteral<CommandSource>) (Object) literal("aether:countBlocks").requires(t -> ((CommandSource) t).hasAdmin())
                .then(ArgumentBuilderRequired.argument("first", ArgumentTypeVector3d.vec3d())
                    .then(ArgumentBuilderRequired.argument("second", ArgumentTypeVector3d.vec3d())
                        .executes(c -> {
                            calcBlockCount(c);
                            return 1;
                        }))));
    }

    private static void calcBlockCount(@NonNull CommandContext<Object> c) throws CommandSyntaxException {
        CommandSource sauce = (CommandSource) c.getSource();
        World world = sauce.getWorld();
        DoubleCoordinates first = c.getArgument("first", DoubleCoordinates.class);
        DoubleCoordinates second = c.getArgument("second", DoubleCoordinates.class);

        int fx = (int) Math.round(first.getX(sauce));
        int fy = (int) Math.round(first.getY(sauce, true));
        int fz = (int) Math.round(first.getZ(sauce));

        int sx = (int) Math.round(second.getX(sauce));
        int sy = (int) Math.round(second.getY(sauce, true));
        int sz = (int) Math.round(second.getZ(sauce));

        Map<String, Integer> count = new HashMap<>();

        for (int x = Math.min(fx, sx); x <= Math.max(fx, sx); x++) {
            for (int y = Math.min(fy, sy); y <= Math.max(fy, sy); y++) {
                for (int z = Math.min(fz, sz); z <= Math.max(fz, sz); z++) {
                    int id = world.getBlockId(x, y, z);
                    Block<?> block = Blocks.getBlock(id);
                    String name = TRANSLATOR.translateKey(block.getLanguageKey(0) + ".name");
                    count.merge(name, 1, Integer::sum);
                }
            }
        }
        long total = (long) (Math.abs(fx - sx)) * Math.abs(fy - sy) * Math.abs(fz - sz);
        sauce.sendMessage("Total Blocks:" + total);
        count.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEach(entry ->
            sauce.sendMessage(entry.getKey() + "→ Count: " + entry.getValue())
        );
    }
}
