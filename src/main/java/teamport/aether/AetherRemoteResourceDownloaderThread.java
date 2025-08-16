package teamport.aether;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

@Environment(EnvType.CLIENT)
public class AetherRemoteResourceDownloaderThread extends Thread {

    public final Minecraft mc;
    public File resourcesFolder;
    public AtomicInteger progress;

    public AetherRemoteResourceDownloaderThread(File file, Minecraft minecraft) {
        super("Aether Resource Download");
        this.mc = minecraft;
        this.progress = new AtomicInteger(0);
        this.setDaemon(true);

        this.resourcesFolder = new File(file, "resources/mod/"+AetherMod.MOD_ID);
        if (!this.resourcesFolder.exists() && !this.resourcesFolder.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + this.resourcesFolder);
        }
    }

    @Override
    public void run() {
        super.run();

        AetherMod.LOGGER.info("HHELLLO");
    }
}
