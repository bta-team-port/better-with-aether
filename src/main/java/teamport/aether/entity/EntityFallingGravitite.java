package teamport.aether.entity;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.entity.EntityFallingBlock;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicOreGravitite;

public class EntityFallingGravitite extends EntityFallingBlock {
    public EntityFallingGravitite(World world) {
        super(world);
        this.carriedBlock = new CarriedBlock(this, AetherBlocks.BLOCK_GRAVITITE, 0, null);
        this.fallTime = 0;
        this.setSize(1.0F, 1.0F);
        this.heightOffset = this.bbHeight / 2.0F;
    }

    public EntityFallingGravitite(World world, double x, double y, double z, int blockId, int blockMeta, @Nullable TileEntity tileEntity) {
        super(world);
        this.fallTime = 0;
        this.carriedBlock = new CarriedBlock(this, blockId, blockMeta, tileEntity);
        if (tileEntity != null) {
            tileEntity.worldObj = null;
            tileEntity.carriedBlock = this.carriedBlock;
        }

        this.blocksBuilding = true;
        this.setSize(1.0F, 1.0F);
        this.heightOffset = this.bbHeight / 2.0F;
        this.setPos(x, y, z);
        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public void tick() {
        if (this.carriedBlock.blockId == 0) {
            this.remove();
        } else {
            this.pushesThisTick = 0;
            this.pushTime *= 0.98F;
            if (this.pushTime < 0.05F || (double)this.pushTime < 0.25 && this.onGround) {
                this.pushTime = 0.0F;
            }

            else {
                this.xo = this.x;
                this.yo = this.y;
                this.zo = this.z;
                if ((double)this.pushTime < 0.01 && this.yd <= 0.0) {
                    ++this.fallTime;
                }

                this.yd += 0.04;
                this.move(this.xd, this.yd, this.zd);
                this.xd *= 0.9800000190734863;
                this.yd *= 0.9800000190734863;
                this.zd *= 0.9800000190734863;
                int x = MathHelper.round(this.x - 0.5);
                int y = MathHelper.round(this.y);
                int z = MathHelper.round(this.z - 0.5);
                if (this.world.getBlockId(x, y, z) == this.carriedBlock.blockId && !this.hasRemovedBlock) {
                    this.world.setBlockWithNotify(x, y, z, 0);
                    this.hasRemovedBlock = true;
                }

                if (this.onGround) {
                    this.xd *= 0.699999988079071;
                    this.zd *= 0.699999988079071;
                    this.yd *= -0.5;
                    this.remove();
                }

                double v = Math.hypot(this.xd, this.zd);
                if (v < 0.001 || this.isInWall()) {
                    if (!this.onGround && !this.isInWall()) {
                        if (this.fallTime > 100 && !this.world.isClientSide) {
                            this.remove();
                        }
                    } else {
                        this.remove();
                        TileEntity oldEnt;
                        if ((!this.world.canBlockBePlacedAt(this.carriedBlock.blockId, x, y, z, true, Side.BOTTOM) || BlockLogicOreGravitite.canFallAbove(this.world, x, y + 1, z) || !this.world.setBlock(x, y, z, this.carriedBlock.blockId)) && !this.world.isClientSide) {
                            if (this.hasRemovedBlock) {
                                this.drop();
                            }
                        } else if (!this.world.isClientSide) {
                            this.world.setBlockMetadata(x, y, z, this.carriedBlock.metadata);
                            if (this.carriedBlock.entity != null) {
                                oldEnt = this.world.getTileEntity(x, y, z);
                                if (oldEnt != null) {
                                    oldEnt.invalidate();
                                }

                                this.carriedBlock.entity.validate();
                                this.carriedBlock.entity.x = x;
                                this.carriedBlock.entity.y = y;
                                this.carriedBlock.entity.z = z;
                                this.carriedBlock.entity.worldObj = this.world;
                                this.carriedBlock.entity.carriedBlock = null;
                                this.world.replaceBlockTileEntity(x, y, z, this.carriedBlock.entity);
                            }

                            this.world.notifyBlockChange(x, y, z, this.carriedBlock.blockId);
                        }


                    }

                    this.carriedBlock.heldTick(this.world, this);
                }

            }
        }
    }

}
