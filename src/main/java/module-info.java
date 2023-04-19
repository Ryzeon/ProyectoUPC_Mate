module me.ryzeon.projectoupc_mate {

    requires MaterialFX;
    requires VirtualizedFX;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;

    requires org.controlsfx.controls;

    requires org.kordamp.bootstrapfx.core;

    requires static lombok; // Lombok is only required at compile time, not at runtime.

    opens me.ryzeon.mate.start;
    opens me.ryzeon.mate.controllers;
    opens me.ryzeon.mate.screens;
    opens me.ryzeon.mate;
}