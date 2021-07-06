package sample.views;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.EventHandler;

public class Ganador extends Stage implements EventHandler {

    private Scene escena;
    private Label lblInstructions;
    private Button btn;
    private VBox vbox;
    private String mensaje;

    public Ganador(boolean result){
        UICreate(result);
        this.setTitle("Batalla naval -- Jugador 2");
        this.setScene(escena);
        this.show();
    }

    private void UICreate(boolean result) {
        if(result == true){
            mensaje = "¡¡Ganaste el juego¡¡\n\n";
        } else{
            mensaje = "Perdiste\n\n";
        }
        lblInstructions = new Label(mensaje);
        btn = new Button("Aceptar");
        btn.setPrefSize(150,70);
        vbox = new VBox();

        btn.setOnAction(event -> {
            System.exit(0);
        });

        vbox.getChildren().addAll(lblInstructions, btn);
        vbox.setAlignment(Pos.CENTER);
        escena = new Scene(vbox,400,300);
        escena.getStylesheets().add(getClass().getResource("../assets/estilo.css").toExternalForm());
    }


    @Override
    public void handle(Event event) {

    }
}
