package me.ryzeon.mate.utils;

import lombok.experimental.UtilityClass;
import me.ryzeon.mate.model.user.User;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.services.UserService;

import java.io.File;

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
}
