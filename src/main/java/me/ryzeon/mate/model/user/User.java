package me.ryzeon.mate.model.user;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/19/23 @ 19:16
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@Entity
@Table(name = "USERS")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "saltKey")
    private String saltKey;

    @Column(name = "email")
    private String email;
}
