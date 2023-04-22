package me.ryzeon.mate.utils;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import lombok.experimental.UtilityClass;
import me.ryzeon.mate.model.user.User;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.UserService;

import java.io.File;
import java.util.List;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/2/23 @ 21:28
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@UtilityClass
public class Utils {


    public void saveLocalCredentials(User user) {
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir + "/.mate");
        if (!file.exists()) {
            file.mkdir();
        }
        File file1 = new File(homeDir + "/.mate/credentials");
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String nicePassword = ServiceContainer.get(UserService.class).getNicePassword(user);
        String content = user.getUsername() + ":" + nicePassword;
        try {
            java.nio.file.Files.write(file1.toPath(), content.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BooleanExpression containsSpecialCharacter(StringProperty textProperty) {
        String text = textProperty.get();
        if (text == null) {
            return Bindings.when(textProperty.isNull()).then(false).otherwise(false);
        }
        return Bindings.createBooleanBinding(() -> text.matches("[a-zA-Z0-9@_-]+"), textProperty);
    }

    public String[] recoverPassword() {
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir + "/.mate");
        if (!file.exists()) {
            file.mkdir();
        }
        File file1 = new File(homeDir + "/.mate/credentials");
        if (!file1.exists()) {
            return null;
        }
        try {
            String content = new String(java.nio.file.Files.readAllBytes(file1.toPath()));
            return content.split(":");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validators(MFXTextField username, Label validatorText, MFXPasswordField password) {
        MFXValidator validator = username.getValidator();
        List<Constraint> constraints = validator.validate();
        if (!constraints.isEmpty()) {
            validatorText.setText(constraints.get(0).getMessage());
            validatorText.setManaged(true);
            return true;
        }
        validator = password.getValidator();
        constraints = validator.validate();
        if (!constraints.isEmpty()) {
            validatorText.setText(constraints.get(0).getMessage());
            validatorText.setManaged(true);
            return true;
        }
        return false;
    }

    public void handlePressed(MFXTextField password_1, MFXPasswordField password_2, MFXButton register, Label validatorText) {
        password_1.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().getName().equals("Enter")) {
                password_2.requestFocus();
            }
        });

        password_2.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().getName().equals("Enter")) {
                register.fire();
            }
        });

        validatorText.setTextFill(Color.RED);
        validatorText.setPrefWidth(300);
        validatorText.setText("");
        validatorText.setManaged(false);
    }
}
