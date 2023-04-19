package me.ryzeon.mate.screens;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/2/23 @ 22:52
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@AllArgsConstructor
public enum Screen {

    LOGIN("login-screen"),
    ;

    private final String screenName;

    public String getScreenName() {
        return screenName + ".fxml";
    }
}