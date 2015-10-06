package uk.co.plasmabeamgames.pipegame.android.app;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import uk.co.plasmabeamgames.pipegame.android.graphics.drawable.TileImageDrawable;
import uk.co.plasmabeamgames.pipegame.android.view.TileImageView;
import uk.co.plasmabeamgames.pipegame.core.viewmodel.ConveyorTileViewModel;

public abstract class TileContainerFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PipeGameApplication) getActivity().getApplication()).inject(this);
    }

    protected void setTileImageDrawable(ConveyorTileViewModel conveyorTileViewModel, TileImageView tileImageView) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), conveyorTileViewModel.getImageId());
        TileImageDrawable tileImageDrawable = new TileImageDrawable(getResources(), bitmap);
        tileImageDrawable.setAngle(conveyorTileViewModel.getAngle());
        tileImageView.setImageDrawable(tileImageDrawable);
    }
}