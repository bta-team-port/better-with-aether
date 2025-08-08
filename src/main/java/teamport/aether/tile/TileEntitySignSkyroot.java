package teamport.aether.tile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumSignPicture;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketTileEntityData;
import net.minecraft.core.util.helper.UUIDHelper;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TileEntitySignSkyroot extends TileEntity {
    public static final int MAX_LINE_SIZE = 15;
    public String[] signText = new String[]{"", "", "", ""};
    public int lineBeingEdited = -1;
    private int selectedPicture = 0;
    private int selectedColor = 15;
    private @Nullable UUID owner;
    private boolean glowing = false;

    public TileEntitySignSkyroot() {
    }

    public void writeToNBT(CompoundTag tag) {
        super.writeToNBT(tag);
        tag.putString("Text1", this.signText[0]);
        tag.putString("Text2", this.signText[1]);
        tag.putString("Text3", this.signText[2]);
        tag.putString("Text4", this.signText[3]);
        tag.putInt("Picture", this.selectedPicture);
        tag.putInt("Color", this.selectedColor);
        tag.putBoolean("Glowing", this.glowing);
        UUIDHelper.writeToTag(tag, this.owner, "OwnerUUID");
    }

    public void readFromNBT(CompoundTag tag) {
        super.readFromNBT(tag);

        for(int i = 0; i < 4; ++i) {
            this.signText[i] = tag.getString("Text" + (i + 1));
            if (this.signText[i].length() > 15) {
                this.signText[i] = this.signText[i].substring(0, 15);
            }
        }

        this.selectedPicture = tag.getIntegerOrDefault("Picture", 0);
        this.selectedColor = tag.getIntegerOrDefault("Color", 15);
        this.glowing = tag.getBooleanOrDefault("Glowing", false);
        UUID ownerUUID = UUIDHelper.readFromTag(tag, "OwnerUUID");
        if (ownerUUID == null) {
            String s = tag.getString("Owner");
            if (!s.isEmpty()) {
                UUIDHelper.runConversionAction(s, (uuid) -> {
                    this.owner = uuid;
                }, (UUIDHelper.StringFunction)null);
            }
        } else {
            this.owner = ownerUUID;
        }

    }

    public boolean isEditableBy(Player player) {
        return player.uuid.equals(this.owner);
    }

    public void setOwner(Player player) {
        this.owner = player.uuid;
    }

    public @Nullable EnumSignPicture getPicture() {
        return EnumSignPicture.fromId(this.selectedPicture);
    }

    public void setPicture(EnumSignPicture picture) {
        if (picture == null) {
            this.selectedPicture = 0;
        } else {
            this.selectedPicture = picture.getId();
        }
    }

    public TextFormatting getColor() {
        return this.selectedColor >= 0 && this.selectedColor <= 15 ? TextFormatting.FORMATTINGS[this.selectedColor] : TextFormatting.BLACK;
    }

    public void setColor(TextFormatting color) {
        this.selectedColor = color.id;
    }

    public boolean isGlowing() {
        return this.glowing;
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    public Packet getDescriptionPacket() {
        return new PacketTileEntityData(this);
    }
}
