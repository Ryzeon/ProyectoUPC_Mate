package me.ryzeon.mate.service.exception;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/2/23 @ 22:37
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class AlreadyRegisterException extends RuntimeException {

    public AlreadyRegisterException(Class<?> clazz) {
        super("The service " + clazz.getSimpleName() + " is already registered.");
    }
}
