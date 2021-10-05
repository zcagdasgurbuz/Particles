module com.zeynelcgurbuz.particles {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.zeynelcgurbuz.particles to javafx.fxml;
    exports com.zeynelcgurbuz.particles;
    exports com.zeynelcgurbuz.particles.animation;
    exports com.zeynelcgurbuz.particles.redux;
    exports com.zeynelcgurbuz.particles.store;
}
