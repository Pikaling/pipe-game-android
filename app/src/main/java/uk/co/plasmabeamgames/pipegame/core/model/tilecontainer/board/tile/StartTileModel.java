package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;

public class StartTileModel extends TerminusTileModel {

    public StartTileModel(int angle) {
        super(angle, BoardTileType.START_PIPE);
    }

    @Override
    public void setFillable(GridModel.GridPosition gridPosition) {
        if (!isFillable) {
            spillModel = new EmptyTileModel(calculateSpillAngle(gridPosition));
        }
    }
}