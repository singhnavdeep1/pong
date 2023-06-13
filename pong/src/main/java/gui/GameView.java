package gui;

//Ce fichier se charge des graphiques de l'espace de jeu

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Court;
import javafx.scene.text.*;

public class GameView extends View {
    

    // children of the game main node
    private final Rectangle racketA, racketB;
    private final Circle ball;
    private final Text countdown;
    private final Text scoreboard;
    private final Button start;
    private static double deltaT = 0;
  

    
    //Variables de debugging : ne doivent etre visibles a l'ecran UNIQUEMENT EN PHASE DE TEST
    private final Text[] debug = null;

    /**
     * @param court le "modele" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le noeud racine dans la scene JavaFX dans lequel le jeu sera affiche
     * @param scale le facteur d'echelle entre les distances du modele et le nombre de pixels correspondants dans la vue
     */
    
    public GameView(Court court, Pane root, double scale, AllScene allScene) {
    	//Initialisation des champs
        super(court,root,scale,allScene);


        
        //Initialisation de la raquette du joueur A
        
        racketA = new Rectangle();
        racketA.setHeight(((Court) ((Court) getCourt())).getRacketA().getRacketSize() * scale);
        racketA.setWidth(getRacketThickness());
        racketA.setFill(Color.BLACK);
        //Positionnement
        racketA.setX(getXMargin() - getRacketThickness());
        racketA.setY(((Court) getCourt()).getRacketA().getRacketY() * scale);
        
        //Initialisation de la raquette du joueur B
        
        racketB = new Rectangle();
        racketB.setHeight(((Court) getCourt()).getRacketB().getRacketSize() * scale);
        racketB.setWidth(getRacketThickness());
        racketB.setFill(Color.BLACK);
        //Positionnement
        racketB.setX(((Court) getCourt()).getWidth() * scale + getXMargin());
        racketB.setY(((Court) getCourt()).getRacketB().getRacketY() * scale);
        
        //Initialisation de la balle
        
        ball = new Circle();
        ball.setRadius(((Court) getCourt()).getBall().getBallRadius());
        ball.setFill(Color.BLACK);
        //Positionnement
        ball.setCenterX(((Court) getCourt()).getBall().getBallX() * scale + getXMargin());
        ball.setCenterY(((Court) getCourt()).getBall().getBallY() * scale);
        
        //Initialisation du texte

        scoreboard = new Text(200,100, "0 : 0");
        scoreboard.setFont(new Font(100));
        
        countdown = new Text(600, 100, ((Court) getCourt()).getCountdown()/60 + " sec");
        countdown.setFont(new Font(100));

        start = new Button("Menu");
        start.setLayoutX(((court.getWidth() / 2) * scale) - 5);
        start.setLayoutY(((court.getHeight() / 2) * scale) - 60);
        start.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        start.setOnAction(event -> allScene.goToGame(getRoot()));
        
        //DEBUG : à commenter absolument avant de push, ne sert qu'aux tests
        
        //debug = new Text[10];
        
        //Ajout des éléments à l'espace de jeu
        
        getRoot().getChildren().addAll(racketA, racketB, ball, countdown, scoreboard);
        
        //DEBUG : Affichage
        //for(Text txt : debug) if (txt != null) gameRoot.getChildren().add(txt);

       

    }

    public static double getDeltaT() {
        return deltaT;
    }


    public void animate() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                deltaT = (now - last) * 1.0e-9;
                ((Court) getCourt()).update(deltaT); // convert nanoseconds to seconds
                last = now;
                racketA.setY(((Court) getCourt()).getRacketA().getRacketY() * getScale());
                racketA.setHeight(((Court) getCourt()).getRacketA().getRacketSize());
                racketB.setY(((Court) getCourt()).getRacketB().getRacketY() * getScale());
                racketB.setHeight(((Court) getCourt()).getRacketB().getRacketSize());
                ball.setCenterX(((Court) getCourt()).getBall().getBallX() * getScale() + getXMargin());
                ball.setCenterY(((Court) getCourt()).getBall().getBallY() * getScale());
                countdown.setText((int) ((Court) getCourt()).getCountdown() + " sec");
                scoreboard.setText(((Court) getCourt()).getScoreA() + " : " + ((Court) getCourt()).getScoreB()); //mise a jour du score 
                //DEBUG : Mise à jour
                //for (int i = 0 ; i < debug.length ; i++) {
                //}
            }
        }.start();
    }
}