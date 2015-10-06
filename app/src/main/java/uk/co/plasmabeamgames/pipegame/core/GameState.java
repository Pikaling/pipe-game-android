package uk.co.plasmabeamgames.pipegame.core;

import java.util.LinkedList;

import uk.co.plasmabeamgames.pipegame.android.graphics.drawable.GridTileAnimationDrawable;
import uk.co.plasmabeamgames.pipegame.core.model.GameModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileModel;

public class GameState {

    private static GameState instance;
    private GameModel gameModel;
    private LinkedList<ConveyorTileModel> conveyor;
    private BoardTileModel[][] boardTileModels;
    private int currentFrameForAnimatingTile;

    private GameState() {
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public LinkedList<ConveyorTileModel> getConveyor() {
        return conveyor;
    }

    public void setConveyor(LinkedList<ConveyorTileModel> conveyor) {
        this.conveyor = conveyor;
    }

    public BoardTileModel[][] getBoardTileModels() {
        return boardTileModels;
    }

    public void setBoardTileModels(BoardTileModel[][] boardTileModels) {
        this.boardTileModels = boardTileModels;
    }

    public int getCurrentFrameForAnimatingTile() {
        return currentFrameForAnimatingTile;
    }

    public void setCurrentFrameForAnimatingTile(int currentFrameForAnimatingTile) {
        this.currentFrameForAnimatingTile = currentFrameForAnimatingTile;
    }
}
