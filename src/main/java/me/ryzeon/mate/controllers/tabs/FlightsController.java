package me.ryzeon.mate.controllers.tabs;

import com.dustinredmond.fxalert.FXAlert;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.util.StringConverter;
import me.ryzeon.mate.model.travel.TravelInfo;
import me.ryzeon.mate.screens.IScreenController;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.FlightService;
import me.ryzeon.mate.services.TravelInfoService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 5/3/23 @ 23:42
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class FlightsController implements IScreenController<FlightsController> {

    public MFXListView<TravelInfo> list_view;
    public MFXFilterComboBox<String> to_box;
    public MFXFilterComboBox<String> from_box;
    public MFXSlider scales;
    public MFXButton search;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FlightService flightService = ServiceContainer.bind(FlightService.class);
        to_box.getItems().addAll(flightService.getCities());
        from_box.getItems().addAll(flightService.getCities());

        from_box.getSelectionModel().selectIndex(0);

        list_view.setConverter(converter);
        list_view.setCellFactory(travelInfo -> new FlightInfoCell(list_view, travelInfo));
        list_view.features().enableBounceEffect();
        list_view.features().enableSmoothScrolling(0.5);

        search.setOnAction(event -> {
            String from = from_box.getSelectionModel().getSelectedItem();
            String to = to_box.getSelectionModel().getSelectedItem();
            list_view.getItems().clear();
            list_view.getItems().addAll(
                    flightService.searchFlights(from, to, fromDoubleToInt(scales.getValue()))
                            .stream()
                            .map(TravelInfo::generate).collect(Collectors.toList())
            );
            if (list_view.getItems().isEmpty()) {
                // Send a notification
                FXAlert.showWarning("No se encontraron vuelos");
            }
        });

        list_view.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TravelInfo selectedItem = list_view.getSelectionModel().getSelectedValue();
                FXAlert.showInfo("Vuelo seleccionado", "Vuelo seleccionado", "Vuelo seleccionado: " + selectedItem.getFlightId());
                ServiceContainer.bind(TravelInfoService.class)
                        .saveTravelInfo(selectedItem);
                BuyTabController.getInstance().update(); // Update the buy tab
            }
        });
    }

    public static final StringConverter<TravelInfo> converter = FunctionalStringConverter.to(
            travelInfo -> {
                String origin = travelInfo.getOrigin();
                String destination = travelInfo.getDestination();
                String boardTime = travelInfo.getBoardTime();
                String amount = travelInfo.getConnections();
                String vuelo = travelInfo.getFlightId();
                return String.format("Origen: %s - Destino: %s - Hora: %s - Vuelo: %s - %s", origin, destination, boardTime, vuelo, amount);
            }
    );

    int fromDoubleToInt(double value) {
        return (int) value;
    }

    static class FlightInfoCell extends MFXListCell<TravelInfo> {

        private final MFXFontIcon icon;

        public FlightInfoCell(MFXListView<TravelInfo> listView, TravelInfo data) {
            super(listView, data);
            icon = new MFXFontIcon("fas-plane", 24);
            icon.getStyleClass().add("user-icon");
            render(data);
        }

        @Override
        protected void render(TravelInfo data) {
            super.render(data);
            if (icon != null) getChildren().add(0, icon);
        }
    }
}
