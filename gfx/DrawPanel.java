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
    private int counter;

    //methods
    public DrawPanel(MainState mainState) {
        super();
        this.mainState = mainState;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(100, 100, 100, 100);

        g.drawString("Liczba: " + Integer.toString(this.counter), 400, 400);

        this.mainState.render(g);

        System.out.println("elo");
        this.counter++;
    }
}
