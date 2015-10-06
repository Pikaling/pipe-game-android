package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;

public class EndTileModel extends TerminusTileModel {

    public EndTileModel(int angle) {
        super(angle, BoardTileType.END_PIPE);
    }

    @Override
    public void setFillable(GridModel.GridPosition gridPosition) {
        isFillable = true;
    }
}