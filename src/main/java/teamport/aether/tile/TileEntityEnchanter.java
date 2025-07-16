package teamport.aether.tile;


// TODO implement the class, this should be mostly a port from 7.2
public class TileEntityEnchanter extends AetherTileEntity {

    // not sure if I even want to rename them but fuck it.
    /// missing tick                    -> work
    /// missing canSmelt                -> canProcess
    /// missing smeltItem               -> processItem
    /// missing updateFurnace
    /// missing getBurnTimeFromItem
    ///  missing getCookProgressScaled  -> getProgressScale

    // this needs to be overwritten
    @Override
    public String getNameTranslationKey(){
        return "aether.container.enchanter.name";
    }
}
