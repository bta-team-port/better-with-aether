package teamport.aether.entity.tile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.dungeon.BlockLogicChestMimic;

public class TileEntityMimic extends TileEntityChest implements Container {
    public String nickname = "";
    public byte chatColor = 0;

    @Override
    public void dropContents(World world, int x, int y, int z) {
    }

    @Override
    public @Nullable ItemStack removeItem(int index, int takeAmount) {
        return null;
    }

    public String getNameTranslationKey() {
        return "aether.container.chest.trapped.name";
    }

    public void setCustomName(String nickname, byte chatColor ){
        this.nickname = nickname;
        this.chatColor = chatColor;
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

    public @NotNull String getNickName() {
        return nickname;
    }

    public byte getChatColor() {
        return chatColor;
    }
}
