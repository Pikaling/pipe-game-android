package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;

public class EmptyTileModel extends BoardTileModel {

    public EmptyTileModel(int angle) {
        super(angle, BoardTileType.EMPTY_TILE);
    }

    @Override
    public boolean isFillable() {
        return false;
    }

    @Override
    public void setFillable(GridModel.GridPosition gridPosition) {
        isFillable = false;
        setAngle(gridPosition);
    }

    @Override
    public GridModel.GridPosition getNextGridPosition() {
        return null;
    }

    private void setAngle(GridModel.GridPosition gridPosition) {
        int row = gridPosition.row();
        int col = gridPosition.col();
        if (row == 1) {
            angle = 180;
        } else if (row == -1) {
            angle = 0;
        } else if (col == 1) {
            angle = 90;
        } else if (col == -1) {
            angle = 270;
        }
    }
}