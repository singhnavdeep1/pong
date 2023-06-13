package model;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class MenuPrincipal{

    private final Text menu;
    private Button[] boutons;
    protected final double width, height;


    public MenuPrincipal(Text menu, String []titreBoutons,double width, double height){
        this.menu=menu;
        boutons = new Button[titreBoutons.length];
        for(int i = 0 ; i < boutons.length ; i++){
            boutons[i]=new Button(titreBoutons[i]);
        }
        this.width=width;
        this.height=height;
    }

    public MenuPrincipal(Text menu, int nbBoutons,double width, double height){
        this.menu = menu;
        boutons = new Button[nbBoutons];
        this.width=width;
        this.height=height;

    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Text getTitre(){return menu;}

    public Button getBoutons(int i){return boutons[i];}

    public boolean setListeBoutons(Button b, int i) {
        if (boutons[i] != null) return false;
        boutons[i] = b;
        return true;
      }
}