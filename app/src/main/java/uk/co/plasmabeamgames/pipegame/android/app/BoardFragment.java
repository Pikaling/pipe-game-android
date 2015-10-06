package uk.co.plasmabeamgames.pipegame.android.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import javax.inject.Inject;
import javax.inject.Singleton;

import uk.co.plasmabeamgames.pipegame.R;
import uk.co.plasmabeamgames.pipegame.android.graphics.AnimationFrames;
import uk.co.plasmabeamgames.pipegame.android.graphics.drawable.GridTileAnimationDrawable;
import uk.co.plasmabeamgames.pipegame.android.view.TileImageView;
import uk.co.plasmabeamgames.pipegame.core.GameConfig;
import uk.co.plasmabeamgames.pipegame.core.GameState;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;
import uk.co.plasmabeamgames.pipegame.core.presenter.BoardPresenter;
import uk.co.plasmabeamgames.pipegame.core.presenter.GamePresenter;
import uk.co.plasmabeamgames.pipegame.core.presenter.LevelPresenter;
import uk.co.plasmabeamgames.pipegame.core.view.BoardView;
import uk.co.plasmabeamgames.pipegame.core.viewmodel.BoardTileViewModel;

@Singleton
public class BoardFragment extends TileContainerFragment implements BoardView {

    private static final String TAG = BoardFragment.class.getCanonicalName();

    @Inject
    BoardPresenter boardPresenter;
    @Inject
    LevelPresenter levelPresenter;
    @Inject
    GamePresenter gamePresenter;
    private TableLayout grid;
    private GridTileAnimationDrawable tileCurrentlyFilling;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        grid = (TableLayout) inflater.inflate(R.layout.fragment_board, container, false);
        return grid;
    }

    @Override
    public void onStart() {
        super.onStart();
        boardPresenter.setBoardView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (tileCurrentlyFilling != null) {
            tileCurrentlyFilling.stop();
            GameState.getInstance().setCurrentFrameForAnimatingTile(tileCurrentlyFilling.getCurrentFrame());
        }
    }

    @Override
    public void drawGrid() {
        grid.removeAllViews();
        TableRow tableRow;
        TileImageView tileImageView;
        for (int rowCounter = 0; rowCounter < GameConfig.getGridNumRows(); rowCounter++) {
            tableRow = new TableRow(getActivity());
            grid.addView(tableRow);
            for (int colCounter = 0; colCounter < GameConfig.getGridNumCols(); colCounter++) {
                tileImageView = new TileImageView(getActivity());
                tileImageView.setOnClickListener(new TileImageViewClickListener());
                tileImageView.setOnAnimationCompleteListener(new TileImageViewAnimationCompleteListener());
                tableRow.addView(tileImageView);
            }
        }
    }

    @Override
    public void placeTile(BoardTileViewModel boardTileViewModel) {
        TileImageView tileImageView = getTileImageView(boardTileViewModel.getGridPosition());
        setTileImageDrawable(boardTileViewModel, tileImageView);
    }

    @Override
    public void fillTile(final BoardTileViewModel boardTileViewModel, final int frameLength) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fillTileOnUiThread(boardTileViewModel, frameLength);
            }
        });
    }

    @Override
    public void setFrameLength(int frameLength) {
        if (this.tileCurrentlyFilling != null)
            this.tileCurrentlyFilling.setFrameLength(frameLength);
    }

    @Override
    public void pause() {
        setTileAlpha(0);
        if (this.tileCurrentlyFilling != null)
            this.tileCurrentlyFilling.stop();
    }

    @Override
    public void resume() {
        setTileAlpha(255);
        if (this.tileCurrentlyFilling != null)
            this.tileCurrentlyFilling.start();
    }

    private TileImageView getTileImageView(GridModel.GridPosition gridPosition) {
        TableRow tableRow = (TableRow) grid.getChildAt(gridPosition.row());
        return (TileImageView) tableRow.getChildAt(gridPosition.col());
    }

    private GridModel.GridPosition getGridPosition(TileImageView tileImageView) {
        return getPositionOfTile(tileImageView);
    }

    private GridModel.GridPosition getPositionOfTile(TileImageView tileImageView) {
        for (int rowCounter = 0; rowCounter < grid.getChildCount(); rowCounter++) {
            TableRow tableRow = (TableRow) grid.getChildAt(rowCounter);
            for (int colCounter = 0; colCounter < tableRow.getChildCount(); colCounter++) {
                TileImageView currentTile = (TileImageView) tableRow.getChildAt(colCounter);
                if (currentTile.equals(tileImageView)) {
                    return new GridModel.GridPosition(rowCounter, colCounter);
                }
            }
        }
        return null;
    }

    private void fillTileOnUiThread(BoardTileViewModel boardTileViewModel, int frameDuration) {
        TileImageView tileImageView = getTileImageView(boardTileViewModel.getGridPosition());
        AnimationFrames frames = new AnimationFrames(getResources(), boardTileViewModel.getAnimationXmlId(), boardTileViewModel.getAngle(), boardTileViewModel.getScaleX());
        GridTileAnimationDrawable gridTileAnimationDrawable = new GridTileAnimationDrawable(frames, frameDuration);
        if (boardTileViewModel.isFillable()) {
            tileImageView.setImageDrawable(gridTileAnimationDrawable);
        } else {
            tileImageView.addSpillDrawable(gridTileAnimationDrawable);
        }
        tileCurrentlyFilling = gridTileAnimationDrawable;
        gridTileAnimationDrawable.start();
    }

    private void setTileAlpha(int alpha) {
        TableRow tableRow;
        TileImageView tileImageView;
        for (int rowCounter = 0; rowCounter < GameConfig.getGridNumRows(); rowCounter++) {
            tableRow = (TableRow) grid.getChildAt(rowCounter);
            for (int colCounter = 0; colCounter < GameConfig.getGridNumCols(); colCounter++) {
                tileImageView = (TileImageView) tableRow.getChildAt(colCounter);
                tileImageView.setImageAlpha(alpha);
            }
        }
    }

    class TileImageViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            GridModel.GridPosition gridPosition = getGridPosition((TileImageView) view);
            levelPresenter.placeTile(gridPosition);
        }
    }

    class TileImageViewAnimationCompleteListener implements TileImageView.OnAnimationCompleteListener {
        @Override
        public void onAnimationComplete(TileImageView tileImageView) {
            GridModel.GridPosition gridPosition = getGridPosition(tileImageView);
            gamePresenter.checkGameState();
            boardPresenter.fillNextTile(gridPosition);
        }
    }
}