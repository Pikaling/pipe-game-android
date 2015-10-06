package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;

public class StraightTileModel extends BoardTileModel {

    public StraightTileModel(int angle) {
        super(angle, BoardTileType.STRAIGHT_PIPE);
    }

    @Override
    public void setFillable(GridModel.GridPosition gridPosition) {
        if (isFillable) {
            int row = gridPosition.row();
            int col = gridPosition.col();
            int angle = getAngle();
            if (((angle == 0 || angle == 180) && Math.abs(row) == 1) ||
                ((angle == 90 || angle == 270) && Math.abs(col) == 1)) {
                nextGridPosition = this.gridPosition.add(gridPosition.inverse());
            } else {
                isFillable = false;
            }
        }
        if (isFillable) {
            setAngle(gridPosition);
        } else {
            spillModel = new EmptyTileModel(calculateSpillAngle(gridPosition));
        }
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
