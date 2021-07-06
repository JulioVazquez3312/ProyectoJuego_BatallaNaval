package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.conexion.ServidorSocket;
import sample.views.Seleccion;

public class Main extends Application {

    private Scene escena;
    private HBox hbox, hboxBotoncitos, hboxEtiquetas;
    private VBox vbox;
    private Button btn;
    private Label lbl;
    private TextField txtQuant;
    private ServidorSocket server;
    public Integer barcos = 0;
    public String posEnemiga[];

    @Override
    public void start(Stage primaryStage) throws Exception{

        CrearMenu();

        primaryStage.setTitle("Batalla naval -- Jugador 2");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("assets/barco.png")));
        primaryStage.setScene(escena);
        primaryStage.show();
    }

    private void CrearMenu() {
        hbox = new HBox();
        hboxEtiquetas = new HBox();
        hboxBotoncitos = new HBox();
        vbox = new VBox();
        lbl = new Label("\n\n\tBienvenido!!! \n ");
        txtQuant = new TextField();
        btn = new Button("Comenzar");
        btn.setPrefSize(150,70);
        btn.setOnAction(event -> localConnection());

        hboxBotoncitos.getChildren().addAll(btn);
        vbox.getChildren().addAll(lbl, hboxBotoncitos, hboxEtiquetas);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().addAll(vbox);

        escena = new Scene(hbox, 320, 200);
        escena.getStylesheets().add(getClass().getResource("assets/estilo.css").toExternalForm());
    }

    public void localConnection(){
        server = new ServidorSocket();
        int total = Integer.parseInt(server.iniciarServidor());
        posEnemiga = new String[total];
        shipEnemyPosition(total);
        new Seleccion(total, posEnemiga);
        hbox.setVisible(false);
    }

    public void shipEnemyPosition(int total){
        System.out.println(total);
        for(int i = 0; i < total; i++){
            posEnemiga[i] = server.iniciarServidor();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
