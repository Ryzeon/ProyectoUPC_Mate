package me.ryzeon.mate.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import me.ryzeon.mate.screens.IScreenController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
    public Label warning;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        test.setOnMouseClicked(mouseEvent -> {
            System.out.println("Clicked");
        });
    }

    public void onLoginButtonClick(ActionEvent actionEvent) {
        VerifyLogin(username.getText(), password.getText());
    }

    private void VerifyLogin(String username, String password) {
        if (username.equals("Gustavo") && password.equals("1234")) {
            warning.setText("Loged in successfully");
        } else {
            warning.setText("Access denied: Invalid username or password");
        }
    }

}
