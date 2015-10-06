package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;

public class CrossTileModel extends BoardTileModel {

    private StraightTileModel vertical;
    private StraightTileModel horizontal;

    public CrossTileModel(int angle) {
        super(angle, BoardTileType.CROSS_PIPE);
        this.vertical = new StraightTileModel(0);
        this.horizontal = new StraightTileModel(90);
    }

    @Override
    public void setGridPosition(GridModel.GridPosition gridPosition) {
        this.gridPosition = gridPosition;
        vertical.gridPosition = gridPosition;
        horizontal.gridPosition = gridPosition;
    }

    @Override
    public void setFillable(GridModel.GridPosition gridPosition) {
        if (isFillable()) {
            if (Math.abs(gridPosition.row()) == 1) {
                vertical.setFillable(gridPosition);
                if (vertical.isFillable()) {
                    vertical.setFilling(true);
                    angle = vertical.getAngle();
                    if (horizontal.isFull())
                        type = BoardTileType.CROSS_PIPE_HALF_FULL;
                } else {
                    isFillable = false;
                    spillModel = vertical.spillModel;
                }
                nextGridPosition = vertical.nextGridPosition;
            } else {
                horizontal.setFillable(gridPosition);
                if (horizontal.isFillable()) {
                    this.angle = horizontal.getAngle();
                    if (vertical.isFull())
                        type = BoardTileType.CROSS_PIPE_HALF_FULL;
                } else {
                    isFillable = false;
                    spillModel = horizontal.spillModel;
                }
                nextGridPosition = horizontal.nextGridPosition;
            }
        }
    }

    @Override
    public void setFilling(boolean isFilling) {
        if (vertical.isFilling()) {
            vertical.setFilling(false);
            vertical.setFull();
        }
        if (horizontal.isFilling()) {
            horizontal.setFilling(false);
            horizontal.setFull();
        }
    }

    @Override
    public boolean isFilling() {
        return horizontal.isFilling() || vertical.isFilling();
    }

    public void setFull() {
        this.isFull = true;
    }

    @Override
    public GridModel.GridPosition getNextGridPosition() {
        return nextGridPosition;
    }
}