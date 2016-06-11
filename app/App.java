package app;

import gui.Configurator;
import states.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by br33 on 11.06.2016.
 */
public class App {
    //attributes
    private JFrame frame;
    private ConfigState configState;
    private BufferedImage img;

    //methods
    public App(){
        this.configState = new ConfigState();
        this.frame = new JFrame("Projekt PW v1.0");
        State.setFrame(this.frame);
        ImageIcon icon = new ImageIcon("ikona.png");
        this.frame.setIconImage(icon.getImage());
       /* try {
            img = ImageIO.read(new File("tlo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image background=img.getScaledInstance(800,800,Image.SCALE_SMOOTH);
        ImageIcon imageIcon=new ImageIcon(background);
        this.frame.setContentPane(new JLabel("tlo.png"));*/
    }

    private void launch(){
        State.setState(this.configState);
        State.launch();
    }

    public static void main(String[] args) {
        App app = new App();
        app.launch();
    }
}
