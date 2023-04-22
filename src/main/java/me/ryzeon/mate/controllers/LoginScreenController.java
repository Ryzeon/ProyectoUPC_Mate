package me.ryzeon.mate.controllers;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
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
 * Date: 4/19/23 @ 14:46
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class LoginScreenController implements IScreenController<LoginScreenController> {

    public MFXToggleButton keepLogin;
    public MFXTextField username;
    public MFXPasswordField password;
    public MFXButton login;
    public MFXButton signUp;
    public Label validatorText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Icons
        username.setLeadingIcon(new MFXIconWrapper("fas-user", 16, Color.web("#4D4D4D"), 30));
        password.setLeadingIcon(new MFXIconWrapper("fas-lock", 16, Color.web("#4D4D4D"), 30));

        // Validators
        username.getValidator()
                .constraint("Username must be at least 4 characters long", username.textProperty().length().greaterThanOrEqualTo(4));
        username.getValidator()
                .constraint("Username must not contain special characters",
                        containsSpecialCharacter(username.textProperty()).not());
        password.getValidator()
                .constraint("Password must be at least 8 characters long", password.textProperty().length().greaterThanOrEqualTo(4));

        username.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().getName().equals("Enter")) {
                password.requestFocus();
            }
        });
        password.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().getName().equals("Enter")) {
                login.fire();
            }
        });

        validatorText.setTextFill(Color.RED);
        validatorText.setPrefWidth(300);
        validatorText.setText("");
        validatorText.setManaged(false);
    }


    BooleanExpression containsSpecialCharacter(StringProperty textProperty) {
        String text = textProperty.get();
        if (text == null) {
            return Bindings.when(textProperty.isNull()).then(false).otherwise(false);
        }
        return Bindings.createBooleanBinding(() -> text.matches("[a-zA-Z0-9@_-]+"), textProperty);
    }

    boolean validate() {
        MFXValidator validator = username.getValidator();
        List<Constraint> constraints = validator.validate();
        if (!constraints.isEmpty()) {
            validatorText.setText(constraints.get(0).getMessage());
            validatorText.setManaged(true);
            return false;
        }
        validator = password.getValidator();
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

    @FXML
    public void onLoginButtonClick(ActionEvent actionEvent) {
        if (!validate()) {
            return;
        }
        UserService userService = ServiceContainer.get(UserService.class);
        Optional<User> user = userService.getUser(username.getText());
        if (user.isEmpty()) {
            validatorText.setText("User not found, please sign up");
            validatorText.setManaged(true);
            return;
        }
        if (password.textProperty().get().equals(userService.getNicePassword(user.get()))) {
            if (keepLogin.isSelected()) {
                Utils.saveLocalCredentials(user.get());
            }
            try {
                FlightApplication.getInstance().switchTo(Screen.PRINCIPAL_PAGE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            validatorText.setText("Wrong credentials.");
            validatorText.setManaged(true);
        }

    }

    @FXML
    public void onSignUp(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            try {
                FlightApplication.getInstance().switchTo(Screen.SIGN_UP);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    public void onFocus(ContextMenuEvent contextMenuEvent) {
        validate();
    }
}
