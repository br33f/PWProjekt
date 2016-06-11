package gui;

import states.MainState;
import states.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Singleton.
 * Created by br33 on 11.06.2016.
 */
public class Configurator {
    //attributes
    public static Configurator obj = null;

    private int cars;

    private JPanel configPanel;
    private JLabel titleLabel;
    private JLabel carsLabel;
    private JTextField carsInput;
    private JButton okButton;

    //methods
    private Configurator() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                butClicked();
            }
        });
    }

    public static Configurator getInstance(){
        if(Configurator.obj == null)
            return new Configurator();
        else
            return Configurator.obj;
    }

    public JPanel getConfigPanel() {
        return configPanel;
    }

    private void butClicked(){
        this.cars = (Integer.parseInt(this.carsInput.getText()) > 0) ? Integer.parseInt(this.carsInput.getText()) : 3;

        State.setState(new MainState());
        State.launch();
    }

    public int getCars() {
        return cars;
    }
}
