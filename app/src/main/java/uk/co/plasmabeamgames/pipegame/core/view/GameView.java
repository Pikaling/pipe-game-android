package uk.co.plasmabeamgames.pipegame.core.view;

public interface GameView {

    void updateLevel(int currentLevel);
    void updateScore(int currentScore);
    void endGame();
    void nextLevel();
}
