package uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.base;

import org.junit.Before;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.plasmabeamgames.pipegame.android.app.PipeGameApplication;
import uk.co.plasmabeamgames.pipegame.core.PipeGameTestModule;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.board.BoardModelTest;
import uk.co.plasmabeamgames.pipegame.core.model.tilecontainer.conveyor.ConveyorModelTest;

public class BaseModelTest {

    PipeGameTest pipeGameTest;

    @Component(dependencies = PipeGameApplication.PipeGame.class, modules = PipeGameTestModule.class)
    @Singleton
    public interface PipeGameTest {
        void inject(BoardModelTest boardModelTest);
        void inject(ConveyorModelTest conveyorModelTest);
    }

    @Before
    public void setUp() {
        pipeGameTest = DaggerBaseModelTest_PipeGameTest.create();
    }

    public PipeGameTest component() {
        return pipeGameTest;
    }
}