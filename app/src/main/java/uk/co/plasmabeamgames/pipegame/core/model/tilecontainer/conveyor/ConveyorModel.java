package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor;

import java.util.LinkedList;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.core.GameConfig;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileModelFactory;

public class ConveyorModel {

    private ConveyorTileModelFactory conveyorTileModelFactory;
    private int numTiles;
    private LinkedList<ConveyorTileModel> list;

    @Inject
    public ConveyorModel(ConveyorTileModelFactory conveyorTileModelFactory) {
        this.conveyorTileModelFactory = conveyorTileModelFactory;
        numTiles = GameConfig.getConveyorNumTiles();
    }

    public void createList() {
        list = new LinkedList<>();
        for (int counter = 0; counter < numTiles; counter++) {
            list.add(conveyorTileModelFactory.createTileModel());
        }
    }

    public LinkedList<ConveyorTileModel> getList() {
        return list;
    }

    public ConveyorTileModel getNextTile() {
        return list.peekFirst();
    }

    public LinkedList<ConveyorTileModel> move() {
        list.addLast(conveyorTileModelFactory.createTileModel());
        list.removeFirst();
        return list;
    }

}