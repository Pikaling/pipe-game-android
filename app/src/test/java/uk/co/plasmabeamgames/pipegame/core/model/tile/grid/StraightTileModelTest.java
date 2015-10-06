package uk.co.plasmabeamgames.pipegame.core.model.tile.grid;

import org.junit.Test;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.StraightTileModel;

import static org.assertj.core.api.Assertions.assertThat;

public class StraightTileModelTest {

    private StraightTileModel straightTileModel;

    @Test
    public void givenStraightTileAtZeroDegrees_whenFilledFromTop_thenIsFillable() {
        // Given
        givenAStraightPipe(0, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(-1, 0));

        // Then
        assertThat(straightTileModel.isFillable()).isTrue();
    }

    @Test
    public void givenStraightTileAtZeroDegrees_whenFilledFromLeft_thenIsNotFillable() {
        // Given
        givenAStraightPipe(0, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(0, -1));

        // Then
        assertThat(straightTileModel.isFillable()).isFalse();
    }

    @Test
    public void givenStraightTileAtZeroDegrees_whenFilledFromBottom_thenItRotatesToOneEighty() {
        // Given
        givenAStraightPipe(0, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(1, 0));

        // Then
        assertThat(straightTileModel.getAngle()).isEqualTo(180);
    }

    @Test
    public void givenStraightTileAtZeroDegrees_whenFilledFromBottom_thenItSetsNextPositionCorrectly() {
        // Given
        givenAStraightPipe(0, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(1, 0));

        // Then
        assertThat(straightTileModel.getNextGridPosition().row()).isEqualTo(2);
        assertThat(straightTileModel.getNextGridPosition().col()).isEqualTo(3);
    }

    @Test
    public void givenStraightTileAtNinetyDegrees_whenFilledFromLeft_thenIsFillable() {
        // Given
        givenAStraightPipe(90, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(0, -1));

        // Then
        assertThat(straightTileModel.isFillable()).isTrue();
    }

    @Test
    public void givenStraightTileAtNinetyDegrees_whenFilledFromBottom_thenIsNotFillable() {
        // Given
        givenAStraightPipe(90, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(-1, 0));

        // Then
        assertThat(straightTileModel.isFillable()).isFalse();
    }

    @Test
    public void givenStraightTileAtNinetyDegrees_whenFilledFromRight_thenItRotatesToTwoSeventy() {
        // Given
        givenAStraightPipe(90, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(0, 1));

        // Then
        assertThat(straightTileModel.getAngle()).isEqualTo(270);
    }

    @Test
    public void givenStraightTileAtNinetyDegrees_whenFilledFromRight_thenItSetsNextPositionCorrectly() {
        // Given
        givenAStraightPipe(90, new GridModel.GridPosition(3, 3));

        // When
        whenTryToFillWithLiquid(new GridModel.GridPosition(0, 1));

        // Then
        assertThat(straightTileModel.getNextGridPosition().row()).isEqualTo(3);
        assertThat(straightTileModel.getNextGridPosition().col()).isEqualTo(2);
    }

    private void givenAStraightPipe(int angle, GridModel.GridPosition gridPosition) {
        straightTileModel = new StraightTileModel(angle);
        straightTileModel.setGridPosition(gridPosition);
    }

    private void whenTryToFillWithLiquid(GridModel.GridPosition gridPosition) {
        straightTileModel.setFillable(gridPosition);
    }
}
