package teamport.aether.halplibe_temp;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import turniplabs.halplibe.HalpLibe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextureHelper {
    @SuppressWarnings("unused")
    public static void initializeAllFiles(String modId, AtlasStitcher atlas, int depth) {
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(modId);
        if(!modContainer.isPresent()) {
            HalpLibe.LOGGER.error("Failed to find mod {} when loading textures!", modId);
            return;
        }

        String path = String.format("%s/%s/%s", "assets", modId, atlas.directoryPath);
        String atlasKey = TextureRegistry.stitcherMapReverse.get(atlas);
        Optional<Path> optionalPath = modContainer.get().findPath(path);

        if (!optionalPath.isPresent()) {
            HalpLibe.LOGGER.debug("Failed to find path to {} [{}]", path, modId);
            return;
        }
        Path p = optionalPath.get();

        try (Stream<Path> stream = Files.walk(p, depth)) {

            List<Path> paths = stream.collect(Collectors.toList());
            for (Path filePath : paths) {
                if (!isPNG(filePath)) continue;

                String fileName = p.relativize(filePath).toString().replace(".png", "").replaceAll("\\\\", "/");
                String formatted = String.format("%s:%s/%s", modId, atlasKey, fileName);
                TextureRegistry.getTexture(formatted);
            }

        } catch (IOException e) {
            HalpLibe.LOGGER.error("Failed to initialize textures!", e);
        }
    }

    @SuppressWarnings("unused")
    public static void initializeAllFiles(String modId, AtlasStitcher atlas) {
        initializeAllFiles(modId, atlas, 1);
    }

    public static boolean isPNG(Path p) {
        try {
            String cType = Files.probeContentType(p);
            return cType != null && cType.equals("image/png");
        } catch (IOException e) {
            return false;
        }
    }
}
