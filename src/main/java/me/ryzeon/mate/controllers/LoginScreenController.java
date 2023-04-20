package me.ryzeon.mate.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import me.ryzeon.mate.screens.IScreenController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/19/23 @ 14:46
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class LoginScreenController implements IScreenController<LoginScreenController> {

    public MFXToggleButton test;
    public MFXTextField username;
    public MFXPasswordField password;
    public MFXButton login;
    public MFXButton login1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        test.setOnMouseClicked(mouseEvent -> {
            System.out.println("Clicked");
        });
    }
}
