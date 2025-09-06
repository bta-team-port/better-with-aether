package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelRotatable;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import teamport.aether.blocks.BlockLogicDungeonDoor;

import static teamport.aether.blocks.BlockLogicDungeonDoor.DoorDungeonHeight;
import static teamport.aether.blocks.BlockLogicDungeonDoor.DoorDungeonSide;


@Environment(EnvType.CLIENT)
public class BlockModelDungeonDoor extends BlockModelRotatable<BlockLogicDungeonDoor> {

    public BlockModelDungeonDoor(Block<BlockLogicDungeonDoor> block) {
        super(block);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int meta) {
        Side blockRot = BlockLogicRotatable.getDirectionFromMeta(meta).getSide();

        DoorDungeonHeight doorHeight = BlockLogicDungeonDoor.getHeightByMeta(meta);
        DoorDungeonSide doorSide = BlockLogicDungeonDoor.getSideByMeta(meta);

        StringBuilder tex = new StringBuilder("aether:block/door/boss/");

        switch (doorHeight) {
            case TOP:
                tex.append("top");
                break;
            case BOTTOM:
                tex.append("bottom");
                break;
            case MIDDLE:
                tex.append("middle");
                break;
        }

        tex.append("_");

        switch (doorSide) {
            case LEFT:
                if (side == Side.EAST || side == Side.NORTH) tex.append("right");
                else tex.append("left");
                break;

            case RIGHT:
                if (side == Side.EAST || side == Side.NORTH) tex.append("left");
                else tex.append("right");
                break;

            case MIDDLE:
                tex.append("middle");
                break;
        }


        return TextureRegistry.getTexture(tex.toString());
    }
}
