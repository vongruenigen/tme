package org.zhaw.tme;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.StringBuffer;
import java.lang.Thread;

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
      // If the step mode is activated, we also activate verbose as default
      if (stepMode) { verbose = true; }
      if (verbose)  { this.outputDescription(); }

      if (!this.tape.setContent(word)) {
        return;
      }

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

            if (verbose && !stepMode) {
              try { Thread.sleep(50); } catch (Exception ex) {}
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
      this.outputTapeDescription();
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

        states += "|-- (" + name + ")";

        if (this.acceptingStates.contains(s)) {
          states += " [accepting]";
        } else if (s == this.startState) {
          states += " [start]";
        }

        states += "\n|\n";

        if (s.getTransitions().size() > 0) {
          for (Transition t : s.getTransitions()) {
            String direction = t.getDirection().toString();

            // For aligned printing of the directions
            if (direction.equals("LEFT") || direction.equals("NONE")) {
              direction += " ";
            }

            states += String.format("|---- %s/%s, %s => %s\n", t.getSymbolUnderCursor(),
                                                               t.getSymbolToWriteBack(),
                                                               direction,
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

    private void outputTapeDescription() {
      ArrayList<String> tapeContent = this.tape.getContent();
      int tapeContentSize = tapeContent.size();
      StringBuffer tapeContentInWords = new StringBuffer();

      String currentSymbol = null;
      int currentSymbolCount = 0;

      for (int i = 0; i < tapeContentSize; i++) {
        String symbol = tapeContent.get(i);

        boolean isBlankSymbol = symbol.equals(this.blankSymbol);
        boolean isCurrentSymbol = symbol.equals(currentSymbol);
        boolean isLastIndex = i == tapeContentSize - 1;

        if (!isCurrentSymbol || isLastIndex) {
          if (isCurrentSymbol) currentSymbolCount++;

          if (currentSymbolCount > 0) {
            String symbolText = String.format("%d times '%s'", currentSymbolCount, currentSymbol);

            if (tapeContentInWords.length() > 0) {
              tapeContentInWords.append(", ");
            }

            tapeContentInWords.append(symbolText);

            currentSymbol = null;
            currentSymbolCount = 0;
          }

          if (!isBlankSymbol) {
            currentSymbol = symbol;
            currentSymbolCount = 1;
          }
        } else {
          currentSymbolCount++;
        }
      }

      System.out.println("\nIn words (without blank symbols): " + tapeContentInWords.toString());
      System.out.println();
    }

    private void askNextStep() {
      Scanner scanner = new Scanner(System.in);
      System.out.print("(press enter to run the next step)");
      String line = scanner.nextLine();
      System.out.println();
    }
}
