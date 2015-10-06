package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.base.tile;

public abstract class TileModel <Type extends Enum> {

    protected int angle;
    protected Type type;

    public TileModel(int angle, Type type) {
        this.angle = angle;
        this.type = type;
    }

    public int getAngle() {
        return angle;
    }
    public Type getType() {
        return type;
    }
}
