package uk.co.plasmabeamgames.pipegame.core.model.tile.grid;

import org.junit.Test;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.CornerTileModel;

import static org.assertj.core.api.Assertions.assertThat;

public class CornerTileModelTest {

    private CornerTileModel cornerTileModel;

    @Test
    public void givenACornerTileAtZeroDegrees_whenFillingFromTop_thenIsFillable() {
        // Given
        givenACornerTile(0, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(-1, 0));

        // Then
        assertThat(cornerTileModel.isFillable()).isTrue();
    }

    @Test
    public void givenACornerTileAtZeroDegrees_whenFillingFromLeft_thenIsNotFillable() {
        // Given
        givenACornerTile(0, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(0, -1));

        // Then
        assertThat(cornerTileModel.isFillable()).isFalse();
    }

    @Test
    public void givenACornerTileAtZeroDegrees_whenFillingFromTop_thenNextPositionIsRight() {
        // Given
        givenACornerTile(0, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(-1, 0));

        // Then
        assertThat(cornerTileModel.getNextGridPosition().row()).isEqualTo(3);
        assertThat(cornerTileModel.getNextGridPosition().col()).isEqualTo(4);
    }

    @Test
    public void givenACornerTileAtZeroDegrees_whenFillingFromRight_thenNextPositionIsAbove() {
        // Given
        givenACornerTile(0, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(0, 1));

        // Then
        assertThat(cornerTileModel.getNextGridPosition().row()).isEqualTo(2);
        assertThat(cornerTileModel.getNextGridPosition().col()).isEqualTo(3);
    }

    @Test
    public void givenACornerTileAtNinetyDegrees_whenFillingFromBottom_thenIsFillable() {
        // Given
        givenACornerTile(90, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(1, 0));

        // Then
        assertThat(cornerTileModel.isFillable()).isTrue();
    }

    @Test
    public void givenACornerTileAtNinetyDegrees_whenFillingFromLeft_thenIsNotFillable() {
        // Given
        givenACornerTile(90, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(0, -1));

        // Then
        assertThat(cornerTileModel.isFillable()).isFalse();
    }

    @Test
    public void givenACornerTileAtNinetyDegrees_whenFillingFromRight_thenNextPositionIsBottom() {
        // Given
        givenACornerTile(90, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(0, 1));

        // Then
        assertThat(cornerTileModel.getNextGridPosition().row()).isEqualTo(4);
        assertThat(cornerTileModel.getNextGridPosition().col()).isEqualTo(3);
    }

    @Test
    public void givenACornerTileAtNinetyDegrees_whenFillingFromBottom_thenNextPositionIsRight() {
        // Given
        givenACornerTile(90, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(1, 0));

        // Then
        assertThat(cornerTileModel.getNextGridPosition().row()).isEqualTo(3);
        assertThat(cornerTileModel.getNextGridPosition().col()).isEqualTo(4);
    }

    private void givenACornerTile(int angle, GridModel.GridPosition gridPosition) {
        cornerTileModel = new CornerTileModel(angle);
        cornerTileModel.setGridPosition(gridPosition);
    }

    private void whenTryToFillWithLiquid(GridModel.GridPosition gridPosition) {
        cornerTileModel.setFillable(gridPosition);
    }
}
