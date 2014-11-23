package org.zhaw.tme;

public class Transition {
    private State toState;
    private String symbolUnderCursor;
    private String symbolToWriteBack;
    private HeadDirection direction;

    public Transition(State toState, String symbolUnderCursor, String symbolToWriteBack, HeadDirection direction) {
        this.toState = toState;
        this.symbolUnderCursor = symbolUnderCursor;
        this.symbolToWriteBack = symbolToWriteBack;
        this.direction = direction;
    }

    public State getToState() {
        return toState;
    }

    public void setToState(State toState) {
        this.toState = toState;
    }

    public String getSymbolUnderCursor() {
        return symbolUnderCursor;
    }

    public void setSymbolUnderCursor(String symbolUnderCursor) {
        this.symbolUnderCursor = symbolUnderCursor;
    }

    public String getSymbolToWriteBack() {
        return symbolToWriteBack;
    }

    public void setSymbolToWriteBack(String symbolToWriteBack) {
        this.symbolToWriteBack = symbolToWriteBack;
    }

    public HeadDirection getDirection() {
        return direction;
    }

    public void setDirection(HeadDirection direction) {
        this.direction = direction;
    }
}
