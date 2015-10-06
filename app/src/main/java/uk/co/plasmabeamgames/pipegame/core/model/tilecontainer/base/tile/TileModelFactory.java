package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.base.tile;

import java.util.Random;

public abstract class TileModelFactory {

    protected Random random;

    public TileModelFactory(Random random) {
        this.random = random;
    }

    protected int generateAngle() {
        return random.nextInt(4) * 90;
    }
}
