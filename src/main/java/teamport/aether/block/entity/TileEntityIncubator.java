package teamport.aether.block.entity;


import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.monster.MobSlime;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketTileEntityData;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.AetherRecipes;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.machine.BlockLogicIncubator;
import teamport.aether.entity.animal.moa.MobMoa;
import teamport.aether.lookup.LookupFuelIncubator;
import teamport.aether.recipe.RecipeEntryIncubator;

public class TileEntityIncubator extends AetherTileEntityMachine {
    public TileEntityIncubator() {
        this.containerItemStacks = new ItemStack[2];
    }

    @Override
    public @NonNull String getNameTranslationKey() {
        return "container.incubator.name";
    }

    @Override
    public void tick() {
        boolean isEnergyTimeHigherThan0 = this.getCurrentEnergyTime() > 0;
        boolean updateMachine = false;
        if (this.getCurrentEnergyTime() > 0) {
            this.setCurrentEnergyTime(this.getCurrentEnergyTime() - 1);
        }
        if (canProcess()) {
            this.setMaxProcessTime(AetherRecipes.INCUBATOR.findRecipe(containerItemStacks[0]).getData());
        }
        if (isUpdateMachine(updateMachine, isEnergyTimeHigherThan0)) {
            this.setChanged();
        }
    }

    public boolean isUpdateMachine(boolean updateMachine, boolean isEnergyTimeHigherThan0) {
        if (this.worldObj == null || !this.worldObj.isClientSide) {
            updateMachine = eternallyLit(updateMachine);

            if (this.getCurrentEnergyTime() == 0 && this.containerItemStacks[1] != null && this.canProcess()) {
                this.setCurrentEnergyTime(this.getEnergyTimeFromItem(this.containerItemStacks[1]));
                this.setMaxEnergyTime(this.getCurrentEnergyTime());
                if (this.getCurrentEnergyTime() > 0) {
                    updateMachine = true;
                    if (this.containerItemStacks[1] != null) {
                        --this.containerItemStacks[1].stackSize;
                        if (this.containerItemStacks[1].stackSize <= 0) {
                            this.containerItemStacks[1] = null;
                        }

                    }
                }
            }

            if (this.isProcessing() && this.canProcess()) {
                this.setCurrentProcessTime(this.getCurrentProcessTime() + 1);
                if (this.getCurrentProcessTime() >= this.getMaxProcessTime()) {
                    this.setCurrentProcessTime(0);
                    this.processItem();
                    updateMachine = true;
                }
            } else {
                this.setCurrentProcessTime(0);
            }

            if (isEnergyTimeHigherThan0 != this.getCurrentEnergyTime() > 0) {
                this.updateContainer(false);
                updateMachine = true;
            }

            if (containerItemStacks[0] == null) {
                updateMachine = true;
            }

        }
        return updateMachine;
    }

    public boolean eternallyLit(boolean updateMachine) {
        if ((this.worldObj == null
            || this.worldObj.getBlockId(this.tilePos.x, this.tilePos.y, this.tilePos.z) == AetherBlocks.INCUBATOR_IDLE.id())
            && this.getCurrentEnergyTime() == 0 && this.containerItemStacks[0] == null
            && this.containerItemStacks[1] != null
            && this.containerItemStacks[1].itemID == Blocks.WOOL.id()
        ) {
            --this.containerItemStacks[1].stackSize;
            if (this.containerItemStacks[1].stackSize <= 0) {
                this.containerItemStacks[1] = null;
            }

            this.updateContainer(true);
            return true;
        }
        return updateMachine;
    }

