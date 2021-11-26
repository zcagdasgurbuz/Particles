module com.zeynelcgurbuz.particles {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.zeynelcgurbuz.particles to javafx.fxml;
    exports com.zeynelcgurbuz.particles;
    exports com.zeynelcgurbuz.particles.animation;
    exports com.zeynelcgurbuz.particles.redux;
    exports com.zeynelcgurbuz.particles.store;
    exports com.zeynelcgurbuz.particles.ui;
    opens com.zeynelcgurbuz.particles.ui to javafx.fxml;
    exports com.zeynelcgurbuz.particles.store.actions;
    exports com.zeynelcgurbuz.particles.calculator;
    opens com.zeynelcgurbuz.particles.calculator to javafx.fxml;
}
