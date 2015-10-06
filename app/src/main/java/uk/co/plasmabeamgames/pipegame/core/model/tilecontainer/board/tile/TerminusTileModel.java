package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;

public abstract class TerminusTileModel extends BoardTileModel {

    protected TerminusTileModel(int angle, BoardTileType boardTileType) {
        super(angle, boardTileType);
    }

    @Override
    public void setGridPosition(GridModel.GridPosition gridPosition) {
        super.setGridPosition(gridPosition);
        setNextGridPosition();
    }

    @Override
    public void rotate() {
        super.rotate();
        setNextGridPosition();
    }

    private void setNextGridPosition() {
        switch (getAngle()) {
            case 0:
                nextGridPosition = new GridModel.GridPosition(-1, 0);
                break;
            case 90:
                nextGridPosition = new GridModel.GridPosition(0, 1);
                break;
            case 180:
                nextGridPosition = new GridModel.GridPosition(1, 0);
                break;
            case 270:
                nextGridPosition = new GridModel.GridPosition(0, -1);
                break;
        }
        nextGridPosition = nextGridPosition.add(gridPosition);
    }
}
