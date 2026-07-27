package teamport.aether.entity.floating_block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.BlocksContainer;
import net.minecraft.core.world.pos.TilePos;

@Environment(EnvType.CLIENT)
public class EntityRendererFloatingBlock extends EntityRenderer<EntityFloatingBlock> {
    private BlocksContainer container = null;

    public EntityRendererFloatingBlock() {
        super(0.5F);
    }

    @Override
    public void render(TessellatorGeneral tessellator, EntityFloatingBlock floatingBlock, double x, double y, double z, float yaw, float partialTick) {
        if (this.container == null || this.container.world != floatingBlock.world) {
            this.container = new BlocksContainer(floatingBlock.world);
        }

        GLRenderer.pushFrame();
        GLRenderer.modelM4f().translate((float) x, (float) y, (float) z);
        TextureRegistry.worldAtlas.bind();
        Lighting.disable();
        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
        GLRenderer.enableState(State.BLEND);
        GLRenderer.disableState(State.CULL_FACE);

        int blockX = MathHelper.floor(floatingBlock.x);
        int blockY = MathHelper.floor(floatingBlock.y);
        int blockZ = MathHelper.floor(floatingBlock.z);
        TilePos blockPos = new TilePos(blockX, blockY, blockZ);
        CarriedBlock carriedBlock = floatingBlock.getCarriedBlock();
        Block<?> block = Blocks.getBlock(carriedBlock.blockId);

        if (block != null) {
            tessellator.startDrawingQuads();
            tessellator.setTranslation((-blockX) - 0.5, (-blockY) - 0.5, (-blockZ) - 0.5);

            this.container.partialTick = partialTick;
            this.container.setLightReferenceEntity(floatingBlock);
            this.container.setBlock(blockX, blockY, blockZ, carriedBlock.blockId, carriedBlock.metadata, carriedBlock.entity);

            BlockModelDispatcher.getInstance().getDispatch(block).renderNoCulling(tessellator, this.container, blockPos);

            this.container.setLightReferenceEntity(null);
            this.container.clear();

            tessellator.setTranslation(0.0, 0.0, 0.0);
            tessellator.draw();
        }

        Lighting.enableLight();
        GLRenderer.popFrame();

        TileEntity tileEntity = carriedBlock.entity;
        if (tileEntity == null) {
            return;
        }
        TileEntityRenderer<TileEntity> renderer = TileEntityRenderDispatcher.instance.getRenderer(tileEntity);
        if (renderer != null) {
            GLRenderer.pushFrame();
            renderer.doRender(tessellator, tileEntity, x - 0.5, y - 0.5, z - 0.5, partialTick);
            GLRenderer.popFrame();
        }
    }
}
