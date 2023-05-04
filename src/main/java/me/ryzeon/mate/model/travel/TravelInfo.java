package me.ryzeon.mate.model.travel;

import jakarta.persistence.*;
import lombok.Data;
import me.ryzeon.mate.model.flight.IFlight;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.UserService;

import java.util.Date;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 5/3/23 @ 20:31
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@Entity
@Table(name = "TICKETS")
@Data
public class TravelInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "buyer_id")
    private int buyerId;

    @Column(name = "flight_id")
    private String flightId;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "date")
    private String date;

    @Column(name = "board_time")
    private String boardTime;

    @Column(name = "connections")
    private String connections;

    @Column(name = "gate_number")
    private String gateNumber;

    @Column(name = "seat_code")
    private String seatCode;

    public static TravelInfo generate(IFlight flight) {
        TravelInfo info = new TravelInfo();
        info.setOrigin(flight.origin());
        info.setBuyerId(ServiceContainer.bind(UserService.class).getUser().getId());
        info.setDestination(flight.destination());
        Date date = new Date();
        // Add a random date before the current date
        date.setTime(date.getTime() - (long) (Math.random() * 1000000000));
        // Parse to dd/MM/yyyy
        info.setBoardTime(String.format("%d/%d/%d", date.getDate(), date.getMonth() + 1, date.getYear() + 1900));

        String gateNumber = String.valueOf((int) (Math.random() * 100));
        info.setGateNumber(gateNumber);

        Date now = new Date();
        info.setDate(String.format("%d/%d/%d", now.getDate(), now.getMonth() + 1, now.getYear() + 1900));

        String flightNumber = String.valueOf((int) (Math.random() * 1000));
        info.setFlightId(flightNumber);

        String seatCode = String.valueOf((char) (Math.random() * 26 + 'A')) + (int) (Math.random() * 100);
        info.setSeatCode(seatCode);

        info.setConnections(flight.isDirectFlight() ? "Directo" : "Con " + flight.countConnections() + " escalas");
        return info;
    }
}
