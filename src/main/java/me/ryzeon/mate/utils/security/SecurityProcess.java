package me.ryzeon.mate.utils.security;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/22/23 @ 04:27
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public interface SecurityProcess {

    String encrypt(String text, String key) throws Exception;

    String decrypt(String text, String key) throws Exception;
}
