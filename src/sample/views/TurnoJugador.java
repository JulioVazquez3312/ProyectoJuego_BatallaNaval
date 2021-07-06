package sample.views;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TurnoJugador extends Stage implements EventHandler {
    private Scene escena;
    private Label label;
    private VBox vbox;

    public TurnoJugador() {
        UICreate();
        this.setTitle("Espera... para el turno...");
        this.setScene(escena);
        this.show();
    }

    public void UICreate() {
        label = new Label("Turno del jugador 2");
        vbox = new VBox();

        vbox.getChildren().addAll(label);
        escena = new Scene(vbox, 400, 300);
        escena.getStylesheets().add(getClass().getResource("../assets/estilo.css").toExternalForm());
    }

    public void closeScreen(boolean flag) {
        if (flag == true) {
            this.close();
        }
    }

    @Override
    public void handle(Event event) {

    }
}

