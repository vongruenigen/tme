package org.zhaw.tme;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Machine {
    private String specification;
    private Tape tape;
    private ArrayList<State> states;
    private Alphabet inputAlphabet;
    private Alphabet tapeAlphabet;
    private State startState;
    private String blankSymbol;
    private ArrayList<State> acceptingStates;

    public Machine(ArrayList<State> states, Alphabet inputAlphabet, Alphabet tapeAlphabet,
                   State startState, String blankSymbol, ArrayList<State> acceptingStates) {
      this.states = states;
      this.inputAlphabet = inputAlphabet;
      this.tapeAlphabet = tapeAlphabet;
      this.startState = startState;
      this.blankSymbol = blankSymbol;
      this.acceptingStates = acceptingStates;

      this.initialize();
    }

    public void run(String word, boolean verbose, boolean stepMode) {
      if (verbose) {
        this.outputDescription();
      }

      this.tape.setContent(word);

      System.out.println("Running calculation on word '" + word + "'.\n");

      int stepCount = 0;
      State currentState = startState;
      System.out.format("Start configuration:\n", stepCount, currentState.getName());
      System.out.println();
      this.tape.output();
      System.out.println();

      if (stepMode) { this.askNextStep(); }

      while (!this.acceptingStates.contains(currentState)) {
        ArrayList<Transition> possibleTransitions = currentState.getTransitions();
        boolean found = false;

        for (Transition trans : possibleTransitions) {
          if (trans.getSymbolUnderCursor().equals(tape.getCurrentSymbol())) {
            found = true;

            this.tape.write(trans.getSymbolToWriteBack());
            this.tape.moveHead(trans.getDirection());

            stepCount++;
            currentState = trans.getToState();

            if (verbose) {
              System.out.format("Step #%d (state %s)\n", stepCount, currentState.getName());
              System.out.println();
              this.tape.output();
              System.out.println();
            }

            if (stepMode && !this.acceptingStates.contains(currentState)) {
              this.askNextStep();
            }

            break;
          }
        }

        if (!found) {
          System.out.println("STOP: No further transition possible, halting!");
          break;
        }
      }

      System.out.format("TM halted at state %s after %d steps with the following content on the tape:\n\n", currentState.getName(), stepCount);
      this.tape.output();

      ArrayList<String> tapeContent = this.tape.getContent();
      String tapeContentInWords = "";
      String currentSymbol = null;
      int currentSymbolCount = 0;

      for (String symbol : tapeContent) {
        if (!symbol.equals(this.blankSymbol)) {
          if (currentSymbol != null && symbol.equals(currentSymbol)) {
            currentSymbolCount++;
          } else {
            if (currentSymbol != null && currentSymbolCount > 0) {
              tapeContentInWords += String.format("%d times '%s', ", currentSymbolCount, currentSymbol);
            }

            // TODO: Does not work!

            currentSymbol = symbol;
            currentSymbolCount = 1;
          }
        }
      }

      System.out.println("\nIn words: " + tapeContentInWords);
      System.out.println();
    }

    private void initialize() {
      this.tape = new Tape(tapeAlphabet, blankSymbol);
    }

    private void outputDescription() {
      System.out.println("Starting to emulate the following TM");
      System.out.println("------------------------------------\n");

      String states = "";

      for (State s : this.states) {
        String name = s.getName();

        if (this.acceptingStates.contains(s)) {
          name += " (accepting)";
        } else if (s == this.startState) {
          name += " (start)";
        }

        states += "|-- " + name + "\n";

        states += "|\n";

        if (s.getTransitions().size() > 0) {
          for (Transition t : s.getTransitions()) {
            states += String.format("|---- %s/%s, %s => %s\n", t.getSymbolUnderCursor(),
                                                               t.getSymbolToWriteBack(),
                                                               t.getDirection(),
                                                               t.getToState().getName());
          }
        } else {
          states += "|---- (no transitions)";
        }

        states += "\n";
      }

      System.out.println("Input alphabet: " + this.inputAlphabet.getDescription());
      System.out.println("Tape alphabet:  " + this.tapeAlphabet.getDescription());
      System.out.println("Blank symbol:   '" + this.blankSymbol + "'\n");
      System.out.println("States and transitions:\n\n" + states);
      System.out.println("------------------------------------\n");
    }

    private void askNextStep() {
      Scanner scanner = new Scanner(System.in);
      System.out.print("(press enter to run the next step)");
      String line = scanner.nextLine();
      System.out.println();
    }
}
