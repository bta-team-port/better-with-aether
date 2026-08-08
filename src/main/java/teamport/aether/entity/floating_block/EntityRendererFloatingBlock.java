package teamport.aether.entity.floating_block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.Shaders;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.world.BlocksContainer;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;
import org.lwjgl.opengl.GL41;

@Environment(EnvType.CLIENT)
public class EntityRendererFloatingBlock extends EntityRenderer<EntityFloatingBlock> {
    private BlocksContainer container = null;

    public EntityRendererFloatingBlock() {
        super(0.5F);
    }

    public void render(@NonNull TessellatorGeneral tessellator, @NonNull EntityFloatingBlock floatingBlock, double x, double y, double z, float yaw, float partialTick) {
        if (this.container == null || this.container.world != floatingBlock.world) {
            this.container = new BlocksContainer(floatingBlock.world);
        }

        GLRenderer.pushFrame();
        GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GLRenderer.setLightmapCoord2f(15.0F, 15.0F);
        GLRenderer.modelM4f().translate((float) x, (float) y, (float) z);
        GLRenderer.setShader(Shaders.ITEM);
        TextureRegistry.worldAtlas.bind();
        GL41.glActiveTexture(33986);
        TextureRegistry.worldAtlas.layerTextureMap.get("emissive").bind();
        GL41.glActiveTexture(33987);
        TextureRegistry.worldAtlas.layerTextureMap.get("maskColor").bind();
        GL41.glActiveTexture(33984);
        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
        GLRenderer.enableState(State.BLEND);
        GLRenderer.disableState(State.CULL_FACE);
        TilePos blockPos = new TilePos(floatingBlock);
        tessellator.startDrawingQuads();
        tessellator.setTranslation((double) (-blockPos.x) - (double) 0.5F, (double) (-blockPos.y) - (double) 0.5F, (double) (-blockPos.z) - (double) 0.5F);
        this.container.setLightReferenceEntity(floatingBlock);
        this.container.setBlock(blockPos.x, blockPos.y, blockPos.z, floatingBlock.carriedBlock.blockId, floatingBlock.carriedBlock.metadata, floatingBlock.carriedBlock.entity);
        BlockModelDispatcher.getInstance().getDispatch(Blocks.getBlock(floatingBlock.carriedBlock.blockId)).renderNoCulling(GLRenderer.getTessellator(), this.container, blockPos);
        this.container.setLightReferenceEntity(null);
        this.container.clear();
        tessellator.setTranslation(0.0F, 0.0F, 0.0F);
        tessellator.draw();
        GLRenderer.popFrame();
        TileEntityRenderer<TileEntity> renderer = TileEntityRenderDispatcher.instance.getRenderer(floatingBlock.carriedBlock.entity);
        if (renderer != null) {
            GLRenderer.pushFrame();
            renderer.doRender(tessellator, floatingBlock.carriedBlock.entity, x - (double) 0.5F, y - (double) 0.5F, z - (double) 0.5F, partialTick);
            GLRenderer.popFrame();
        }

    }
}
