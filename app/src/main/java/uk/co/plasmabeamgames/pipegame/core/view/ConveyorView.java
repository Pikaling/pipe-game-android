package uk.co.plasmabeamgames.pipegame.core.view;

import java.util.List;

import uk.co.plasmabeamgames.pipegame.core.viewmodel.ConveyorTileViewModel;

public interface ConveyorView {
    void drawList(List<ConveyorTileViewModel> list);
}
