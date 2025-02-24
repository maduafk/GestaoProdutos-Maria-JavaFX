module com.example.sla {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;
    opens com.example.sla to javafx.fxml, org.hibernate.orm.core;
    exports com.example.sla;
}
