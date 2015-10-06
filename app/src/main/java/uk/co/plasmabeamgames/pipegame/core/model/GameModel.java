package uk.co.plasmabeamgames.pipegame.core.model;

import uk.co.plasmabeamgames.pipegame.core.GameConfig;
import uk.co.plasmabeamgames.pipegame.core.message.MessagingCenter;
import uk.co.plasmabeamgames.pipegame.core.presenter.GamePresenter;

public final class GameModel implements MessagingCenter.GameEventMessageSubscriber {

    private static GameModel instance;

    private int currentLevel;
    private int score = 0;
    private int crossTilesFilled = 0;
    private boolean isLevelComplete;
    private boolean isGameOver;
    private transient GamePresenter presenter;

    private GameModel() {
        MessagingCenter.getInstance().subscribeToGameEventMessages(this);
    }

    public static GameModel getInstance() {
        if (instance == null) {
            instance = new GameModel();
        }
        return instance;
    }

    public void setPresenter(GamePresenter presenter) {
        this.presenter = presenter;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isLevelComplete() {
        return isLevelComplete;
    }

    public void setIsLevelComplete(boolean isLevelComplete) {
        this.isLevelComplete = isLevelComplete;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setIsGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public void reset() {
        currentLevel = 0;
        score = 0;
        isLevelComplete = false;
        isGameOver = false;
    }

    public LevelModel createLevel() {
        isLevelComplete = false;
        crossTilesFilled = 0;
        LevelModel levelModel = new LevelModel(GameConfig.getLiquidDelay(currentLevel), GameConfig.getLiquidSpeed(currentLevel));
        currentLevel++;
        return levelModel;
    }

    @Override
    public void onGameEventMessageReceived(GameConfig.GameEvent gameEvent) {
        if (gameEvent.equals(GameConfig.GameEvent.CROSS_TILE_FILLED)) {
            crossTilesFilled += 1;
            if (crossTilesFilled == 5) {
                score += gameEvent.getValue();
            }
        } else {
            score += gameEvent.getValue();
            if (score < 0) {
                score = 0;
            }
        }
        presenter.updateScore(score);
    }
}