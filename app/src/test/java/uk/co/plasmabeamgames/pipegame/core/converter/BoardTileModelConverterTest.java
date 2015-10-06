package uk.co.plasmabeamgames.pipegame.core.converter;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.CornerTileModel;
import uk.co.plasmabeamgames.pipegame.core.viewmodel.BoardTileViewModel;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTileModelConverterTest {

    @Inject
    BoardTileModelConverter boardTileModelConverter;

    @Before
    public void setUp() {
        boardTileModelConverter = new BoardTileModelConverter();
    }

    @Test
    public void givenCornerTileModelFilling_whenConvertingToDomainModel_setsScaleToOne() {
        // Given
        CornerTileModel cornerTileModel = new CornerTileModel(0);
        cornerTileModel.setGridPosition(new GridModel.GridPosition(3, 3));
        cornerTileModel.setFillable(new GridModel.GridPosition(-1, 0));

        // When
        BoardTileViewModel boardTileViewModel = boardTileModelConverter.convert(cornerTileModel);

        // Then
        assertThat(boardTileViewModel.getScaleX()).isEqualTo(1f);
    }

    @Test
    public void givenCornerTileModelFilling_whenConvertingToDomainModel_setsScaleToMinusOne() {
        // Given
        CornerTileModel cornerTileModel = new CornerTileModel(0);
        cornerTileModel.setGridPosition(new GridModel.GridPosition(3, 3));
        cornerTileModel.setFillable(new GridModel.GridPosition(0, 1));

        // When
        BoardTileViewModel boardTileViewModel = boardTileModelConverter.convert(cornerTileModel);

        // Then
        assertThat(boardTileViewModel.getScaleX()).isEqualTo(-1f);
    }

    @Test
    public void givenCornerTileModelNotFilling_whenConvertingToDomainModel_setsScaleToOne() {
        // Given
        CornerTileModel cornerTileModel = new CornerTileModel(0);
        cornerTileModel.setGridPosition(new GridModel.GridPosition(3, 3));

        // When
        BoardTileViewModel boardTileViewModel = boardTileModelConverter.convert(cornerTileModel);

        // Then
        assertThat(boardTileViewModel.getScaleX()).isEqualTo(1f);
    }
}
