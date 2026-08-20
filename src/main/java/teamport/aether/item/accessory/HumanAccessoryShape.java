package teamport.aether.item.accessory;

public enum HumanAccessoryShape implements IAccessoryShape {
    GLOVES(),
    CAPE(),
    TRINKET();

    HumanAccessoryShape() {
    }

    public int getSlotIndex() {
        return this.ordinal();
    }

}
