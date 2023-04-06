package me.ryzeon.mate;

import me.ryzeon.mate.model.IFlight;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.FlightService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

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
        ServiceContainer.enableServices();
        LOGGER.info(() -> "Registered container");
    }

    @Test
    void testFlight() {

        LogaritAnalysis.INSTANCE.start();
        IFlight flight = ServiceContainer.get(FlightService.class).searchFlight("Arequipa", "Puerto Maldonado");
        IFlight flight1 = ServiceContainer.get(FlightService.class).searchFlight("Puerto Maldonado", "Juliaca");
        IFlight flight2 = ServiceContainer.get(FlightService.class).searchFlight("Lima", "Cancun");
        LogaritAnalysis.INSTANCE.finish();
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
