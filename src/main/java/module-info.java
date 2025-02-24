module com.example.sla {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;
    opens com.example.sla to javafx.fxml, org.hibernate.orm.core;
    exports com.example.sla;
    exports com.example.sla.Controller;
    opens com.example.sla.Controller to javafx.fxml, org.hibernate.orm.core;
    exports com.example.sla.Entity;
    opens com.example.sla.Entity to javafx.fxml, org.hibernate.orm.core;
    exports com.example.sla.DAO;
    opens com.example.sla.DAO to javafx.fxml, org.hibernate.orm.core;
}
