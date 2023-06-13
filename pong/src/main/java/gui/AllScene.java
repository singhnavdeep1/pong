package gui;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

import model.*;

import javafx.scene.Parent;

public class AllScene {

    private Stage stage;
    private Scene scene;
    private Pane root;
    private View view;
    private AbstractCourt court;
    private Player playerA, playerB;

    public AllScene(Stage stage,Player playerA, Player playerB) {
   
        this.root = new Pane();
        this.scene = new Scene(root);
        this.stage = stage;
        this.playerA = playerA;
        this.playerB = playerB;
    }

    public Scene getScene() {
        return scene;
    }

    public Parent getRoot() {
        return root;
    }

    public Stage getStage() {
        return stage;
    }

    //Jeu pong basique

    public void goToGame(Pane menuRoot) {
        menuRoot.getChildren().clear(); 
        court = new Court(playerA, playerB, 1000, 600);
        view = new GameView((Court) court, root, 1.0,this);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        ((GameView) view).animate();

    }

    public void goToAcceleration(Pane menuRoot) {
        menuRoot.getChildren().clear(); 
        court = new Acceleration(playerA, playerB, 1000, 600);
        var view = new AccelerationView((Acceleration) court, root, 1.0,this);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        view.animate();

    }

    public void goToRacketChangeante(Pane menuRoot) {
        menuRoot.getChildren().clear(); 
        court = new RacketChangeante(playerA, playerB, 1000, 600);
        var view = new RacketChangeanteView((RacketChangeante) court, root, 1.0,this);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        view.animate();

    }

    //Mode multi-objet
    public void goToSuperMode(Pane superMode) { //Mode de jeu de Fares aussi appelé CustomCourt
        superMode.getChildren().clear(); // on retire tous les elements
        double[] iSizes = {100, 100};
        double[] iSpeeds = {300, 300};
        double[] cSizes = {1, 1};
        double[] cCoefs = {1, 1};
        double[] aCoefs = {1, 1};
        court = new CustomCourt (1000, 600, playerA, playerB, 2, iSizes, iSpeeds,null, null, cSizes, cCoefs, aCoefs); //mettre constructeur de Fares
        var gameView = new CustomView((CustomCourt) court, superMode, 1.0, this);//classFares : ajoute les argument du constructeur de cette classe view stp
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        gameView.animate();
    }

    //passer du jeu au menu

    public void switchToMenu(Pane menuView) {
        menuView.getChildren().clear();
        court = new Court(playerA, playerB, 1000, 600);
        view = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
    }

    public void setMenuScene() {
        court = new Court(playerA, playerB, 1000, 600);
       // root.setStyle("-fx-background-color: #FF0000"); //Changement couleure bg
        view = new MenuView(court, root, 1.0, this);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}

