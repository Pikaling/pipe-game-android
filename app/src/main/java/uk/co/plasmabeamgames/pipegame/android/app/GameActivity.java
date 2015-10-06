package uk.co.plasmabeamgames.pipegame.android.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import javax.inject.Inject;

import uk.co.plasmabeamgames.pipegame.R;
import uk.co.plasmabeamgames.pipegame.core.GameConfig;
import uk.co.plasmabeamgames.pipegame.core.message.MessagingCenter;
import uk.co.plasmabeamgames.pipegame.core.presenter.GamePresenter;
import uk.co.plasmabeamgames.pipegame.core.view.GameView;

public class GameActivity extends Activity implements GameView {

    private static final String TAG = GameActivity.class.getCanonicalName();
    @Inject
    GamePresenter gamePresenter;
    private TextView levelTextView;
    private TextView scoreTextView;
    private AlertDialog gamePaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ((PipeGameApplication) getApplication()).inject(this);
        gamePresenter.setGameView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onStart();
        gamePresenter.startPresenting();
        attachButtonHandlers();
        // Set initial value of score
    }

    @Override
    protected void onPause() {
        super.onPause();
        gamePresenter.stopPresenting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
    }

    @Override
    public void updateLevel(int currentLevel) {
        if (levelTextView == null) {
            levelTextView = (TextView) findViewById(R.id.level_value);
        }
        levelTextView.setText(currentLevel + "");
    }

    @Override
    public void updateScore(int currentScore) {
        if (scoreTextView == null) {
            scoreTextView = (TextView) findViewById(R.id.score_value);
        }
        scoreTextView.setText(currentScore + "");
    }

    @Override
    public void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.game_over)
                .setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gamePresenter.startGame();
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gamePresenter.exitGame();
                    }
                });
        builder.setCancelable(false);
        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }

    @Override
    public void nextLevel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.level_complete)
                .setPositiveButton(R.string.next_level, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gamePresenter.startNextLevel();
                    }
                });
        builder.setCancelable(false);
        AlertDialog levelCompleteDialog = builder.create();
        levelCompleteDialog.show();
    }

    private void attachButtonHandlers() {
        ImageButton fastForwardButton = (ImageButton) findViewById(R.id.fast_forward);
        fastForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePresenter.doubleLiquidSpeed();
            }
        });
        ImageButton pauseButton = (ImageButton) findViewById(R.id.pause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePresenter.pauseGame();
                showGamePausedDialog();
            }
        });
    }

    private void showGamePausedDialog() {
        if (gamePaused == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Game Paused")
                    .setPositiveButton("Resume", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gamePresenter.resumeGame();
                        }
                    });
            builder.setCancelable(false);
            gamePaused = builder.create();
        }
        gamePaused.show();
    }
}
