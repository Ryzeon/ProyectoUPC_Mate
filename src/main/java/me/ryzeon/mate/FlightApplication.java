package me.ryzeon.mate;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.ryzeon.mate.backend.SQLService;
import me.ryzeon.mate.screens.Screen;
import me.ryzeon.mate.screens.ScreenFactory;
import me.ryzeon.mate.screens.ScreenLoader;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.FlightService;
import me.ryzeon.mate.services.TravelInfoService;
import me.ryzeon.mate.services.UserService;

import java.io.IOException;

public class FlightApplication {

    private Scene scene;

    private Stage stage;

    private Stage activeSubStage;

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
        this.stage = stage;
        // Init
        ServiceContainer.register(SQLService.class);
        ServiceContainer.register(FlightService.class);
        ServiceContainer.register(UserService.class);
        ServiceContainer.register(TravelInfoService.class);
        ServiceContainer.enableServices();

        UserService userService = ServiceContainer.get(UserService.class);
        ScreenLoader screen = ScreenFactory.getInstance().loadScreenWithController(
                userService.isLoggedIn() ? Screen.PRINCIPAL_PAGE : Screen.LOGIN
        );
        scene = new Scene(screen.loadScreen());
        stage.initStyle(StageStyle.DECORATED);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public ScreenLoader switchTo(Screen screen) throws Exception {
        ScreenLoader screenLoader = ScreenFactory.getInstance().loadScreenWithController(screen);
        scene = new Scene(screenLoader.loadScreen());
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        stage.setScene(scene);
        return screenLoader;
    }

    public ScreenLoader openTab(Screen screen) throws Exception {
        ScreenLoader screenLoader = ScreenFactory.getInstance().loadScreenWithController(screen);
        activeSubStage = new Stage();
        activeSubStage.initOwner(stage);
        Parent parent = screenLoader.loadScreen();
        Scene scene = new Scene(parent);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        activeSubStage.setScene(scene);
        activeSubStage.show();
        activeSubStage.setOnCloseRequest(event -> {
            activeSubStage.close();
            activeSubStage = null;
        });
        return screenLoader;
    }

}