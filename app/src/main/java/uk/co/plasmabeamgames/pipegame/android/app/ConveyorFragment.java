package uk.co.plasmabeamgames.pipegame.android.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.R;
import uk.co.plasmabeamgames.pipegame.android.view.TileImageView;
import uk.co.plasmabeamgames.pipegame.core.presenter.ConveyorPresenter;
import uk.co.plasmabeamgames.pipegame.core.view.ConveyorView;
import uk.co.plasmabeamgames.pipegame.core.viewmodel.ConveyorTileViewModel;

public class ConveyorFragment extends TileContainerFragment implements ConveyorView {

    private static final String TAG = ConveyorFragment.class.getCanonicalName();

    @Inject
    ConveyorPresenter conveyorPresenter;
    private LinearLayout tileList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tileList = (LinearLayout) inflater.inflate(R.layout.fragment_conveyor, container, false);

        return tileList;
    }

    @Override
    public void onStart() {
        super.onStart();
        conveyorPresenter.setConveyorView(this);
    }

    @Override
    public void onResume() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void drawList(List<ConveyorTileViewModel> list) {
        tileList.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TileImageView tileImageView;
        for (int listCounter = list.size() - 1; listCounter >= 0; listCounter--) {
            tileImageView = new TileImageView(getActivity());
            tileImageView.setLayoutParams(layoutParams);
            setTileImageDrawable(list.get(listCounter), tileImageView);
            tileList.addView(tileImageView);
        }
    }
}