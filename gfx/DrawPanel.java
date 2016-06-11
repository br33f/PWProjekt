package gfx;

import states.MainState;

import javax.swing.*;
import javax.swing.text.html.parser.Entity;
import java.awt.*;

/**
 * Created by br33 on 11.06.2016.
 */
public class DrawPanel extends JPanel {
    //attributes
    private MainState mainState;

    //methods
    public DrawPanel(MainState mainState) {
        super();
        this.mainState = mainState;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.mainState.world.render(g);
    }
}
