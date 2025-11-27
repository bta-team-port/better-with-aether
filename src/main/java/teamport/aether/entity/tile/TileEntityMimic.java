package teamport.aether.entity.tile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

public class TileEntityMimic extends TileEntityChest implements Container {
    private String nickname = "";
    private byte chatColor = 0;


    public void dropContentForced(World world, int x, int y, int z) {
        super.dropContents(world, x, y, z);
    }

    @Override
    public void dropContents(World world, int x, int y, int z) {
    }

    @Override
    public String getNameTranslationKey() {
        if (nickname.isEmpty()) {
            return "container.chest.trapped.name";
        }
        if (chatColor < 0) {
            return nickname;
        }
        return TextFormatting.get(chatColor).toString() + nickname;
    }

    public void setCustomName(String nickname, byte chatColor) {
        this.nickname = nickname;
        this.chatColor = chatColor; // to prevent
    }


    @Override
    public void readFromNBT(CompoundTag tag) {
        super.readFromNBT(tag);
        this.nickname = tag.getString("MimicNickname");
        this.chatColor = tag.getByte("MimicChatColor");
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        super.writeToNBT(tag);
        tag.putString("MimicNickname", this.nickname);
        tag.putByte("MimicChatColor", this.chatColor);
    }

    public @NonNull String getNickName() {
        return nickname;
    }

    public byte getChatColor() {
        return chatColor;
    }
}
