package uk.co.plasmabeamgames.pipegame.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TableLayout;

import uk.co.plasmabeamgames.pipegame.android.view.TileImageView;
import uk.co.plasmabeamgames.pipegame.core.GameConfig;

public class GridTableLayout extends TableLayout {

    private static final String TAG = GridTableLayout.class.getCanonicalName();
    private Rect bounds = new Rect();

    public GridTableLayout(Context context) {
        super(context);
    }

    public GridTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        getLocalVisibleRect(bounds);
        if (!bounds.isEmpty()) {
            int maxTileWidth = bounds.width() / GameConfig.getGridNumCols();
            int maxTileHeight = bounds.height() / GameConfig.getGridNumRows();
            int tileSize = Math.min(maxTileWidth, maxTileHeight);
            TileImageView.setSize(tileSize);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
