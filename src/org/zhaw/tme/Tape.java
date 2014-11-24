package org.zhaw.tme;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Tape {
    public final static int TAPE_SIZE = 31;
    public final static int HEAD_POSITION = 15;

    private int headPosition;
    private String blankSymbol;
    private Alphabet alphabet;
    private ArrayList<String> content;

    public Tape(Alphabet alphabet, String blankSymbol) {
        this.alphabet = alphabet;
        this.blankSymbol = blankSymbol;
        this.content = new ArrayList<String>();
        this.headPosition = 0;
    }

    public boolean setContent(String content) {
      if (!this.alphabet.isCompatible(content)) {
        System.out.println("ERROR: Word '" + content + "' is not compatible with the tape alphabet!");
        return false;
      }

      this.content = new ArrayList<String>(Arrays.asList(content.split("(?!^)")));

      return true;
    }

    public ArrayList<String> getContent() {
      return this.content;
    }

    public void write(String symbol) {
      if (this.headPosition < 0) {
        int blanksToAdd = Math.abs(this.headPosition);

        for (int i = 0; i < blanksToAdd; i++) {
          this.content.add(0, this.blankSymbol);
        }

        this.headPosition = 0;
      }

      if (this.headPosition > this.content.size() - 1) {
        int blanksToAdd = this.headPosition - (this.content.size() - 1);

        for (int i = 0; i < blanksToAdd; i++) {
          this.content.add(this.blankSymbol);
        }
      }

      this.content.set(this.headPosition, symbol);
    }

    public String getCurrentSymbol() {
      if (this.headPosition < 0 || this.headPosition > this.content.size() - 1) {
        return this.blankSymbol;
      } else {
        return this.content.get(this.headPosition);
      }
    }

    public void output() {
      System.out.print("      |");

      for (int i = 0; i < TAPE_SIZE; i++) {
        String symbol = "  ";

        if (i == HEAD_POSITION) { symbol = "V "; }
        if (i == TAPE_SIZE - 1) { symbol = " ";  }

        System.out.print(symbol);
      }

      System.out.print("|\n");
      System.out.print("Tape: |");

      int startContentAt = ((TAPE_SIZE - 1) / 2) - this.headPosition;

      for (int i = 0; i < TAPE_SIZE; i++) {
        String symbol = null;
        boolean printContent = i >= startContentAt && i - startContentAt < this.content.size();

        if (printContent) {
          symbol = this.content.get(i - startContentAt);
        } else {
          symbol = this.blankSymbol;
        }

        System.out.format("%s|", symbol);
      }

      System.out.println();
    }

    public void moveHead(HeadDirection direction) {
      switch (direction) {
        case LEFT:  this.headPosition--; break;
        case RIGHT: this.headPosition++; break;
      }
    }
}
