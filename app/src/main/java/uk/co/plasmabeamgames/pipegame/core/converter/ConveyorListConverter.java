package uk.co.plasmabeamgames.pipegame.core.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.tile.ConveyorTileModel;
import uk.co.plasmabeamgames.pipegame.core.viewmodel.ConveyorTileViewModel;

public class ConveyorListConverter implements Converter<List<ConveyorTileModel>, List<ConveyorTileViewModel>> {

    @Inject
    public ConveyorListConverter() {
    }

    @Override
    public List<ConveyorTileViewModel> convert(List<ConveyorTileModel> domainModel) {
        List<ConveyorTileViewModel> conveyorTileViewModels = new ArrayList<>();
        ConveyorTileViewModel conveyorTileViewModel;
        for (ConveyorTileModel conveyorTileModel : domainModel) {
            conveyorTileViewModel = convertToConveyorTileViewModel(conveyorTileModel);
            conveyorTileViewModels.add(conveyorTileViewModel);
        }
        return conveyorTileViewModels;
    }

    private ConveyorTileViewModel convertToConveyorTileViewModel(ConveyorTileModel conveyorTileModel) {
        return new ConveyorTileViewModel(conveyorTileModel.getAngle(), conveyorTileModel.getType().getImageId());
    }
}