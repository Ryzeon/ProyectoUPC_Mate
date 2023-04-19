package me.ryzeon.mate.start;

import javafx.application.Application;
import javafx.stage.Stage;
import me.ryzeon.mate.FlightApplication;

import java.io.IOException;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/19/23 @ 16:48
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class Start extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FlightApplication.getInstance().start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
