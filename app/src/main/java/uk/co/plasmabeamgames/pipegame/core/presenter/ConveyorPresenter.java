package uk.co.plasmabeamgames.pipegame.core.presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import uk.co.plasmabeamgames.pipegame.core.GameState;
import uk.co.plasmabeamgames.pipegame.core.converter.ConveyorListConverter;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.ConveyorModel;
import uk.co.plasmabeamgames.pipegame.core.view.ConveyorView;
import uk.co.plasmabeamgames.pipegame.core.viewmodel.ConveyorTileViewModel;

@Singleton
public class ConveyorPresenter implements Presenter {

    private ConveyorModel conveyorModel;
    private ConveyorView conveyorView;
    private ConveyorListConverter conveyorListConverter;

    @Inject
    public ConveyorPresenter(ConveyorModel conveyorModel, ConveyorListConverter conveyorListConverter) {
        this.conveyorModel = conveyorModel;
        this.conveyorListConverter = conveyorListConverter;
    }

    public void setConveyorView(ConveyorView conveyorView) {
        this.conveyorView = conveyorView;
    }

    public ConveyorModel getConveyorModel() {
        return conveyorModel;
    }

    @Override
    public void startPresenting() {
        List<ConveyorTileViewModel> conveyorTileViewModels = conveyorListConverter.convert(conveyorModel.getList());
        conveyorView.drawList(conveyorTileViewModels);
    }

    @Override
    public void stopPresenting() {
        GameState.getInstance().setConveyor(conveyorModel.getList());
    }

    public void initialiseConveyorModel() {
        conveyorModel.createList();
    }

    public void moveConveyor() {
        conveyorModel.move();
        conveyorView.drawList(conveyorListConverter.convert(conveyorModel.getList()));
    }
}
