package teamport.aether.entity.monster.mimic;

import java.util.Objects;

public class MimicEntry {
    int mimicVariant;
    String pathName;
    int mimicChestID;
    int mimicChestMetadata;
    int chestID;
    int chestMetadata;

    public MimicEntry(
        int mimicVariant, String pathName,
        int mimicChestID, int mimicChestMetadata,
        int itemChestID, int itemChestMetadata
    ) {
        this.mimicVariant = mimicVariant;
        this.pathName = pathName;
        this.mimicChestID = mimicChestID;
        this.mimicChestMetadata = mimicChestMetadata;
        this.chestID = itemChestID;
        this.chestMetadata = itemChestMetadata;
    }

    public static MimicEntry mimicEntry(
        int mimicVariant,
        String pathName,
        int mimicChestId, int mimicChestMetadata,
        int chestID, int chestMetadata
    ) {
        return new MimicEntry(mimicVariant, pathName, mimicChestId, mimicChestMetadata, chestID, chestMetadata);
    }

    public int getMimicVariant() {
        return mimicVariant;
    }

    public String getPathName() {
        return pathName;
    }

    public int getMimicChestID() {
        return mimicChestID;
    }

    public int getMimicChestMetadata() {
        return mimicChestMetadata;
    }

    public int getChestID() {
        return chestID;
    }

    public int getChestMetadata() {
        return chestMetadata;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MimicEntry)) return false;
        MimicEntry that = (MimicEntry) o;
        return mimicVariant == that.mimicVariant && pathName.equalsIgnoreCase(that.pathName) && mimicChestID == that.mimicChestID && mimicChestMetadata == that.mimicChestMetadata && chestID == that.chestID && chestMetadata == that.chestMetadata;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mimicVariant, pathName, mimicChestID, mimicChestMetadata, chestID, chestMetadata);
    }
}
