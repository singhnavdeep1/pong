package gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.AbstractCourt;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class MenuView extends View { 

    // children of the game main node
    private final Text title;
    private final Button start;

    /**
     * @param court le "modèle" de cette vue (le titre et le bouton)
     * 
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera
     *              affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre
     *              de pixels correspondants dans la vue
     */
    public MenuView(AbstractCourt court, Pane root, double scale, AllScene allScene) {
        super(court, root, scale, allScene);

        root.setMinWidth(court.getWidth() * scale + 2 * getXMargin());
        root.setMinHeight(court.getHeight() * scale);
    

        title = new Text(); 
        title.setX(((court.getWidth() / 4))); //* scale) - 20);
        title.setY(60);
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        title.setFill(Color.WHITE);
        title.setText("Bienvenue sur Pong !");

        start = new Button("pong");
        start.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        start.setLayoutY(((court.getHeight() / 2) * scale) - 40);
        start.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        start.setOnAction(event -> allScene.goToGame(getRoot())); // Lorsqu'on appuie sur le bouton, cela
                                                                         // enclanche la méthode switchToGame()

        Button startSuperMode = new Button("2 balles");
        startSuperMode.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        startSuperMode.setLayoutY(((court.getHeight() / 2) * scale) + 30);
        startSuperMode.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        startSuperMode.setOnAction(event -> allScene.goToSuperMode(getRoot())); // Lorsqu'on appuie sur le bouton, cela enclenche la méthode goToSuperMode

        Button startAcceleration = new Button("Acceleration");
        startAcceleration.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        startAcceleration.setLayoutY(((court.getHeight() / 2) * scale) + 100);
        startAcceleration.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        startAcceleration.setOnAction(event -> allScene.goToAcceleration(getRoot()));

        Button startRacketChangeante = new Button("Raquette changeante");
        startRacketChangeante.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        startRacketChangeante.setLayoutY(((court.getHeight() / 2) * scale) + 170);
        startRacketChangeante.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        startRacketChangeante.setOnAction(event -> allScene.goToRacketChangeante(getRoot()));
        //met le background du menu en noir
        
        Canvas canvas = new Canvas(court.getWidth() + 200, court.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
       
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
        getRoot().getChildren().add(canvas);
        
        getRoot().getChildren().addAll(title, start, startSuperMode,startAcceleration,startRacketChangeante); // On ajoute le title
        // et les boutons aux éléments du Pane

    }
    
    

}