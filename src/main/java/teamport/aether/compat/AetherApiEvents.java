package teamport.aether.compat;

import org.jetbrains.annotations.ApiStatus;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.util.map.DungeonLogic;
import teamport.aether.world.feature.util.map.DungeonMap;
import turniplabs.halplibe.event.impl.SortedBaseEvent;
import turniplabs.halplibe.event.impl.SortedSingleEvent;

import java.util.function.Consumer;

@ApiStatus.AvailableSince("1.1.0+8.0.1")
public class AetherApiEvents {
    /**
     * Adds blocks that should also be banned in the Aether.
     *
     * <p>By default, blocks are banned permanently. To allow a block to be
     * unbanned when the Sun Spirit is defeated, pass {@code true} as the
     * second argument to {@link teamport.aether.world.AetherDimension.BannedBlock#add(int, boolean)}.</p>
     *
     * <p>Blocks can also be replaced when they are placed in the Aether using
     * {@link teamport.aether.world.AetherDimension.BannedBlock#add(int, int)}.</p>
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * public class Example {
     *     private static boolean initialized = false;
     *
     *     public static void init() {
     *         if (initialized) return;
     *         initialized = true;
     *
     *         AetherEvents.DIMENSION_BLACKLIST.listen(
     *             Key.of(MOD_ID),
     *             Example::banSoulSand
     *         );
     *     }
     *
     *     public static void banSoulSand(BannedBlock aetherBlacklist) {
     *         aetherBlacklist.add(Blocks.FIRE.id());
     *         aetherBlacklist.add(Blocks.SOULSAND.id(), true);
     *         aetherBlacklist.add(
     *              Blocks.PUMICE_WET.id(),
     *              Blocks.PUMICE_DRY.id()
     *         );
     *     }
     * }
     * }</pre>
     *
     * <p>In this example, Fire is permanently banned, Soul Sand is banned until
     * the Sun Spirit is defeated, and Wet Pumice is replaced with Dry Pumice
     * when player tried placing it.</p>
     */
    public static final SortedBaseEvent<Consumer<AetherDimension.BannedBlock>> DIMENSION_BLACKLIST = new SortedBaseEvent<>();

    /**
     * Due to how ChunkProvider works dungeons cannot guarantee to be given all needed chuck.
     * This results in brokenly generated dungeons. To prevent this from happening dungeon are treated as entities.
     * As such they need, just like entities, to be registered.
     *
     * <p>
     *     This is an event collecting all dungeon entities registration. To register a dungeon call </br>
     *     {@link DungeonMap#registerDungeonType(String, Class<? extends DungeonLogic)}. </br>
     *     For example see {@link DungeonMap#registerDungeons()}
     * </p>
     * */
    public static final SortedSingleEvent<Runnable> DUNGEON_REGISTER = new SortedSingleEvent<>("Aether:DungeonRegistry");

}
