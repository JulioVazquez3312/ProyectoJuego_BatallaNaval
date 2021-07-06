package sample.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.conexion.ClienteSocket;
import sample.conexion.ServidorSocket;

public class TableroEnemigo extends Stage implements EventHandler {
    private Scene escena;
    private Label lblInstructions;
    private HBox hBoxBtn[], hBoxCoordinates, hBoxGrid;
    private VBox vBoxBtn, vBoxCoordinates, vBoxSelection;
    private Button btnBoardSelection[][], btnVerticalCoordinates[], btnHorizontalCoordinates[];
    private String[] horizontalCoordinates = {"0","1","2","3","4","5","6","7","8","9"};
    private String[] verticalCoordinates   = {"10","20","30","40","50","60","70","80","90","100"};
    private ClienteSocket client  = new ClienteSocket();
    private ServidorSocket server = new ServidorSocket();
    private TurnoJugador turn;
    private Ganador win;
    private int aliadeCount = 0, enemyCount = 0;

    public TableroEnemigo(String imgPosition[], String[] imgEnemyPosition,int total){
        UICreate(imgPosition, imgEnemyPosition,total);
        this.setTitle("Tablero Enemigo -- Jugador 2");
        this.setScene(escena);
        this.show();
    }

    public void UICreate(String imgPosition[], String[] imgEnemyPosition,int total){
        turn = new TurnoJugador();
        lblInstructions = new Label("Selecciona una posicion");
        hBoxBtn = new HBox[10];
        hBoxGrid= new HBox();
        vBoxBtn = new VBox();
        vBoxSelection = new VBox();
        hBoxCoordinates= new HBox();
        vBoxCoordinates = new VBox();
        btnBoardSelection = new Button[10][9];
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
                final int a = x;
                final int b = y;
                btnBoardSelection[x][y] = new Button(verticalCoordinates[x]+" - "+horizontalCoordinates[y+1]);
                btnBoardSelection[x][y].setPrefSize(50,50);
                btnBoardSelection[x][y].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        turn.closeScreen(true);
                        String position = "" + event.getSource();
                        client.connectToServer(pushedButton(position));
                        for(int x = 0; x < total; x++){
                            String enemy = pushedButton(position);
                            String enemyPosition = imgEnemyPosition[x];
                            if(enemy.equals(enemyPosition)){
                                enemyCount++;
                                imgEnemyPosition[x] = "";
                                if(enemyCount == total){
                                    win = new Ganador(true);
                                    break;
                                }
                            }
                        }
                        if(enemyCount != total){
                            String barco = server.iniciarServidor();
                            for(int i = 0; i < total; i++){
                                if(barco.equals(imgPosition[i])){
                                    aliadeCount++;
                                    imgPosition[i] = "";
                                    if(aliadeCount == total){
                                        win = new Ganador(false);
                                    }
                                }
                            }
                            turn.show();
                        }
                    }
                });
                hBoxBtn[x].getChildren().addAll(btnBoardSelection[x][y]);
            }
            vBoxSelection.getChildren().addAll(hBoxBtn[x]);
        }

        hBoxGrid.getChildren().addAll(vBoxCoordinates,vBoxSelection);
        vBoxBtn.getChildren().addAll(lblInstructions,hBoxCoordinates, hBoxGrid);

        escena = new Scene(vBoxBtn, 600, 635);
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
