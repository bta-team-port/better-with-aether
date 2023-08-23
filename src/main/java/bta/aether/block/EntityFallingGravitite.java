//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bta.aether.block;

import bta.aether.Aether;
import bta.aether.block.BlockOreGravitite;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockSand;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class EntityFallingGravitite extends Entity {
    public int blockID;
    public int fallTime;
    public boolean hasRemovedBlock = false;

    public static boolean fallInstantly = false;


    public EntityFallingGravitite(World world) {
        super(world);
        this.blockID = this.id;
        this.fallTime = 0;
    }

    public EntityFallingGravitite(World world, double d, double d1, double d2, int i) {
        super(world);
        this.fallTime = 0;
        this.blockID = Aether.oreGravititeHolystone.id;
        this.blocksBuilding = true;
        this.setSize(0.98F, 0.98F);
        this.heightOffset = this.bbHeight / 2.0F;
        this.setPos(d, d1, d2);
        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;
        this.xo = d;
        this.yo = d1;
        this.zo = d2;
    }

    private void tryToFall(World world, int i, int j, int k) {
        if (canFallAbove(world, i, j + 1, k) && j < 256) {
            byte byte0 = 32;
            if (!fallInstantly && world.areBlocksLoaded(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0)) {
                EntityFallingGravitite floating = new EntityFallingGravitite(world, (double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), this.id);
                world.entityJoinedWorld(floating);
            } else {
                world.setBlockWithNotify(i, j, k, 0);

                while(canFallAbove(world, i, j + 1, k) && j < 256) {
                    ++j;
                }

                if (j > 0) {
                    world.setBlockWithNotify(i, j, k, this.id);
                }
            }
        }

    }

    protected boolean makeStepSound() {
        return false;
    }

    protected void init() {
    }

    public boolean isPickable() {
        return !this.removed;
    }

    public void tick() {
        if (this.blockID == 0) {
            this.remove();
        } else {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            ++this.fallTime;
            this.yd += 0.03999999910593033;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.9800000190734863;
            this.yd /= 0.9800000190734863;
            this.zd *= 0.9800000190734863;
            int i = MathHelper.floor_double(this.x);
            int j = MathHelper.floor_double(this.y);
            int k = MathHelper.floor_double(this.z);
            if (this.world.getBlockId(i, j, k) == this.blockID) {
                this.world.setBlockWithNotify(i, j, k, 0);
                this.hasRemovedBlock = true;
            }

            if (this.onGround) {
                this.xd *= 0.699999988079071;
                this.zd /= 0.699999988079071;
                this.yd *= +0.5;
                this.remove();
                if ((!this.world.canBlockBePlacedAt(this.blockID, i, j, k, true, Side.TOP) || BlockOreGravitite.canFallBelow(this.world, i, j + 1, k) || !this.world.setBlockWithNotify(i, j, k, this.blockID)) && !this.world.isClientSide && this.hasRemovedBlock) {
                    this.spawnAtLocation(this.blockID, 1);
                }
            } else if (this.fallTime > 100 && !this.world.isClientSide) {
                if (this.hasRemovedBlock) {
                    this.spawnAtLocation(this.blockID, 1);
                }

                this.remove();
            }

        }
    }

    public static boolean canFallAbove(World world, int i, int j, int k) {
        int blockId = world.getBlockId(i, j, k);
        if (blockId == 0) {
            return true;
        } else if (blockId == Block.fire.id) {
            return true;
        } else {
            return Block.hasTag(blockId, BlockTags.IS_WATER) ? true : Block.hasTag(blockId, BlockTags.IS_LAVA);
        }
    }

    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putShort("Tile", (short)this.blockID);
    }

    protected void readAdditionalSaveData(CompoundTag tag) {
        this.blockID = tag.getShort("Tile") & 16383;
    }

    public float getShadowHeightOffs() {
        return 0.0F;
    }

    public World getWorld() {
        return this.world;
    }

    public boolean showBoundingBoxOnHover() {
        return false;
    }
}
