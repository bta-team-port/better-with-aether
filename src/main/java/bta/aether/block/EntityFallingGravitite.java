package bta.aether.block;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;
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
        this.blockID = AetherBlocks.oreGravititeHolystone.id;
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
    public String getEntityTexture() {
        return "/assets/aether/block/GravititeOre.png";
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
            ++this.fallTime;

            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;

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

            // imagine actually checking for collision, couldn't be me.
            if (this.world.getBlockId(i, j+1, k) != 0 && (j + 0.5) - y < 0.001) {
                this.world.setBlock(i, j, k, this.blockID);
                this.remove();
            }

            if (this.fallTime > 100 && !this.world.isClientSide) {
                if (this.hasRemovedBlock) {
                    this.spawnAtLocation(this.blockID, 1);
            }

            this.remove();
            }

        }
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putShort("Tile", (short)this.blockID);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
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
