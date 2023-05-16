package me.ryzeon.mate.screens;

import lombok.AllArgsConstructor;

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
    SIGN_UP("sign-in"),
    PRINCIPAL_PAGE("principal_page"),

    BUY_TICKET("buy_ticket"),

    TICKET_INFO("flight_ticket"),
    ;

    private final String screenName;

    public String getScreenName() {
        return screenName + ".fxml";
    }
}