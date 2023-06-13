package gui;

import model.RacketController;

public class Player implements RacketController { 
                                                  
    public State state = State.IDLE;

    public State getState() {
        return state;
    }

    
}