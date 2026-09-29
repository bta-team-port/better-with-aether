package teamport.aether.block.entity;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobSlime;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.ItemWandSpawner;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketTileEntityData;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
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

    public World getWorld() {
        if (this.worldObj != null) {
            return this.worldObj;
        }
        if (this.carriedBlock != null) {
            return this.carriedBlock.world;
        }
        return null;
    }

    @Override
    public void heldTick(World world, Entity holder) {
        this.tick();
    }

    @Override
    public boolean tryPlace(World world, Entity holder, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        boolean success = super.tryPlace(world, holder, blockX, blockY, blockZ, side, xPlaced, yPlaced);
        if (success) {
            this.carriedBlock = null;
            this.updateContainer(false);
        }
        return success;
    }

    @Override
    public void tick() {
        boolean isEnergyTimeHigherThan0 = this.getCurrentEnergyTime() > 0;
        boolean updateMachine = false;
        if (this.getCurrentEnergyTime() > 0) {
            this.setCurrentEnergyTime(this.getCurrentEnergyTime() - 1);
        }
        if (canProcess()) {
            RecipeEntryIncubator recipe = AetherRecipes.INCUBATOR.findRecipe(containerItemStacks[0]);
            if (recipe != null) {
                this.setMaxProcessTime(recipe.getData());
            }
        }
        if (isUpdateMachine(updateMachine, isEnergyTimeHigherThan0)) {
            this.setChanged();
        }
    }

    public boolean isUpdateMachine(boolean updateMachine, boolean isEnergyTimeHigherThan0) {
        World world = getWorld();
        if (world == null || !world.isClientSide) {
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
        World world = getWorld();
        if ((world == null
            || world.getBlockType(this.tilePos) == AetherBlocks.INCUBATOR_IDLE)
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
                    this.worldObj.markBlockNeedsUpdate(this.tilePos);
                }

                return itemstack;
            } else {
                ItemStack itemstack1 = this.containerItemStacks[index].splitStack(takeAmount);
                if (this.containerItemStacks[index].stackSize <= 0) {
                    this.containerItemStacks[index] = null;
                    if (this.worldObj != null && index == 0) {
                        this.worldObj.markBlockNeedsUpdate(this.tilePos);
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
            this.worldObj.markBlockNeedsUpdate(this.tilePos);
        }

    }

    @Override
    public void processItem() {
        if (!this.canProcess()) {
            return;
        }

        World world = getWorld();
        if (world == null) {
            return;
        }

        ItemStack inputStack = containerItemStacks[0];
        boolean wand = inputStack.getItem() instanceof ItemWandSpawner;
        Entity entity;

        if (wand) {
            String monsterId = inputStack.getData().getString("monster");
            if (monsterId.isEmpty()) {
                monsterId = "Pig";
            }
            entity = EntityDispatcher.getInstance().createEntityInWorld(monsterId, world);
            if (entity != null) {
                entity.spawnInit();
                String customName = inputStack.getCustomName();
                if (customName != null && entity instanceof Mob mob) {
                    if (inputStack.hasCustomColor()) {
                        mob.chatColor = inputStack.getCustomColor();
                    }
                    mob.setNickname(customName);
                }
            }
        } else {
            RecipeEntryIncubator recipe = AetherRecipes.INCUBATOR.findRecipe(inputStack);
            EntityDispatcher.EntityDispatcherEntry<?> entry = recipe == null ? null : EntityDispatcher.getInstance().entryForId(recipe.getOutput().getEntity());
            Class<? extends Entity> entityClazz = entry == null ? null : entry.entityClass;

            if (entityClazz == null) {
                return;
            }
            entity = createEntity(entityClazz, world);
        }

        if (entity == null) {
            return;
        }

        if (this.worldObj == null && this.carriedBlock != null) {
            Entity holder = this.carriedBlock.holder;
            entity.moveTo(holder.x, holder.y + holder.getHeadHeight() / 2, holder.z, holder.yRot, holder.xRot);
        } else {
            entity.moveTo(this.tilePos.x + 0.5, this.tilePos.y + 1.0, this.tilePos.z + 0.5, 0.0F, 0.0F);
        }

        world.entityJoinedWorld(entity);
        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, entity.x, entity.y, entity.z, "tile.activator.use", 1.0F, 2.0F);

        if (!wand) {
            inputStack.stackSize--;
            if (inputStack.stackSize <= 0) {
                containerItemStacks[0] = null;
            }
        }

        if (entity instanceof MobMoa) {
            Player player = world.getClosestPlayerToEntity(entity, 16);
            if (player != null) {
                player.triggerAchievement(AetherAchievements.MOA);
            }
        }
    }

    private Entity createEntity(Class<? extends Entity> entityClazz, World world) {
        Entity entity = EntityDispatcher.getInstance().createEntityInWorld(entityClazz, world);
        if (entity instanceof MobMoa mobMoa) {
            mobMoa.setTamed(true);
            mobMoa.heal(100);
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
        if (this.containerItemStacks[0].getItem() instanceof ItemWandSpawner) {
            return true;
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
            BlockLogicIncubator.updateFurnaceBlockState(this.worldObj, this.tilePos, forceLit || this.getCurrentEnergyTime() > 0);
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
}
