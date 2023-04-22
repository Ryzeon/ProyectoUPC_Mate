package me.ryzeon.mate.screens;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/19/23 @ 16:21
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@Getter
@ToString
public class ScreenLoader {
    private final FXMLLoader loader;
    private Parent parent;
    private final IScreenController<?> controller;

    private Bounds bounds;

    public ScreenLoader(FXMLLoader loader) throws IOException {
        this.loader = loader;
        this.controller = loader.getController();
    }

    public Parent loadScreen() throws IOException {
        this.parent = loader.load();
        this.bounds = parent.getBoundsInLocal();
        return parent;
    }
}