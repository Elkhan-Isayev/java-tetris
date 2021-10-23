module com.encom.simpletetris {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.encom.simpletetris to javafx.fxml;
    exports com.encom.simpletetris;
}