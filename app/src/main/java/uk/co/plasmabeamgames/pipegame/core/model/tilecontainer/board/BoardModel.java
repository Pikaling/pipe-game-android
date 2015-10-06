package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board;

import com.google.common.base.Optional;

import java.util.Random;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.core.GameConfig;
import uk.co.plasmabeamgames.pipegame.core.message.MessagingCenter;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileModelFactory;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileType;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.EmptyTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.EndTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.StartTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileModel;

public class BoardModel {

    private BoardTileModelFactory boardTileModelFactory;
    private Random random;
    private int numRows;
    private int numCols;
    private GridModel gridModel;
    private BoardTileModel sourceTileModel;
    private BoardTileModel sinkTileModel;

    @Inject
    public BoardModel(BoardTileModelFactory boardTileModelFactory, Random random) {
        this.boardTileModelFactory = boardTileModelFactory;
        this.random = random;
        numRows = GameConfig.getGridNumRows();
        numCols = GameConfig.getGridNumCols();
        gridModel = new GridModel(boardTileModelFactory, numRows, numCols);
    }

    public GridModel gridModel() {
        return gridModel;
    }

    public BoardTileModel getSourceTileModel() {
        return sourceTileModel;
    }

    public BoardTileModel getSinkTileModel() {
        return sinkTileModel;
    }

    public void placeSourceAndSink() {
        GridModel.GridPosition gridPosition = new GridModel.GridPosition(random.nextInt(numRows), random.nextInt(numCols));
        sourceTileModel = boardTileModelFactory.createTileModel(BoardTileType.START_PIPE);
        boolean placedSource = placeTileModel(gridPosition, sourceTileModel);

        boolean placedSink;
        do {
            gridPosition = new GridModel.GridPosition(random.nextInt(numRows), random.nextInt(numCols));
            sinkTileModel = boardTileModelFactory.createTileModel(BoardTileType.END_PIPE);
            placedSink = placeTileModel(gridPosition, sinkTileModel);
        } while (!placedSink);

        try {
            rotateTile(sourceTileModel);
            rotateTile(sinkTileModel);
        } catch (GridPositionOutOfBoundsException e) {
            // Do nothing
            // There may be exceptions due to the tile being adjacent
            // to an edge and facing towards it, that's why we're rotating it.
        }
    }

    public boolean placeTileModel(GridModel.GridPosition gridPosition, BoardTileModel boardTileModel) {
        if (cannotPlaceTileAtPosition(gridPosition)) {
            return false;
        }
        gridModel.set(gridPosition, boardTileModel);
        return true;
    }

    public Optional<BoardTileModel> placeTileModel(GridModel.GridPosition gridPosition, ConveyorTileModel conveyorTileModel) {
        if (cannotPlaceTileAtPosition(gridPosition)) return Optional.absent();
        BoardTileModel boardTileModel = boardTileModelFactory.createTileModel(conveyorTileModel);
        gridModel.set(gridPosition, boardTileModel);
        return Optional.of(boardTileModel);
    }

    public BoardTileModel startLiquid() {
        return sourceTileModel;
    }

    public Optional<BoardTileModel> fillNextTileModel(GridModel.GridPosition gridPosition) {
        BoardTileModel boardTileModel, nextTileModel;
        Optional<BoardTileModel> boardTileModelOptional = Optional.absent();
        try {
            boardTileModel = gridModel.get(gridPosition);
            boardTileModel.setFilling(false);
            boardTileModel.setFull();
            GridModel.GridPosition nextGridPosition = boardTileModel.getNextGridPosition();
            nextTileModel = gridModel.get(nextGridPosition);
            nextTileModel.setFillable(gridPosition.subtract(nextGridPosition));
            boardTileModelOptional = Optional.of(nextTileModel);

        } catch (GridPositionOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        return boardTileModelOptional;
    }

    private boolean cannotPlaceTileAtPosition(GridModel.GridPosition gridPosition) {
        BoardTileModel tileAtPosition;
        try {
            tileAtPosition = gridModel.get(gridPosition);
            if (tileAtPosition instanceof StartTileModel || tileAtPosition instanceof EndTileModel || tileAtPosition.isFull()) {
                return true;
            }
            if (!(tileAtPosition instanceof EmptyTileModel)) {
                MessagingCenter.getInstance().postGameEventMessage(GameConfig.GameEvent.TILE_DESTROYED);
            }
            return false;
        } catch (GridPositionOutOfBoundsException e) {
            return true;
        }
    }

    /**
     * Rotates the tile if necessary
     *
     * @param tileToRotate         the tile to rotate
     */
    private void rotateTile(BoardTileModel tileToRotate) throws GridPositionOutOfBoundsException {
        GridModel.GridPosition gridPosition = tileToRotate.getNextGridPosition();
        while (cannotPlaceTileAtPosition(gridPosition)) {
            tileToRotate.rotate();
            gridPosition = tileToRotate.getNextGridPosition();
        }
    }
}