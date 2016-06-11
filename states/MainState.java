package states;

import app.World;
import gfx.DrawPanel;
import gui.Configurator;
import entitites.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by br33 on 11.06.2016.
 */
public class MainState extends State {
    //attributes
    private int width, height;
    private DrawPanel drawPanel;
    private Timer mainTimer;
    public World world;

    //methods
    public MainState(){
        this.width = 800;
        this.height = 800;
        this.drawPanel = new DrawPanel(this);
        this.world = new World();
    }

    @Override
    public void run(){
        this.initDisp();

        //timer
         mainTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                world.tick();
                drawPanel.repaint();
                //System.out.println("timer inside");
            }
        });
        mainTimer.start();
    }

    public void initDisp() {
        JFrame frame = State.getFrame();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(this.width, this.height);
        frame.setFocusable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(this.drawPanel);

    }
}
