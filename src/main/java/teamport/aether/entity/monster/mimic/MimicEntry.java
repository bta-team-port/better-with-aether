package teamport.aether.entity.monster.mimic;

import java.util.Objects;

public class MimicEntry {
    int mimicVariant;
    int mimicChestID;
    int mimicChestMetadata;
    int chestID;
    int chestMetadata;

    public MimicEntry(
        int mimicVariant,
        int mimicChestID, int mimicChestMetadata,
        int itemChestID, int itemChestMetadata
    ) {
        this.mimicVariant = mimicVariant;
        this.mimicChestID = mimicChestID;
        this.mimicChestMetadata = mimicChestMetadata;
        this.chestID = itemChestID;
        this.chestMetadata = itemChestMetadata;
    }

    public static MimicEntry mimicEntry(
        int mimicVariant,
        int mimicChestId, int mimicChestMetadata,
        int chestID, int chestMetadata
    ) {
        return new MimicEntry(mimicVariant, mimicChestId, mimicChestMetadata, chestID, chestMetadata);
    }

    public int getMimicVariant() {
        return mimicVariant;
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
        return mimicVariant == that.mimicVariant && mimicChestID == that.mimicChestID && mimicChestMetadata == that.mimicChestMetadata && chestID == that.chestID && chestMetadata == that.chestMetadata;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mimicVariant, mimicChestID, mimicChestMetadata, chestID, chestMetadata);
    }
}
