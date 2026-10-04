package teamport.aether.achievements;

import net.minecraft.client.render.texture.stitcher.IconCoordinate;

public class SubIcon extends IconCoordinate {
    private final IconCoordinate source;
    private final int dx;
    private final int dy;
    private final int height;
    private final int width;

    public SubIcon(IconCoordinate source, int dx, int dy, int width, int height) {
        super(source.parentAtlas, source.namespaceId);
        this.source = source;
        this.dx = dx;
        this.dy = dy;
        this.width = width;
        this.height = height;
        sync();
    }

    private void sync() {
        this.setDimension(this.sw(), this.sh());
        this.setPosition(this.source.iconX + dx, this.source.iconY + dy);
        this.cacheUVs();
    }

    private int sw() {
        return this.width > 0 ? this.width : this.source.width / 2;
    }

    private int sh() {
        return this.height > 0 ? this.height : this.source.height / 2;
    }

    @Override
    public double getIconUMin() {
        return (this.source.iconX + this.dx) * this.parentAtlas.getInverseWidth();
    }

    @Override
    public double getIconUMax() {
        return (this.source.iconX + this.dx + this.sw()) * this.parentAtlas.getInverseWidth();
    }

    @Override
    public double getIconVMin() {
        return (this.source.iconY + this.dy) * this.parentAtlas.getInverseHeight();
    }

    @Override
    public double getIconVMax() {
        return (this.source.iconY + this.dy + this.sh()) * this.parentAtlas.getInverseHeight();
    }

    @Override
    public double getIconUSize() {
        return this.sw() * this.parentAtlas.getInverseWidth();
    }

    @Override
    public double getIconVSize() {
        return this.sh() * this.parentAtlas.getInverseHeight();
    }

    @Override
    public double getSubIconU(double o) {
        return this.getIconUMin() + this.getIconUSize() * o;
    }

    @Override
    public double getSubIconV(double o) {
        return this.getIconVMin() + this.getIconVSize() * o;
    }
}
