package uk.co.plasmabeamgames.pipegame.core.view;

import uk.co.plasmabeamgames.pipegame.core.viewmodel.BoardTileViewModel;

public interface BoardView {
    void drawGrid();
    void placeTile(BoardTileViewModel boardTileViewModel);
    void fillTile(BoardTileViewModel boardTileViewModel, int frameLength);
    void setFrameLength(int frameLength);
    void pause();
    void resume();
}
