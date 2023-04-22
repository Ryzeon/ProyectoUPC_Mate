package me.ryzeon.mate;

import me.ryzeon.mate.backend.SQLService;
import me.ryzeon.mate.model.flight.IFlight;
import me.ryzeon.mate.model.user.User;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.FlightService;
import me.ryzeon.mate.services.FlightServicesV_2;
import me.ryzeon.mate.services.UserService;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.List;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/4/23 @ 16:26
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */

public class TestFlight {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestFlight.class);

    @BeforeAll
    @DisplayName("Registering container")
    static void initContainer() {
        ServiceContainer.register(FlightService.class);
        ServiceContainer.register(SQLService.class);
        ServiceContainer.register(UserService.class);
        ServiceContainer.register(FlightServicesV_2.class);
        ServiceContainer.enableServices();
        LOGGER.info(() -> "Registered container");
    }

    @Test
    void testMatricezBooleanas() {
        Boolean[][] matriz1 = {
                {true, false, true},
                {false, true, true},
                {true, true, false},
                {false, false, true}
        };
        Boolean[][] matriz2 = {
                {false, false},
                {true, true},
                {true, false}
        };

        boolean[][] result = ServiceContainer.get(FlightServicesV_2.class).multiply(matriz1, matriz2);

        for (boolean[] booleans : result) {
            for (boolean aBoolean : booleans) {
                System.out.print(aBoolean + " ");
            }
            System.out.println();
        }

    }

    @Test
    void testFlight() {

        LogaritAnalysis.INSTANCE.start();
//        IFlight flight = ServiceContainer.get(FlightService.class).searchFlight("Arequipa", "Puerto Maldonado");
//        IFlight flight1 = ServiceContainer.get(FlightService.class).searchFlight("Puerto Maldonado", "Juliaca");
//        IFlight flight2 = ServiceContainer.get(FlightService.class).searchFlight("Lima", "Cancun");
//        IFlight flight3 = ServiceContainer.get(FlightService.class).searchFlight("Trujillo", "Bogota");
//        IFlight flight4 = ServiceContainer.get(FlightService.class).searchFlight("Cusco", "Bogota");
//        IFlight flight5 = ServiceContainer.get(FlightService.class).searchFlight("Puerto Maldonado", "San Salvador");
        // TODO: Improve code to search flights more faster
        // TODO: Try to use async methods
        // TODO: Try to improve the algorithm and allow more 4 connections
        // TODO: Try to use a cache system with database
        List<IFlight> xd = ServiceContainer.get(FlightService.class).searchFlights("Trujillo", "Bogota", 2);
        for (IFlight iFlight : xd) {
            System.out.println(iFlight);
        }
        LogaritAnalysis.INSTANCE.finish();
    }

    @Test
    void insertUser() {
        User user = new User();
        user.setUsername("ryzeon");
        user.setPassword("123456");
        user.setEmail("xd@xd");

        Session session = ServiceContainer.get(SQLService.class).getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        User user1 = session.get(User.class, 1);
        System.out.println(user1);
        Assertions.assertNotNull(user1);

        User userByUsername = session.createQuery("from User where username = :username", User.class)
                .setParameter("username", "ryzeon")
                .getSingleResult();

        System.out.println(userByUsername);
        Assertions.assertNotNull(userByUsername);

        Assertions.assertEquals(user1, userByUsername);

        session.close();

    }

    static class LogaritAnalysis {

        public static final LogaritAnalysis INSTANCE = new LogaritAnalysis();

        private long start = System.currentTimeMillis();

        public void finish() {
            long end = System.currentTimeMillis();
            LOGGER.info(() -> "Time: " + (end - start) + "ms");
            boolean isEfective = (end - start) < 1000;
            String efective = isEfective ? "Efective" : "Not Efective";
            LOGGER.info(() -> "Efective: " + efective);
        }

        public void start() {
            this.start = System.currentTimeMillis();
        }
    }
}
