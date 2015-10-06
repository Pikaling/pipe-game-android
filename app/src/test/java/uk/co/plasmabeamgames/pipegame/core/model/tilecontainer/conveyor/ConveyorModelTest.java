package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Random;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.assertj.ListAssert;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.base.BaseModelTest;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class ConveyorModelTest extends BaseModelTest {

    @Inject
    Random mockRandom;
    @Inject
    ConveyorModel conveyorModel;

    @Before
    public void setUp() {
        super.setUp();
        component().inject(this);
    }

    @Test
    public void whenIRemoveAPipeFromTheConveyor_ThenTheFirstPipeIsRemoved() {
        // Given
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(0, 1)   // Type 0, angle 90
                .thenReturn(1, 2)   // Type 1, angle 180
                .thenReturn(2, 0)   // Type 2, angle 0
                .thenReturn(1, 3)   // Type 1, angle 270
                .thenReturn(1, 2);  // Type 1, angle 180

        // When
        conveyorModel.createList();
        ConveyorTileModel nextPipe = conveyorModel.getNextTile();
        conveyorModel.move();

        // Then
        assertThat(nextPipe.getType()).isEqualTo(getPipeType(0));
        assertThat(nextPipe.getAngle()).isEqualTo(90);
    }

    @Test
    public void whenIRemoveAtPipeFromTheConveyor_ThenTheListIsShifted() {
        // Given
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(0, 1)   // Type 0, angle 90
                .thenReturn(1, 2)   // Type 1, angle 180
                .thenReturn(2, 0)   // Type 2, angle 0
                .thenReturn(1, 3)   // Type 1, angle 270
                .thenReturn(1, 2)   // Type 1, angle 180
                .thenReturn(2, 1);  // Type 2, angle 90
        conveyorModel.createList();
        LinkedList<ConveyorTileModel> originalList = conveyorModel.getList();

        // When
        LinkedList<ConveyorTileModel> newList = conveyorModel.move();
        originalList.removeFirst();

        // Then
        ListAssert.assertThat(newList).startsWith(originalList);
    }

    @Test
    public void whenIRemoveAPipeFromTheConveyor_ThenANewPipeIsAddedToTheEnd() {
        // Given
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(0, 1)   // Type 0, angle 90
                .thenReturn(1, 2)   // Type 1, angle 180
                .thenReturn(2, 0)   // Type 2, angle 0
                .thenReturn(1, 3)   // Type 1, angle 270
                .thenReturn(1, 2)   // Type 1, angle 180
                .thenReturn(2, 1);  // Type 2, angle 90
        conveyorModel.createList();

        // When
        LinkedList<ConveyorTileModel> list = conveyorModel.move();

        //Then
        assertThat(list.get(4).getType()).isEqualTo(getPipeType(2));
        assertThat(list.get(4).getAngle()).isEqualTo(90);
    }

    private ConveyorTileType getPipeType(int index) {
        return ConveyorTileType.values()[index];
    }

}
