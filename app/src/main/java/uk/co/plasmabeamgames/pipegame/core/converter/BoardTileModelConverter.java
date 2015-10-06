package uk.co.plasmabeamgames.pipegame.core.converter;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.GridModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileModel;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.tile.BoardTileType;
import uk.co.plasmabeamgames.pipegame.core.viewmodel.BoardTileViewModel;

public class BoardTileModelConverter implements Converter<BoardTileModel, BoardTileViewModel> {

    @Inject
    public BoardTileModelConverter() {
    }

    @Override
    public BoardTileViewModel convert(BoardTileModel domainModel) {
        if (domainModel.isFillable() || domainModel.getType().equals(BoardTileType.EMPTY_TILE)) {
            if (flipPipe(domainModel)) {
                return new BoardTileViewModel(calculateAngle(domainModel),
                        domainModel.getType().getImageId(),
                        domainModel.getType().getAnimationXmlId(),
                        -1f,
                        domainModel.isFillable(),
                        domainModel.isFilling(),
                        domainModel.isFull(),
                        domainModel.getGridPosition());
            }
            return new BoardTileViewModel(domainModel.getAngle(),
                    domainModel.getType().getImageId(),
                    domainModel.getType().getAnimationXmlId(),
                    1f,
                    domainModel.isFillable(),
                    domainModel.isFilling(),
                    domainModel.isFull(),
                    domainModel.getGridPosition());
        }
        BoardTileModel spillModel = domainModel.getSpillModel();
        return new BoardTileViewModel(spillModel.getAngle(),
                spillModel.getType().getImageId(),
                spillModel.getType().getAnimationXmlId(),
                1f,
                domainModel.isFillable(),
                domainModel.isFilling(),
                domainModel.isFull(),
                domainModel.getGridPosition());
    }

    private boolean flipPipe(BoardTileModel domainModel) {
        if (domainModel.getType().equals(BoardTileType.CORNER_PIPE)) {
            GridModel.GridPosition nextGridPosition = domainModel.getNextGridPosition();
            if (nextGridPosition != null) {
                int angle = domainModel.getAngle();
                GridModel.GridPosition gridPosition = domainModel.getGridPosition();
                GridModel.GridPosition relativeNext = nextGridPosition.subtract(gridPosition);
                if (((angle == 0 || angle == 180) && (Math.abs(relativeNext.row()) == 1)) ||
                        ((angle == 90 || angle == 270) && (Math.abs(relativeNext.col()) == 1))) {
                    return true;
                }
            }
        }
        return false;
    }

    private int calculateAngle(BoardTileModel domainModel) {
        int angle = domainModel.getAngle();
        if (domainModel.getType().equals(BoardTileType.CORNER_PIPE)) {
            if (angle == 0)
                return 270;
            if (angle == 90)
                return 180;
            if (angle == 180)
                return 90;
            if (angle == 270)
                return 0;
        }
        return angle;
    }
}