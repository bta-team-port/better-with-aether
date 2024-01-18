package bta.aether.tile;

import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import bta.aether.recipe.AetherRecipes;
import net.minecraft.core.item.ItemStack;

import java.util.Random;

public class TileEntityEnchanter extends TileEntityAetherMachine {

    private final Random random = new Random();
    @Override
    public void tick() {
        super.tick();
        work();
    }

    private void work(){
        boolean update = false;
        if(fuelBurnTicks > 0){
            fuelBurnTicks--;
        }
        if(canProcess()){
            progressMaxTicks = AetherRecipes.ENCHANTER.findRecipe(contents[0]).getData();
        }
        if(!worldObj.isClientSide){
            if(progressTicks == 0 && canProcess()){
                update = fuel();
            }
            if(isBurning() && canProcess()){
                if(progressTicks % 5 == 0){
                    worldObj.spawnParticle("flame",x+0.5,y+1+Math.min(random.nextFloat(),0.2),z+0.5,0,0,0,3);
                    worldObj.spawnParticle("smoke",x+0.5,y+1+Math.min(random.nextFloat(),0.2),z+0.5,0,0.01,0,5);
                }
                progressTicks++;
                if(progressTicks >= progressMaxTicks){
                    progressTicks = 0;
                    processItem();
                    update = true;
                }
            } else if (canProcess()) {
                fuel();
                if(fuelBurnTicks > 0){
                    fuelBurnTicks++;
                }
            }

            if(contents[0] == null) this.progressTicks = 0;
        }

        if(update){
            this.onInventoryChanged();
        }
    }

    private boolean fuel(){

        int fuel = 0;
        if (contents[1] != null) {
            if (contents[1].getItem() == AetherItems.ambrosium) {
                fuel = 500;
            }

            if (contents[1].getItem() == AetherBlocks.blockAmbrosium.asItem()){
                fuel = 4000;
            }
        }

        if(fuel > 0 && canProcess()){
            fuelMaxBurnTicks = fuelBurnTicks = fuel;
            contents[1].stackSize--;
            if(contents[1].stackSize == 0){
                contents[1] = null;
            }
            return true;
        }
        return false;
    }

    private void processItem(){
        if(canProcess()){
            ItemStack stack = AetherRecipes.ENCHANTER.findOutput(contents[0]);
            if(stack != null){
                if(contents[2] == null){
                    setInventorySlotContents(2, stack);
                } else if(contents[2].isItemEqual(stack)) {
                    contents[2].stackSize += stack.stackSize;
                }
                if(this.contents[0].getItem().hasContainerItem()) {
                    this.contents[0] = new ItemStack(this.contents[0].getItem().getContainerItem());
                } else {
                    --this.contents[0].stackSize;
                }
                if(this.contents[0].stackSize <= 0) {
                    this.contents[0] = null;
                }
            }
        }
    }

    private boolean canProcess() {
        if(contents[0] == null) {
            return false;
        } else {
            ItemStack stack = AetherRecipes.ENCHANTER.findOutput(contents[0]);
            return stack != null && (contents[2] == null
                    || (contents[2].isItemEqual(stack)
                    && (contents[2].stackSize < getInventoryStackLimit()
                    && contents[2].stackSize < contents[2].getMaxStackSize()
                    || contents[2].stackSize < stack.getMaxStackSize())));
        }
    }
}
