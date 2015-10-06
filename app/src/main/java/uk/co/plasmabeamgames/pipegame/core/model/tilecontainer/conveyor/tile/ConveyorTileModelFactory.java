package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile;

import java.util.Random;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.base.tile.TileModelFactory;

public class ConveyorTileModelFactory extends TileModelFactory {

    @Inject
    public ConveyorTileModelFactory(Random random) {
        super(random);
    }

    public ConveyorTileModel createTileModel() {
        return new ConveyorTileModel(generateAngle(), generatePipeType());
    }

    private ConveyorTileType generatePipeType() {
        ConveyorTileType[] conveyorTileTypes = ConveyorTileType.values();
        return conveyorTileTypes[random.nextInt(conveyorTileTypes.length)];
    }
}
