package uk.co.plasmabeamgames.pipegame.core.presenter;

import android.util.Log;

import com.google.common.base.Optional;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import uk.co.plasmabeamgames.pipegame.core.model.LevelModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileModel;

@Singleton
public class LevelPresenter implements Presenter {

    private static final String TAG = LevelPresenter.class.getCanonicalName();

    private BoardPresenter boardPresenter;
    private ConveyorPresenter conveyorPresenter;
    private LevelModel levelModel;
    private ScheduledFuture scheduledFuture;
    private ScheduledExecutorService scheduledExecutorService;
    private boolean isLiquidFlowing;
    private long timeLeftBeforeLiquidStart;

    @Inject
    public LevelPresenter(BoardPresenter boardPresenter, ConveyorPresenter conveyorPresenter) {
        this.boardPresenter = boardPresenter;
        this.conveyorPresenter = conveyorPresenter;
    }

    @Override
    public void startPresenting() {
        boardPresenter.startPresenting();
        conveyorPresenter.startPresenting();
    }

    @Override
    public void stopPresenting() {
        boardPresenter.stopPresenting();
        conveyorPresenter.stopPresenting();
    }

    public void initialiseLevelModels(final LevelModel levelModel) {
        this.levelModel = levelModel;
        boardPresenter.setFrameLength(levelModel.getFrameLength());
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduleLiquidStart(levelModel.getLiquidDelay());
        boardPresenter.initialiseBoardModel();
        conveyorPresenter.initialiseConveyorModel();
    }

    public void placeTile(GridModel.GridPosition gridPosition) {
            ConveyorTileModel nextTile = conveyorPresenter.getConveyorModel().getNextTile();
            Optional<BoardTileModel> boardTileModelOptional = boardPresenter.getBoardModel().placeTileModel(gridPosition, nextTile);
            if (boardTileModelOptional.isPresent()) {
                boardPresenter.placeTile(boardTileModelOptional.get());
                conveyorPresenter.moveConveyor();
            }
    }

    public void increaseLiquidSpeed() {
        if (levelModel.increaseLiquidSpeed()) {
            if (!scheduledFuture.isDone() && !scheduledFuture.isCancelled()) {
                scheduledFuture.cancel(false);
                boardPresenter.startLiquid();
            }
            boardPresenter.setFrameLength(levelModel.getFrameLength());
        }
    }

    private void scheduleLiquidStart(long liquidDelay) {
        scheduledFuture = scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                isLiquidFlowing = true;
                boardPresenter.startLiquid();
            }
        }, liquidDelay, TimeUnit.MILLISECONDS);
    }

    public void pause() {
        if (!scheduledFuture.isDone() && !scheduledFuture.isCancelled()) {
            timeLeftBeforeLiquidStart = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
            scheduledFuture.cancel(false);
            Log.d(TAG, timeLeftBeforeLiquidStart + "");
        } else {
            boardPresenter.pause();
        }
    }

    public void resume() {
        if (!isLiquidFlowing && timeLeftBeforeLiquidStart > 0) {
            scheduleLiquidStart(timeLeftBeforeLiquidStart);
        } else {
            boardPresenter.resume();
        }
    }
}