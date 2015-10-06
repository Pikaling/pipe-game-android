package uk.co.plasmabeamgames.pipegame.core.model;

public class LevelModel {

    private final int liquidDelay;
    private int frameLength;
    private static final int MIN_FRAME_LENGTH = 10;

    public LevelModel(int liquidDelay, int frameLength) {
        this.liquidDelay = liquidDelay;
        this.frameLength = frameLength;
    }

    public int getLiquidDelay() {
        return liquidDelay;
    }

    public int getFrameLength() {
        return frameLength;
    }

    public boolean increaseLiquidSpeed() {
        if (frameLength == MIN_FRAME_LENGTH) {
            return false;
        }
        frameLength = frameLength / 2;
        if (frameLength < MIN_FRAME_LENGTH) {
            frameLength = MIN_FRAME_LENGTH;
        }
        return true;
    }
}