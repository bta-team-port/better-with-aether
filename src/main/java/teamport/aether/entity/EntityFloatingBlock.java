package teamport.aether.entity;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityFallingBlock;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.IVehicle;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicOreGravitite;


// TODO fix multiplayer desync

///  all the comment were made to understand what the logic does
public class EntityFloatingBlock extends EntityFallingBlock {
    public boolean onCeiling = false;
    public int ceilingTime = 0;
    public final int maxFloatingTime = 600;
    public boolean pushedByPiston = false;

    public EntityFloatingBlock(World world) {
        super(world);
        this.carriedBlock = new CarriedBlock(this, AetherBlocks.ORE_GRAVITITE_HOLYSTONE, 0, null);
        this.fallTime = 0;
        this.setSize(1.0F, 1.0F);
        this.heightOffset = this.bbHeight / 2.0F;
    }

    public EntityFloatingBlock(World world, double x, double y, double z, int blockId, int blockMeta, @Nullable TileEntity tileEntity) {
        super(world, x, y, z, blockId, blockMeta, tileEntity);
        this.carriedBlock = new CarriedBlock(this, blockId, blockMeta, tileEntity);
        if (tileEntity != null) {
            tileEntity.worldObj = null;
            tileEntity.carriedBlock = this.carriedBlock;
        }
    }

    public void tick() {
        // -------------------------------------------------------------------------------------------------------------
        // if air stop tick
        if (this.carriedBlock.blockId == 0) {
            // remove the entity
            this.remove();
            return;
        }

        //--------------------------------------------------------------------------------------------------------------
        // move the block do some calc
        this.pushesThisTick = 0;
        this.pushTime *= 0.98F;
        if (this.pushTime < 0.05F || (double) this.pushTime < 0.25 && this.onGround) {
            this.pushTime = 0.0F;
        }

        // multiplying by -1 results in the block rising forever
        int x = MathHelper.floor(this.x - 0.5);
        int y = MathHelper.floor(this.y);
        int z = MathHelper.floor(this.z - 0.5);
        this.isOnCeiling(x, y, z);

        if (onCeiling) ceilingTime++;
        else ceilingTime = 0;

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        ++this.fallTime;

        // rising block
        this.yd += 0.04;

        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.98;
        this.yd *= 0.98; // does the sand do that too? hm no sand multiplies what does this do?
        this.zd *= 0.98;


        //--------------------------------------------------------------------------------------------------------------
        if (this.world.getBlockId(x, y, z) == this.carriedBlock.blockId && !this.hasRemovedBlock) {
            this.world.setBlockWithNotify(x, y, z, 0);
            this.hasRemovedBlock = true;
        }

        //--------------------------------------------------------------------------------------------------------------
        // idk when this is getting hit, it seems once the block is moved its never onGround
        if (this.onGround) {
            this.xd *= 0.7;
            this.zd /= 0.7;
            this.yd *= 0.5;
        }

        if (this.onCeiling || y > 600) {
            this.xd *= 0.7;
            this.zd /= 0.7;
            this.yd *= 0.5;

            // JANK JANK JANK JANK JANK
            if (pushedByPiston && ceilingTime < 60) {}
            else this.remove();
        }

        //--------------------------------------------------------------------------------------------------------------
        // what does this do?
        double speed = Math.hypot(this.xd, this.zd);
        if (speed < 0.001 || this.isInWall()) {

            if (!this.onCeiling && !this.isInWall()) {
                if (this.fallTime > maxFloatingTime && !this.world.isClientSide) {
                    this.drop();
                    this.ejectRider();
                    this.remove();
                }
                this.carriedBlock.heldTick(this.world, this);
                return;
            }

            //----------------------------------------------------------------------------------------------------------
            // steel piston up here
            Entity rider = this.getPassenger();
            this.ejectRider();
            TileEntity oldEnt;
            //

            if (!this.world.isClientSide) {
                if (world.canBlockBePlacedAt(this.carriedBlock.blockId, x, y, z, true, Side.BOTTOM)
                        && !BlockLogicOreGravitite.canFallAbove(this.world, x, y + 1, z)
                ) {

                    boolean blockPlacedSuccessfully = this.world.setBlockWithNotify(x, y, z, this.carriedBlock.blockId);
                    if (!blockPlacedSuccessfully) this.drop();
                }

                else {
                    this.world.setBlockMetadataWithNotify(x, y, z, this.carriedBlock.metadata);

                    // this seems more concerned with rider than actual block setting logic
                    if (this.carriedBlock.entity != null) {
                        oldEnt = this.world.getTileEntity(x, y, z);
                        if (oldEnt != null) oldEnt.invalidate();

                        // not sure what happens here
                        this.carriedBlock.entity.validate();
                        this.carriedBlock.entity.x = x;
                        this.carriedBlock.entity.y = y;
                        this.carriedBlock.entity.z = z;
                        this.carriedBlock.entity.worldObj = this.world;
                        this.carriedBlock.entity.carriedBlock = null;
                        this.world.replaceBlockTileEntity(x, y, z, this.carriedBlock.entity);
                    }

                    // notify surrounding blocks
                    this.world.notifyBlockChange(x, y, z, this.carriedBlock.blockId);
                }
            }

            //----------------------------------------------------------------------------------------------------------
            // rider stuff
            if (rider != null) {
                oldEnt = this.world.getTileEntity(x, y, z);
                if (oldEnt instanceof IVehicle) {
                    rider.startRiding((IVehicle) oldEnt);
                }
            }

            //----------------------------------------------------------------------------------------------------------
            // ticks again?
            this.carriedBlock.heldTick(this.world, this);
        }
    }

    public void isOnCeiling(int x, int y, int z) {
        boolean canPlace = this.world.canBlockBePlacedAt(this.carriedBlock.blockId, x, y + 1, z, true, Side.TOP);
        boolean isWorldHeight = y + 1 >= world.getHeightBlocks();
        Block<?> block = world.getBlock(x, y + 1, z);
        if (block == null) return;
        onCeiling = !isWorldHeight & !canPlace & block.id() != Blocks.COBWEB.id();
    }

    @Override
    public AABB getBb() {
        return bb.expand(10, 10, 10);
    }

    @Override
    public boolean isPushable() {
        return true;
    }
}
