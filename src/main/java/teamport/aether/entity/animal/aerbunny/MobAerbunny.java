package teamport.aether.entity.animal.aerbunny;

import net.minecraft.client.Minecraft;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.net.packet.PacketSetRiding;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.IVehicle;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.AetherRideable;
import teamport.aether.entity.animal.MobAetherAnimal;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherItemTags;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.net.message.AetherRideableNetworkMessage;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

public class MobAerbunny extends MobAetherAnimal implements AetherRideable {
    private boolean grab;

    public MobAerbunny(World world) {
        super(world);
        this.setSize(0.4F, 0.4F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "aerbunny");
        this.mobDrops.add(new WeightedRandomLootObject(Items.STRING.getDefaultStack(), 1));
    }

    @Override
    public boolean isFavouriteItem(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.itemID < Blocks.blocksList.length) {
            Block<?> block = Blocks.blocksList[itemStack.itemID];
            if (block != null && block.hasTag(BlockTags.SHEEPS_FAVOURITE_BLOCK)) return true;
        }
        return itemStack.getItem().hasTag(AetherItemTags.NATURE_STAFF_FOLLOW);
    }

    public boolean beingRidden() {
        return vehicle != null;
    }

    private boolean isVehicleSneaking() {
        return vehicle instanceof Player && ((Player) vehicle).isSneaking();
    }

    @Override
    public void trySuffocate() {
        if (!beingRidden()) {
            super.trySuffocate();
        }
    }

    @Override
    public boolean collidesWithBlock(Block<?> block, int metadata) {
        if (beingRidden()) { return false; }

        return super.collidesWithBlock(block, metadata);
    }

    @Override
    public boolean collidesWith(Entity entity) {
        if (beingRidden()) { return false; }

        return super.collidesWith(entity);
    }

    @Override
    public boolean isSelectable() {
        if (beingRidden() && !isVehicleSneaking()) { return false; }

        return super.isSelectable();
    }

    @Override
    public boolean isPickable() {
        if (beingRidden() && !isVehicleSneaking()) { return false; }

        return super.isPickable();
    }

    @Override
    public boolean isPushable() {
        if (beingRidden()) { return false; }

        return super.isPushable();
    }

    @Override
    public int getMaxHealth() {
        return 4;
    }

    @Override
    public double getRidingHeight() {
        if (EnvironmentHelper.isClientWorld() && this.vehicle != Minecraft.getMinecraft().thePlayer) {
            return this.heightOffset + 0.5F;
        }
        return this.heightOffset - 1.1f;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(20, 0, Integer.class);
    }

    public float getPuffiness() {
        return Float.intBitsToFloat(this.entityData.getInt(20));
    }

    public void setPuffiness(float val) {
        this.entityData.set(20, Float.floatToIntBits(val));
    }


    @Override
    public void controlEntity(float moveForward, float moveStrafe, boolean isJumping, float xRot, float yRot) {
        if (EnvironmentHelper.isClientWorld()) {
            NetworkHandler.sendToServer(
                new AetherRideableNetworkMessage(moveForward, moveStrafe, isJumping, xRot, yRot)
            );
        }

        Entity vehicle = (Entity) this.vehicle;

        if (vehicle!= null && vehicle.yd < -0.225F && isJumping && !vehicle.noPhysics) {
            (vehicle).yd = 0.125F;

            this.cloudPoop();
            setPuffiness(1.15F);
        }
    }

    @Override
    public void tick() {
        float puffiness = getPuffiness();

        if (puffiness > 0.0F) {
            puffiness -= 0.1F;
        } else {
            puffiness = 0.0F;
        }

        setPuffiness(puffiness);

        if (vehicle != null) {
            if (this.vehicle.isRemoved()) this.startRiding(this.vehicle);
        } else if (!grab) {
            if (this.moveForward != 0.0F) {
                int x = MathHelper.floor(this.x);
                int y = MathHelper.floor(this.bb.minY);
                int z = MathHelper.floor(this.z);

                if (this.world != null && (this.world.getBlockId(x, y - 1, z) != 0 || this.world.getBlockId(x, y - 2, z) != 0)
                    && this.world.getBlockId(x, y + 1, z) == 0 && this.world.getBlockId(x, y + 2, z) == 0) {
                    if (this.yd < 0.0) {
                        this.cloudPoop();
                        setPuffiness(0.9F);
                    }
                    this.yd = 0.2;
                }
            }

            if (this.yd < -0.1) {
                this.yd = -0.1;
            }
        }

        if (this.vehicle instanceof Player) {
            Player player = (Player) vehicle;

            if (!player.onGround && !player.noPhysics) {
                if (!player.isInWater()) player.yd += 0.05F;
                ((EntityAccessor) player).setFallDistance(0.0F);
            }

            player.handleSpecialVehicleControl();
            player.sendSpecialVehiclePacket();
        }

        this.noPhysics = beingRidden();
        super.tick();
    }

    @Override
    public void causeFallDamage(float distance) {
    }

    @Override
    public void onLivingUpdate() {
        if (onGround && this.moveForward != 0.0F) {
            this.jump();
            setPuffiness(1.15F);
            this.cloudPoop();
        }

        if (grab && onGround) {
            grab = false;

            if (this.world != null) {
                this.world.playSoundAtEntity(null, this, "aether:mob.aerbunny.land", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(12.0, 12.0, 12.0))) {
                    if (entity instanceof MobMonster) {
                        ((MobMonster) entity).setTarget(this);
                    }
                }
            }
        }

        if (this.isInWater()) {
            this.jump();
        }

        super.onLivingUpdate();
    }

    public void cloudPoop() {
        double factor = this.random.nextFloat() - 0.5F;
        double x = this.x + factor * 0.4000000059604645;
        double y = this.bb.minY;
        double z = this.z + factor * 0.4000000059604645;

        if (EnvironmentHelper.isServerEnvironment() && this.vehicle != null) {
            y += ((Player) vehicle).bbHeight;
        }

        ParticleMaker.spawnParticle(world, "explode", x, y, z, 0.0, -0.07500000298023224, 0.0, 0);
    }

    @Override
    public void lavaHurt() {
        if (!beingRidden()) super.lavaHurt();
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker == this.vehicle) return false;
        return super.hurt(attacker, damage, type);
    }

    @Override
    public boolean interact(@NonNull Player player) {
        if (player.isSneaking() && vehicle == null) return super.interact(player);

        if (this.vehicle == player) {
            grab = false;

            vehicle.ejectRider();
            return true;
        }

        if (player.getPassenger() instanceof MobAerbunny) {
            return false;
        }

        this.startRiding(player);

        grab = true;
        if (this.world != null) {
            this.world.playSoundAtEntity(null, this, "aether:mob.aerbunny.lift", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }
        this.isJumping = false;

        return true;
    }

    @Override
    public String getLivingSound() {
        return "aether:mob.aerbunny.lift";
    }

    @Override
    public String getHurtSound() {
        return "aether:mob.aerbunny.hurt";
    }

    @Override
    public String getDeathSound() {
        return "aether:mob.aerbunny.death";
    }


    @Override
    public void startRiding(IVehicle vehicle) {
        super.startRiding(vehicle);

        if (EnvironmentHelper.isServerEnvironment() && this.world != null) {
            MinecraftServer.getInstance().playerList.sendPacketToPlayersAroundPoint(
                x, y, z, 32, this.world.dimension.id,
                new PacketSetRiding(this, (Entity) this.vehicle)
            );
        }
    }
}
