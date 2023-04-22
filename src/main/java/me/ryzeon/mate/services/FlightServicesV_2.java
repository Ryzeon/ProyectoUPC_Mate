package me.ryzeon.mate.services;

import me.ryzeon.mate.service.IService;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/22/23 @ 06:53
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */public class FlightServicesV_2 implements IService {

    @Override
    public void enable() {
        Boolean[][] a = createBooleanArray(1000, 1000);
        Boolean[][] b = createBooleanArray(1000, 1000);
        multiply(a, b);
    }

    @Override
    public void disable() {

    }

    public boolean[][] multiply(Boolean[][] a, Boolean[][] b) {
        long startTime = System.currentTimeMillis();
        int rowsInA = a.length;
        int columnsInA = a[0].length; // same as rows in B
        int columnsInB = b[0].length;
        boolean[][] c = new boolean[rowsInA][columnsInB];
        for (int i = 0; i < rowsInA; i++) {
            for (int k = 0; k < columnsInA; k++) {
                if (a[i][k]) {
                    for (int j = 0; j < columnsInB; j++) {
                        c[i][j] |= b[k][j];
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
        return c;
    }

    public Boolean[][] createBooleanArray(int rows, int columns) {
        Boolean[][] array = new Boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j] = ThreadLocalRandom.current().nextBoolean();
            }
        }
        return array;
    }
}
