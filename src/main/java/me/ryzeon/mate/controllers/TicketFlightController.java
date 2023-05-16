package me.ryzeon.mate.controllers;

import javafx.scene.control.Label;
import me.ryzeon.mate.model.travel.TravelInfo;
import me.ryzeon.mate.model.user.User;
import me.ryzeon.mate.screens.IScreenController;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 5/3/23 @ 23:05
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class TicketFlightController implements IScreenController<TicketFlightController> {

    public Label passenger_name;
    public Label from;
    public Label to;
    public Label board_time;
    public Label gate_number;
    public Label date;
    public Label flight_number;
    public Label seat_code;
    public Label passenger_name_2;
    public Label from_2;
    public Label to_2;
    public Label gate_number_2;
    public Label date_2;
    public Label flight_number_2;
    public Label board_time_2;
    public Label seat_code_2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void load(TravelInfo info) {
        User user = ServiceContainer.get(UserService.class).getUser();

        passenger_name.setText(user.getUsername().toLowerCase());
        passenger_name_2.setText(user.getUsername().toLowerCase());

        from.setText(info.getOrigin());
        from_2.setText(info.getOrigin());

        to.setText(info.getDestination());
        to_2.setText(info.getDestination());

        board_time.setText(info.getBoardTime());
        board_time_2.setText(info.getBoardTime());

        gate_number.setText(info.getGateNumber());
        gate_number_2.setText(info.getGateNumber());

        date.setText(info.getDate());
        date_2.setText(info.getDate());

        flight_number.setText(info.getFlightId());
        flight_number_2.setText(info.getFlightId());

        seat_code.setText(info.getSeatCode());
        seat_code_2.setText(info.getSeatCode());
    }
}
