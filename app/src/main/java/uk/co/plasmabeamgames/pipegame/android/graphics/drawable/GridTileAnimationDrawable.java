package uk.co.plasmabeamgames.pipegame.android.graphics.drawable;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

import java.util.List;

import uk.co.plasmabeamgames.pipegame.android.graphics.AnimationFrames;

public class GridTileAnimationDrawable extends AnimationDrawable {

    private int currentFrame = 0;
    private boolean isRunning;
    private int frameLength;
    private OnAnimationCompleteListener onAnimationCompleteListener;

    public interface OnAnimationCompleteListener {
        void onAnimationComplete();
    }

    public GridTileAnimationDrawable(AnimationFrames frames, int frameLength) {
        this.frameLength = frameLength;
        addFrames(frames.getTileImageDrawables());
    }

    public void setFrameLength(int frameLength) {
        this.frameLength = frameLength;
    }

    public void setOnAnimationCompleteListener(OnAnimationCompleteListener onAnimationCompleteListener) {
        this.onAnimationCompleteListener = onAnimationCompleteListener;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    @Override
    public void start() {
        if (!isRunning) {
            run();
        }
    }

    @Override
    public void stop() {
        if (isRunning) {
            unscheduleSelf(this);
            isRunning = false;
        }
    }

    @Override
    public void run() {
        int n = getNumberOfFrames();
        unscheduleSelf(this);
        if (++currentFrame >= n) {
            if (isRunning) {
                isRunning = false;
                onAnimationCompleteListener.onAnimationComplete();
            }
        } else {
            isRunning = true;
            selectDrawable(currentFrame);
            scheduleSelf(this, SystemClock.uptimeMillis() + frameLength);
        }
    }

    private void addFrames(List<TileImageDrawable> drawables) {
        for (Drawable drawable : drawables) {
            addFrame(drawable, frameLength);
        }
    }
}