package states;

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
    private Configurator cfg;
    private DrawPanel drawPanel;
    private Entity entity;
    private Timer mainTimer;

    //methods
    public MainState(){
        this.width = 800;
        this.height = 800;
        this.cfg = Configurator.getInstance();
        this.drawPanel = new DrawPanel(this);
        this.entity = new Entity(0, 0, 20, 30);
    }

    @Override
    public void run(){
        this.initDisp();

        //timer
         mainTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tick();
                drawPanel.repaint();
                System.out.println("timer inside");
            }
        });
        mainTimer.start();
    }

    public void render(Graphics g){
        this.entity.render(g);
        System.out.println("render inside");
    }

    public void tick(){
        this.entity.tick();
        System.out.println("tick inside");
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
