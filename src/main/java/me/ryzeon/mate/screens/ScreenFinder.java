package me.ryzeon.mate.screens;

import javafx.fxml.FXMLLoader;
import lombok.experimental.UtilityClass;
import me.ryzeon.mate.FlightApplication;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/2/23 @ 21:28
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@UtilityClass
public class ScreenFinder {

    public FXMLLoader getScreen(Screen screen) {
        if (FlightApplication.class.getResource(screen.getScreenName()) == null) {
            throw new NullPointerException("The screen " + screen.getScreenName() + " does not exist.");
        }
        return new FXMLLoader(FlightApplication.class.getResource(screen.getScreenName()));
    }
}
