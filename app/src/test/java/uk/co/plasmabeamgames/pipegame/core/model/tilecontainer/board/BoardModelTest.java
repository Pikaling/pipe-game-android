package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board;

import com.google.common.base.Optional;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.core.GameConfig;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.base.BaseModelTest;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileModelFactory;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileType;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.EmptyTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.EndTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.StartTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileModelFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class BoardModelTest extends BaseModelTest {

    @Inject
    Random mockRandom;
    @Inject
    BoardTileModelFactory boardTileModelFactory;
    @Inject
    ConveyorTileModelFactory conveyorTileModelFactory;
    @Inject
    BoardModel boardModel;

    @Before
    public void setUp() {
        super.setUp();
        component().inject(this);
    }

    @Test
    public void whenBoardIsInitialised_thenGridShouldBeFilledWithEmptyTiles() throws GridPositionOutOfBoundsException {
        // When
        boardModel.gridModel().create();

        // Then
        for (int rowCounter = 0; rowCounter < GameConfig.getGridNumRows(); rowCounter++) {
            for (int colCounter = 0; colCounter < GameConfig.getGridNumCols(); colCounter++) {
                assertThat(boardModel.gridModel().get(new GridModel.GridPosition(rowCounter, colCounter))).isExactlyInstanceOf(EmptyTileModel.class);
            }
        }
    }

    @Test
    public void whenPlacingSourceAndSink_thenSourceAndSinkShouldBeAtCorrectAngle() {
        // Given
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(3, 4)     // position of source
                .thenReturn(1)        // angle of source
                .thenReturn(1, 2)     // position of sink
                .thenReturn(2);       // angle of sink

        // When
        boardModel.gridModel().create();
        boardModel.placeSourceAndSink();
        int sourceAngle = boardModel.getSourceTileModel().getAngle();
        int sinkAngle = boardModel.getSinkTileModel().getAngle();

        // Then
        assertThat(sourceAngle).isEqualTo(90);
        assertThat(sinkAngle).isEqualTo(180);
    }

    @Test
    public void whenPlacingSourceAndSink_thenSourceAndSinkShouldBePlaced() {
        // Given
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(3, 4)       // position of source
                .thenReturn(1)          // angle of source
                .thenReturn(1, 2)       // position of sink
                .thenReturn(1);         // angle of sink

        // When
        boardModel.gridModel().create();
        boardModel.placeSourceAndSink();
        GridModel.GridPosition sourcePosition = boardModel.getSourceTileModel().getGridPosition();
        GridModel.GridPosition sinkPosition = boardModel.getSinkTileModel().getGridPosition();

        // Then
        assertThat(sourcePosition.row()).isEqualTo(3);
        assertThat(sourcePosition.col()).isEqualTo(4);
        assertThat(sinkPosition.row()).isEqualTo(1);
        assertThat(sinkPosition.col()).isEqualTo(2);
    }

    @Test
    public void givenPlacingSourceAndSink_whenSinkIsPlacedOnTopOfSource_thenSinkIsMoved() {
        // Given
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(3, 4)       // position of source
                .thenReturn(1)          // angle of source
                .thenReturn(3, 4)       // position of sink
                .thenReturn(1)          // angle of sink
                .thenReturn(1, 2);      // new position of sink

        // When
        boardModel.gridModel().create();
        boardModel.placeSourceAndSink();
        GridModel.GridPosition sourcePosition = boardModel.getSourceTileModel().getGridPosition();
        GridModel.GridPosition sinkPosition = boardModel.getSinkTileModel().getGridPosition();

        // Then
        assertThat(sourcePosition.row()).isEqualTo(3);
        assertThat(sourcePosition.col()).isEqualTo(4);
        assertThat(sinkPosition.row()).isEqualTo(1);
        assertThat(sinkPosition.col()).isEqualTo(2);
    }

    @Test
    public void givenPlacingSourceAndSink_whenTheyAreAdjacentAndSourceIsFacingSink_thenSourceIsRotated() {
        // Given
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(3, 4)       // position of source
                .thenReturn(1)          // angle of source
                .thenReturn(3, 5)       // position of sink
                .thenReturn(1);         // angle of sink

        // When
        boardModel.gridModel().create();
        boardModel.placeSourceAndSink();

        // Then
        assertThat(boardModel.getSourceTileModel().getAngle()).isEqualTo(180);
        assertThat(boardModel.getSinkTileModel().getAngle()).isEqualTo(90);
    }

    @Test
    public void givenPlacingSourceAndSink_whenTheyAreAdjacentAndSinkIsFacingSource_thenSinkIsRotated() {
        // Given
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(3, 5)       // position of source
                .thenReturn(1)          // angle of source
                .thenReturn(3, 4)       // position of sink
                .thenReturn(1);         // angle of sink

        // When
        boardModel.gridModel().create();
        boardModel.placeSourceAndSink();

        // Then
        assertThat(boardModel.getSourceTileModel().getAngle()).isEqualTo(90);
        assertThat(boardModel.getSinkTileModel().getAngle()).isEqualTo(180);
    }

    @Test
    public void givenPlacingSourceAndSink_whenSourceIsFacingAdjacentWall_thenItIsRotated() {
        // Given
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(0, 0)       // position of source
                .thenReturn(3)          // angle of source
                .thenReturn(3, 4)       // position of sink
                .thenReturn(1);         // angle of sink

        // When
        boardModel.gridModel().create();
        boardModel.placeSourceAndSink();

        // Then
        assertThat(boardModel.getSourceTileModel().getAngle()).isEqualTo(90);
        assertThat(boardModel.getSinkTileModel().getAngle()).isEqualTo(90);
    }

    @Test
    public void givenPlacingSourceAndSink_whenSinkIsFacingAdjacentWall_thenItIsRotated() {
        // Given
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(0, 1)       // position of source
                .thenReturn(1)          // angle of source
                .thenReturn(0, 0)       // position of sink
                .thenReturn(3);         // angle of sink

        // When
        boardModel.gridModel().create();
        boardModel.placeSourceAndSink();

        // Then
        assertThat(boardModel.getSourceTileModel().getAngle()).isEqualTo(90);
        assertThat(boardModel.getSinkTileModel().getAngle()).isEqualTo(180);
    }

    @Test
    public void givenARowAndAColumn_shouldPlaceTile() {
        // Given
        int row = GameConfig.getGridNumRows() - 1;
        int col = GameConfig.getGridNumCols() - 1;
        GridModel.GridPosition gridPosition = new GridModel.GridPosition(row, col);
        ConveyorTileModel conveyorTileModel = conveyorTileModelFactory.createTileModel();

        // When
        boardModel.gridModel().create();
        Optional<BoardTileModel> boardTileModelOptional = boardModel.placeTileModel(gridPosition, conveyorTileModel);
        BoardTileModel boardTileModel = boardTileModelOptional.get();

        // Then
        assertThat(boardTileModel.getType()).isEqualTo(conveyorTileModel.getType());
        assertThat(boardTileModel.getAngle()).isEqualTo(conveyorTileModel.getAngle());
    }

    @Test
    public void givenARowAndColumn_whenSourceIsFoundAtPosition_thenNewTileShouldNotBePlaced() throws GridPositionOutOfBoundsException {
        // Given
        StartTileModel startTileModel = new StartTileModel(0);
        boardModel.gridModel().create();
        GridModel.GridPosition gridPosition = new GridModel.GridPosition(0, 1);
        boardModel.placeTileModel(gridPosition, startTileModel);
        ConveyorTileModel conveyorTileModel = conveyorTileModelFactory.createTileModel();

        // When
        Optional<BoardTileModel> boardTileModelOptional = boardModel.placeTileModel(gridPosition, conveyorTileModel);

        // Then
        assertThat(boardModel.gridModel().get(gridPosition)).isEqualTo(startTileModel);
        assertThat(boardTileModelOptional.isPresent()).isFalse();
    }

    @Test
    public void givenARowAndColumn_whenSinkIsFoundAtPosition_thenNewTileShouldNotBePlaced() throws GridPositionOutOfBoundsException {
        // Given
        EndTileModel endTileModel = new EndTileModel(0);
        boardModel.gridModel().create();
        GridModel.GridPosition gridPosition = new GridModel.GridPosition(0, 1);
        boardModel.placeTileModel(gridPosition, endTileModel);
        ConveyorTileModel conveyorTileModel = conveyorTileModelFactory.createTileModel();

        // When
        Optional<BoardTileModel> boardTileModelOptional = boardModel.placeTileModel(gridPosition, conveyorTileModel);

        // Then
        assertThat(boardModel.gridModel().get(gridPosition)).isEqualTo(endTileModel);
        assertThat(boardTileModelOptional.isPresent()).isFalse();
    }

    @Test
    public void givenARowAndColumn_whenTileFilledWithLiquidIsFoundAtPosition_thenNewTileShouldNotBePlaced() throws GridPositionOutOfBoundsException {
        // Given
        ConveyorTileModel conveyorTileModel = conveyorTileModelFactory.createTileModel();
        boardModel.gridModel().create();
        GridModel.GridPosition gridPosition = new GridModel.GridPosition(0, 1);
        Optional<BoardTileModel> boardTileModelOptional = boardModel.placeTileModel(gridPosition, conveyorTileModel);
        BoardTileModel boardTileModel = boardTileModelOptional.get();
        boardTileModel.setFull();
        conveyorTileModel = conveyorTileModelFactory.createTileModel();

        // When
        Optional<BoardTileModel> boardTileModelOptionalNotPlaced = boardModel.placeTileModel(gridPosition, conveyorTileModel);

        // Then
        assertThat(boardModel.gridModel().get(gridPosition)).isEqualTo(boardTileModel);
        assertThat(boardTileModelOptionalNotPlaced.isPresent()).isFalse();
    }

    @Test
    public void givenATileModel_andItIsOnTheGrid_whenGettingPositionOfTile_shouldReturnPositionOfTile() {
        // Given
        boardModel.gridModel().create();
        GridModel.GridPosition gridPosition = new GridModel.GridPosition(2, 2);
        BoardTileModel sinkTileModel = boardTileModelFactory.createTileModel(BoardTileType.END_PIPE_TYPE);
        boardModel.placeTileModel(gridPosition, sinkTileModel);

        // When
        GridModel.GridPosition actualPosition = sinkTileModel.getGridPosition();

        // Then
        assertThat(actualPosition).isEqualToComparingFieldByField(gridPosition);
    }

    @Test
    public void givenATileModel_andItIsNotOnTheGrid_whenGettingPositionOfTile_shouldReturnNull() {
        // Given
        EndTileModel endTileModel = new EndTileModel(0);
        boardModel.gridModel().create();

        // When
        GridModel.GridPosition actualPosition = endTileModel.getGridPosition();

        // Then
        assertThat(actualPosition).isNull();
    }
}