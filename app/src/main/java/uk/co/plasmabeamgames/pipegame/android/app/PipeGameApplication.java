package uk.co.plasmabeamgames.pipegame.android.app;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import uk.co.plasmabeamgames.pipegame.android.AndroidModule;
import uk.co.plasmabeamgames.pipegame.core.PipeGameModule;

public class PipeGameApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new AndroidModule(this), new PipeGameModule());
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }
}