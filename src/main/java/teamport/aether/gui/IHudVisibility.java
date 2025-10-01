package teamport.aether.gui;

/**
 * Implement if you effect hearts to change when effect is applied
 * or player screen to be affected.
 * */
public interface IHudVisibility {
    default String getPath(){
        return "minecraft:gui/hud/heart/";
    }
}
