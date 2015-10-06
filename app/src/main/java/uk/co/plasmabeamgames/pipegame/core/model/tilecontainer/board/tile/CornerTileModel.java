package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;

public class CornerTileModel extends BoardTileModel {

    public CornerTileModel(int angle) {
        super(angle, BoardTileType.CORNER_PIPE);
    }

    @Override
    public void setFillable(GridModel.GridPosition gridPosition) {
        if (isFillable) {
            if (angle == 0) {
                isFillable = ((gridPosition.row() == -1) || (gridPosition.col() == 1));
            } else if (angle == 90) {
                isFillable = ((gridPosition.row() == 1) || (gridPosition.col() == 1));
            } else if (angle == 180) {
                isFillable = ((gridPosition.row() == 1) || (gridPosition.col() == -1));
            } else if (angle == 270) {
                isFillable = ((gridPosition.row() == -1) || (gridPosition.col() == -1));
            }
        }
        if (isFillable) {
            setNextGridPosition(gridPosition);
        } else {
            spillModel = new EmptyTileModel(calculateSpillAngle(gridPosition));
        }
    }

    private void setNextGridPosition(GridModel.GridPosition gridPosition) {
        if (gridPosition.row() == 1) {
            if (angle == 90) {
                nextGridPosition = this.gridPosition.add(new GridModel.GridPosition(0, 1));
            } else if (angle == 180) {
                nextGridPosition = this.gridPosition.add(new GridModel.GridPosition(0, -1));
            }
        } else if (gridPosition.row() == -1) {
            if (angle == 270) {
                nextGridPosition = this.gridPosition.add(new GridModel.GridPosition(0, -1));
            } else if (angle == 0) {
                nextGridPosition = this.gridPosition.add(new GridModel.GridPosition(0, 1));
            }
        } else if (gridPosition.col() == 1) {
            if (angle == 0) {
                nextGridPosition = this.gridPosition.add(new GridModel.GridPosition(-1, 0));
            } else if (angle == 90) {
                nextGridPosition = this.gridPosition.add(new GridModel.GridPosition(1, 0));
            }
        } else if (gridPosition.col() == -1) {
            if (angle == 180) {
                nextGridPosition = this.gridPosition.add(new GridModel.GridPosition(1, 0));
            } else if (angle == 270) {
                nextGridPosition = this.gridPosition.add(new GridModel.GridPosition(-1, 0));
            }
        }
    }
}
