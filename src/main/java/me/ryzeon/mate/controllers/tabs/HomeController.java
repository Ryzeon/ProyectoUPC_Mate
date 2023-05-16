package me.ryzeon.mate.controllers.tabs;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.control.Label;
import me.ryzeon.mate.controllers.TabsController;
import me.ryzeon.mate.screens.IScreenController;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements IScreenController<HomeController> {
    public Label usuario    ;
    public MFXButton buy;
    public MFXButton bought;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuario.setText(ServiceContainer.bind(UserService.class).getUser().getUsername());

        buy.setOnMouseClicked(e -> {
            TabsController.getTabsController().tab_view.getSelectionModel().select(1);

        });
        bought.setOnMouseClicked(e -> {
            TabsController.getTabsController().tab_view.getSelectionModel().select(2);
        });
    }
}
