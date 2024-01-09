//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bta.aether.entity;

import bta.aether.block.BlockOreGravitite;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityFallingSand;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class EntityFallingGravitite extends EntityFallingSand {

    public EntityFallingGravitite(World world) {
        super(world);
        this.blockID = this.id;
        this.fallTime = 0;
    }

    public EntityFallingGravitite(World world, double d, double d1, double d2, int i) {
        super(world);
        this.fallTime = 0;
        this.blockID = i;
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
    @Override
    public void tick() {
        if (this.blockID == 0) {
            this.remove();
            return;
        }
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        ++this.fallTime;
        this.yd += 0.04;
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.98;
        this.yd /= 0.98;
        this.zd *= 0.98;
        int blockX = MathHelper.floor_double(this.x);
        int blockY = MathHelper.floor_double(this.y);
        int blockZ = MathHelper.floor_double(this.z);
        if (this.world.getBlockId(blockX, blockY, blockZ) == this.blockID) {
            this.world.setBlockWithNotify(blockX, blockY, blockZ, 0);
            this.hasRemovedBlock = true;
        }

        if (!world.isAirBlock(blockX, blockY + 1, blockZ) && this.world.isAirBlock(blockX, blockY, blockZ)) {
            this.xd *= 0.699999988079071;
            this.zd /= 0.699999988079071;
            this.yd *= +0.5;
            this.remove();
            if ((!this.world.canBlockBePlacedAt(this.blockID, blockX, blockY, blockZ, true, Side.BOTTOM) || BlockOreGravitite.canFallTo(this.world, blockX, blockY + 1, blockZ) || !this.world.setBlockWithNotify(blockX, blockY, blockZ, this.blockID)) && !this.world.isClientSide && this.hasRemovedBlock) {
                this.spawnAtLocation(this.blockID, 1);
            }
        } else if (this.fallTime > 100 && !this.world.isClientSide) {
            if (this.hasRemovedBlock) {
                this.spawnAtLocation(this.blockID, 1);
            }

            this.remove();
        }
    }
    @Override
    public boolean showBoundingBoxOnHover() {
        return false;
    }
}
