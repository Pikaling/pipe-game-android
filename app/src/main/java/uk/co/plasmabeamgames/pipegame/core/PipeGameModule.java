package uk.co.plasmabeamgames.pipegame.core;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.plasmabeamgames.pipegame.android.app.BoardFragment;
import uk.co.plasmabeamgames.pipegame.android.app.ConveyorFragment;
import uk.co.plasmabeamgames.pipegame.android.app.GameActivity;

@Module(
        injects = {
                GameActivity.class,
                BoardFragment.class,
                ConveyorFragment.class
        },
        complete = false
)
public class PipeGameModule {

    @Provides
    @Singleton
    Random provideRandom() {
        return new Random();
    }
}