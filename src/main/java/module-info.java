/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 5/4/23 @ 01:39
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
module me.ryzeon.mate {
    requires MaterialFX;
    requires FXAlert;
    requires static lombok;
    requires java.naming;

    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.sql;

    opens me.ryzeon.mate.start;
    opens me.ryzeon.mate.model.user;
    opens me.ryzeon.mate.model.flight;
    opens me.ryzeon.mate.model.travel;
    opens me.ryzeon.mate.screens;
    opens me.ryzeon.mate.controllers;
    opens me.ryzeon.mate.controllers.tabs;
    opens me.ryzeon.mate.controllers.view;
    opens me.ryzeon.mate;

    exports me.ryzeon.mate;
}
