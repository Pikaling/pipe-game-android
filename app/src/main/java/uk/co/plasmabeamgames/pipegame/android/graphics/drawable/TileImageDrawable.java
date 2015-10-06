package uk.co.plasmabeamgames.pipegame.android.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import java.io.InputStream;

public class TileImageDrawable extends BitmapDrawable {

    private Matrix matrix;
    private float pivotX;
    private float pivotY;
    private Paint paint;

    public TileImageDrawable(Resources res, Bitmap bitmap) {
        super(res, bitmap);
        matrix = new Matrix();
        paint = new Paint();
        pivotX = getIntrinsicWidth() / 2;
        pivotY = getIntrinsicHeight() / 2;
    }

    public TileImageDrawable(Resources res, String filepath) {
        super(res, filepath);
        matrix = new Matrix();
    }

    public TileImageDrawable(Resources res, InputStream is) {
        super(res, is);
        matrix = new Matrix();
    }

    public void setAngle(int angle) {
        matrix.postRotate(angle, pivotX, pivotY);
    }

    public void setScaleX(float scaleX) {
        matrix.postScale(scaleX, 1f, pivotX, pivotY);
    }

    @Override
    public void setAlpha(int alpha) {
        int oldAlpha = paint.getAlpha();
        if (alpha != oldAlpha) {
            paint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.getBitmap(), matrix, paint);
    }


}
