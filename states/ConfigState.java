package states;

import javax.swing.*;
import gui.Configurator;

/**
 * Created by br33 on 11.06.2016.
 */
public class ConfigState extends State {
    //attributes
    private Configurator configurator;
    private int width, height;

    //methods
    public ConfigState(){
        this.configurator = Configurator.getInstance();
        this.width = 600;
        this.height = 150;
    }

    public void run(){
        this.render();
    }

    public void render(){
        State.getFrame().setContentPane(this.configurator.getConfigPanel());
        State.getFrame().setVisible(true);
        State.getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        State.getFrame().setSize(this.width, this.height);
        State.getFrame().setFocusable(false);
        State.getFrame().setLocationRelativeTo(null);
    }

}
