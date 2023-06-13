package gui;

import javafx.scene.layout.Pane;
import model.AbstractCourt;


public abstract class View {
    private final AllScene allScene;
    private final AbstractCourt court;
    private final Pane root;
    private final double scale, xMargin = 50.0, racketThickness = 10.0;

    public View(AbstractCourt court, Pane root, double scale, AllScene allScene) {
        this.court = court;
        this.root = root;
        this.scale = scale;
        this.allScene = allScene;

        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * scale);
    }

    public AbstractCourt getCourt() {
        return court;
    }

    public double getXMargin() {
        return xMargin;
    }

    public double getRacketThickness() {
        return racketThickness;
    }

    public Pane getRoot() {
        return root;
    }

    public double getScale() {
        return scale;
    }

    public AllScene getAllScene() {
        return allScene;
    }
}