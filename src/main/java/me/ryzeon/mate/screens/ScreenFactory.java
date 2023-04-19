package me.ryzeon.mate.screens;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/19/23 @ 15:05
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class ScreenFactory {

    private static ScreenFactory instance;

    public static ScreenFactory getInstance() {
        synchronized (ScreenFactory.class) {
            if (instance == null) {
                instance = new ScreenFactory();
            }
        }
        return instance;
    }

    public Parent loadScreen(Screen screen) throws IOException {
        FXMLLoader loader = ScreenFinder.getScreen(screen);
        return loader.load();
    }

    public IScreenController<?> getController(Parent parent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(parent);
        return loader.getController();
    }

    public ScreenLoader loadScreenWithController(Screen screen) throws IOException {
        FXMLLoader loader = ScreenFinder.getScreen(screen);
        return new ScreenLoader(loader);
    }
}
