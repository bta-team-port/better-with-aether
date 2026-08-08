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
import teamport.aether.helper.Pair;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final Minecraft mc;
    private final File resourcesFolder;
    private final AtomicInteger progress;
    private volatile int toDownload = 0;

    private volatile State state;

    private final String url;

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

        synchronized (AetherConfig.CONFIGURATION_LOCK) {
            url = AetherConfig.REMOTE_RESOURCE_URL;
        }
    }

    @Override
    @SuppressWarnings({"java:S2674", "ResultOfMethodCallIgnored"})
    public void run() {
        JsonArray manifest;

        try {
            String manifestURL = url + "manifest.json";
            manifest = JsonParser.parseString(StringUtils.getWebsiteContentAsString(manifestURL)).getAsJsonArray();
            LOGGER.info("Manifest Downloaded");

        } catch (Exception except) {
            this.state = State.ERROR;
            LOGGER.error("Failed to fetch resource manifest.");
            return;
        }

        List<JsonElement> entries = manifest.asList();
        List<Pair<File, String>> entriesToDownload = new ArrayList<>();

        for (JsonElement entry : entries) {
            if (!(entry instanceof JsonObject entryObj)) continue;

			String key = entryObj.get("Key").getAsString();
            String md5 = entryObj.get("MD5").getAsString();

            File soundFile = new File(resourcesFolder, key);

            boolean fileAlreadyDownloaded = false;
            try {
                if (soundFile.exists()) {
                    byte[] localFileMD5;

                    Path localfilePath = soundFile.toPath();
                    try (InputStream fileHandle = Files.newInputStream(localfilePath)) {
                        byte[] fileData;
                        fileData = new byte[(int) Files.size(localfilePath)];
                        fileHandle.read(fileData);

                        localFileMD5 = MessageDigest.getInstance("MD5").digest(fileData);
                    }

                    byte[] remoteFileMD5 = new BigInteger(md5, 16).toByteArray();

                    // remove leading zero if any.
                    if (remoteFileMD5[0] == 0) {
                        byte[] newBytes = new byte[remoteFileMD5.length - 1];
                        System.arraycopy(remoteFileMD5, 1, newBytes, 0, newBytes.length);
                        remoteFileMD5 = newBytes;
                    }

                    if (Arrays.equals(remoteFileMD5, localFileMD5)) fileAlreadyDownloaded = true;
                }

            } catch (Exception e) {
                LOGGER.error("Failed to assess local file: {}", soundFile);
            }

            if (fileAlreadyDownloaded) {
                LOGGER.info("File Already Downloaded: {}", soundFile);
            } else {
                entriesToDownload.add(new Pair<>(soundFile, key));
            }
        }

        toDownload = entriesToDownload.size();

        if (toDownload > 0) {
            this.state = State.DOWNLOADING;

            for (Pair<File, String> entry : entriesToDownload) {
                File soundFile = entry.first();
                String key = entry.second();

                try {
                    downloadSoundFile(key, soundFile);
                } catch (Exception e) {
                    LOGGER.error("Failed to download File: {}", key);
                }

                progress.incrementAndGet();
            }

            LOGGER.info("Finished Downloading files!");

            SoundRepository.reload();
            mc.sndManager.destroy();
            mc.sndManager = new SoundEngine();
            mc.sndManager.init();

            state = State.IDLE;
        }
    }

    private void downloadSoundFile(String name, File file) throws Exception {
        String theUrl = this.url + name;
        theUrl = theUrl.replace(" ", "%20");
        LOGGER.info("Downloading File: {}", theUrl);

        StreamUtils.transferDataAndClose(
                new BufferedInputStream(CertificateHelper.getWebsiteAsStream(theUrl)),
                new BufferedOutputStream(Files.newOutputStream(FileUtils.createNewFile(file).toPath()))
        );
    }
    public AtomicInteger getProgress() {
        return progress;
    }
    public int getToDownload() {
        return toDownload;
    }
    public State getTheState() {
        return state;
    }

}
