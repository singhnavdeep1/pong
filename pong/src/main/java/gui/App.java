package gui;

import gui.*;
import model.RacketController;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.RacketChangeante;
import model.*;
import javafx.scene.Scene;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        var playerA = new Player();
        var playerB = new Player();
        AllScene allScene = new AllScene(primaryStage, playerA, playerB);


        //Controle du clavier : change les etats des joueurs en fonction des touches enfoncees et relacheeeeeeees
        allScene.getScene().setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case CONTROL:
                    playerA.state = RacketController.State.GOING_UP;
                    break;
                case ALT:
                    playerA.state = RacketController.State.GOING_DOWN;
                    break;
                case UP:
                    playerB.state = RacketController.State.GOING_UP;
                    break;
                case DOWN:
                    playerB.state = RacketController.State.GOING_DOWN;
                    break;
            }
        });
        
        allScene.getScene().setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
                case CONTROL:
                    if (playerA.state == RacketController.State.GOING_UP) playerA.state = RacketController.State.IDLE;
                    break;
                    case ALT:
                    if (playerA.state == RacketController.State.GOING_DOWN) playerA.state = RacketController.State.IDLE;
                    break;
                case UP:
                    if (playerB.state == RacketController.State.GOING_UP) playerB.state = RacketController.State.IDLE;
                    break;
                case DOWN:
                    if (playerB.state == RacketController.State.GOING_DOWN) playerB.state = RacketController.State.IDLE;
                    break;
            }
        });
        
       // Initialisation de l'espace de jeu
        double[] iSizes = {100, 100};
        double[] iSpeeds = {300, 300};
        double[] cSizes = {1, 1};
        double[] cCoefs = {1, 1};
        double[] aCoefs = {1, 1};
        var court = new CustomCourt(1000.0, 600.0, playerA, playerB, 2, iSizes, iSpeeds, null, 100, cSizes, cCoefs, aCoefs); 		//Mode de jeu en fontion du type de court
        var gameView = new CustomView(court, (Pane) allScene.getRoot(), 1.0, allScene);
        
        allScene.setMenuScene();
    }
}

