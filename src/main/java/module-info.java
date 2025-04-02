module com.example.sla {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;

    opens com.example.sla to javafx.fxml;
    opens com.example.sla.Controller to javafx.fxml;
    opens com.example.sla.Entity to org.hibernate.orm.core, javafx.base;

    exports com.example.sla;
    exports com.example.sla.Controller;
    exports com.example.sla.Entity;
    exports com.example.sla.DAO;
}