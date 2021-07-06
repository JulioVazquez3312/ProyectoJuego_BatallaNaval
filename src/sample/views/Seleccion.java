package sample.views;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.conexion.ServidorSocket;
import sample.conexion.ClienteSocket;


public class Seleccion extends Stage implements EventHandler {

    private Scene escena;
    private Label lblInstructions;
    private HBox hBoxBtn[], hBoxCoordinates, hBoxGrid;
    private VBox vBoxBtn, vBoxCoordinates, vBoxSelection;
    private Button btnBoardSelection[][], btnVerticalCoordinates[], btnHorizontalCoordinates[], btnContinue;
    private String[] horizontalCoordinates = {"0","1","2","3","4","5","6","7","8","9"};
    private String[] verticalCoordinates = {"10","20","30","40","50","60","70","80","90","100"};
    private String[] imgPositions;
    private Integer arrPosition;
    private ServidorSocket server;
    private TurnoJugador turn;

    public Seleccion(int quant, String[] imgEnemyPosition){
        turn = new TurnoJugador();
        UICreate(quant, imgEnemyPosition);
        this.setTitle("Selecciona del jugador 2");
        this.setScene(escena);
        this.show();
    }

    public void UICreate(int quant, String[] imgEnemyPosition){
        lblInstructions = new Label("Selecciona un campo con la coordenada 'n-n' para establecer el barco, tienes " + quant + " disponibles");
        hBoxBtn= new HBox[10];
        hBoxGrid= new HBox();
        vBoxBtn = new VBox();
        vBoxSelection= new VBox();
        hBoxCoordinates= new HBox();
        vBoxCoordinates = new VBox();
        imgPositions= new String[quant];
        btnContinue= new Button("Siguiente");
        arrPosition= 0;
        btnBoardSelection= new Button[10][9];
        btnVerticalCoordinates= new Button[10];
        btnHorizontalCoordinates = new Button[10];

        for(int h = 0; h < horizontalCoordinates.length; h++){
            btnHorizontalCoordinates[h] = new Button(horizontalCoordinates[h]);
            btnHorizontalCoordinates[h].setPrefSize(50,50);
            btnHorizontalCoordinates[h].setDisable(true);
            hBoxCoordinates.getChildren().addAll(btnHorizontalCoordinates[h]);
        }
        for(int v = 0; v < verticalCoordinates.length; v++){
            btnVerticalCoordinates[v] = new Button(verticalCoordinates[v]);
            btnVerticalCoordinates[v].setPrefSize(50,50);
            btnVerticalCoordinates[v].setDisable(true);
            vBoxCoordinates.getChildren().addAll(btnVerticalCoordinates[v]);
        }
        for(int x = 0; x < 10; x++){
            hBoxBtn[x] = new HBox();
            for(int y = 0; y < 9; y++){
                btnBoardSelection[x][y] = new Button(verticalCoordinates[x]+" - "+horizontalCoordinates[y+1]);
                btnBoardSelection[x][y].setPrefSize(50,50);
                btnBoardSelection[x][y].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String position = "" + event.getSource();
                        if(arrPosition != quant) {
                            imgPositions[arrPosition] = pushedButton(position);
                            new ClienteSocket().connectToServer(imgPositions[arrPosition]);
                            arrPosition += 1;
                        }
                        else{
                            Alert dialAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            dialAlert.setTitle("Advertencia");
                            dialAlert.setHeaderText(null);
                            dialAlert.setContentText("No quedan barcos, porque ya los usaste todos!");
                            dialAlert.initStyle(StageStyle.UTILITY);
                            dialAlert.showAndWait();
                        }
                    }
                });

                hBoxBtn[x].getChildren().addAll(btnBoardSelection[x][y]);
            }
            vBoxSelection.getChildren().addAll(hBoxBtn[x]);
        }

        btnContinue.setOnAction(event -> {
            if(arrPosition == quant){
                turn.closeScreen(true);
                new TableroAliado(imgPositions);
                new TableroEnemigo(imgPositions, imgEnemyPosition,quant);
                server = new ServidorSocket();
                server.iniciarServidor();
                this.close();
            } else{
                Alert dialAlert = new Alert(Alert.AlertType.CONFIRMATION);
                dialAlert.setTitle("Aviso");
                dialAlert.setHeaderText(null);
                dialAlert.setContentText("Aún te faltan barcos" + arrPosition);
                dialAlert.initStyle(StageStyle.UTILITY);
                dialAlert.showAndWait();
            }
        });

        hBoxGrid.getChildren().addAll(vBoxCoordinates,vBoxSelection);
        vBoxBtn.getChildren().addAll(lblInstructions,hBoxCoordinates, hBoxGrid, btnContinue);

        escena = new Scene(vBoxBtn, 560, 600);
        escena.getStylesheets().add(getClass().getResource("../assets/estilo.css").toExternalForm());
    }

    private String pushedButton(String entrada){
        String chain = "";
        for(int i = 0; i < entrada.length(); i++){
            if(entrada.charAt(i) == '\''){
                for(int j = i+1; j < entrada.length()-1; j++){
                    chain += entrada.charAt(j);
                }
                break;
            }
        }
        return chain;
    }

    @Override
    public void handle(Event event) {

    }
}

