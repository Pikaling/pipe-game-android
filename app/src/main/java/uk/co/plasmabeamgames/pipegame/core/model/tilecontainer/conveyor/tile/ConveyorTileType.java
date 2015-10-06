package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile;

import uk.co.plasmabeamgames.pipegame.R;

public enum ConveyorTileType {

    STRAIGHT_PIPE(R.drawable.straight_pipe_1),
    CORNER_PIPE(R.drawable.corner_pipe_1),
    CROSS_PIPE(R.drawable.cross_pipe_1);

    private final int imageId;

    ConveyorTileType(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }
}
