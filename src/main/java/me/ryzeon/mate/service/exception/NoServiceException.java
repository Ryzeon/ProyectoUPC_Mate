package me.ryzeon.mate.service.exception;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/2/23 @ 22:38
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class NoServiceException extends RuntimeException{

        public NoServiceException(Class<?> clazz) {
            super("The service " + clazz.getSimpleName() + " is not a service!");
        }
}
