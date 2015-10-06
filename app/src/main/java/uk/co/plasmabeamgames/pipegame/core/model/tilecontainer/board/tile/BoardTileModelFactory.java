package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile;

import java.util.Random;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.base.tile.TileModelFactory;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileType;

import static uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileType.END_PIPE;
import static uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileType.START_PIPE;
import static uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileType.CORNER_PIPE;
import static uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileType.CROSS_PIPE;
import static uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileType.STRAIGHT_PIPE;

public class BoardTileModelFactory extends TileModelFactory {

    @Inject
    public BoardTileModelFactory(Random random) {
        super(random);
    }

    public BoardTileModel createTileModel() {
        return new EmptyTileModel(0);
    }

    public BoardTileModel createTileModel(BoardTileType boardTileType) {
        int angle = generateAngle();
        BoardTileModel boardTileModel = null;
        switch (boardTileType) {
            case START_PIPE:
                boardTileModel = new StartTileModel(angle);
                break;
            case END_PIPE:
                boardTileModel = new EndTileModel(angle);
                break;
        }
        return boardTileModel;
    }

    public BoardTileModel createTileModel(ConveyorTileModel conveyorTileModel) {
        ConveyorTileType conveyorTileType = conveyorTileModel.getType();
        int angle = conveyorTileModel.getAngle();
        BoardTileModel boardTileModel = null;
        switch (conveyorTileType) {
            case CORNER_PIPE:
                boardTileModel = new CornerTileModel(angle);
                break;
            case CROSS_PIPE:
                boardTileModel = new CrossTileModel(angle);
                break;
            case STRAIGHT_PIPE:
                boardTileModel = new StraightTileModel(angle);
                break;
        }
        return boardTileModel;
    }
}