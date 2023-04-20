package me.ryzeon.mate.model.user;

import lombok.Data;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/19/23 @ 19:16
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@Data
public class UserModel implements IData {

    private final String ID;
    private final String username;
    private final String password;
    private final String email;

}
