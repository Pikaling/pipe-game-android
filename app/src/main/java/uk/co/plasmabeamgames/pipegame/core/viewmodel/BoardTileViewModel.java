package uk.co.plasmabeamgames.pipegame.core.viewmodel;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;

public class BoardTileViewModel extends ConveyorTileViewModel {

    private int animationXmlId;
    private float scaleX;
    private boolean isFillable;
    private boolean isFilling;
    private boolean isFull;
    private GridModel.GridPosition gridPosition;

    public BoardTileViewModel(int angle, int imageId, int animationXmlId, float scaleX, boolean isFillable, boolean isFilling, boolean isFull, GridModel.GridPosition gridPosition) {
        super(angle, imageId);
        this.animationXmlId = animationXmlId;
        this.scaleX = scaleX;
        this.isFillable = isFillable;
        this.isFilling = isFilling;
        this.isFull = isFull;
        this.gridPosition = gridPosition;
    }

    public int getAnimationXmlId() {
        return animationXmlId;
    }

    public float getScaleX() {
        return scaleX;
    }

    public boolean isFillable() {
        return isFillable;
    }

    public boolean isFilling() {
        return isFilling;
    }

    public boolean isFull() {
        return isFull;
    }

    public GridModel.GridPosition getGridPosition() {
        return gridPosition;
    }
}
