package teamport.aether.entity.floating_block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.BlocksContainer;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class EntityRendererFloatingBlock extends EntityRenderer<EntityFloatingBlock> {
    private final Minecraft mc = Minecraft.getMinecraft();
    private BlocksContainer container = null;
    private RenderBlocks containerRenderBlock = null;

    public EntityRendererFloatingBlock() {
        this.shadowSize = 0.5F;
    }

    public void render(Tessellator tessellator, EntityFloatingBlock floatingBlock, double x, double y, double z, float yaw, float partialTick) {
        if (this.container == null || this.container.world != floatingBlock.world) {
            this.container = new BlocksContainer(floatingBlock.world);
            this.containerRenderBlock = new RenderBlocks(this.container);
        }

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        TextureRegistry.blockAtlas.bind();
        Lighting.disable();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        if (this.mc.isAmbientOcclusionEnabled()) {
            GL11.glShadeModel(7425);
        } else {
            GL11.glShadeModel(7424);
        }

        int blockX = MathHelper.floor(floatingBlock.x);
        int blockY = MathHelper.floor(floatingBlock.y);
        int blockZ = MathHelper.floor(floatingBlock.z);

        tessellator.startDrawingQuads();
        tessellator.setTranslation((-blockX) - 0.5, (-blockY) - 0.5, (-blockZ) - 0.5);
        BlockModel.setRenderBlocks(this.containerRenderBlock);

        this.container.setLightReferenceEntity(floatingBlock);
        this.container.setBlock(blockX, blockY, blockZ, floatingBlock.getCarriedBlock().blockId, floatingBlock.getCarriedBlock().metadata, floatingBlock.getCarriedBlock().entity);

        BlockModelDispatcher.getInstance().getDispatch(Blocks.getBlock(floatingBlock.getCarriedBlock().blockId)).renderNoCulling(Tessellator.instance, blockX, blockY, blockZ);

        this.container.setLightReferenceEntity(null);
        this.container.clear();

        tessellator.setTranslation(0.0, 0.0, 0.0);
        tessellator.draw();
        Lighting.enableLight();
        GL11.glPopMatrix();
        TileEntityRenderer<TileEntity> renderer = TileEntityRenderDispatcher.instance.getRenderer(floatingBlock.getCarriedBlock().entity);

        if (renderer != null) {
            GL11.glPushMatrix();
            renderer.doRender(tessellator, floatingBlock.getCarriedBlock().entity, x - 0.5, y - 0.5, z - 0.5, partialTick);
            GL11.glPopMatrix();
        }
    }
}
