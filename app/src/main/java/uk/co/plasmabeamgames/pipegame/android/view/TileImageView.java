package uk.co.plasmabeamgames.pipegame.android.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import uk.co.plasmabeamgames.pipegame.R;
import uk.co.plasmabeamgames.pipegame.android.graphics.drawable.GridTileAnimationDrawable;

public class TileImageView extends ImageView {

    private static final String TAG = TileImageView.class.getCanonicalName();
    private static int size = 0;
    private OnAnimationCompleteListener onAnimationCompleteListener;

    public TileImageView(Context context) {
        super(context);
        setStateAndBackground();
    }

    public TileImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStateAndBackground();
    }

    public TileImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setStateAndBackground();
    }

    public interface OnAnimationCompleteListener {
        void onAnimationComplete(TileImageView tileImageView);
    }

    public static void setSize(int size) {
        TileImageView.size = size;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable instanceof GridTileAnimationDrawable) {
            GridTileAnimationDrawable gridTileAnimationDrawable = (GridTileAnimationDrawable) drawable;
            gridTileAnimationDrawable.setOnAnimationCompleteListener(new PipeAnimationDrawableAnimationCompleteListener());
        }
    }

    public void addSpillDrawable(GridTileAnimationDrawable gridTileAnimationDrawable) {
        Drawable currentDrawable = getDrawable();
        if (currentDrawable != null) {
            Drawable[] drawables = new Drawable[2];
            drawables[0] = currentDrawable;
            drawables[1] = gridTileAnimationDrawable;
            LayerDrawable layerDrawable = new LayerDrawable(drawables);
            setImageDrawable(layerDrawable);
            gridTileAnimationDrawable.setOnAnimationCompleteListener(new PipeAnimationDrawableAnimationCompleteListener());
        } else {
            setImageDrawable(gridTileAnimationDrawable);
        }
    }

    public void setOnAnimationCompleteListener(OnAnimationCompleteListener onAnimationCompleteListener) {
        this.onAnimationCompleteListener = onAnimationCompleteListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (size > 0) {
            setMeasuredDimension(size, size);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void setStateAndBackground() {
        setScaleType(ScaleType.CENTER_CROP);
        setFocusable(true);
        setClickable(true);
        setBackgroundResource(R.drawable.tile);
    }

    class PipeAnimationDrawableAnimationCompleteListener implements GridTileAnimationDrawable.OnAnimationCompleteListener {
        @Override
        public void onAnimationComplete() {
            onAnimationCompleteListener.onAnimationComplete(TileImageView.this);
        }
    }

}