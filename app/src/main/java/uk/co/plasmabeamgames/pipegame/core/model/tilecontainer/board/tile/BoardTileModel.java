package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.base.tile.TileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;

public abstract class BoardTileModel extends TileModel<BoardTileType> {

    protected boolean isFillable = true;
    protected boolean isFilling;
    protected boolean isFull;
    protected GridModel.GridPosition gridPosition;
    protected GridModel.GridPosition nextGridPosition;
    protected BoardTileModel spillModel;

    protected BoardTileModel(int angle, BoardTileType boardTileType) {
        super(angle, boardTileType);
    }

    public boolean isFillable() {
        return isFillable;
    }

    public abstract void setFillable(GridModel.GridPosition gridPosition);


    public boolean isFilling() {
        return isFilling;
    }

    public void setFilling(boolean isFilling) {
        this.isFilling = isFilling;
        if (isFilling) {
            isFillable = false;
        }
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull() {
        this.isFull = true;
    }

    public GridModel.GridPosition getGridPosition() {
        return gridPosition;
    }

    public void setGridPosition(GridModel.GridPosition gridPosition) {
        this.gridPosition = gridPosition;
    }

    public GridModel.GridPosition getNextGridPosition() {
        return nextGridPosition;
    }

    public BoardTileModel getSpillModel() {
        return spillModel;
    }

    public void rotate() {
        angle += 90;
        if (angle > 270) {
            angle = 0;
        }
    }

    protected int calculateSpillAngle(GridModel.GridPosition gridPosition) {
        if (gridPosition.row() == 1)
            return 180;
        if (gridPosition.row() == -1)
            return  0;
        if (gridPosition.col() == 1)
            return 90;
        if (gridPosition.col() == -1)
            return 270;
        return -1;
    }
}