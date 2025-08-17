package teamport.aether;

import com.b100.utils.FileUtils;
import com.b100.utils.StreamUtils;
import com.b100.utils.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.net.CertificateHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static teamport.aether.AetherMod.LOGGER;

@Environment(EnvType.CLIENT)
public class AetherRemoteResourceDownloaderThread extends Thread {

    @Environment(EnvType.CLIENT)
    public enum State {
        IDLE,
        DOWNLOADING,
        ERROR
    }

    public final Minecraft mc;
    public File resourcesFolder;
    public AtomicInteger progress;
    public volatile int toDownload = 0;

    public volatile State state;

    public AetherRemoteResourceDownloaderThread(File file, Minecraft minecraft) {
        super("Aether Resource Download");
        this.mc = minecraft;
        this.progress = new AtomicInteger(0);
        this.setDaemon(true);
        this.state = State.IDLE;

        this.resourcesFolder = new File(file, "resources/");
        if (!this.resourcesFolder.exists() && !this.resourcesFolder.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + this.resourcesFolder);
        }
    }

    @Override
    public void run() {
        this.state = State.DOWNLOADING;

        JsonArray manifest;

        try {
            String manifestURL = AetherConfig.REMOTE_RESOURCE_URL + "index.json";
            manifest = JsonParser.parseString(StringUtils.getWebsiteContentAsString(manifestURL)).getAsJsonArray();
            LOGGER.info("Manifest Downloaded");
        } catch (Exception except) {
            this.state = State.ERROR;
            LOGGER.error("Failed to fetch resource manifest.");
            return;
        }

        List<JsonElement> entries = manifest.asList();
        toDownload = entries.size();

        for (JsonElement entry : entries) {
            if (!(entry instanceof JsonObject)) continue;
            JsonObject entryObj = (JsonObject) entry;

            String key = entryObj.get("Key").getAsString();

            File soundFile = new File(resourcesFolder, key);

            if (soundFile.exists()) {
                LOGGER.info("File Already Downloaded: {}", soundFile);
                progress.incrementAndGet();
                continue;
            }

            if (!soundFile.getParentFile().mkdirs()) {
                throw new RuntimeException("Couldn't create directory.");
            }

            try {
                downloadSoundFile(key, soundFile);
            }
            catch (Exception e) {
                LOGGER.error("Failed to download File: {}", key);
            }

            progress.incrementAndGet();
        }

        LOGGER.info("Finished Downloading files!");

        SoundRepository.reload();
        mc.sndManager.destroy();
        mc.sndManager = new SoundEngine();
        mc.sndManager.init(this.mc.gameSettings);
        state = State.IDLE;
    }

    private void downloadSoundFile(String name, File file) throws Exception {
        String url = AetherConfig.REMOTE_RESOURCE_URL + name;
        url = url.replace(" ", "%20");
        LOGGER.info("Downloading File: {}", url);

        StreamUtils.transferDataAndClose(
                new BufferedInputStream(CertificateHelper.getWebsiteAsStream(url)),
                new BufferedOutputStream(Files.newOutputStream(FileUtils.createNewFile(file).toPath()))
        );
    }
}
