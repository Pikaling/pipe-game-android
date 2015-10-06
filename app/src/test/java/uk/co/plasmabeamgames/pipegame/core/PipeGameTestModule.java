package uk.co.plasmabeamgames.pipegame.core;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class PipeGameTestModule extends PipeGameModule {

    @Provides
    @Singleton
    Random provideRandom() {
        return mock(Random.class);
    }

}


