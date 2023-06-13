package gui;

//Ce fichier se charge des graphiques de l'espace de jeu

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.CustomCourt;

public class CustomView extends View {
    // class parameters
    private final CustomCourt court;
    private final Pane gameRoot; // main node of the game
    private final double scale;
    private final double xMargin = 50.0, racketThickness = 10.0; // pixels

    // children of the game main node
    private Rectangle racketA;
    private Rectangle racketB = null;
    private final Circle[] balls;
    private Text countdown = null;
    private final Text scoreboard;
    
    //Variables de debugging : ne doivent �tre visibles � l'ecran UNIQUEMENT EN PHASE DE TEST
    private final Text[] debug = null;

    /**
     * @param court le "modele" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le noeud racine dans la scene JavaFX dans lequel le jeu sera affiche
     * @param scale le facteur d'echelle entre les distances du modele et le nombre de pixels correspondants dans la vue
     */
    
    public CustomView(CustomCourt court, Pane root, double scale, AllScene allScene) {
    	//Initialisation des champs
    	super(court, root, scale, allScene);
        this.court = court;
        this.gameRoot = root;
        this.scale = scale;
        
        //Restriction de la taille minimale de la fen�tre

        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * scale);
        
        //Initialisation de la raquette du joueur A
        
        racketA = new Rectangle();
        racketA.setHeight(court.getRacket(0).getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.BLACK);
        //Positionnement
        racketA.setX(xMargin - racketThickness);
        racketA.setY(court.getRacket(0).getRacketY() * scale);
        
        //Initialisation de la raquette du joueur B
        
        if (court.getRacket(1) != null) {
        
	        racketB = new Rectangle();
	        racketB.setHeight(court.getRacket(1).getRacketSize() * scale);
	        racketB.setWidth(racketThickness);
	        racketB.setFill(Color.BLACK);
	        //Positionnement
	        racketB.setX(court.getWidth() * scale + xMargin);
	        racketB.setY(court.getRacket(1).getRacketY() * scale);
	        
        }
        
        //Initialisation des balles
        
        balls = new Circle[court.getBallAmnt()];
        for (int i = 0 ; i < balls.length ; i++) {
        	balls[i] = new Circle();
        	balls[i].setRadius(court.getBall(i).getBallRadius());
        	balls[i].setFill(Color.BLACK);
        	//Positionnement
        	balls[i].setCenterX(court.getBall(i).getBallX() * scale + xMargin);
        	balls[i].setCenterY(court.getBall(i).getBallY() * scale);
        }
        
        //Initialisation du texte
        
        scoreboard = new Text(400, 100, "0 : 0");
        scoreboard.setFont(new Font(100));
        
        if (court.getCountdown() != null) {
        	countdown = new Text(1100, 100, court.getCountdown() + " sec");
        	countdown.setFont(new Font(100));
        }
        
        //DEBUG : a commenter absolument avant de push, ne sert qu'aux tests
        
        //debug = new Text[10];
        
        //Ajout des elements a l'espace de jeu
        
        gameRoot.getChildren().addAll(racketA, scoreboard);
        if (racketB != null) gameRoot.getChildren().add(racketB);
        for (Circle c : balls) gameRoot.getChildren().add(c);
        if (countdown != null) gameRoot.getChildren().add(countdown);
        
        //DEBUG : Affichage
        //for(Text txt : debug) if (txt != null) gameRoot.getChildren().add(txt);

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
                court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(court.getRacket(0).getRacketY() * scale);
                racketA.setHeight(court.getRacket(0).getRacketSize());
                if (racketB != null) {
	                racketB.setY(court.getRacket(1).getRacketY() * scale);
	                racketB.setHeight(court.getRacket(1).getRacketSize());
                }
                for (int i = 0 ; i < balls.length ; i++) {
                	if (court.getBall(i) != null) {
                		balls[i].setCenterX(court.getBall(i).getBallX() * scale + xMargin);
	                	balls[i].setCenterY(court.getBall(i).getBallY() * scale);
                	} else {
                		//Effacer la balle de l'affichage
                	}
                }
                if (countdown != null) countdown.setText(court.getCountdown().intValue() + " sec");
                scoreboard.setText(court.getScore(0) + " : " + court.getScore(1)); //mise a jour du score 
                //DEBUG : Mise a jour
                //for (int i = 0 ; i < debug.length ; i++) {
                //}
            }
        }.start();
    }
}
