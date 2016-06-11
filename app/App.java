package app;

import gui.Configurator;
import states.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by br33 on 11.06.2016.
 */
public class App {
    //attributes
    private JFrame frame;
    private ConfigState configState;

    //methods
    public App(){
        this.configState = new ConfigState();
        this.frame = new JFrame("Projekt PW v0.2");
        State.setFrame(this.frame);
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
