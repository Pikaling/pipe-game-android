package uk.co.plasmabeamgames.pipegame.android;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.plasmabeamgames.pipegame.android.app.PipeGameApplication;

@Module(library = true)
public class AndroidModule {
    private final PipeGameApplication application;

    public AndroidModule(PipeGameApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return application;
    }
}
