package states;

import javafx.embed.swing.JFXPanel;

import javax.swing.*;

/**
 * Created by br33 on 11.06.2016.
 */
public abstract class State
{
    //attributes
    private static JFrame frame = null;
    private static State currentState = null;

    //methods
    /**
     * Statyczna metoda przestawiająca aktualny stan.
     * @param state stan który ma być ustawiony jako aktualny.
     */
    public static void setState(State state)
    {
        State.currentState = state;
    }

    /**
     * Statyczna metoda zwracająca aktualny stan.
     * @return obiekt State - stan
     */
    public static State getState()
    {
        return State.currentState;
    }

    public static void setFrame(JFrame frame){
        State.frame = frame;
    }

    public static JFrame getFrame(){
        return State.frame;
    }

    public static void launch(){
        State.getState().run();
    }

    public abstract void run();

}