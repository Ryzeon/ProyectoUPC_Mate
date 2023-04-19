package me.ryzeon.mate.screens;

import javafx.fxml.Initializable;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/19/23 @ 16:07
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public interface IScreenController<T> extends Initializable {

    default T getController() {
        return (T) this;
    }

}
