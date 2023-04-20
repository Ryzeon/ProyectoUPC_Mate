package me.ryzeon.mate.backend;

import me.ryzeon.mate.service.IService;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/19/23 @ 19:19
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class SQLService implements IService {

    @Override
    public void enable() {
        // Using h2 database for now
    }

    @Override
    public void disable() {

    }
}
