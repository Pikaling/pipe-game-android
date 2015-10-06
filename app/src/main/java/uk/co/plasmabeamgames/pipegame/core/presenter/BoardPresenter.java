package uk.co.plasmabeamgames.pipegame.core.presenter;

import com.google.common.base.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import uk.co.plasmabeamgames.pipegame.core.GameConfig;
import uk.co.plasmabeamgames.pipegame.core.GameState;
import uk.co.plasmabeamgames.pipegame.core.converter.BoardTileModelConverter;
import uk.co.plasmabeamgames.pipegame.core.message.MessagingCenter;
import uk.co.plasmabeamgames.pipegame.core.model.GameModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.BoardModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.CrossTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.EndTileModel;
import uk.co.plasmabeamgames.pipegame.core.view.BoardView;
import uk.co.plasmabeamgames.pipegame.core.viewmodel.BoardTileViewModel;

@Singleton
public class BoardPresenter implements Presenter {

    private BoardModel boardModel;
    private BoardView boardView;
    private BoardTileModelConverter boardTileModelConverter;
    private int frameLength;

    @Inject
    public BoardPresenter(BoardModel boardModel, BoardTileModelConverter boardTileModelConverter) {
        this.boardModel = boardModel;
        this.boardTileModelConverter = boardTileModelConverter;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    @Override
    public void startPresenting() {
        boardView.drawGrid();

        BoardTileModel sourceTileModel = boardModel.getSourceTileModel();
        boardView.placeTile(boardTileModelConverter.convert(sourceTileModel));

        BoardTileModel sinkTileModel = boardModel.getSinkTileModel();
        boardView.placeTile(boardTileModelConverter.convert(sinkTileModel));
    }

    @Override
    public void stopPresenting() {
        GameState.getInstance().setBoardTileModels(boardModel.gridModel().getGrid());
    }

    public void initialiseBoardModel() {
        boardModel.gridModel().create();
        boardModel.placeSourceAndSink();
    }

    public void placeTile(BoardTileModel boardTileModel) {
        boardView.placeTile(boardTileModelConverter.convert(boardTileModel));
    }

    public void fillNextTile(GridModel.GridPosition gridPosition) {
        GameModel gameModel = GameModel.getInstance();
        if (!gameModel.isGameOver()) {
            // Previous tile just finished filling, so send a tile filled message
            MessagingCenter.getInstance().postGameEventMessage(GameConfig.GameEvent.TILE_FILLED);
            if (!gameModel.isLevelComplete()) {
                // Game is still in progress, so fill the next tile
                Optional<BoardTileModel> nextTileModelOptional;
                nextTileModelOptional = boardModel.fillNextTileModel(gridPosition);
                if (nextTileModelOptional.isPresent()) {
                    BoardTileModel nextTileModel = nextTileModelOptional.get();
                    if (!nextTileModel.isFillable()) {
                        gameModel.setIsGameOver(true);
                    } else {
                        if (nextTileModel instanceof EndTileModel) {
                            gameModel.setIsLevelComplete(true);
                        }
                    }
                    // Fill tile, even if it's not fillable (in this case it will spill)
                    fillTile(nextTileModel);
                }
            }
        }
    }

    public void startLiquid() {
        BoardTileModel tileToFill = boardModel.startLiquid();
        fillTile(tileToFill);
    }

    public void setFrameLength(int frameLength) {
        this.frameLength = frameLength;
        boardView.setFrameLength(frameLength);
    }

    public void pause() {
        boardView.pause();
    }

    public void resume() {
        boardView.resume();
    }

    private void fillTile(BoardTileModel boardTileModel) {
        BoardTileViewModel boardTileViewModel = boardTileModelConverter.convert(boardTileModel);
        boardView.fillTile(boardTileViewModel, frameLength);
        if (boardTileModel instanceof CrossTileModel && boardTileModel.isFull() && !boardTileModel.isFillable()) {
            MessagingCenter.getInstance().postGameEventMessage(GameConfig.GameEvent.CROSS_TILE_FILLED);
        }
        boardTileModel.setFilling(true);
    }
}