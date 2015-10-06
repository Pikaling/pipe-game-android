package uk.co.plasmabeamgames.pipegame.core.message;

import java.util.ArrayList;
import java.util.List;

import uk.co.plasmabeamgames.pipegame.core.GameConfig;

public class MessagingCenter {

    private static final String TAG = MessagingCenter.class.getCanonicalName();
    private static MessagingCenter instance;

    public interface GameEventMessageSubscriber {
        void onGameEventMessageReceived(GameConfig.GameEvent gameEvent);
    }

    private List<GameEventMessageSubscriber> gameEventMessageSubscribers;

    private MessagingCenter() {
    }

    public static MessagingCenter getInstance() {
        if (instance == null) {
            instance = new MessagingCenter();
        }
        return instance;
    }

    public void subscribeToGameEventMessages(GameEventMessageSubscriber subscriber) {
        if (gameEventMessageSubscribers == null) {
            gameEventMessageSubscribers = new ArrayList<>();
        }
        gameEventMessageSubscribers.add(subscriber);
    }

    public void postGameEventMessage(GameConfig.GameEvent gameEvent) {
        if (gameEventMessageSubscribers != null) {
            for (GameEventMessageSubscriber subscriber : gameEventMessageSubscribers) {
                subscriber.onGameEventMessageReceived(gameEvent);
            }
        }
    }
}