package teamport.aether.entity.entityFloatingBlock;

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

    public void render(Tessellator tessellator, EntityFloatingBlock fallingBlock, double x, double y, double z, float yaw, float partialTick) {
        if (this.container == null || this.container.world != fallingBlock.world) {
            this.container = new BlocksContainer(fallingBlock.world);
            this.containerRenderBlock = new RenderBlocks(this.container);
        }

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        TextureRegistry.blockAtlas.bind();
        Lighting.disable();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        if (this.mc.isAmbientOcclusionEnabled()) {
            GL11.glShadeModel(7425);
        } else {
            GL11.glShadeModel(7424);
        }

        int blockX = MathHelper.floor(fallingBlock.x);
        int blockY = MathHelper.floor(fallingBlock.y);
        int blockZ = MathHelper.floor(fallingBlock.z);

        tessellator.startDrawingQuads();
        tessellator.setTranslation((double) (-blockX) - (double) 0.5F, (double) (-blockY) - (double) 0.5F, (double) (-blockZ) - (double) 0.5F);
        BlockModel.setRenderBlocks(this.containerRenderBlock);

        this.container.setLightReferenceEntity(fallingBlock);
        this.container.setBlock(blockX, blockY, blockZ, fallingBlock.carriedBlock.blockId, fallingBlock.carriedBlock.metadata, fallingBlock.carriedBlock.entity);

        BlockModelDispatcher.getInstance().getDispatch(Blocks.getBlock(fallingBlock.carriedBlock.blockId)).renderNoCulling(Tessellator.instance, blockX, blockY, blockZ);

        this.container.setLightReferenceEntity(null);
        this.container.clear();

        tessellator.setTranslation(0.0F, 0.0F, 0.0F);
        tessellator.draw();
        Lighting.enableLight();
        GL11.glPopMatrix();
        TileEntityRenderer<TileEntity> renderer = TileEntityRenderDispatcher.instance.getRenderer(fallingBlock.carriedBlock.entity);

        if (renderer != null) {
            GL11.glPushMatrix();
            renderer.doRender(tessellator, fallingBlock.carriedBlock.entity, x - 0.5F, y - 0.5F, z - 0.5F, partialTick);
            GL11.glPopMatrix();
        }
    }
}
