package me.ryzeon.mate.controllers.tabs;

import com.dustinredmond.fxalert.FXAlert;
import io.github.palexdev.materialfx.controls.MFXListView;
import lombok.Getter;
import me.ryzeon.mate.FlightApplication;
import me.ryzeon.mate.controllers.view.TicketFlightController;
import me.ryzeon.mate.model.travel.TravelInfo;
import me.ryzeon.mate.screens.IScreenController;
import me.ryzeon.mate.screens.ScreenFactory;
import me.ryzeon.mate.screens.ScreenLoader;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.TravelInfoService;
import me.ryzeon.mate.services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

import static me.ryzeon.mate.controllers.tabs.FlightsController.converter;
import static me.ryzeon.mate.screens.Screen.TICKET_INFO;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 5/4/23 @ 01:17
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class BuyTabController implements IScreenController<BuyTabController> {

    @Getter
    private static BuyTabController instance;
    public MFXListView<TravelInfo> list_view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;

        list_view.setConverter(converter);
        list_view.setCellFactory(travelInfo -> new FlightsController.FlightInfoCell(list_view, travelInfo));
        list_view.features().enableBounceEffect();
        list_view.features().enableSmoothScrolling(0.5);

        list_view.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TravelInfo travelInfo = list_view.getSelectionModel().getSelectedValue();
                try {
                    ScreenLoader screenLoader = FlightApplication.getInstance().openTab(TICKET_INFO);
                    TicketFlightController controller = (TicketFlightController) screenLoader.getController();
                    System.out.println(controller);
                    controller.load(travelInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    FXAlert.showException(e, "Error", "Error al cargar la pantalla");
                }
            }
        });

        update();
    }


    public void update() {
        TravelInfoService travelInfoService = ServiceContainer.bind(TravelInfoService.class);
        list_view.getItems().clear();
        list_view.getItems().addAll(
                travelInfoService.getTravelInfo(ServiceContainer.bind(UserService.class).getUser().getId())
        );
    }
}
