package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile;

import uk.co.plasmabeamgames.pipegame.R;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileType;

public enum BoardTileType {

    EMPTY_TILE(R.drawable.tile, R.xml.liquid_spill),
    START_PIPE(R.drawable.source_pipe_1, R.xml.source_pipe),
    END_PIPE(R.drawable.sink_pipe_1, R.xml.sink_pipe),
    STRAIGHT_PIPE(ConveyorTileType.STRAIGHT_PIPE.getImageId(), R.xml.straight_pipe),
    CORNER_PIPE(ConveyorTileType.CORNER_PIPE.getImageId(), R.xml.corner_pipe),
    CROSS_PIPE(ConveyorTileType.CROSS_PIPE.getImageId(), R.xml.cross_pipe),
    CROSS_PIPE_HALF_FULL(R.drawable.cross_pipe_one_full_1, R.xml.cross_pipe_one_full);

    private final int imageId;
    private final int animationXmlId;

    BoardTileType(int imageId, int animationXmlId) {
        this.imageId = imageId;
        this.animationXmlId = animationXmlId;
    }

    public int getImageId() {
        return imageId;
    }

    public int getAnimationXmlId() {
        return animationXmlId;
    }

}