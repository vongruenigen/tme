package org.zhaw.tme;

import java.util.ArrayList;

public class State {
    private String name;
    private ArrayList<Transition> transitions;

    public State(String name) {
        this.name = name;
        this.transitions = new ArrayList<Transition>();
    }

    public void addTransition(State toState, String symbolUnderCursor,
                              String symbolToWriteBack, HeadDirection direction) {
        this.addTransition(new Transition(toState, symbolUnderCursor, symbolToWriteBack, direction));
    }

    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }

    public ArrayList<Transition> getTransitions() {
        return this.transitions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