    @Override
    public @Nullable ItemStack removeItem(int index, int takeAmount) {
        if (this.containerItemStacks[index] != null) {
            if (this.containerItemStacks[index].stackSize <= takeAmount) {
                ItemStack itemstack = this.containerItemStacks[index];
                this.containerItemStacks[index] = null;
                if (this.worldObj != null && index == 0) {
                    this.worldObj.markBlockNeedsUpdate(this.tilePos.x, this.tilePos.y, this.tilePos.z);
                }

                return itemstack;
            } else {
                ItemStack itemstack1 = this.containerItemStacks[index].splitStack(takeAmount);
                if (this.containerItemStacks[index].stackSize <= 0) {
                    this.containerItemStacks[index] = null;
                    if (this.worldObj != null && index == 0) {
                        this.worldObj.markBlockNeedsUpdate(this.tilePos.x, this.tilePos.y, this.tilePos.z);
                    }
                }

                return itemstack1;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setItem(int index, @Nullable ItemStack itemstack) {
        this.containerItemStacks[index] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getMaxStackSize()) {
            itemstack.stackSize = this.getMaxStackSize();
        }

        if (this.worldObj != null && index == 0) {
            this.worldObj.markBlockNeedsUpdate(this.tilePos.x, this.tilePos.y, this.tilePos.z);
        }

    }

    @Override
    public void processItem() {
        if (!this.canProcess()) {
            return;
        }

        RecipeEntryIncubator recipe = AetherRecipes.INCUBATOR.findRecipe(containerItemStacks[0]);
        EntityDispatcher.EntityDispatcherEntry<?> entry = EntityDispatcher.getInstance().entryForId(recipe.getOutput().getEntity());
        Class<? extends Entity> entityClazz = entry == null ? null : entry.entityClass;

        if (entityClazz == null) {
            return;
        }

        Entity entity = createEntity(entityClazz);
        if (entity == null) {
            return;
        }
        entity.moveTo(this.tilePos.x + 0.5, this.tilePos.y + 1.0, this.tilePos.z, 0.0F, 0.0F);
        if (this.worldObj != null) this.worldObj.entityJoinedWorld(entity);

        containerItemStacks[0].stackSize--;
        if (containerItemStacks[0].stackSize <= 0) {
            containerItemStacks[0] = null;
        }

        if (this.worldObj != null && (entity instanceof MobMoa)) {
            Player player = this.worldObj.getClosestPlayerToEntity(entity, 16);
            if (player != null) {
                player.triggerAchievement(AetherAchievements.MOA);
            }
        }
    }

    private Entity createEntity(Class<? extends Entity> entityClazz) {
        Entity entity = EntityDispatcher.getInstance().createEntityInWorld(entityClazz, this.worldObj);
        if (entity instanceof MobMoa mobMoa) {
            mobMoa.setTamed(true);
        }
        if (entity instanceof MobSlime slime) {
            slime.setSlimeSize(random.nextInt(4) + 1);
        }
        return entity;
    }

    @Override
    public boolean canProcess() {
        if (this.containerItemStacks[0] == null) {
            return false;
        }
        return AetherRecipes.INCUBATOR.findOutput(this.containerItemStacks[0]) != null;
    }

    @Override
    public Packet getDescriptionPacket() {
        return this.containerItemStacks[0] != null ? new PacketTileEntityData(this) : null;
    }

    @Override
    public void updateContainer(boolean forceLit) {
        if (this.worldObj != null) {
            BlockLogicIncubator.updateFurnaceBlockState(forceLit || this.getCurrentEnergyTime() > 0, this.worldObj, this.tilePos.x, this.tilePos.y, this.tilePos.z);
            return;
        }
        if (this.carriedBlock != null) {
            this.carriedBlock.blockId = forceLit || this.getCurrentEnergyTime() > 0 ? AetherBlocks.INCUBATOR_ACTIVE.id() : AetherBlocks.INCUBATOR_IDLE.id();
        }
    }

    @Override
    public int getEnergyTimeFromItem(ItemStack itemStack) {
        // just in case where will be more options later
        return itemStack == null ? 0 : LookupFuelIncubator.INSTANCE.getFuelYield(itemStack.getItem().id);
    }

    @Override
    public CarriedBlock getCarriedEntry(World world, Entity holder, Block<?> currentBlock, int currentMeta) {
        return new CarriedBlock(holder, currentBlock, 0, this);
    }

    @Override
    public void dropContents(World world, int x, int y, int z) {
        super.dropContents(world, x, y, z);
        if (!BlockLogicIncubator.isKeepIncubatorInventory()) {
            for (int l = 0; l < this.getContainerSize(); ++l) {
                ItemStack itemstack = this.getItem(l);
                if (itemstack != null) {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int i1 = this.random.nextInt(21) + 10;
                        if (i1 > itemstack.stackSize) {
                            i1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= i1;
                        EntityItem entityItem = new EntityItem(
                            world, x + f, y + f1, z + f2,
                            new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
                        float f3 = 0.05F;
                        entityItem.xd = (float) this.random.nextGaussian() * f3;
                        entityItem.yd = (float) this.random.nextGaussian() * f3 + 0.2F;
                        entityItem.zd = (float) this.random.nextGaussian() * f3;
                        world.entityJoinedWorld(entityItem);
                    }
                }
            }
        }
    }
}
