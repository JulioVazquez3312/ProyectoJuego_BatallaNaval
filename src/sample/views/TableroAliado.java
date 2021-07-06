package sample.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TableroAliado extends Stage implements EventHandler {
    private Scene escena;
    private Label lblInstructions;
    private HBox hBoxBtn[], hBoxCoordinates, hBoxGrid;
    private VBox vBoxBtn, vBoxCoordinates, vBoxSelection;
    private Button btnBoardSelection[][], btnVerticalCoordinates[], btnHorizontalCoordinates[];
    private String[] horizontalCoordinates = {"0","1","2","3","4","5","6","7","8","9"};
    private String[] verticalCoordinates   = {"10","20","30","40","50","60","70","80","90","100"};
    private String[] imgPositions;

    public TableroAliado(String[] imgPositions){
        UICreate(imgPositions);
        this.setTitle("Este es tu tablero con tus naves -- Jugador 2");
        this.setScene(escena);
        this.show();
    }

    public void UICreate(String[] imgPositions){
        lblInstructions = new Label("Estos son tus barcos con la cantidad total de ellos");
        hBoxBtn= new HBox[10];
        hBoxGrid= new HBox();
        vBoxBtn= new VBox();
        vBoxSelection = new VBox();
        hBoxCoordinates = new HBox();
        vBoxCoordinates = new VBox();
        btnBoardSelection = new Button[10][9];
        btnVerticalCoordinates = new Button[10];
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
                for(int i = 0; i < imgPositions.length; i++){
                    if((verticalCoordinates[x]+" - "+horizontalCoordinates[y+1]).equals(imgPositions[i])){
                        Image img = new Image("sample/assets/barco.png");
                        ImageView imv = new ImageView(img);
                        imv.setFitHeight(48);
                        imv.setPreserveRatio(true);
                        btnBoardSelection[x][y].setGraphic(imv);
                    }
                }
                hBoxBtn[x].getChildren().addAll(btnBoardSelection[x][y]);
            }
            vBoxSelection.getChildren().addAll(hBoxBtn[x]);
        }

        hBoxGrid.getChildren().addAll(vBoxCoordinates,vBoxSelection);
        vBoxBtn.getChildren().addAll(lblInstructions,hBoxCoordinates, hBoxGrid);

        escena = new Scene(vBoxBtn, 600, 635);
        escena.getStylesheets().add(getClass().getResource("../assets/estilo.css").toExternalForm());
    }

    @Override
    public void handle(Event event) {

    }
}

