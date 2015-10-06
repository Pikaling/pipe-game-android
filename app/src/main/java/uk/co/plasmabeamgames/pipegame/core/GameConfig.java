package uk.co.plasmabeamgames.pipegame.core;

public class GameConfig {

    private static final int GRID_NUM_ROWS = 7;
    private static final int GRID_NUM_COLS = 9;
    private static final int CONVEYOR_NUM_TILES = 5;
    private static final String SAVE_FILE_NAME = "pipe-game-save";

    private GameConfig() {}

    public static int getGridNumRows() {
        return GRID_NUM_ROWS;
    }

    public static int getGridNumCols() {
        return GRID_NUM_COLS;
    }

    public static int getConveyorNumTiles() {
        return CONVEYOR_NUM_TILES;
    }

    public static int getLiquidDelay(int level) {
        return Math.round((0.2f * level * level) - (225 * level) + 20225);
    }

    public static int getLiquidSpeed(int level) {
        return Math.round((0.08f * level * level) - (10.25f * level) + 210);
    }

    public static String getSaveFileName() {
        return SAVE_FILE_NAME;
    }

    public enum GameEvent {
        TILE_FILLED(100),
        TILE_DESTROYED(-50),
        CROSS_TILE_FILLED(5000),
        LEVEL_COMPLETE(0);

        private final int value;

        GameEvent(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
