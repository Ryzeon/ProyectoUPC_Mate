package me.ryzeon.mate;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.ryzeon.mate.screens.Screen;
import me.ryzeon.mate.screens.ScreenFactory;
import me.ryzeon.mate.screens.ScreenLoader;

import java.io.IOException;

public class FlightApplication {

    private Scene scene;

    private static FlightApplication instance;

    // Private constructor to prevent instantiation
    public static FlightApplication getInstance() {
        synchronized (FlightApplication.class) {
            if (instance == null) {
                instance = new FlightApplication();
            }
        }
        return instance;
    }

    public void start(Stage stage) throws IOException {
        ScreenLoader screen = ScreenFactory.getInstance().loadScreenWithController(Screen.LOGIN);
        Scene scene = new Scene(screen.loadScreen());
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public ScreenLoader switchTo(Screen screen)throws Exception {
        ScreenLoader screenLoader = ScreenFactory.getInstance().loadScreenWithController(screen);
        scene.setRoot(screenLoader.loadScreen());
        return screenLoader;
    }

}