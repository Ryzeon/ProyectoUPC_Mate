module me.ryzeon.projectoupc_mate {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens me.ryzeon.mate to javafx.fxml;
    exports me.ryzeon.mate;
}