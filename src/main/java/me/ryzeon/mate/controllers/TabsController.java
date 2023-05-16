package me.ryzeon.mate.controllers;

import javafx.scene.control.TabPane;
import lombok.Getter;
import me.ryzeon.mate.screens.IScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class TabsController implements IScreenController<TabsController> {

    @Getter
    private static TabsController tabsController;

    public TabPane tab_view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabsController = this;
    }
}
