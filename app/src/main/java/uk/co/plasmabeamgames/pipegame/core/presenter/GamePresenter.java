package uk.co.plasmabeamgames.pipegame.core.presenter;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;
import javax.inject.Singleton;

import uk.co.plasmabeamgames.pipegame.core.GameConfig;
import uk.co.plasmabeamgames.pipegame.core.GameState;
import uk.co.plasmabeamgames.pipegame.core.model.GameModel;
import uk.co.plasmabeamgames.pipegame.core.model.LevelModel;
import uk.co.plasmabeamgames.pipegame.core.view.GameView;

@Singleton
public class GamePresenter implements Presenter {

    private GameModel gameModel;
    private GameView gameView;
    private LevelPresenter levelPresenter;
    private LevelModel currentLevel;
    private Context context;

    @Inject
    public GamePresenter(LevelPresenter levelPresenter, Context context) {
        this.levelPresenter = levelPresenter;
        this.context = context;
        gameModel = GameModel.getInstance();
        gameModel.setPresenter(this);
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void startPresenting() {
//        if (loadGame()) {
//            //TODO create game objects from save data
//        } else {
            currentLevel = gameModel.createLevel();
            gameView.updateLevel(gameModel.getCurrentLevel());
            gameView.updateScore(gameModel.getScore());
            levelPresenter.initialiseLevelModels(currentLevel);
            levelPresenter.startPresenting();
//        }
    }

    @Override
    public void stopPresenting() {
        GameState.getInstance().setGameModel(gameModel);
        levelPresenter.stopPresenting();
        saveGame();
    }

    public void doubleLiquidSpeed() {
        levelPresenter.increaseLiquidSpeed();
    }

    public void startGame() {
        gameModel.reset();
        startPresenting();
    }

    //TODO this is a placeholder implementation
    public void exitGame() {
        startGame();
    }

    public void startNextLevel() {
        startPresenting();
    }

    public void pauseGame() {
        levelPresenter.pause();
    }

    public void resumeGame() {
        levelPresenter.resume();
    }

    public void updateScore(int score) {
        gameView.updateScore(score);
    }

    public void checkGameState() {
        if (gameModel.isGameOver()) {
            gameView.endGame();
        }
        if (gameModel.isLevelComplete()) {
            gameView.nextLevel();
        }
    }

    private void saveGame() {
        Gson gson = new Gson();
        String gameModelData = gson.toJson(GameState.getInstance().getGameModel());
        String conveyorData = gson.toJson(GameState.getInstance().getConveyor());
        String boardData = gson.toJson(GameState.getInstance().getBoardTileModels());
        String animationData = gson.toJson(GameState.getInstance().getCurrentFrameForAnimatingTile());

        String saveData = gson.toJson(GameState.getInstance());
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(GameConfig.getSaveFileName(), Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.write(saveData.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean loadGame() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(GameConfig.getSaveFileName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fileInputStream != null) {
            BufferedReader reader = new BufferedReader((new InputStreamReader(fileInputStream)));
            StringBuilder saveData = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    saveData.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Gson gson = new Gson();
            gson.fromJson(saveData.toString(), GameState.class);
            return true;
        }
        return false;
    }
}