package teamport.aether.entity.floating_block;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityDispatcher;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityPrimedTNT;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.IVehicle;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlocks;

public class EntityFloatingBlock extends Entity {
    public CarriedBlock carriedBlock;
    public int floatTime;
    public boolean hasRemovedBlock = false;
    public EntityFloatingBlock.@NonNull FloatChecker floatChecker = EntityFloatingBlock::defaultFloatCheck;

    public static boolean defaultFloatCheck(@NonNull World world, @NonNull CarriedBlock carriedBlock, @NonNull TilePosc destination) {
        Block<?> block = world.getBlockType(destination);
        return block == Blocks.AIR || block.hasTag(BlockTags.PLACE_OVERWRITES);
    }

    public EntityFloatingBlock(World world) {
        super(world);
        this.carriedBlock = new CarriedBlock(this, AetherBlocks.ORE_GRAVITITE_HOLYSTONE, 0, null);
        this.floatTime = 0;
        this.setSize(1.0F, 1.0F);
        this.heightOffset = this.bbHeight / 2.0F;
    }

    public EntityFloatingBlock(World world, double x, double y, double z, int blockId, int blockMeta, @Nullable TileEntity tileEntity) {
        super(world);
        this.floatTime = 0;
        this.carriedBlock = new CarriedBlock(this, blockId, blockMeta, tileEntity);
        if (tileEntity != null) {
            tileEntity.worldObj = null;
            tileEntity.carriedBlock = this.carriedBlock;
        }

        this.blocksBuilding = true;
        this.setSize(1.0F, 1.0F);
        this.heightOffset = this.bbHeight / 2.0F;
        this.setPos(x, y, z);
        this.xd = 0.0F;
        this.yd = 0.0F;
        this.zd = 0.0F;
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected boolean makeStepSound() {
        return false;
    }

    protected void defineSynchedData() {
    }

    @Override
    public boolean isPickable() {
        return !this.removed;
    }

    @Override
    @SuppressWarnings("java:S6541")
    public void tick() {
        boolean isClient = this.world.isClientSide;
        if (this.carriedBlock.blockId == 0) {
            this.remove();
        } else {
            this.pushesThisTick = 0;
            this.pushTime *= 0.98F;
            if (this.pushTime < 0.05F || ((double) this.pushTime < 0.25 && this.verticalCollision)) {
                this.pushTime = 0.0F;
            }

            if (!isClient && this.carriedBlock.blockId == Blocks.TNT.id() && this.isOnFire()) {
                this.remove();
                EntityPrimedTNT entityPrimedTNT = new EntityPrimedTNT(this.world, this.x, this.y, this.z);
                entityPrimedTNT.xd = this.xd;
                entityPrimedTNT.yd = this.yd;
                entityPrimedTNT.zd = this.zd;
                entityPrimedTNT.pushTime = this.pushTime;
                this.world.entityJoinedWorld(entityPrimedTNT);
            } else {
                this.xo = this.x;
                this.yo = this.y;
                this.zo = this.z;
                if ((double) this.pushTime < 0.01 && this.yd >= 0.0) {
                    ++this.floatTime;
                }

                this.yd += 0.04;
                double oldYd = this.yd;
                this.move(this.xd, this.yd, this.zd);
                this.xd *= 0.98;
                this.yd *= 0.98;
                this.zd *= 0.98;
                TilePosc blockPos = this.tilePosApprox();
                if (!isClient && this.world.getBlockType(blockPos).id() == this.carriedBlock.blockId && !this.hasRemovedBlock) {
                    this.world.setBlockTypeNotify(blockPos, Blocks.AIR);
                    this.hasRemovedBlock = true;
                }

                if (this.y > world.getHeightBlocks() + 32) {
                    this.remove();
                    return;
                }

                boolean hitCeiling = this.verticalCollision && oldYd > 0.0;
                if (hitCeiling) {
                    Block<?> selfBlock = this.carriedBlock.block();
                    Block<?> blockAbove = this.world.getBlockType(blockPos.up(new TilePos()));
                    Block<?> blockAt = this.world.getBlockType(blockPos);
                    double friction = selfBlock.friction;
                    if (BlockTags.OVERRIDE_FRICTION.appliesTo(blockAt)) {
                        friction *= (double) blockAt.friction * 0.91;
                    } else if (blockAbove != Blocks.AIR) {
                        friction *= (double) blockAbove.friction * 0.91;
                    } else {
                        friction *= 0.98;
                    }

                    this.xd *= friction;
                    this.zd *= friction;
                    this.yd *= -0.5F;
                    this.pushTime *= (float) friction;
                }

                if (!isClient) {
                    label111:
                    {
                        if (!this.isInWall()) {
                            if (Math.hypot(this.xd, this.zd) >= 0.0625) {
                                break label111;
                            }

                            if (!hitCeiling) {
                                if (this.floatTime > 600) {
                                    if (this.hasRemovedBlock) {
                                        this.drop();
                                    }

                                    this.ejectRider();
                                    this.remove();
                                }
                                break label111;
                            }
                        }

                        Entity passenger = this.getPassenger();
                        this.ejectRider();
                        this.remove();
                        if (this.hasRemovedBlock) {
                            boolean shouldPlaceHere = !this.floatChecker.canFloatInto(this.world, this.carriedBlock, blockPos.up(new TilePos()));
                            boolean isPlaced = shouldPlaceHere && this.world.setBlockTypeData(blockPos, this.carriedBlock.block(), this.carriedBlock.metadata);
                            if (!isPlaced) {
                                this.drop();
                            } else {
                                TileEntity tileEntity = this.carriedBlock.entity;
                                if (tileEntity != null) {
                                    tileEntity.validate();
                                    tileEntity.tilePos.set(blockPos);
                                    tileEntity.worldObj = this.world;
                                    tileEntity.carriedBlock = null;
                                    this.world.replaceTileEntity(blockPos, tileEntity);
                                    if (passenger != null && tileEntity instanceof IVehicle vehicle) {
                                        passenger.startRiding(vehicle);
                                    }
                                }

                                Block.disableNormalEntityLogic = true;
                                this.carriedBlock.block().onPlacedByWorld(this.world, blockPos);
                                Block.disableNormalEntityLogic = false;
                                this.world.notifyBlockChange(blockPos, this.carriedBlock.block());
                            }
                        }

                        return;
                    }
                }

                this.carriedBlock.heldTick(this.world, this);
            }
        }
    }

    public void drop() {
        Block<?> block = this.carriedBlock.block();
        ItemStack[] drops = block.getBreakResult(this.world, EnumDropCause.SILK_TOUCH, this.carriedBlock.metadata, this.carriedBlock.entity);
        if (drops != null) {
            for (ItemStack drop : drops) {
                this.dropItem(drop, 0.0F);
            }
        }

        TilePosc bpos = this.tilePosApprox();
        if (this.carriedBlock.entity != null) {
            this.carriedBlock.entity.dropContents(this.world, bpos.x(), bpos.y(), bpos.z());
        }

    }

    @Override
    public byte calcLightIndex(float partialTick) {
        if (Global.accessor.isFullbrightEnabled()) {
            return LightIndexHelper.lightIndex2i(15, 15);
        } else {
            TilePos blockPos = new TilePos(this.x, this.y - (double) this.heightOffset + (double) this.ySlideOffset + (double) this.bbHeight * 0.66, this.z);
            byte lightIndex = 0;
            if (!this.world.getBlockLitInteriorSurface(blockPos) && this.world.getBlockType(blockPos) != this.carriedBlock.block()) {
                lightIndex = this.world.getSavedLightIndex(blockPos);
            } else {
                TilePos queryPos = new TilePos();
                lightIndex = LightIndexHelper.max(lightIndex, this.world.getSavedLightIndex(blockPos.up(queryPos)));
                lightIndex = LightIndexHelper.max(lightIndex, this.world.getSavedLightIndex(blockPos.west(queryPos)));
                lightIndex = LightIndexHelper.max(lightIndex, this.world.getSavedLightIndex(blockPos.east(queryPos)));
                lightIndex = LightIndexHelper.max(lightIndex, this.world.getSavedLightIndex(blockPos.south(queryPos)));
                lightIndex = LightIndexHelper.max(lightIndex, this.world.getSavedLightIndex(blockPos.north(queryPos)));
            }

            return lightIndex;
        }
    }

    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        tag.putShort("Tile", (short) this.carriedBlock.blockId);
        tag.putShort("TileData", (short) this.carriedBlock.metadata);
        if (this.carriedBlock.entity != null) {
            CompoundTag entityTag = new CompoundTag();
            this.carriedBlock.entity.writeToNBT(entityTag);
            tag.putCompound("TileEntity", entityTag);
        }

        tag.putBoolean("hasRemovedBlock", this.hasRemovedBlock);
    }

    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        this.carriedBlock = new CarriedBlock(this, tag.getShort("Tile") & 16383, tag.getShort("TileData") & 255, tag.containsKey("TileEntity") ? TileEntityDispatcher.createAndLoadEntity(tag.getCompound("TileEntity")) : null);
        if (this.carriedBlock.entity != null) {
            this.carriedBlock.entity.carriedBlock = this.carriedBlock;
        }

        this.hasRemovedBlock = tag.getBooleanOrDefault("hasRemovedBlock", true);
    }

    @Override
    public void fling(double xd, double yd, double zd, float pushTime) {
        super.fling(xd, yd, zd, pushTime);
        this.floatTime = 0;
    }

    @Override
    public float getShadowHeightOffs() {
        return 0.0F;
    }

    @Override
    public boolean showBoundingBoxOnHover() {
        return true;
    }

    @Override
    public double getRideHeight() {
        return 0.0F;
    }

    public @NonNull TilePosc tilePosApprox() {
        return new TilePos(MathHelper.round(this.x - (double) 0.5F), MathHelper.floor(this.y), MathHelper.round(this.z - (double) 0.5F));
    }

    public CarriedBlock getCarriedBlock() {
        return carriedBlock;
    }

    public void setHasRemovedBlock(boolean hasRemovedBlock) {
        this.hasRemovedBlock = hasRemovedBlock;
    }

    public interface FloatChecker {
        boolean canFloatInto(@NonNull World var1, @NonNull CarriedBlock var2, @NonNull TilePosc var3);
    }

}
