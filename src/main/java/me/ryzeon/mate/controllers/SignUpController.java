package me.ryzeon.mate.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import me.ryzeon.mate.FlightApplication;
import me.ryzeon.mate.model.user.User;
import me.ryzeon.mate.screens.IScreenController;
import me.ryzeon.mate.screens.Screen;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.UserService;
import me.ryzeon.mate.utils.Utils;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/22/23 @ 05:31
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class SignUpController implements IScreenController<SignUpController> {

    public MFXTextField username;
    public MFXTextField mail;
    public MFXButton register;
    public MFXButton go_back;
    public Label validatorText;
    public MFXPasswordField password_2;
    public MFXPasswordField password_1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        password_1.setLeadingIcon(new MFXIconWrapper("fas-lock", 16, Color.web("#4D4D4D"), 30));

        // Validators
        username.getValidator()
                .constraint("Username must be at least 4 characters long", username.textProperty().length().greaterThanOrEqualTo(4));
        username.getValidator()
                .constraint("Username must not contain special characters",
                        Utils.containsSpecialCharacter(username.textProperty()).not());
        password_1.getValidator()
                .constraint("Password must be at least 8 characters long", password_1.textProperty().length().greaterThanOrEqualTo(4));

        password_2.getValidator()
                        .constraint("Password must be the same", password_2.textProperty().isEqualTo(password_1.textProperty()));

        username.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().getName().equals("Enter")) {
                password_1.requestFocus();
            }
        });

        Utils.handlePressed(password_1, password_2, register, validatorText);
    }


    public void onGoBack(ActionEvent actionEvent) {
        try {
            FlightApplication.getInstance().switchTo(Screen.LOGIN);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    boolean validate() {
        if (Utils.validators(username, validatorText, password_1)) return false;
        MFXValidator validator;
        List<Constraint> constraints;

        validator = password_2.getValidator();
        constraints = validator.validate();
        if (!constraints.isEmpty()) {
            validatorText.setText(constraints.get(0).getMessage());
            validatorText.setManaged(true);
            return false;
        }

        validatorText.setText("");
        validatorText.setManaged(false);
        return true;
    }

    public void onRegister(ActionEvent actionEvent) {
        if (!validate()) return;
        UserService userService = ServiceContainer.bind(UserService.class);
        Optional<User> user = userService.getUser(username.getText());
        if (user.isPresent()) {
            validatorText.setText("User already exists");
            validatorText.setManaged(true);
            return;
        }
        user = userService.getUserByEmail(mail.getText());
        if (user.isPresent()) {
            validatorText.setText("Email already exists");
            validatorText.setManaged(true);
            return;
        }
        User newUser = new User();
        newUser.setUsername(username.getText());
        newUser.setEmail(mail.getText());
        newUser.setPassword(password_1.getText());
        userService.saveUser(newUser);

        validatorText.setTextFill(Color.GREEN);
        validatorText.setText("User created successfully");
        validatorText.setManaged(true);


        Platform.runLater(() -> {
            try {
                Thread.sleep(2000);
                FlightApplication.getInstance().switchTo(Screen.LOGIN);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }
}
