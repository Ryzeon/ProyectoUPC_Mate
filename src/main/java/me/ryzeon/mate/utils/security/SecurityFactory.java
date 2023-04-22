package me.ryzeon.mate.utils.security;

import lombok.Getter;
import me.ryzeon.mate.utils.security.impl.AESSecurity;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/22/23 @ 04:26
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class SecurityFactory {
    private static SecurityFactory factory;

    @Getter
    private final SecurityProcess security = new AESSecurity();

    private final Random secureRandom = new SecureRandom();

    public static SecurityFactory getFactory() {
        synchronized (SecurityFactory.class) {
            if (factory == null) {
                factory = new SecurityFactory();
            }
        }
        return factory;
    }

    public String createRandomSecretKey() {
        int keyLength = secureRandom.nextInt(18) + 10;
        StringBuilder builder = new StringBuilder();
        while (builder.length() < keyLength) {
            String ALPHA_NUMERIC_STRING_LOWER_UPPER_SPECIAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{};':,./<>?`~";
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING_LOWER_UPPER_SPECIAL.length());
            builder.append(ALPHA_NUMERIC_STRING_LOWER_UPPER_SPECIAL.charAt(character));
        }
        return builder.toString();
    }
}
