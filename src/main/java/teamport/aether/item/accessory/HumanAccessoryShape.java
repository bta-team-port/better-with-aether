package teamport.aether.item.accessory;

public enum HumanAccessoryShape implements IAccessoryShape {
    GLOVES(),
    CAPE(),
    TRINKET_1(),
    TRINKET_2();

    HumanAccessoryShape() {
    }

    public int getSlotIndex() {
        return this.ordinal();
    }

}
