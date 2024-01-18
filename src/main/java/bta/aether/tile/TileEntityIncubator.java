package bta.aether.tile;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityMoa;
import bta.aether.item.AetherItems;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;

import java.util.Random;

public class TileEntityIncubator extends TileEntityAetherMachine {

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
            progressMaxTicks = 8000;
        }

        if(!worldObj.isClientSide){
            if(progressTicks == 0 && canProcess()){
                update = fuel();
            }

            if(isBurning() && canProcess()){
                if(progressTicks % 5 == 0){
                    worldObj.spawnParticle("smoke",x+0.5,y+1+Math.min(random.nextFloat(),0.2),z+0.5,0,0.01,0,5);
                }

                progressTicks++;
                if(progressTicks >= progressMaxTicks){
                    progressTicks = 0;
                    processItem();
                    update = true;
                }
            }

            else if (canProcess()) {
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
            if (contents[1].getItem() == AetherBlocks.torchAmbrosium.asItem()) {
                fuel = 500;
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
            if (contents[0].getItem() == Item.eggChicken) {
                spawnEntity(this.worldObj, this.x, this.y + 2, this.z, EntityChicken.class);
                contents[0].stackSize--;
            }

            if (contents[0].getItem() == AetherItems.eggMoaBlue) {
                spawnEntity(this.worldObj, this.x, this.y + 2, this.z, EntityMoa.class);
                contents[0].stackSize--;
            }
        }

        if (contents[0].stackSize <= 0) contents[0] = null;
    }

    private boolean canProcess() {
        if (contents[0] != null) {
            return contents[0].getItem().hasTag(AetherItems.aetheregg) || contents[0].getItem() == Item.eggChicken;
        }
        return false;
    }

    public static void spawnEntity(World world, int x, int y, int z, Class<? extends Entity> entityToSpawn) {
        Entity entity;

        try {
            entity = entityToSpawn.getConstructor(World.class).newInstance(world);
        }

        catch (Exception exception) {
            Aether.LOGGER.error("SOMETHING WENT WRONG");
            Aether.LOGGER.error(String.valueOf(exception));
            return;
        }

        entity.spawnInit();
        entity.moveTo(x, y, z, 0.0F, 0.0F);
        world.entityJoinedWorld(entity);
    }

}
